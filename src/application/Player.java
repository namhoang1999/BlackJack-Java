package application;

public class Player extends Players{

	private int bet_ ;
	private boolean f_;
	
	public Player(String name, int cash) {
		super(name, cash);
	}

	public void fold() {
		calcReward(3);
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
	
	/**
	 * 0: Lose ; 
	 * 1: Win ( normal win without BlackJack ); 
	 * 2: Draw; 
	 * 3: Folded; 
	 * 4: 5 Cards and total < 21; 
	 * 5: 5 Cards and total = 21;
	 * 6: Dealer has 5 cards and dealerPoints < 21;
	 * 7: Dealer has less than 5 cards and dealerPoints = 21;
	 * @param result
	 */
	public void calcReward(int result) {
		if (result == 1) addCash(getBet()*2); 
		if (result == 2) addCash(getBet());
		if (result == 3) addCash(getBet()/2);
		if (result == 4) addCash(getBet()*3);
		if (result == 5) addCash(getBet()*5/2);
		if (result == 6) addCash(-getBet()*3/2);
		if (result == 7) addCash(-getBet());
	}

	public void isWinner(Dealer d) {
		int dealerPoints = d.getTotalPoints();
		int playerPoints = getTotalPoints();
		int playerCards = this.hand_.size();
		int dealerCards = d.hand_.size();
		int result = -1;
		if(getCash() >= 0)
			System.out.print(getName() + " " + getTotalPoints() + " " + dealerPoints + " Dealer ");
			if (f_ == true) {
				System.out.println(" --- " + getName() + " folded and lost half of his/her bet.");
				result = 3;
				f_ = false;
			}
			else if(playerCards == 2 && dealerCards == 2 && playerPoints == 21 && dealerCards == 21) {
				System.out.println("--- Draw");
				result = 2 ;
			}
			else if(playerCards > 2  && playerCards < 5 && dealerCards == 2 && playerPoints == 21 && dealerCards == 21) {
				System.out.println("--- Draw");
				result = 2 ;
			}
			else if(playerCards < 5 && playerPoints == 21) {
				System.out.println("--- Win");
				result = 4 ;
			}
			else if(playerCards == 5 && playerPoints <= 21){
				System.out.println("--- Win");
				result = 5;
			}
			else if(dealerPoints > 21) {
				System.out.println("--- Win");
				result = 1;
			}
			else if(playerCards > 2 && dealerCards == 2 && dealerPoints == 21) {
				System.out.println("--- Lose");
				result = 7 ;
			}
			else if(dealerCards < 5 && dealerPoints == 21) {
				System.out.println("--- Lose");
				result = 7 ;
			}
			else if(dealerCards == 5 && dealerPoints <= 21) {
				System.out.println("--- Lose");
				result = 6 ;
			}
			else {
				if(playerPoints > 21 || playerPoints < dealerPoints) {
						System.out.println("--- Lose");
						result = 0;
				}

				else if(playerPoints != dealerPoints) {
					System.out.println("--- Win");
					result = 1;
				} 
				else {
					System.out.println("--- Draw");
					result = 2;
				}
			}

		calcReward(result);
	}
}
