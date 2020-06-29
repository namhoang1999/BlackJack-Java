package BlackJack;

import BlackJack.Deck.Rank;
import BlackJack.Deck.Suit;

public class Card {
	private Suit suit_;
	private Rank rank_;
	private int points_, points1_;
	private boolean up_;
	
	public Card(Suit s, Rank r, boolean up) {
		this.up_ = up;
		this.suit_ = s;
		this.rank_ = r;
		switch(r) {
			case ACE : this.points_ = 1; points1_ = 11; break;
			case TWO : this.points_ = 2; break;
			case THREE : this.points_ = 3; break;
			case FOUR : this.points_ = 4; break;
			case FIVE : this.points_ = 5; break;
			case SIX : this.points_ = 6; break;
			case SEVEN : this.points_ = 7; break;
			case EIGHT : this.points_ = 8; break;
			case NINE : this.points_ = 9; break;
			case TEN : this.points_ = 10; break;
			case J : this.points_ = 10; break;
			case Q : this.points_ = 10; break;
			case K : this.points_ = 10; break;
		}
	}
	public int getPoints() {
		return this.points_;
	}
	public int getPoints1() {
		return this.points1_;
	}
	public Rank getRank() {
		return this.rank_;
	}
	public String toString() {
		return this.suit_.name() + " " + this.rank_.name();
	}
	public boolean isUp() {
		return this.up_;
	}
	public void setUp(boolean u) {
		this.up_ = u;
	}
}
