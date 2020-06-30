package application;

import java.util.ArrayList;
import java.util.Scanner;


public class Game {
	private ArrayList<Players> players = new ArrayList<Players>(); // 4 players and 1 dealer
	private Dealer dealer;
	private Scanner scan;
	
	public Game() {
		this.scan = new Scanner(System.in);
		Deck d = new Deck();
		this.dealer = new Dealer(d);
		
		createPlayers();
		addPlayer(this.dealer);
		initTable();
//		gameLoop();
	}
	
////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Add player to the game
	 * @param p player to add
	 */
	public void addPlayer(Players p) {
		players.add(p);
	}
	
	/**
	 * Remove player from the game
	 * @param p player to remove
	 */
	public void removePlayer(Players p) {
		players.remove(p);
	}
	
	/**
	 * Return number of players
	 * @return number of players
	 */
	public int playerSize() {
		return players.size();
	}
	
	/**
	 * Return the board to string
	 */
	public String toString() {
		String str = "";
		for(Players p: this.players) {
			if (p != null) str += p.toString() +"\n";
		}
		return str;
	}

////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Condition for the game to end
	 * @return can the game end or not
	 */
	public boolean end() {
		boolean ans = true;
		for (Players p : this.players) {
			if ((p != null) && (p.getCash() > 0)) ans = false;
		}
		return ans;
	}
	
	/**
	 * Create players with their names
	 */
	public void createPlayers() {
		System.out.println("Choose the number of players (maximum 4): ");
		int n = Integer.parseInt(scan.nextLine());
		for (int i = 0; i < n; i++) {
			System.out.println("Enter Player's name: ");
			addPlayer(new Player(scan.nextLine(), 1000));
		}
		for (Players p : this.players) {
			if (p != null) System.out.println("Welcome " + p.getName());
		}
	}
	
	/**
	 * Deal each player 2 cards
	 */
	public void initTable() {
		// Assign a new (shuffled) deck to the Dealer
		this.dealer.assignDeck(new Deck());
		
		for (Players p : this.players) {
			// Each player got dealt 2 cards (Dealer got 1 face up and 1 face down)
			if (p != null) {
				// All players (including the Dealer) reset cards on hand
				p.resetHand();
				
				if (p instanceof Dealer) {
					this.dealer.deal(p, true);
					this.dealer.deal(p, false);
				} else {
					this.dealer.deal(p, true);
					this.dealer.deal(p, true);
				}
			}
		}
	}
	
	/**
	 * Each player (except for Dealer) chooses their bet
	 */
	public void chooseBet() {
        for (Players p: this.players) {
			if (p instanceof Player) {
				System.out.println(p.getName() +"(" + p.getCash() + "$)" + " Choose your bet: ");
				int bet = scan.nextInt();
				while (bet > p.getCash()) {
					System.out.println("Bet is too high, try again");
					bet = scan.nextInt();
				}
				((Player) p).bet(bet);
			}
		} 
	}
	
	/**
	 * Show player options
	 * @param p player to play
	 */
	public void playerOptions(Player p) {
		int count = p.hand_.size();
		while (p.getTotalPoints() <= 21 && count <= 5) {
			System.out.println(p.getName() + "Choose your option: (H)it, (S)tand or (F)old?");
			String option = (scan.nextLine()).toLowerCase();
			System.out.println("Option chosen: " + option);
			if (option.equals("h")) {			    // if hit, deal another card
				System.out.println("Deal");
				this.dealer.deal(p, true);
			} else if (option.equals("s")) {		// if stand, skip the turn
				System.out.println("Stand");
				break;
			} else if (option.equals("f")) {		// if fold, skip the turn
				System.out.println("Fold");
				((Player) p).fold();
				break;
			}
			System.out.println(toString());
		}
	}
	
	public void clearPLayer() {
		ArrayList<Players> clear = new ArrayList<Players>();
		for (Players p : this.players) {
			if (p.getCash() <= 0) {
				clear.add(p);
			}
		}
		this.players.removeAll(clear);
	}

////////////////////////////////////////////////////////////////////////////
	
	// Game Loop
	public void gameLoop() {
		while(!end()) {	
			
			// Deal initial cards to players
			initTable();
			System.out.println(toString());
			
			chooseBet();
			System.out.println(toString());
	        
			// Player option
			for (Players p: this.players) {
	        	if (p != null && p instanceof Player) {
	        		playerOptions((Player)p);
	        	} 
			}
			System.out.println(toString());
			
			// Dealer's turn
			this.dealer.handUp();
			while (this.dealer.getTotalPoints() < 17) {
    			this.dealer.deal(this.dealer, true);
    		}
			System.out.println(toString());
			
	        // Calculating score
			for (Players p : this.players) {
	        	if (p != null && p instanceof Player) {
	        		((Player)p).isWinner(this.dealer);
	        	}
	        }
			
			clearPLayer();
		}
		scan.close();
	}
	
///////////////////////////////////////////////////////////////////////////
	
	public ArrayList<Players> getPlayers() {
		return this.players;
	}
	
	public static void main(String[] args) {

		Game g = new Game();
	}
}
