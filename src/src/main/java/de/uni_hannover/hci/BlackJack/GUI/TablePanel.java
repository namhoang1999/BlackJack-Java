package src.main.java.de.uni_hannover.hci.BlackJack.GUI;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.transform.Transform;
import src.main.java.de.uni_hannover.hci.BlackJack.Client.Client;
import src.main.java.de.uni_hannover.hci.BlackJack.GameLogic.Game;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.Dealer;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.Deck;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.Player;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.Players;
import src.main.java.de.uni_hannover.hci.BlackJack.Server.ServerThread;

public class TablePanel extends Pane {
	public static final int WIDTH = 1362;
	public static final int HEIGHT = 898;
	private ArrayList<ServerThread> threads;
//	private Object[] players = new Players[7];
//	private PlayerPanel[] playerPanes = new PlayerPanel[7];
	private ImageView iv = null;
	private PlayerPanel playerPane;
	private ArrayList<Players> players;
	
	public TablePanel() {
		File file = new File("Icon/xskat/Table.png");
		Image image = new Image(file.toURI().toString());
	    iv = new ImageView(image);
	    setPrefSize(WIDTH, HEIGHT);
	    players = new ArrayList<Players>();
//	    players.add(new Dealer(new Deck()));
	    draw(players);
	}
	
	public int getMove() {
		return this.playerPane.getMove();
	}
	/**
	 * Draw/Update method for GUI
	 * @param g the current Game state (basically we're only taking the Players array)
	 */
	public void draw(ArrayList<Players> players) {
		Transform t = null;
		
		// update all players in the game
		//setPlayers(g.getPlayers());
		
		getChildren().clear();			// clear everything first
	    
		// and start reconstructing all component from the start
		getChildren().add(iv);
		if(threads.size() > 0) {
			for (int i = 0; i <= threads.size(); i++) {
		    	Players p = threads.get(i).getPlayer();
		    	if (p != null) {
				    if (p instanceof Dealer) {						// Dealer
						playerPane = new PlayerPanel((Dealer)p);
					
						t = Transform.translate((WIDTH-300)/2, (HEIGHT-250)/2);
					} else {															// Player
						playerPane = new PlayerPanel((Player)p);
					    
						if (i == 0) t = Transform.translate(0, 0);
						if (i == 1) t = Transform.translate((WIDTH-300)/2, 0);
						if (i == 2) t = Transform.translate(WIDTH-300, 0);
						if (i == 3) t = Transform.translate(0, HEIGHT-250);
						if (i == 4) t = Transform.translate((WIDTH-300)/2, HEIGHT-250);
						if (i == 5) t = Transform.translate(WIDTH-300, HEIGHT-250);
					}
		    	}
		    	players.add(p);
				playerPane.getTransforms().add(t);
				getChildren().add(playerPane);
		    }
		}
	    
	}
	
	
//	public void setGlow(Players p) {
//		for (int i = 0; i < players.length; i++) {
//			if (this.players[i] != null) {
//				if ((Players)this.players[i] == p) {
//					this.playerPanes[i].setDisable(false);
//				} else {
//					this.playerPanes[i].setDisable(true);
//				}
//			}
//		}
//	}
}
