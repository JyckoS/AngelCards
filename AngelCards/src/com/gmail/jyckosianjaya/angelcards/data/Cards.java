package com.gmail.jyckosianjaya.angelcards.data;

import java.util.UUID;

public class Cards {
	private UUID owner;
	private int amount;
	private boolean isenabled = true;
	public Cards(UUID owner, int amount) {
		this.owner = owner;
		this.amount = amount;
	}
	public UUID getOwner() {
		return this.owner;
	}
	public boolean isEnabled() {
		return this.isenabled;
	}
	public void setEnabled(boolean tf) {
		this.isenabled = tf;
	}
	public int getAmount() {
		return this.amount;
	}
	public void setAmount(int a) {
		this.amount = a;
	}
}
