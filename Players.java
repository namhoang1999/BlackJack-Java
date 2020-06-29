

import java.util.ArrayList;

/**
 * this class contains constructor and methods to create dealer and players.
 * @author Duong La
 *
 */
public abstract class Players {
	protected String name_ = "";
	protected int points_ = 0;
	protected int cash_ = 0;
	protected ArrayList<Card> hand_ = new ArrayList<Card>();
	/**
	 * this is a constructor.
	 * @param name
	 * @param cash
	 */
	public Players(String name, int cash) {
		this.name_ = name;
		this.cash_ = cash;
	}
	/**
	 * this method counts ace in player hand.
	 * @return number of aces in player hand.
	 */
	public int countOfAce(){
		int count = 0;
		for(int i = 0; i < this.hand_.size(); i++){
			if(this.hand_.get(i).getRank() == Rank.ACE) count++;
		}
		return count;
	}
	/**
	 * this method calculates points of player.	
	 * @return points of player.
	 */
	public int getTotalPoints() {	
		int points = 0;
		for(Card c: this.hand_) {
			if (c.getRank() != Rank.ACE) points += c.getPoints();
		}
		for(int i = 0; i < countOfAce(); i++){
			
			if(points < 10) {
				points +=11;
			}
			else points += 1;
			
			if(this.hand_.size() == 2) {
				if(this.hand_.get(0).getRank() == Rank.ACE && (this.hand_.get(1).getPoints() == 10)) {
					points = 21;			
				}
				if(this.hand_.get(1).getRank() == Rank.ACE && (this.hand_.get(0).getPoints() == 10)) {
					points = 21;
				}
			}
		}
		return points;
	}
	/**
	 * this method gets hand of players
	 * @return cards in player's hand.
	 */
	public String getHand() {
		String hand = "";
		for (Card c: this.hand_) {
			if(c.isUp() == true) {
				hand += "| "+c.toString() + " | ";
			}
			else {
				hand += "| -------- |";
			}
		}
		return hand;
	}
	/**
	 * this is a function of Players.
	 * @param c : Top Card.
	 * @param u : state of Card.
	 */
	public void draw(Card c, boolean u) {
		c.setFace(u);
		this.hand_.add(c);
	}
	/**
	 * this method resets player's hand after finishing 1 round.
	 */
	public void resetHand() {
		this.hand_.clear();
	}
	/**
	 * this method gets player's cash.
	 * @return player's cash.
	 */
	public int getCash() {
		return this.cash_;
	}
	/**
	 * this method gets player's name.
	 * @return player's name.
	 */
	public String getName() {
		return this.name_;
	}
	/**
	 * this method sets cash to player.
	 * @param c cash.
	 */
	public void setCash(int c) {
		this.cash_ = c;
	}
	/**
	 * this method counts cards in player's hand.
	 * @return number of cards in player's hand.
	 */
	public int countHand() {
		return this.hand_.size();
	}

}
