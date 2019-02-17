package com.gmail.jyckosianjaya.angelcards.storage;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.jyckosianjaya.angelcards.AngelCards;
import com.gmail.jyckosianjaya.angelcards.data.Cards;
import com.gmail.jyckosianjaya.angelcards.utility.Utility;

public class CardManager {
	private AngelCards m;
	private CardStorage storage;
	private File datafolder = null;
	public CardManager(AngelCards m, CardStorage storage) {
		this.m = m;
		this.storage = storage;
		datafolder = new File(m.getDataFolder(), "data");
		if (!datafolder.exists()) {
			datafolder.mkdirs();
		}
	}
	public void loadData(UUID uuid) {
		File f = new File(this.datafolder, uuid.toString() + ".yml");
		if (!f.exists()) {
			if (m.getDataStorage().getDefaultCards() > 0) {
				this.storage.setCards(uuid, m.getDataStorage().getDefaultCards());
			}
			return;
		}
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		int amount = yml.getInt("cards");
		boolean enabled = yml.getBoolean("enabled");
		if (!yml.getKeys(false).contains("enabled")) {
			enabled = true;
		}
		Cards c = new Cards(uuid, amount);
		c.setEnabled(enabled);
		this.storage.setCards(uuid, c);
	}
	public void saveData(UUID uuid) {
		Cards cards = this.storage.getCards(uuid);
		if (cards == null) return;
		File f = new File(this.datafolder, uuid.toString() + ".yml");
		if (f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		yml.set("cards", cards.getAmount());
		yml.set("enabled", cards.isEnabled());
		try {
			yml.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.storage.removeData(uuid);
	}
}
