package src.main.java.de.uni_hannover.hci.BlackJack.GUI;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.*;
public class HandPanel extends Pane {
	private int height = 160, width = 300;
	private ArrayList<Card> hand;
	private Players player;
	
	public HandPanel(Players p) {
		this.player = p;
		this.setPrefSize(width,height);
		draw();
	}
	
	// Rectangle (for background) TODO: find a better way
	/**
	 * Draw background to GUI
	 */
	
	/**
	 * Draw cards to GUI
	 */
	public void drawCards() {
		hand = this.player.getHand();
		
		for (int i = 0; i < this.hand.size(); i++) {
			Label card = new CardPanel(hand.get(i));
			card.getTransforms().addAll(Translate.translate(i*40+20, 10));
			this.getChildren().add(card);	
		}
	}
	
	/**
	 * Draw GUI
	 */
	public void draw() {
		getChildren().clear();
		setStyle("-fx-background-color: cyan");
		drawCards();
	}
	
	
}
