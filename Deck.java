
import java.util.ArrayList;
import java.util.Collections;

/**
 * this class contains constructor and methods to create a deck with 52 cards.
 * @author Duong La
 *
 */
public class Deck {
	
	private ArrayList<Card> deck_= new ArrayList<Card>();
	/**
	 * this is a constructor.
	 */
	public Deck() {
		for(Rank r: Rank.values()) {
			for(Suit s: Suit.values()) {
				deck_.add(new Card(s, r, false));
			}
		}
		shuffle();
	}
	/**
	 * this method shuffles the deck.
	 */
	public void shuffle() {
		Collections.shuffle(deck_);
	}
	/**
	 * this method prints 52 cards in a deck.
	 */
	public String toString() {
		String ans = "";
		for (Card c: deck_) {
			ans += c.toString() + "\n";
		}
		return ans;
	}
	/**
	 * this method deals cards to Players.
	 * @return the top card of deck.
	 */
	public Card dealCard() {
		Card c = this.deck_.get(this.deck_.size()-1);
		this.deck_.remove(this.deck_.size()-1);
		return c;
	}
	/**
	 * this method gets size of deck.
	 * @return the size of the deck.
	 */
	public int size() {
		return this.deck_.size();
	}
}
