
import java.util.ArrayList;
import java.util.Scanner;

/**
 * this class contains constructor and methods to create Game.
 * @author Duong La
 *
 */
public class Game {
	private ArrayList<Players> players = new ArrayList<Players>();
	private Dealer dealer;
	/**
	 * this is a constructor.
	 */
	public Game() {
		Deck d = new Deck();
		this.dealer = new Dealer(d);
		this.players.add(0, this.dealer);
	}
	/**
	 * this method adds player to game.
	 * @param p : player.
	 */
	public void addPlayer(Players p) {
		this.players.add(p);
	}
	/**
	 * this method inits begin state of game.
	 */
	public void initTable() {
		for (Players p : this.players) {
			if (p != null && p.getCash() >= 0) {
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
	 * this method prints informations out.
	 */
	public String toString() {
		//System.out.println(this.dealer.getDeck().size());
		String str = "";
		for(Players p: this.players) {
			if (p != null && p instanceof Player && p.getCash() >= 0) str += p.getName() + "(" + p.getCash() + "$)" + ", Points: " + p.getTotalPoints() + ", Hand: " + p.getHand() + "\n";
			else if(p !=null && p instanceof Dealer) str += p.getName()+ ", Hand: " + p.getHand() +"\n";
		}
		return str;
	}
	/**
	 * this method is condition to end game loop.
	 * @return true : end false : continue
	 */
	public boolean endGame() {
		boolean end = true;
		for (Players p: this.players) {
			if (p instanceof Player && p.getCash() > 0) {
				end = false;
			}
		}
		return end;
	}
	/**
	 * this method checks whether player can continue playing or not.
	 */
	public void hasToLeave() {
		for(int i = 0; i < this.players.size(); i++) {
			if(this.players.get(i).getCash() <= 0) {
				System.out.println(this.players.get(i).getName()+" is out of money and has to leave.");
				this.players.remove(this.players.get(i));
			}
		}
	}
	/**
	 * this method is a game loop.
	 */
	public void gameLoop() {
		Scanner a = new Scanner(System.in);
		while(!endGame()) {
			for(Players p: this.players) {
				if(p != null) {
					System.out.println("Players:");
					System.out.println((this.players.indexOf(p) +1) + ": " + p.getName());

				}
			}
			this.dealer.assignDeck(new Deck());
			
			for (Players p: this.players) {
				if (p != null) p.resetHand();
			}
			
			initTable();
			// Choose bet
	        for (Players p: this.players) {
				if (p instanceof Player) {
					if(p.getCash() > 0) {
						System.out.println(p.getName() +"(" + p.getCash() + "$)" + " Choose your bet: ");
						((Player) p).bet(a.nextInt());
					}
				}
			} 

			System.out.println(toString());
	        // Player option
			for (Players p: this.players) {
	        	if (p != null && p instanceof Player && p.getCash() >= 0) {
					while(true) {
						if(p.getTotalPoints() <= 21) {
							System.out.println(p.getName() + " Hit or Stand or Fold (1/2/3): ");
							int option = a.nextInt();
							if (option == 1) this.dealer.deal(p, true);
							if (option == 2) break;
							if (option == 3) {
								((Player) p).fold();								
								break;
							}
						}			
						else { 	
							break;
						}
						System.out.println(toString());
					}
	        	} 
			}
			
			this.dealer.handUp();
			
			while (this.dealer.getTotalPoints() < 17) {
    			this.dealer.deal(this.dealer, true);
				System.out.println(toString());
    		}
	        // Calculating score
			
			for(Players p: this.players) {
				if(p instanceof Player && p.getCash() >= 0) {
					isWinner((Player)p,this.dealer);
				}
			}

			String str = "";
			for(Players p: this.players) {
				if (p != null && p instanceof Player && p.getCash() >= 0) str += p.getName() + "(" + p.getCash() + "$)" + ", Points: " + p.getTotalPoints() + ", Hand: " + p.getHand() + "\n";
				else if(p!= null && p instanceof Dealer) str += p.getName() +", Points: " + p.getTotalPoints() + ", Hand: " + p.getHand() + "\n";
			}
			System.out.println(str);
			hasToLeave();
		}
		a.close();
	}
	

	/**
	 * Win 1, Lose 0, Draw 2, Folded 3.
	 * @param dealerPoints opponent points
	 * @return win, lose or draw
	 */
	public void isWinner(Player p, Dealer d) {
		int dealerPoints = d.getTotalPoints();
		int playerPoints = p.getTotalPoints();
		int result = -1;
		if(p.getCash() >= 0)
		System.out.print(p.getName() + " " + p.getTotalPoints() + " " + d.getTotalPoints() + " ");
		if (p.f_ == true) {
			System.out.println(p.getName() + " folded and lost half of his/her bet.");
			result = 3;
			p.f_ = false;
		}
		else if(dealerPoints > 21) {
			System.out.println("Win");
			result = 1;
		}else if(p.countHand() == 5 && playerPoints < 21) {
			System.out.println("Win");
			result = 1;
		}
		else {
			if(playerPoints > 21 || playerPoints < dealerPoints) {
				System.out.println("Lose");
				result = 0;
			} else if(playerPoints != dealerPoints) {
				System.out.println("Win");
				result = 1;
			} else {
				System.out.println("Draw");
				result = 2;
			}
		}
		
		if (result == 1) p.addCash(p.getBet()*2); 
		if (result == 2) p.setCash(p.getCash() + p.getBet());
		if (result == 3) p.setCash(p.getCash() + p.getBet()/2);
		
	}
	
	public static void main(String[] args) {
		Players[] addPlayers = new Players[4];
		Player p1 = new Player("", 0);
		Player p2 = new Player("", 0);
		Player p3 = new Player("", 0);
		Player p4 = new Player("", 0);
		addPlayers[0] = p1;
		addPlayers[1] = p2;
		addPlayers[2] = p3;
		addPlayers[3] = p4;
		Scanner a = new Scanner(System.in);
		System.out.println("Add player: (Maximum 4 Players). (1) to add player (2) to start the game. (Only First Name!!)");
		for(int i = 0; i < addPlayers.length; i++) {
			int type = a.nextInt();
			if(type == 1) {
				System.out.println("Player name: ");
				String input = a.next();
				addPlayers[i] = new Player(input, 10000);
				if(i < 3) System.out.println("Add player: (Maximum 4 Players). (1) to add player (2) to start the game. (Only First Name!!)");
			}
			else {
				break;
			}
		}
		Game g = new Game();
		for(int i = 0; i < addPlayers.length; i++) {
			if(addPlayers[i].getName().equals("")) {
				break;
			}
			else {
				g.addPlayer(addPlayers[i]);
			}
		}
		
		g.gameLoop();
		System.out.println("Endgame.");
		
		a.close();
	}
}
