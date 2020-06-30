package application;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class HandPanel extends Pane {
	private int height = 160, width = 300;
	private ArrayList<Card> hand;
	private Players player;
	
	public HandPanel(Players p) {
		this.player = p;
		this.setPrefSize(width,height);
		
		// Rectangle (for background) TODO: find a better way
		Rectangle rect=new Rectangle();
	    rect.setX(0);   
	    rect.setY(0);   
	    rect.setWidth(width);    
	    rect.setHeight(height);
	    rect.setFill(Color.DARKGREEN);
	 
	    getChildren().addAll(rect);  
	}
	
	public void update() {
		hand = this.player.getHand();
		
		for (int i = 0; i < this.hand.size(); i++) {
			Label card = new CardPanel(hand.get(i));
			card.getTransforms().addAll(Translate.translate(i*40+20, 10));
			this.getChildren().add(card);	
		}
	}
	
	public void displayCard() {
		
	}
}
