
/**
 * this class contains a constructor and methods to create a Dealer.
 * @author Duong La
 *
 */
public class Dealer extends Players{
	private Deck deck_;
	/**
	 * this is a constructor.
	 * @param d : a new deck.
	 */
	public Dealer(Deck d) {
		super("DEALER", 100000000);
		this.deck_ = d;
	}
	/**
	 * This method is a function of Dealer.
	 * @param p : Player who receives card.
	 * @param up : state of card (up : true or down : false).
	 */
	public void deal(Players p, boolean up) {
		p.draw(this.deck_.dealCard(), up);
	}
	/**
	 * this method assigns deck
	 * @param d the playing deck.
	 */
	public void assignDeck(Deck d) {
		this.deck_ = d;
	}
	/**
	 * this method shows card in dealer's hand.
	 */
	public void handUp() {
		for(Card c: this.hand_) {
			c.setFace(true);
		}
	}
}
