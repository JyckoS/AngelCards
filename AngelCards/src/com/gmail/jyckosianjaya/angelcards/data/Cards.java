package com.gmail.jyckosianjaya.angelcards.data;

import java.util.UUID;

public class Cards {
	private UUID owner;
	private int amount;
	public Cards(UUID owner, int amount) {
		this.owner = owner;
		this.amount = amount;
	}
	public UUID getOwner() {
		return this.owner;
	}
	public int getAmount() {
		return this.amount;
	}
	public void setAmount(int a) {
		this.amount = a;
	}
}
