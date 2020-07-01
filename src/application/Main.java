package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import application.Deck.Rank;
import application.Deck.Suit;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;


public class Main extends Application {
	public static final int WIDTH = 1362;
	public static final int HEIGHT = 898;
	private PlayerPanel playerPane;
	private TablePanel tablePane;
	private BorderPane root;
	private Scene scene;
	private Game g;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// TODO: integrate Game into this
			// take Players ArrayList
			// loop through and create playerPanels
			
			g = new Game();
			root = new BorderPane();
			scene = new Scene(root,WIDTH,HEIGHT);
			tablePane = new TablePanel(g);		// Player panels for 6 players and 1 dealer
			
//	///////////////////////////////////////////////////////////////
//			Button b = new Button("Test");
//			b.getTransforms().add(Transform.translate(200, 200));
//			b.setOnAction(new EventHandler<ActionEvent>() {  
//	            
//	            @Override  
//	            public void handle(ActionEvent arg0) {  
//	            	// TODO: add event handler
//	            	g.removePlayer(g.getPlayers().get(0));
//	    			tablePane.draw(g);
//	            }  
//	        } );
//			tablePane.getChildren().add(b);
//	///////////////////////////////////////////////////////////////
					
			// Add elements to scene graph
			root.getChildren().add(tablePane);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
