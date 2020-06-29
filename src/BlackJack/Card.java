package BlackJack;

import BlackJack.Deck.Rank;
import BlackJack.Deck.Suit;

public class Card {
	private Suit suit_;
	private Rank rank_;
	private int points_;
	private boolean up_;
	
	public Card(Suit s, Rank r, boolean up) {
		this.up_ = up;
		this.suit_ = s;
		this.rank_ = r;
		switch(r) {
			case ACE : this.points_ = 1; break;
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
	
	public String generateFilePath() {
		String r = "", s = "";
		switch(this.rank_) {
			case ACE : r = "01"; break;
			case TWO : r = "02"; break;
			case THREE : r = "03"; break;
			case FOUR : r = "04"; break;
			case FIVE : r = "05"; break;
			case SIX : r = "06"; break;
			case SEVEN : r = "07"; break;
			case EIGHT : r = "08"; break;
			case NINE : r = "09"; break;
			case TEN : r = "10"; break;
			case J : r = "11"; break;
			case Q : r = "12"; break;
			case K : r = "13"; break;
		}
		
		switch(this.suit_) {
			case HEART : s = "h"; break;
			case CLUB : s = "c"; break;
			case SPADE : s = "s"; break;
			case DIAMOND : s = "d"; break;
		}
		return "Icon/" + r + s + ".gif";
	}
}
