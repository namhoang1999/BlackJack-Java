package src.main.java.de.uni_hannover.hci.BlackJack.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.*;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.*;
public class PlayerPanel extends Pane{
	private Label playerInfo;
	private HandPanel handPanel;
	private Players player;
	Button FButton;
	Button HButton;
	Button SButton;
	private HBox hBox;
	private int move;
	
	public PlayerPanel(Players p) {
		this.player = p;
		playerInfo = new Label("Name: " + p.getName() + ", Cash: " + p.getCash() + ", Points: " + p.getTotalPoints());
		handPanel = new HandPanel(p);
		hBox = new HBox();
		FButton = new Button("(F)old"); 
		HButton = new Button("(H)it"); 
		SButton = new Button("(S)tand"); 

		// add all elements
		draw();
	}
	
	// list of update items
	// - info label (names, points, ...)
	// - card on hand
	// - ...
	/**
	 * Update/Draw method for GUI
	 */
	public void draw() {
		getChildren().clear();
		drawInfoPane();
		drawHandPane();
		drawButtonPane();
    }
	
	public void drawInfoPane() {
		playerInfo.setText("Name: " + this.player.getName() + 
						   ", Cash: " + this.player.getCash() + 
						   ", Points: " + this.player.getTotalPoints());
		playerInfo.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		playerInfo.setTextFill(Color.WHITE);
		playerInfo.getTransforms().add(Translate.translate(0, 0));
		playerInfo.setPrefSize(300, 30);

		getChildren().add(playerInfo);
	}
	
	public void drawHandPane() {
		handPanel.draw();
		handPanel.getTransforms().add(Transform.translate(0,30));
		
		getChildren().add(handPanel);	
	}
	
	public void drawButtonPane() {
		
		// Button event handlers
		////////////////////////////////////////////////////////////////////////
		
		// Hbox for array of buttons
		hBox.setPrefWidth(100);
		
		FButton.setMinWidth(hBox.getPrefWidth());
		HButton.setMinWidth(hBox.getPrefWidth());
		SButton.setMinWidth(hBox.getPrefWidth());
		
		hBox.getChildren().addAll(FButton,HButton,SButton);
		hBox.getTransforms().add(Transform.translate(0,190));
		
		if (this.player instanceof Player) getChildren().add(hBox);		
	}
	
	// FOLD
	// Set player's cash balance
	// next turn
	public void playerFold() {
		this.move = 2;
	}
	
	// HIT
	// Deal 1 card to player
	// Have an if-else card points < 21 => next turn
	public void playerHit() {
		this.move = 0;
	}
	
	// STAND
	// next turn
	public void playerStand() {
		this.move = 1;
	}
	public int getMove() {
		return this.move;
	}
}
