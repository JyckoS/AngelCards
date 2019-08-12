package com.gmail.jyckosianjaya.angelcards.customevents;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.gmail.jyckosianjaya.angelcards.data.Cards;

import lombok.Getter;

public class AngelCardDeathEvent extends Event implements Cancellable {
	private @Getter PlayerDeathEvent originalEvent;
	private @Getter Player player;
	
	private @Nullable @Getter Cards cards;
	private @Getter int cardsBefore;
	private @Getter int cardsAfter;
	public AngelCardDeathEvent(PlayerDeathEvent e, Cards c) {
		this.originalEvent = e;
		this.player = e.getEntity();
		this.cards = c;
		this.cardsBefore = 0;
		if (c != null) {
			cardsBefore = c.getAmount();
		}
		this.cardsAfter = cardsBefore - 1;
		if (cardsAfter < 0) cardsAfter = 0;
	}
	public void cancelDeath() {
		this.originalEvent.setCancelled(true);
	}
	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}
	private boolean cancelled = false;
	@Override
	public void setCancelled(boolean arg0) {
		// TODO Auto-generated method stub
		this.cancelled = arg0;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
	private final static HandlerList handlers = new HandlerList();

}
