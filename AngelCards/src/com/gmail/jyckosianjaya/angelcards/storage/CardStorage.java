package com.gmail.jyckosianjaya.angelcards.storage;

import java.util.HashMap;
import java.util.UUID;

import com.gmail.jyckosianjaya.angelcards.AngelCards;
import com.gmail.jyckosianjaya.angelcards.data.Cards;

public class CardStorage {
	private AngelCards m;
	private CardManager manager;
	private HashMap<UUID, Cards> angelcards = new HashMap<UUID, Cards>();
	public CardStorage(AngelCards m) {
		this.m = m;
		this.manager = new CardManager(m, this);
	}
	public CardManager getManager() {
		return this.manager;
	}
	public Cards getCards(UUID uuid) {
		return angelcards.get(uuid);
	}
	public boolean hasCards(UUID uuid) {
		return angelcards.containsKey(uuid);
	}
	public void setCards(UUID uuid, Cards cards) {
		this.angelcards.put(uuid, cards);
	}
	public Cards setCards(UUID uuid, Integer amount) {
		Cards cards = new Cards(uuid, amount);
		this.angelcards.put(uuid, cards);
		return cards;
	}
	public void removeData(UUID uuid) {
		this.angelcards.remove(uuid);
	}
}
