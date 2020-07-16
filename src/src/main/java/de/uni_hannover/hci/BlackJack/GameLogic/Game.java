package src.main.java.de.uni_hannover.hci.BlackJack.GameLogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.*;
import src.main.java.de.uni_hannover.hci.BlackJack.Server.ServerThread;

/**
 * This Class simulates a BlackJack-Game.
 * @author Duong La, Ngoc Trinh, Jean-Carl Kremser
 * @version 2020.07.05
 */
public class Game {
	
	// ArrayList of ServerThreads that are connected to Players
	private ArrayList<ServerThread> threads = new ArrayList<ServerThread>();

	// Dealer
	private Dealer dealer;
	
	// turn?
	private int turn;
	
	// Constructor
	public Game(ArrayList<ServerThread> threads) throws IOException {
		
		this.threads = threads;
		Deck d = new Deck();
		this.dealer = new Dealer(d);

		System.out.println("Creating Players...");
		createPlayers();
		System.out.println("\nInitializing Table...");
		initTable();
		System.out.println("\nStarting GameLoop...\n");
		gameLoop();

	}
	
////////////////////////////////////////////////////////////////////////////
	
	// returns threads.size() - probably never used.
	public int playerSize() {
		return threads.size();
	}
	
	// returns the next turn? I don't know
	public void nextTurn() {
		this.turn = (this.turn+1)%playerSize();
	}
	
	// return String representing the GameState
	public String toString() {

		String str = "";
		str += dealer.toString() + "\n";
		for (int i = 0; i < threads.size(); i++) {
			str += threads.get(i).getPlayer().toString() + "\n";
		}
		return str;
	}

////////////////////////////////////////////////////////////////////////////
	
	// This Method checks if there are still Players with cash left.
	public boolean end() {
	
		boolean end = true;
		for (int i = 0; i < threads.size(); i++) {
			if(threads.get(i).getPlayer().getCash() > 0){
				end = false;
			}
		}
		return end;
	}
	
	// This Method instructs the Threads to ask for their Clients name
	public void createPlayers() {

		// asks Clients for name
		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).setReady(false);
			threads.get(i).callReceiveName();
		}

		// waits for all Clients to enter their name
		while(!(allPlayersReady())){
			// wait
		}

		// sets Player-names
		for (int i = 0; i <threads.size(); i++) {
			String name = threads.get(i).getPlayerName();
			threads.get(i).getPlayer().setName(name);
			System.out.println("Player " + i +  "'s Name is " + threads.get(i).getPlayer().getName());	
		}
	}
	
	// gives every Player and the Dealer two Cards
	public void initTable() {

		this.dealer.assignDeck(new Deck());

		this.dealer.resetHand();
		this.dealer.deal(dealer, true);
		this.dealer.deal(dealer, false);
		
		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).getPlayer().resetHand();
			this.dealer.deal(threads.get(i).getPlayer(), true);
			this.dealer.deal(threads.get(i).getPlayer(), true);
		}
	}
	
	// This Method instructs the Threads to ask for their Clients bet
	public void chooseBet() {

		// asks Clients for their bet
		for (int i = 0; i < threads.size(); i++) {
			if(threads.get(i).getPlayer().getCash() > 0){
				threads.get(i).setReady(false);
				threads.get(i).callReceiveBet();
			}
		}

		// waits for all Clients to enter their bet
		while(!(allPlayersReady())){
			// wait 
		}

		// sets Player-bets
		for (int i = 0; i <threads.size(); i++) {
			int bet = threads.get(i).getBet();
			System.out.println("Player " + i + " bets " + bet);
			threads.get(i).getPlayer().bet(bet);	
		}

		System.out.print("\n");
	}
	
	// This Method instructs the Threads to ask for their Clients move
	public void playerOptions() throws IOException {

		for (int i = 0; i < threads.size(); i++) {
			while(true){
				int count = threads.get(i).getPlayer().getHand().size();
				if(threads.get(i).getPlayer().getTotalPoints() < 21 && count < 5){
					threads.get(i).setReady(false);
					threads.get(i).setMove();
					threads.get(i).receiveMove();
					while(!(threads.get(i).getReady())){}
					String move = threads.get(i).getMove();	
					if(move.equals("hit")){
						this.dealer.deal(threads.get(i).getPlayer(), true);
					}
					else if(move.equals("stand")){
						break;
					}
					else if(move.equals("fold")){
						threads.get(i).getPlayer().fold();
						break;
					}
				}
				else {
					break;
				}
				System.out.println(toString());
				sendGameStateToAll();	
			}
		}
	}

	// This Method removes Players with no Cash from the Game
	public void clearPlayer() throws IOException{
		for (int i = 0; i < threads.size(); i++) {
			if(threads.get(i).getPlayer().getCash() <= 0){
				System.out.println("Player " + i + " is out of money and gets removed.");
				threads.get(i).exit();
				threads.remove(i);
				i--;
			}
		}
	}

////////////////////////////////////////////////////////////////////////////
	
	// Game Loop
	public void gameLoop() throws IOException {
		while(!end()) {	

			// Deal initial cards to players
			initTable();
			System.out.println(toString());
			sendGameStateToAll();

			// Player bet
			chooseBet();
			System.out.println(toString());
			sendGameStateToAll();
	        
			// Player option
			playerOptions();
			System.out.println(toString());
			sendGameStateToAll();
			
			// Dealer's turn
			this.dealer.handUp();
			while (this.dealer.getTotalPoints() < 17) {
    			this.dealer.deal(this.dealer, true);
    		}
			System.out.println(toString());
			sendGameStateToAll();
			
			// Game Result
			for (int i = 0; i < threads.size(); i++) {
				String result = threads.get(i).getPlayer().isWinner(this.dealer);
				threads.get(i).sendGameResult(result);
			}

			System.out.print("\n");
			
			// clearing Players
			clearPlayer();
		}
	}
	
///////////////////////////////////////////////////////////////////////////

	// returns this.threads - probably never used
	public ArrayList<ServerThread> getThreads() {
		return this.threads;
	}

	// returns this.turn
	public int getTurn() {
		return this.turn;
	}
	
	// returns what exactly?
	public Players getPlayerInTurn() {
		return this.threads.get((this.turn)).getPlayer();
	}

	/**
	 * This Method sends the GameState to all Players
	 * @throws IOException
	 */
	public void sendGameStateToAll() throws IOException{
		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).setReady(false);
			threads.get(i).callSendGameState(threads, dealer);
		}
		while(!(allPlayersReady())){}
	}
	
	/**
	 * This Method checks if all Players are ready for the next turn.
	 * @return boolean representing if all players are ready.
	 */
	public boolean allPlayersReady(){
		boolean ready = true;
		for (int i = 0; i < threads.size(); i++) {
			if(!(threads.get(i).getReady())){
				ready = false;
			}
		}
		return ready;
	}
}
