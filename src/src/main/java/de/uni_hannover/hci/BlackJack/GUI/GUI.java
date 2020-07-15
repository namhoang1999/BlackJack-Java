package src.main.java.de.uni_hannover.hci.BlackJack.GUI;

import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import src.main.java.de.uni_hannover.hci.BlackJack.Client.Client;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.*;

/**
 * This class is a GUI-replacement and is later going to be exchanged for the real GUI.
 * It displays the GameState via console and ask for client input via Scanner.nextLine().
 * @author 	Jean-Carl Kremser
 * @version 2020.06.29
 */
public class GUI{
	
	// TODO: add this to Main
//	public static final int WIDTH = 1362;
//	public static final int HEIGHT = 898;
//	private BorderPane root;
//	private Scene scene;
	// Server-Thread ID
	private int id;
	private int move;
	private TablePanel tp;
	// Constructor
	public GUI(int id){
		this.id = id;
		//tp = new TablePanel();
	}

	/**
	 * This Method prints the Game-State depending of the given Arguments
	 * @param players	ArrayList<Player> with all Information concerning the Players
	 * @param dealer	Dealer with al information concerning the Dealer
	 */
	public void printGameState(ArrayList<Player> players, Dealer dealer){
		String str = "";
		//str += dealer.getName()+ ", Hand: " + dealer.getHand() +"\n";
		str += "\n" + dealer.toString() + "\n";
		for (int i = 0; i < players.size(); i++) {
			str += players.get(i).getName() + "(" + players.get(i).getCash() + "$)"; 
			str += ", Points: " + players.get(i).getTotalPoints() ;
			str += ", Hand: " + players.get(i).getHand() + "\n";
		}
		System.out.print(str);
		
	}
	public void draw(ArrayList<Players> players) {
		tp.draw(players);
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

	/**
	 * This Method gets the Players Name via Scanner.readLine()
	 * @return String representing the Players name
	 */
	public String getName(){
		String name = null;
		Scanner sc = new Scanner(System.in);
		while(name == null){
			System.out.print("\nEnter Name: ");
			name = sc.nextLine();	
		}
		return name;
	}

	/**
	 * This Method gets the Bet from the Player
	 * @param cash	int representing the number of cash the player has (to calculate whether the bet is valid)
	 * @return 		int representing the Bet
	 */
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

	/**
	 * This Method gets the next Move from the Player via Scanner.nextLine()
	 * @return	String representing the next move
	 */
	public String getMove(){
		String move = null;
		
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.print("\nEnter \"hit\", \"stand\" or \"fold\": ");
			move = sc.nextLine();
			if(move.equals("hit")){
				return "hit";
			}
			else if(move.equals("stand")){
				return "stand";
			}
			else if(move.equals("fold")){
				return "fold";
			}
		}
	}

	public void exit(){
		System.out.println("\nExiting...");
		System.exit(0);
	}

// TODO: add this to Main
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		// TODO Auto-generated method stub
//		root = new BorderPane();
//		scene = new Scene(root,WIDTH,HEIGHT);
//
//		root.getChildren().add(tp);
//		primaryStage.setScene(scene);
//		primaryStage.show();
//	}


}
