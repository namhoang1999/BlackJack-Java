package src.main.java.de.uni_hannover.hci.BlackJack.GUI;

import javafx.application.Application;
import javafx.stage.Stage;
import src.main.java.de.uni_hannover.hci.BlackJack.Client.Client;

public class GUIPanel extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Client c = new Client();
		c.startClient("123.456.78.9", 6666);
	}
	
}
