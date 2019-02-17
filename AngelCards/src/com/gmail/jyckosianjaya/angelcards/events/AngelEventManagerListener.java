package com.gmail.jyckosianjaya.angelcards.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.jyckosianjaya.angelcards.AngelCards;
import com.gmail.jyckosianjaya.angelcards.data.Cards;
import com.gmail.jyckosianjaya.angelcards.storage.DataStorage;
import com.gmail.jyckosianjaya.angelcards.storage.DataStorage.Messages;
import com.gmail.jyckosianjaya.angelcards.utility.Utility;
import com.gmail.jyckosianjaya.angelcards.utility.XSound;

public class AngelEventManagerListener implements Listener {
	public AngelEventManagerListener(AngelCards m) {
		this.m = m;
	}
	private AngelCards m;
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Cards cards = this.m.getCardStorage().getCards(e.getEntity().getUniqueId());
		if (cards == null) return;
		int amount = cards.getAmount();
		if (amount <= 0) return;
		if (!cards.isEnabled()) return;
		DataStorage data = this.m.getDataStorage();
		if (data.isExperienceSaved()) {
		e.setDroppedExp(0);
		}
		if (data.isInventorySaved()) {
		e.setKeepInventory(true);
		}
		if (data.isLevelSaved()) {
		e.setKeepLevel(true);
		}
		cards.setAmount(amount - 1);
		this.m.getDataStorage().sendMsg(e.getEntity(), Messages.ANGEL_CARD_USED);
		Utility.PlaySound(e.getEntity(), XSound.BLAZE_DEATH.bukkitSound(), 0.8F, 1.8F);
		Utility.PlaySound(e.getEntity(), XSound.BLAZE_DEATH.bukkitSound(), 0.1F, 0.1F);

		Utility.PlaySound(e.getEntity(), XSound.BLAZE_DEATH.bukkitSound(), 0.1F, 0F);
		Utility.PlaySound(e.getEntity(), XSound.ORB_PICKUP.bukkitSound(), 2F, 1.3F);

	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		this.m.getCardStorage().getManager().loadData(e.getPlayer().getUniqueId());
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		this.m.getCardStorage().getManager().saveData(e.getPlayer().getUniqueId());
	}
}
