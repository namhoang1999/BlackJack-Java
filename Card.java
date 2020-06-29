/**
 * Class Card contains methods and constructor.
 * @author Duong La
 *
 */
public class Card {
	private Suit suit_;
	private Rank rank_;
	private int points_;
	private boolean up_;
    /**
     * method Card is a constructor
     * @param s : Suit of card
     * @param r : Rank of card
     * @param up : true = up , false = down
     */
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
	/**
	 * this method gets value of a card
	 * @return points
	 */
	public int getPoints() {
		return this.points_;
	}
	/**
	 * this method gets rank of a card
	 * @return rank of card
	 */
	public Rank getRank() {
		return this.rank_;
	}
	/**
	 * this method prints Suit and Rank of a card in output
	 */
	public String toString() {
		return this.suit_.name() + " " + this.rank_.name();
	}
	/**
	 * this method show if the card is up or down
	 * @return state of a card
	 */
	public boolean isUp() {
		return this.up_;
	}
	/**
	 * this method sets face of a card.
	 */
	public void setFace(boolean up) {
		this.up_ = up;
	}
	
}
