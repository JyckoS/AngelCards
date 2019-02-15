package com.gmail.jyckosianjaya.angelcards.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.jyckosianjaya.angelcards.AngelCards;
import com.gmail.jyckosianjaya.angelcards.data.Cards;
import com.gmail.jyckosianjaya.angelcards.utility.Utility;

public class DataStorage {
	private AngelCards m;
	private int defaultcard;
	private HashMap<Messages, List<String>> msg = new HashMap<Messages, List<String>>();
	public DataStorage(AngelCards m) {
		this.m = m;
		reloadConfig();
	}
	public void reloadConfig() {
		m.reloadConfig();
		FileConfiguration config = m.getConfig();
		{
		this.defaultcard = config.getInt("default_cards");
		ConfigurationSection msgs = config.getConfigurationSection("messages");
		this.msg.clear();
		for (String str : msgs.getKeys(false)) {
			String strz = str.toUpperCase();
			Messages msg = null;
			try {
				msg = Messages.valueOf(strz);
			} catch (Exception e) {
				Utility.sendConsole("&aAC > &7Can't load the message " + strz + " (Missing Enum)");
			}
			this.msg.put(msg, Utility.TransColor(msgs.getStringList(str)));

		}
	}
		{
		ConfigurationSection saves = config.getConfigurationSection("saved_stats");
		this.experience_saved = saves.getBoolean("experience");
		this.inventory_saved = saves.getBoolean("inventory");
		this.level_saved = saves.getBoolean("level");
		}
	}
	public final boolean isExperienceSaved() {
		return this.experience_saved;
	}
	public final boolean isInventorySaved() {
		return this.inventory_saved;
	}
	public final boolean isLevelSaved() {
		return this.level_saved;
	}
	public int getDefaultCards() {
		return this.defaultcard;
	}
 	public void sendMsg(Player p, Messages msg, String arg1, String arg2, String arg3) {
 		Cards cards = this.m.getCardStorage().getCards(p.getUniqueId());
 		int amount = 0;
 		if (cards != null) {
 			amount = cards.getAmount();
 		}
 		for (String str : this.msg.get(msg)) {
			str = str.replaceAll("@a", arg1);
			str = str.replaceAll("@b", arg2);
			str = str.replaceAll("@c", arg3);
			str = str.replaceAll("%p", p.getName());
			str = str.replaceAll("%c", amount + "");
			Utility.sendMsg(p, str);

		}
	}
	public void sendMsg(Player p, Messages msg, String arg1, String arg2) {
 		Cards cards = this.m.getCardStorage().getCards(p.getUniqueId());
 		int amount = 0;
 		if (cards != null) {
 			amount = cards.getAmount();
 		}
		for (String str : this.msg.get(msg)) {
			str = str.replaceAll("@a", arg1);
			str = str.replaceAll("@b", arg2);
			str = str.replaceAll("%p", p.getName());
			str = str.replaceAll("%c", amount + "");
			Utility.sendMsg(p, str);

		}
	}
	public void sendMsg(Player p, Messages msg, String arg1) {
 		Cards cards = this.m.getCardStorage().getCards(p.getUniqueId());
 		int amount = 0;
 		if (cards != null) {
 			amount = cards.getAmount();
 		}
		for (String str : this.msg.get(msg)) {
			str = str.replaceAll("@a", arg1);
			str = str.replaceAll("@b", p.getName());
			str = str.replaceAll("%c", amount + "");
			Utility.sendMsg(p, str);

		}
	}
	public void sendMsg(Player p, Messages msg) {
 		Cards cards = this.m.getCardStorage().getCards(p.getUniqueId());
 		int amount = 0;
 		if (cards != null) {
 			amount = cards.getAmount();
 		}
		for (String str : this.msg.get(msg)) {
			str = str.replaceAll("%p", p.getName());
			str = str.replaceAll("%c", amount + "");
			Utility.sendMsg(p, str);
		}
	}
	private boolean inventory_saved = false;
	private boolean level_saved = false;
	private boolean experience_saved = false;
	public enum Messages {
		ANGEL_CARD_USED,
		CHECK_CARDS,
		CARDS_RECEIVED,
		CARDS_GIVEN,
		CHECK_CARDS_OTHERS,
		COMMANDS_HELP,
		NO_PERMISSION;
	}
}
