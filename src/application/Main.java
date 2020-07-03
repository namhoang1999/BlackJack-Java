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
import javafx.scene.layout.StackPane;
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
	private WaitingRoomPanel wrp;
	
	public void switchPane (boolean b) {
		if(b) {
			//tablePane.toBack();
			tablePane.setVisible(!b);
			wrp.setVisible(b);
		}
		else {
			//wrp.toBack();
			tablePane.setVisible(!b);
			wrp.setVisible(b);
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// TODO: integrate Game into this
			// take Players ArrayList
			// loop through and create playerPanels
			StackPane sp = new StackPane();
			sp.getTransforms().add(Transform.translate(0, 0));
			g = new Game();
			root = new BorderPane();
			scene = new Scene(root,WIDTH,HEIGHT);
			tablePane = new TablePanel(g);		// Player panels for 6 players and 1 dealer
			//WaitingPlayerPane wpp = new WaitingPlayerPane(new Player("Namm", 1000));
			wrp = new WaitingRoomPanel();
			wrp.addPlayer(new Player("Nam",1000));
			wrp.addPlayer(new Player("Nam",1000));
			wrp.addPlayer(new Player("Nam",1000));
			sp.getChildren().add(tablePane);
			sp.getChildren().add(wrp);
	
			switchPane(true);
			
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
			root.getChildren().add(sp);
			//root.getChildren().add(tablePane);
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
