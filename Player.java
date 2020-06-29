
/**
 * this class contains constructor and methods to create a Player.
 * @author Duong La
 *
 */
public class Player extends Players{

	private int bet_ ;
	boolean f_ = false; // fold or not.
	/**
	 * this method is a constructor.
	 * @param name : player's name.
	 * @param cash : player's cash.
	 */
	public Player(String name, int cash) {
		super(name, cash);
	}
	/**
	 * this method is a function of Player.
	 * @return true if player folds.
	 */
	public boolean fold() {
		return this.f_ = true;
	}
	/**
	 * this method is a function of Player.
	 * @param b : bet of player.
	 */
	public void bet(int b) {
		this.bet_ = b;
		super.cash_ -= bet_;
	}
	/**
	 * this method gets the bet of Player.
	 * @return bet of Player.
	 */
	public int getBet() {
		if(this.bet_ > this.cash_) this.cash_ = 0;
		return this.bet_;
	}
	/**
	 * this method is a function of Player.
	 * @param c : the cash is going to be added to Player.
	 */
	public void addCash(int c) {
		super.cash_ += c;
	}
	
}
