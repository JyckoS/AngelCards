package com.gmail.jyckosianjaya.angelcards.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jyckosianjaya.angelcards.AngelCards;
import com.gmail.jyckosianjaya.angelcards.data.Cards;
import com.gmail.jyckosianjaya.angelcards.storage.DataStorage.Messages;
import com.gmail.jyckosianjaya.angelcards.utility.Utility;

import me.clip.placeholderapi.PlaceholderAPI;

public class AngelCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		if (arg0 instanceof Player) {
			redo((Player) arg0, arg3);
			return true;
		}
		redo(arg0, arg3);
		return true;
	}
	private void redo(CommandSender send, String[] args) {
		Player sender = null;
		if (send instanceof Player) {
			sender = (Player) send;
			
		}
		boolean isPlayer = sender != null;
		if (args.length == 0) {
			if (isPlayer)
			AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.COMMANDS_HELP);
			if (send.hasPermission("angelcards.admin")) {
				Utility.sendMsg(send, "&cAs an admin, you can do:");
				Utility.sendMsg(send, "&6&l> &c/angelcards &fadd/take/set <Player> <Amount>");
				Utility.sendMsg(send, "&6&l> &c/angelcards &freload");
				Utility.sendMsg(send, "&6&l> &c/angelcards &fgiveitem <Player> <Amount>");
				Utility.sendMsg(send, "&6&l> &c/angelcards &fgiveitemall <Amount>");
				Utility.sendMsg(send, "&6&l> &c/angelcards &fgiveall <Amount>");
			}
			return;
		}
		switch (args[0].toLowerCase()) {
		case "withdraw":
		{
			if (!isPlayer) return;
			if (!send.hasPermission("angelcards.withdraw")) {
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);
				return;
			}
			if (args.length < 2) {
				Utility.sendMsg(send, "&e&lAngelCards: &7Use /angelcards withdraw <Amount>");
				return;
			}

			Integer amount = 1;
			Player p = (Player) sender;
			try { 
				amount = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(sender, "&cThat's not an integer.");
				return;
			}
			if (amount <= 0) {
				Utility.sendMsg(sender, "&cThat's not an integer.");
				return;
			}
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(p.getUniqueId());
			if (cards == null) {
				Utility.sendMsg(sender, "&cNot enough cards.");
				return;
			}
			else {
				cards.setAmount(cards.getAmount() - amount);
			}
			AngelCards.getInstance().getDataStorage().sendMsg(p, Messages.CARD_WITHDRAWN, amount.toString());

			p.getInventory().addItem(AngelCards.getInstance().getDataStorage().getAngelCardItem(amount));
			return;
		}
		case "giveitemall":
		{
			if (!send.hasPermission("angelcards.admin")) {
				if (isPlayer)
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);
				return;
			}
			if (args.length < 2) {
				Utility.sendMsg(send, "&e&lAngelCards: &7Use /angelcards giveitemall <Amount>");
				return;
			}
			Integer amount = 0;
			try { 
				amount = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(send, "&cThat's not an integer.");
			}
			if (amount <= 0) {
				Utility.sendMsg(send, "&cThat's not an integer.");
				return;
			}
				for (Player tar : Bukkit.getOnlinePlayers()) {
					tar.getInventory().addItem(AngelCards.getInstance().getDataStorage().getAngelCardItem(amount));
					AngelCards.getInstance().getDataStorage().sendMsg(tar, Messages.CARDITEM_RECEIVED, amount.toString());
				}
				return;
		}
		case "giveitem":
		{
			if (!send.hasPermission("angelcards.admin")) {
				if (isPlayer)
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);
				return;
			}
			if (args.length < 3) {
				Utility.sendMsg(send, "&e&lAngelCards: &7Use /angelcards giveitem <Player> <Amount>");
				return;
			}
			boolean all = false;
			if (args[1].equals("*") || args[1].equals("all")) {
				all = true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			Integer amount = 0;
			try { 
				amount = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(send, "&cThat's not an integer.");
			}
			if (amount <= 0) {
				Utility.sendMsg(send, "&cThat's not an integer.");
				return;
			}
			if (p == null || !p.isOnline()) {
				if (!all) {
				Utility.sendMsg(send, "&7Player is offline.");
				return;
				}
			}
			if (all) {
				for (Player tar : Bukkit.getOnlinePlayers()) {
					tar.getInventory().addItem(AngelCards.getInstance().getDataStorage().getAngelCardItem(amount));
					AngelCards.getInstance().getDataStorage().sendMsg(tar, Messages.CARDITEM_RECEIVED, amount.toString());
				}
				return;
			}
			p.getInventory().addItem(AngelCards.getInstance().getDataStorage().getAngelCardItem(amount));
			AngelCards.getInstance().getDataStorage().sendMsg(p, Messages.CARDITEM_RECEIVED, amount.toString());
			return;
		}
		case "toggle":
		{
			if (!isPlayer) return;
			if (!send.hasPermission("angelcards.toggle")) {
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);
				return;
			}
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(sender.getUniqueId());
			if (cards == null) {
				cards = AngelCards.getInstance().getCardStorage().setCards(sender.getUniqueId(), 0);
			}
			if (cards.isEnabled()) {
				cards.setEnabled(false);
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.TOGGLED_OFF);
				return;
			}
			else {
				cards.setEnabled(true);
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.TOGGLED_ON);
				return;
			}
		}
			case "giveall":
		{
			if (!send.hasPermission("angelcards.admin")) {
				if (isPlayer)
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);
				return;
			}
			if (args.length < 2) {
				Utility.sendMsg(send, "&cPlease put a number!");
				return;
			}
			int amount = 0;
			try {
				amount = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(send, "&cPlease use a &lNUMBER!");
				return;
			}
			
			for (Player p : Bukkit.getOnlinePlayers()) {
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(p.getUniqueId());
			if (cards == null) {
				cards = AngelCards.getInstance().getCardStorage().setCards(p.getUniqueId(), amount);
			}
			else {
				cards.setAmount(cards.getAmount() + amount);
			}
			if (cards.getAmount() < 0) {
				cards.setAmount(amount);
			}
			AngelCards.getInstance().getDataStorage().sendMsg(p, Messages.CARDS_RECEIVED, amount + "");
			}
			Utility.sendMsg(send, "&7Given everyone &f" + amount + "x &7cards.");
			return;
		}
		case "reload":
		{
			if (!send.hasPermission("angelcards.admin")) {
				if (isPlayer)
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);
				return;
			}
			AngelCards.getInstance().getDataStorage().reloadConfig();
			Utility.sendMsg(send, ">> &cConfig reloaded!");
			return;
		}
		case "add":
		{
			if (!send.hasPermission("angelcards.admin")) {
				if (isPlayer)
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);

				return;
			}
			if (args.length < 3) {
				Utility.sendMsg(send, "&7- &a/angelcards add <Player> <Amount>");
				return;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Utility.sendMsg(send, "&cPlayer doesn't exist!");
				return;
			}
			int amount = 0;
			try {
				amount = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(send, "&cThat's not a number.");
				return;
			}
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(p.getUniqueId());
			if (cards == null) {
				cards = AngelCards.getInstance().getCardStorage().setCards(p.getUniqueId(), amount);
			}
			else {
				cards.setAmount(cards.getAmount() + amount);
			}
			if (cards.getAmount() < 0) {
				cards.setAmount(0);
			}
			Utility.sendMsg(send, "&cGiven &f" + p.getName() + " &c" + amount + " of cards.");
			AngelCards.getInstance().getDataStorage().sendMsg(p, Messages.CARDS_RECEIVED, amount + "");
			return;
		}
		
		case "take":
		case "remove":
		{
			if (!send.hasPermission("angelcards.admin")) {
				if (isPlayer)
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);

				return;
			}
			if (args.length < 3) {
				Utility.sendMsg(send, "&7- &a/angelcards take <Player> <Amount>");
				return;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Utility.sendMsg(send, "&cPlayer doesn't exist!");
				return;
			}
			int amount = 0;
			try {
				amount = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(send, "&cThat's not a number.");
				return;
			}
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(p.getUniqueId());
			if (cards != null) {
				cards.setAmount(cards.getAmount() - amount);
			}
			else {
				cards = AngelCards.getInstance().getCardStorage().setCards(p.getUniqueId(), 0);
			}
			if (cards.getAmount() < 0) {
				cards.setAmount(0);
			}
			Utility.sendMsg(send, "&cTaken &f" + p.getName() + "'s &c" + amount + " of cards.");
			return;
		}
		case "set":
		case "put":
		{
			if (!send.hasPermission("angelcards.admin")) {
				if (isPlayer)
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);

				return;
			}
			if (args.length < 3) {
				Utility.sendMsg(send, "&7- &a/angelcards set <Player> <Amount>");
				return;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Utility.sendMsg(send, "&cPlayer doesn't exist!");
				return;
			}
			int amount = 0;
			try {
				amount = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(send, "&cThat's not a number.");
				return;
			}
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(p.getUniqueId());
			if (cards == null) {
				cards = AngelCards.getInstance().getCardStorage().setCards(p.getUniqueId(), amount);
			}
			else {
				cards.setAmount(amount);
			}
			if (cards.getAmount() < 0) {
				cards.setAmount(0);
			}
			Utility.sendMsg(send, "&c Changed &f" + p.getName() + "'s &ccards to &c" + amount + " of cards.");
			return;
		}
		case "check":
		default:
		{
			if (!send.hasPermission("angelcards.check")) {
				if (isPlayer)
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);
				return;
			}
			if (args.length < 2) {
				if (isPlayer)
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.CHECK_CARDS);
				return;
			}
			if (!send.hasPermission("angelcards.check.others")) {
				return;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Utility.sendMsg(send, "&cThat player doesn't exist.");
				return;
			}
			int amount = 0;
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(p.getUniqueId());
			if (cards != null) {
				amount = cards.getAmount();
			}
			if (isPlayer) {
			AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.CHECK_CARDS_OTHERS, p.getName(), amount + "");
			}
			else {
				Utility.sendMsg(send, p.getName() + "&7 has &f" + amount + " &7angelcards.");
			}
			return;
		}
		case "testplaceholder":
		case "testpapi":
			if (!send.hasPermission("angelcards.admin")) {
				return;
			}
			if (args.length < 2) {
				Utility.sendMsg(sender, "&7- &a/angelcards testpapi <Message>");
				return;
			}
			String msg = "";
			for (int i = 1; i < args.length; i++) {
				msg = msg + args[i] + " ";
			}
			if (!AngelCards.getInstance().isPAPIenabled()) return;
			msg = PlaceholderAPI.setPlaceholders(sender, msg);
			Utility.broadcast(msg);
			return;
		case "give":
		case "pay":
		{
			if (!isPlayer) return;
			if (!sender.hasPermission("angelcards.pay")) {
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);

				return;
			}
			if (args.length < 3) {
				Utility.sendMsg(sender, "&7- &a/angelcards give <Player> <Amount>");
				return;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Utility.sendMsg(sender, "&cPlayer doesn't exist!");
				return;
			}
			int amount = 0;
			try {
				amount = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(sender, "&cThat's not a number.");
				return;
			}
			if (amount <= 0) {
				Utility.sendMsg(sender, "&cOops, is that a number?");
				return;
			}
			Cards ownercards = AngelCards.getInstance().getCardStorage().getCards(sender.getUniqueId());
			if (ownercards == null) {
				Utility.sendMsg(sender, "&cYou don't have the cards!");
				return;
			}
			else {
				if (ownercards.getAmount() < amount) {
					Utility.sendMsg(sender, "&cYou don't have the cards!");
					return;
				}
				ownercards.setAmount(ownercards.getAmount() - amount);
			}
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(p.getUniqueId());
			if (cards == null) {
				AngelCards.getInstance().getCardStorage().setCards(p.getUniqueId(), amount);
			}
			else {
				cards.setAmount(cards.getAmount() + amount);

			}
		AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.CARDS_GIVEN, amount + "", p.getName());
		AngelCards.getInstance().getDataStorage().sendMsg(p, Messages.CARDS_RECEIVED, amount + "");	
			return;
		}
		}
	}
	
}
