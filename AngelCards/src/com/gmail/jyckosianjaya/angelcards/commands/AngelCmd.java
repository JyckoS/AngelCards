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
	private void redo(Player sender, String[] args) {
		if (args.length == 0) {
			AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.COMMANDS_HELP);
			return;
		}
		switch (args[0].toLowerCase()) {
		case "giveall":
		{
			if (!sender.hasPermission("angelcards.admin")) {
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);
				return;
			}
			if (args.length < 2) {
				Utility.sendMsg(sender, "&cPlease put a number!");
				return;
			}
			int amount = 0;
			try {
				amount = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(sender, "&cPlease use a &lNUMBER!");
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
			Utility.sendMsg(sender, "&7Given everyone &f" + amount + "x &7cards.");
			return;
		}
		case "reload":
		{
			if (!sender.hasPermission("angelcards.admin")) {
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);
				return;
			}
			AngelCards.getInstance().getDataStorage().reloadConfig();
			Utility.sendMsg(sender, ">> &cConfig reloaded!");
			return;
		}
		case "add":
		{
			if (!sender.hasPermission("angelcards.admin")) {
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);

				return;
			}
			if (args.length < 3) {
				Utility.sendMsg(sender, "&7- &a/angelcards add <Player> <Amount>");
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
			Utility.sendMsg(sender, "&cGiven &f" + p.getName() + " &c" + amount + " of cards.");
			AngelCards.getInstance().getDataStorage().sendMsg(p, Messages.CARDS_RECEIVED, amount + "");
			return;
		}
		
		case "take":
		case "remove":
		{
			if (!sender.hasPermission("angelcards.admin")) {
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);

				return;
			}
			if (args.length < 3) {
				Utility.sendMsg(sender, "&7- &a/angelcards take <Player> <Amount>");
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
			Utility.sendMsg(sender, "&cTaken &f" + p.getName() + "'s &c" + amount + " of cards.");
			return;
		}
		case "set":
		case "put":
		{
			if (!sender.hasPermission("angelcards.admin")) {
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);

				return;
			}
			if (args.length < 3) {
				Utility.sendMsg(sender, "&7- &a/angelcards set <Player> <Amount>");
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
			Utility.sendMsg(sender, "&cSetted &f" + p.getName() + "'s &ccards to &c" + amount + " of cards.");
			return;
		}
		case "check":
		default:
		{
			if (!sender.hasPermission("angelcards.check")) {
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.NO_PERMISSION);
				return;
			}
			if (args.length < 2) {
				AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.CHECK_CARDS);
				return;
			}
			if (!sender.hasPermission("angelcards.check.others")) {
				return;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Utility.sendMsg(sender, "&cThat player doesn't exist.");
				return;
			}
			int amount = 0;
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(p.getUniqueId());
			if (cards != null) {
				amount = cards.getAmount();
			}
			AngelCards.getInstance().getDataStorage().sendMsg(sender, Messages.CHECK_CARDS_OTHERS, p.getName(), amount + "");
			return;
		}
		case "testplaceholder":
		case "testpapi":
			if (!sender.hasPermission("angelcards.admin")) {
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
			if (amount < 0) {
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
	private void redo(CommandSender sender, String[] args) {
		if (!sender.hasPermission("AngelCards.console")) return;
		if (args.length == 0) {
			Utility.sendMsg(sender, "&7As an admin, you can do:");
			Utility.sendMsg(sender, "&7- &a/angelcards give <Player> <Amount>");
			Utility.sendMsg(sender, "&7- &a/angelcards check <Player>");
			Utility.sendMsg(sender, "&4- &c/angelcards &e<add/take/set>&c <Player> <Amount>");
			Utility.sendMsg(sender, "&4- &c/angelcards reload");
			return;
		}
		switch (args[0].toLowerCase()) {
		case "giveall":
		{
			if (args.length < 2) {
				Utility.sendMsg(sender, "&cPlease put a number!");
				return;
			}
			int amount = 0;
			try {
				amount = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(sender, "&cPlease use a &lNUMBER!");
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
			Utility.sendMsg(sender, "&7Given everyone &f" + amount + "x &7cards.");
			return;
		}
		case "reload":
		{
			if (!sender.hasPermission("angelcards.admin")) return;
			AngelCards.getInstance().getDataStorage().reloadConfig();
			Utility.sendMsg(sender, ">> &cConfig reloaded!");
			return;
		}
		case "add":
		{
			if (args.length < 3) {
				Utility.sendMsg(sender, "&7- &a/angelcards add <Player> <Amount>");
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
			Utility.sendMsg(sender, "&cGiven &f" + p.getName() + " &c" + amount + " of cards.");
			AngelCards.getInstance().getDataStorage().sendMsg(p, Messages.CARDS_RECEIVED, amount + "");	

			return;
		}
		
		case "take":
		case "remove":
		{
			if (args.length < 3) {
				Utility.sendMsg(sender, "&7- &a/angelcards take <Player> <Amount>");
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
			Utility.sendMsg(sender, "&cTaken &f" + p.getName() + "'s &c" + amount + " of cards.");
			return;
		}
		case "set":
		case "put":
		{
			if (args.length < 3) {
				Utility.sendMsg(sender, "&7- &a/angelcards set <Player> <Amount>");
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
			Utility.sendMsg(sender, "&cSetted &f" + p.getName() + "'s &ccards to &c" + amount + " of cards.");
			return;
		}
		case "check":
		default:
		{
			if (args.length < 2) {
				Utility.sendMsg(sender, "&7- &a/angelcards check <Player>");
				return;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Utility.sendMsg(sender, "&cThat player doesn't exist.");
				return;
			}
			int amount = 0;
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(p.getUniqueId());
			if (cards != null) {
				amount = cards.getAmount();
			}
			Utility.sendMsg(sender, "&cThe person &f" + p.getName() + " &chas &f" + amount + " &ccards.");
			return;
		}
		case "give":
		case "pay":
		{
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
			Cards cards = AngelCards.getInstance().getCardStorage().getCards(p.getUniqueId());
			if (cards == null) {
				AngelCards.getInstance().getCardStorage().setCards(p.getUniqueId(), amount);
			}
			else {
				cards.setAmount(cards.getAmount() + amount);

			}
			Utility.sendMsg(sender, "&cGiven &f" + p.getName() + " &c" + amount + " of cards.");
			AngelCards.getInstance().getDataStorage().sendMsg(p, Messages.CARDS_RECEIVED, amount + "");	

			return;
		}
		}
	}
	
}
