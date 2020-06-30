package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.*;

public class PlayerPanel extends Pane{
	private Label playerInfo;
	private HandPanel handPanel;
	private Players player;
	
	public PlayerPanel(Players p) {
		this.player = p;
		playerInfo = new Label("Name: " + p.getName() + ", Cash: " + p.getCash() + ", Points: " + p.getTotalPoints());
		handPanel = new HandPanel(p);
		HBox hBox = new HBox();
		Button FButton = new Button("(F)old"); 
		Button HButton = new Button("(H)it"); 
		Button SButton = new Button("(S)tand"); 
		
////////////////////////////////////////////////////////////////////////

		// Button event handlers
		FButton.setOnAction(new EventHandler<ActionEvent>() {  
            
	            @Override  
	            public void handle(ActionEvent arg0) {  
	            	// TODO: add event handler
	            	update();
	            }  
	        } );  
		HButton.setOnAction(new EventHandler<ActionEvent>() {  
	            
	            @Override  
	            public void handle(ActionEvent arg0) {  
	            	// TODO: add event handler
	            	Dealer d = new Dealer(new Deck());
	            	d.deal(p, true);
	            	update();
	            	System.out.println(p.toString());
	            }  
	        } );  
		
		SButton.setOnAction(new EventHandler<ActionEvent>() {  
	        
		        @Override  
		        public void handle(ActionEvent arg0) {  
		        	// TODO: add event handler
		        	update();
		        }  
		    } );  

////////////////////////////////////////////////////////////////////////

		// Hbox for array of buttons
		hBox.setPrefWidth(100);

		FButton.setMinWidth(hBox.getPrefWidth());
		HButton.setMinWidth(hBox.getPrefWidth());
		SButton.setMinWidth(hBox.getPrefWidth());
		
		hBox.getChildren().addAll(FButton,HButton,SButton);
		hBox.getTransforms().add(Transform.translate(0,190));

////////////////////////////////////////////////////////////////////////
		
		// Player Info TODO: update method 
		playerInfo.setText("Name: " + p.getName() + ", Cash: " + p.getCash() + ", Points: " + p.getTotalPoints());
		playerInfo.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		playerInfo.setTextFill(Color.WHITE);
		playerInfo.getTransforms().add(Translate.translate(0, 0));
		playerInfo.setPrefSize(300, 30);
		
////////////////////////////////////////////////////////////////////////

		// Hand Panel
		handPanel.getTransforms().add(Transform.translate(0,30));
		
////////////////////////////////////////////////////////////////////////

		// add all elements
		getChildren().add(playerInfo);
		if (p instanceof Player) getChildren().add(hBox);
		getChildren().add(handPanel);
		update();
	}
	
	// TODO: update method 
	// list of update items
	// - info label (names, points, ...)
	// - card on hand
	// - ...
	public void update() {
		handPanel.update();
    	playerInfo.setText("Name: " + this.player.getName() + 
    						", Cash: " + this.player.getCash() + 
    						", Points: " + this.player.getTotalPoints());
	}
}
