package BlackJack;

public class Player extends Players{

	private int bet_ ; // minimum = 100 eur;
	
	public Player(String name, int cash) {
		super(name, cash);
	}
	public void dbl() {
		super.cash_ -= bet_;
		this.bet_*= 2;
	}
	public void fold() {
		super.cash_ += bet_/2;
	}
	public void bet(int b) {
		this.bet_ = b;
		super.cash_ -= bet_;
	}
	public int getBet() {
		if(this.bet_ > this.cash_) this.cash_ = 0;
		return this.bet_;
	}
	public void addCash(int c) {
		super.cash_ += c;
	}
	
	public void calcReward(int result) {
		if (result == 0) {
			// TODO: loser lose all?
		} else if (result == 1) {
			this.cash_ += this.bet_*2;
		} else {
			this.cash_ += this.bet_;
		}
	}

	/**
	 * Win 1, Lose 0, Draw 2
	 * @param dealerPoints opponent points
	 * @return win, lose or draw
	 */
	public void isWinner(Dealer d) {
		int dealerPoints = d.getTotalPoints(), result;
		System.out.print(getName() + " " + getTotalPoints() + " " + d.getTotalPoints());
		if(dealerPoints > 21) {
			System.out.println("Win");
			result = 1;
		}
		else {
			if(getTotalPoints() > 21 || getTotalPoints() < dealerPoints) {
				System.out.println("Lose");
				result = 0;
			} else if(getTotalPoints() != dealerPoints) {
				System.out.println("Win");
				result = 1;
			} else {
				System.out.println("Draw");
				result = 2;
			}
		}
		calcReward(result);
	}
}
