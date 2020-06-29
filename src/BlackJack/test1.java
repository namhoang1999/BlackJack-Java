package BlackJack;

import java.util.ArrayList;

public class test1 {
	public static void main(String[] args) {
		Player p = new Player("nam",1000);
		Deck d = new Deck();
		Dealer dealer = new Dealer(d);
		
		PlayerPanel pp = new PlayerPanel(p);
	}
}
