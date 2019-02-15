package com.gmail.jyckosianjaya.angelcards.papi;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.gmail.jyckosianjaya.angelcards.AngelCards;
import com.gmail.jyckosianjaya.angelcards.data.Cards;

import me.clip.placeholderapi.external.EZPlaceholderHook;

public final class AngelPlaceholders extends EZPlaceholderHook {
	private AngelCards main;
	@SuppressWarnings("deprecation")
	public AngelPlaceholders(AngelCards m) {
		super(m, "angelcards");
		this.main = m;
		// TODO Auto-generated constructor stub
	}
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if (p == null) {
			return "";
		}
		switch (identifier.toLowerCase()) {
		// This should be "%angelcards_count%"
		case "count":
			int amount = 0;
			Cards cards = main.getCardStorage().getCards(p.getUniqueId());
			if (cards != null) {
				amount = cards.getAmount();
			}
			return amount + "";
		}
		return null;
		
	}
	
}
