package src.main.java.de.uni_hannover.hci.BlackJack.Client;

import java.net.*;
import java.io.*;

import src.main.java.de.uni_hannover.hci.BlackJack.Client.Client;
import src.main.java.de.uni_hannover.hci.BlackJack.GUI.GUI;

/**
 * This class is used to start the Client, 
 * without having to use the GUI, which is how it is supposed to be started later.
 * Has to be started with to parameters: Server-ip and port.
 * @author Jean-Carl Kremser
 * @version 2020.06.29
 */
public class ClientTest {
    public static void main(String[] args)  throws IOException, ClassNotFoundException, InterruptedException{
        
        // exit if wrong numbers of parameters
        if(args.length != 2){
            System.out.println("Client was executed with wrong number of parameters.");
            System.exit(0);
        }

        // Server ip
        String ip = args[0];
        // Server port
        int port = Integer.parseInt(args[1]);

        // Starting new Client
        Client client = new Client(ip, port);
        client.start();

        GUI gui = new GUI(client.getID());

        // This is probably what the controller is going to do
        while(!(client.exit)){
            Thread.sleep(20);
            if(client.printGameState == true){
                client.printGameState = false;
                gui.printGameState(client.players, client.dealer);
            }
            if(client.printGameResult == true){
                client.printGameResult = false;
                gui.printGameResult(client.result);
            }
            if(client.getPlayerName == true){
                client.sendName(gui.getName());
            }
            if(client.getPlayerBet == true){
                int cash = client.players.get(client.getID()).getCash();
                Integer bet = gui.getBet(cash);
                client.sendBet(bet);
            }
            if(client.getPlayerMove == true){
                client.getPlayerMove = false;
                client.sendMove(gui.getMove());
            }
        }
    }
}