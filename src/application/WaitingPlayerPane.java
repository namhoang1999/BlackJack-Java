package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Transform;


public class WaitingPlayerPane extends Pane {
	private Player p;
	private boolean isReady;
	private Button readyButton;
	public WaitingPlayerPane(Player p) {
		this.p = p;
		isReady = false;
		setPrefSize(454 , 449);
		draw();
	}
	public void swapLabel() {
		if(isReady) {
			readyButton.setText("Cancel");
			isReady = false;
		} else {
			readyButton.setText("Ready");
			isReady = true;
		}	
	}
	
	public boolean getIsReady() {
		return this.isReady;
	}
	public void draw() {
		getChildren().clear();
		Label playerLabel = new Label("Name: "+ p.getName()+ " " + p.getCash() + "$");
		playerLabel.getTransforms().add(Transform.translate(0, 0));
		getChildren().add(playerLabel);
		TextField tf = new TextField("Enter Bet: ");
		tf.getTransforms().add(Transform.translate(300, 0));
		getChildren().add(tf);
		readyButton = new Button("Ready");
		getChildren().add(readyButton);
		readyButton.getTransforms().add(Transform.translate(200, 0));
		readyButton.setOnAction(new EventHandler<ActionEvent>() {  
			@Override  
			public void handle(ActionEvent arg0) {  
				// TODO: add event handler
				System.out.println("Hello");
				swapLabel();
			}  
		} );  
	}
}
