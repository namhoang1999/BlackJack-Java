package src.main.java.de.uni_hannover.hci.BlackJack.GUI;

import java.util.ArrayList;
import java.util.Scanner;

import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.Dealer;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.Deck;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.Player;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.Players;

public class MainGUI {
	private TablePanel tp;
	private int id;
	private int move;
	private ArrayList<Players> allPlayers;
	private Dealer dealer = new Dealer(new Deck());
	public MainGUI() {
		tp = new TablePanel();
	}
	public void drawGame(ArrayList<Player> players, Dealer dealer) {
		this.dealer = dealer;
		allPlayers.addAll(players);
		allPlayers.add(dealer);
		tp.draw(allPlayers);
	}
	public String getMove() {
		String move = "";
		if(tp.getMove() == 0) {
			move = "hit";
		}else if(tp.getMove() == 1) {
			move = "stand";
		}else if(tp.getMove() == 2) {
			move = "fold";
		}
		return move;
	}
	
	public String getName(){
		String name = null;
		Scanner sc = new Scanner(System.in);
		while(name == null){
			System.out.print("\nEnter Name: ");
			name = sc.nextLine();	
		}
		return name;
	}
	public int getBet(int cash){
		int bet = -1;
		Scanner sc = new Scanner(System.in);
		while(bet < 1 || bet > cash){
			System.out.print("\nEnter bet: ");			
			try {
				String s = sc.nextLine();
				bet = Integer.parseInt(s);	
			} catch (Exception e) {
				System.out.println("EXCEPTION");
				bet = -1;
			}				
		}
		return bet;
	}
	public void printGameResult(String result){
		if(result.equals("win")){
			System.out.println("\nYou won.");
		}
		else if(result.equals("lose")){
			System.out.println("\nYou lost.");
		}
		else if(result.equals("fold")){
			System.out.println("\nYou folded and lost half of your bet.");
		}
		else if(result.equals("draw")){
			System.out.println("\nYou drew.");
		}
		else {
			System.out.println("\nInvalid argument!");
		}
	}
	
	
}
