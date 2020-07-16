package src.main.java.de.uni_hannover.hci.BlackJack.GUI;


import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.main.java.de.uni_hannover.hci.BlackJack.Client.Client;

public class test1 extends Application{
	public static final int WIDTH = 1362;
	public static final int HEIGHT = 898;
	private PlayerPanel playerPane;
	private TablePanel tablePane;
	private BorderPane root;
	private Scene scene;
	
	
	@Override
	public void start(Stage primaryStage) {
		root = new BorderPane();
		scene = new Scene(root, WIDTH , HEIGHT);
		String ip = "172.16.35.113";
        // Server port
        int port = 6666;

        // Starting new Client
        Client client = new Client(ip, port);
        client.start();

        MainGUI gui = new MainGUI();

        // This is probably what the controller is going to do
        while(!(client.exit)){
            try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(client.printGameState == true){
                client.printGameState = false;
                gui.drawGame(client.players, client.dealer);
            }
            if(client.printGameResult == true){
                client.printGameResult = false;
                gui.printGameResult(client.result);
            }
            if(client.getPlayerName == true){
                try {
					client.sendName(gui.getName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            if(client.getPlayerBet == true){
                int cash = client.players.get(client.getID()).getCash();
                Integer bet = gui.getBet(cash);
                try {
					client.sendBet(bet);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            if(client.getPlayerMove == true){
                client.getPlayerMove = false;
                try {
					client.sendMove(gui.getMove());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        root.getChildren().addAll();
		primaryStage.setScene(scene);
		primaryStage.show();
        
	}
	
	public static void main(String[] args)throws IOException, ClassNotFoundException, InterruptedException{
		launch(args);
	}
}
