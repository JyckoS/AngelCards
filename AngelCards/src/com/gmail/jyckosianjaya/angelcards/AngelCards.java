package com.gmail.jyckosianjaya.angelcards;

import java.io.File;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jyckosianjaya.angelcards.commands.AngelCmd;
import com.gmail.jyckosianjaya.angelcards.events.AngelEventManagerListener;
import com.gmail.jyckosianjaya.angelcards.storage.CardManager;
import com.gmail.jyckosianjaya.angelcards.storage.CardStorage;
import com.gmail.jyckosianjaya.angelcards.storage.DataStorage;
import com.gmail.jyckosianjaya.angelcards.utility.Utility;

public class AngelCards extends JavaPlugin {
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		new Metrics(this);
		instance = this;
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		File datafolder = new File(this.getDataFolder(), "data");
		if (datafolder.exists()) {
			datafolder.mkdirs();
		}
		this.getCommand("angelcard").setExecutor(new AngelCmd());

		Utility.sendConsole(prefix + "Registered Commands");
		this.getServer().getPluginManager().registerEvents(new AngelEventManagerListener(this), this);
		Utility.sendConsole(prefix + "Registered Events");
		this.cardstorage = new CardStorage(this);
		Utility.sendConsole(prefix + "Loaded Card Datas");
		this.datastorage = new DataStorage(this);
		Utility.sendConsole(prefix + "Loaded Config Data");
		this.papienabled = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
		if (papienabled) {
			Utility.sendConsole(prefix +"PAPI found! Add placeholders with /papi ecloud download AngelCards");

		}
	}
	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			this.cardstorage.getManager().saveData(p.getUniqueId());
		}
	}
	public CardStorage getCardStorage() {
		return this.cardstorage;
	}
	public DataStorage getDataStorage() {
		return this.datastorage;
	}
	private CardStorage cardstorage;
	private static AngelCards instance;
	private DataStorage datastorage;
	private Boolean papienabled = false;
	public Boolean isPAPIenabled() {
		return this.papienabled;
	}
	public static AngelCards getInstance() {
		return instance;
	}
	private String prefix = "&e&lAngelCards &7> &r";
}
