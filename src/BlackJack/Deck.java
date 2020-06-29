package BlackJack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	enum Rank{
		ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, J, Q, K
	}
	enum Suit{
		DIAMOND, HEART, SPADE, CLUB
	}
	private ArrayList<Card> deck_= new ArrayList<Card>();
	
	public Deck() {
		for(Rank r: Rank.values()) {
			for(Suit s: Suit.values()) {
				deck_.add(new Card(s, r, false));
			}
		}
		shuffle();
	}
	
	/**
	 * Shuffle the deck
	 */
	public void shuffle() {
		Collections.shuffle(deck_);
	}
	
	/**
	 * Return the Deck in String
	 */
	public String toString() {
		String ans = "";
		for (Card c: deck_) {
			ans += c.toString() + "\n";
		}
		return ans;
	}
	
	/**
	 * Deal the top card 
	 * @return top card
	 */
	public Card dealCard() {
		Card c = this.deck_.get(this.deck_.size()-1);
		this.deck_.remove(c);
		return c;
	}
	
	/**
	 * Return deck size
	 * @return deck size
	 */
	public int size() {
		return this.deck_.size();
	}
}
