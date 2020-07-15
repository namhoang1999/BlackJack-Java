package src.main.java.de.uni_hannover.hci.BlackJack.SerializableData;

import java.io.Serializable;

public class Dealer extends Players implements Serializable{
	private Deck deck_;
	public Dealer(Deck d) {
		super("DEALER", 100000000);
		this.deck_ = d;
	}
	public void deal(Players p, boolean up) {
		p.draw(this.deck_.dealCard(), up);
	}
	public Deck getDeck() {
		return this.deck_;
	}
	public void assignDeck(Deck d) {
		this.deck_ = d;
	}
	public void handUp() {
		for(Card c: this.hand_) {
			c.setUp(true);
		}
	}
}
