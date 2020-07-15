package src.main.java.de.uni_hannover.hci.BlackJack.Client;

import java.net.*;
import java.util.ArrayList;

import src.main.java.de.uni_hannover.hci.BlackJack.GUI.GUI;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.*;

import java.io.*;

/**
 * This Class represents the Client. It waits for instructions from the Server-Thread
 * and sends data from the Player to the Server-Thread.
 * @author  Jean-Carl Kremser
 * @version 2020.06.29
 */
public class Client extends Thread {

    // Server Information
    private Socket socket;
    private int id;

    private String ip;
    private int port;


/*                                          IMPORTANT                                        */
/*###########################################################################################*/

    // boolean to let the Client know it is supposed to stop
    public boolean exit;

    // Important Variables for GUI-People 
    public ArrayList<Player> players;
    public Dealer dealer;
    public String result;

    public boolean printGameState;
    public boolean printGameResult;
    public boolean getPlayerName;
    public boolean getPlayerBet;
    public boolean getPlayerMove;

/*###########################################################################################*/    

    // Default-Constructor
    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        
        printGameState = false;
        printGameResult = false;
        getPlayerName = false;
        getPlayerBet = false;
        getPlayerMove = false;
    }
    
    /**
     * This method needs to be calles to start the Client.
     * It runs in a while Loop and listens for Instructions from the Server-Thread.
     * @param ip    Server-IP
     * @param port  Server-Port
     * @author      Jean-Carl Kremser
     * @version     2020.07.05
     */
    @Override
    public void run() {

        // Tries to connect with Server socket
        try (Socket socket = new Socket(this.ip, this.port)){
            this.socket = socket;

            // sends handshake
            String clientHandshake = "handshake";
            System.out.println("Sending handshake: " + clientHandshake );
            sendHandshake(clientHandshake);
            
            // receives handshake
            String serverHandshake = "handshake";
            System.out.println("Waiting for Server-Handshake");
            
            if(receiveHandshake(serverHandshake)){
                // successfully connected
                System.out.println("Successfully Connected.");
            }
            else {
                // wrong handshake
                System.out.println("Received wrong Handshake.");
                System.exit(0);
            }

            // sets and prints Thread ID (for debug purposes)
            this.id = receiveID();
            System.out.println("My ID is " + this.id + ".");

            exit = false;

            // the loop where the Client listens for Instructions
            while(!exit){

                // Stuff needes to receive Server-Input
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String serverInput = reader.readLine();
                
                // Instruction for receiving the Game State
                if (serverInput.equals("receiveGameState")){
                    receiveGameState();
                    serverInput = null;

                    /*
                    receiveGameState();
                    serverInput = null;
                    */
                }
                else if(serverInput.equals("receiveGameResult")){
                    receiveGameResult();
                    serverInput = null;
                    
                    /*
                    receiveGameResult();
                    serverInput = null;
                    */
                }
                // Instruction for sending the Bet
                else if(serverInput.equals("sendBet")){
                    this.getPlayerBet = true;
                    serverInput = null;
                    
                    /*
                    sendBet();
                    serverInput = null;
                    */
                }
                // Instruction to send the next Move
                else if(serverInput.equals("sendMove")){
                    this.getPlayerMove = true;
                    serverInput = null;
                    
                    /*
                    sendMove();
                    serverInput = null;
                    */
                }
                // Instruction to send the Name
                else if(serverInput.equals("sendName")){
                    this.getPlayerName = true;
                    serverInput = null;

                    /*
                    sendName();
                    serverInput = null;
                    */
                }
                // Instruction to disconnect from the Server
                else if(serverInput.equals("exit")){
                    exit();
                }
            }

        // Exception-handeling
        } catch (IOException e) {
            System.out.println("IO-Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    

    // This Method sends a given Handshake to the connected Server
    public void sendHandshake(String clientHandshake) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(clientHandshake);
    }


    // This Method receives a Handshake from the Server and compares it with the expected handshake
    // @return boolean stating if the Server-Handshake matches the expected Handshake
    public boolean receiveHandshake(String serverHandshake) throws IOException {
        String handshake;
        do {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            handshake = reader.readLine();

            if(handshake == null) {
                // do nothing
            }
            else if(handshake.equals(serverHandshake)){
                return true;
            }
            else {
                return false;
            }

        } while (true);
    }


    // This Method receives an id from the Server, to better identify each Client
    // @return int representing the Players ID
    public int receiveID() throws IOException, ClassNotFoundException{
        do {
            ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
            Integer id = (Integer)objectIn.readObject();
            if(id != null){
                return id;
            }
        } while (true);    
    }


    // This Methods requests the GUI to ask for the Players name and sends it to the Server Thread
    // name can be a random string 
    public void sendName(String name) throws IOException, ClassNotFoundException{
        getPlayerName = false;

        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(name);    
    }


    // This Method requests the GUI to ask for the Players bet and send it to the Server Thread.
    // bet can be a any Integer but please check in the controller or the gui that the player can't
    // enter a bet higher than his cash and no negative bets.
    public void sendBet(Integer playerBet) throws IOException, ClassNotFoundException{
        getPlayerBet = false;
        
        Integer cash = null;
        
        // Stuff needed to send messages to the Server-Thread
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        // sends bet
        ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectOutput.writeObject(playerBet);

    }


    // This methods request the GUI to get the Players next move and sends it to the Server-Thread
    // move has to be "hit" "stand" or "fold" any other String will probably break the Programm 
    // but i have no time to fool proof this
    public void sendMove(String move) throws IOException{
        getPlayerMove = false;
        
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(move);
    }


    // This method receives the Game-State from the Server and orders the GUI to display it
    public void receiveGameState() throws IOException, ClassNotFoundException{
        // where the Game-State is going to be stored
        ArrayList<Player> players;
        Dealer dealer;
        
        // sends confirmation to the Server-Thread
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("readyToReceivePlayers");

        // waits for first Object to arrive (ArrayList<Player>)
        do {
            ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
            players = (ArrayList<Player>)objectIn.readObject();
            if(players != null){
                break;
            }
        } while (true); 

        // sends confirmation to Server-Thread
        output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        writer.println("readyToReceiveDealer");

        // waits for second Object to arrive (Dealer)
        do {
            ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
            dealer = (Dealer)objectIn.readObject();
            if(dealer != null){
                break;
            }
        } while (true);

        // Sends confirmation to the Server-Thread
        output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        writer.println("complete");

        this.printGameState = true;
        this.players = players;
        this.dealer = dealer;

        // orders GUI to print the received Data
        //gui.printGameState(players, dealer);
    }

    // This method receives the Game-Result from the Server and orders the GUI to display it
    public void receiveGameResult() throws IOException{

        // sends confirmation to the Server-Thread
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("readyToReceiveGameResult");

        // waits for message to arrive
        do {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String result = reader.readLine();

            if(result == null) {
                // do nothing
            }
            else if(result.equals("win")){
                this.result = "win";
                printGameResult = true;
                break;
                /*
                gui.printGameResult("win");
                break;
                */
            }
            else if(result.equals("lose")){
                this.result = "lose";
                printGameResult = true;
                break;
                /*
                gui.printGameResult("lose");
                break;
                */
            }
            else if(result.equals("fold")){
                this.result = "fold";
                printGameResult = true;
                break;
                /*
                gui.printGameResult("fold");
                break;
                */
            }
            else if(result.equals("draw")){
                this.result = "draw";
                printGameResult = true;
                break;
                /*
                gui.printGameResult("draw");
                break;
                */
            }
        } while (true);
    }

    // this Method exits the Programm
    public void exit(){
        exit = true;
    }

    public int getID(){
        return id;
    }
}