package application;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.transform.Transform;

public class TablePanel extends Pane {
	private Object[] players = new Players[7];
	private PlayerPanel[] playerPanes = new PlayerPanel[7];
	private ImageView iv = null;
	private PlayerPanel playerPane;
	
	public TablePanel(Game g) {
		File file = new File("Icon/xskat/Table.png");
		Image image = new Image(file.toURI().toString());
	    iv = new ImageView(image);
	    //setMinWidth(1362);
	    //setMinHeight(898);
	    setPrefSize(1362, 898);
	    draw(g);
	}
	
	/**
	 * Draw/Update method for GUI
	 * @param g the current Game state (basically we're only taking the Players array)
	 */
	public void draw(Game g) {
		Transform t = null;
		
		// update all players in the game
		setPlayers(g.getPlayers());
		
		getChildren().clear();			// clear everything first
	    
		// and start reconstructing all component from the start
		getChildren().add(iv);		
	    for (int i = 0; i < players.length; i++) {
	    	if (this.players[i] != null) {
			    if (this.players[i] instanceof Dealer) {						// Dealer
					playerPane = new PlayerPanel((Dealer)this.players[i]);
				
					t = Transform.translate((Main.WIDTH-300)/2, (Main.HEIGHT-250)/2);
				} else {															// Player
					playerPane = new PlayerPanel((Player)this.players[i]);
				    
					if (i == 0) t = Transform.translate(0, 0);
					if (i == 1) t = Transform.translate((Main.WIDTH-300)/2, 0);
					if (i == 2) t = Transform.translate(Main.WIDTH-300, 0);
					if (i == 3) t = Transform.translate(0, Main.HEIGHT-250);
					if (i == 4) t = Transform.translate((Main.WIDTH-300)/2, Main.HEIGHT-250);
					if (i == 5) t = Transform.translate(Main.WIDTH-300, Main.HEIGHT-250);
				}
				playerPanes[i] = playerPane;
	    	}
			playerPane.getTransforms().add(t);
			getChildren().add(playerPane);
	    }
	}

	/**
	 * Set the Players Array List to the current state of the Game
	 * @param players Players Array List and their states
	 */
	public void setPlayers(ArrayList<Players> players) {
		this.players = players.toArray();
	}
	
	public void setGlow(Players p) {
		for (int i = 0; i < players.length; i++) {
			if (this.players[i] != null) {
				if ((Players)this.players[i] == p) {
					this.playerPanes[i].setDisable(false);
				} else {
					this.playerPanes[i].setDisable(true);
				}
			}
		}
	}
}
