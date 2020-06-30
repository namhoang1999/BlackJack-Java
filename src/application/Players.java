package application;

import java.util.ArrayList;

import application.Deck.Rank;

public abstract class Players {
	protected String name_;
	protected int cash_;
	protected ArrayList<Card> hand_ = new ArrayList<Card>();

	public Players(String name, int cash) {
		this.name_ = name;
		this.cash_ = cash;
	}
	
	/**
	 * Player draw a card
	 * @param c card to draw
	 * @param u face up or down
	 */
	public void draw(Card c, boolean u) {
		c.setUp(u);
		this.hand_.add(c);
	}
	
	/**
	 * Return total points of cards on hand
	 * @return total points
	 */
	public int getTotalPoints() {
        int aces = 0, points = 0;
        if (this.hand_.size() > 0) {
	        for(Card c: this.hand_) {
	        	if (c.isUp()) {
		            if (c.getRank() == Rank.ACE) {
		            	aces++;
		            } else {
		            	points += c.getPoints();
		            }
	        	}
	        }
	        for (int i = 0; i < aces; i++) {
	            if ((11 + points) <= 21) {
	            	points += 11;
	            } else {
	            	points += 1;
	            }
	        }
        }
        return points;
    }
	
	/**
	 * Return the Player info in String
	 * @return
	 */
	public String toString() {
		String hand = "";
		if (this.hand_.size() > 0) {
			for (Card c: this.hand_) {
				if(c.isUp() == true) {
					hand += "| "+c.toString() + " ";
				}
				else {
					hand += "| -------- ";
				}
			}
		}
		return this.name_ + "(" + this.cash_ + "$), Points: " +getTotalPoints() + " " + hand;
	}
	
	/**
	 * Empty player's hand
	 */
	public void resetHand() {
		this.hand_.clear();
	}
	
	public int getCash() {
		return this.cash_;
	}
	
	public String getName() {
		return this.name_;
	}
	public void setCash(int c) {
		this.cash_ = c;
	}
	public ArrayList<Card> getHand() {
		return this.hand_;
	}
}
