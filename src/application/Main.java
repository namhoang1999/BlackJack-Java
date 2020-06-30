package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Deck.Rank;
import application.Deck.Suit;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;


public class Main extends Application {
	private final int WIDTH = 1362;
	private final int HEIGHT = 898;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// TODO: integrate Game into this
			// take Players ArrayList
			// loop through and create playerPanels
			
//			Game g = new Game();
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,WIDTH,HEIGHT);
			
			Dealer d = new Dealer(new Deck());
			d.deal(d, true);
			d.deal(d, false);
			PlayerPanel dealerPane = new PlayerPanel(d);
			
			// Player panels for 6 players and 1 dealer
			PlayerPanel pPanel1 = new PlayerPanel(new Player("Player 1",1000));
			PlayerPanel pPanel2 = new PlayerPanel(new Player("Player 2",1000));
			PlayerPanel pPanel3 = new PlayerPanel(new Player("Player 3",1000));
			PlayerPanel pPanel4 = new PlayerPanel(new Player("Player 4",1000));
			PlayerPanel pPanel5 = new PlayerPanel(new Player("Player 5",1000));
			PlayerPanel pPanel6 = new PlayerPanel(new Player("Player 6",1000));
			TablePanel tPanel = new TablePanel();

			// Transformation setup
			dealerPane.getTransforms().add(Transform.translate((WIDTH-300)/2, (HEIGHT-250)/2));
			pPanel1.getTransforms().add(Transform.translate(0, 0));
			pPanel2.getTransforms().add(Transform.translate((WIDTH-300)/2, 0));
			pPanel3.getTransforms().add(Transform.translate(WIDTH-300, 0));
			pPanel4.getTransforms().add(Transform.translate(0, HEIGHT-250));
			pPanel5.getTransforms().add(Transform.translate((WIDTH-300)/2, HEIGHT-250));
			pPanel6.getTransforms().add(Transform.translate(WIDTH-300, HEIGHT-250));
			
			// Add elements to scene graph
			root.getChildren().add(tPanel);
			root.getChildren().add(dealerPane);
			root.getChildren().add(pPanel1);
			root.getChildren().add(pPanel2);
			root.getChildren().add(pPanel3);
			root.getChildren().add(pPanel4);
			root.getChildren().add(pPanel5);
			root.getChildren().add(pPanel6);
			
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
