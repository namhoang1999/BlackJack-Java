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
public class Client {

    // Server Information
    private Socket socket;
    private int id;

    // GUI
    private GUI gui;

    // boolean to let the Client know it is supposed to stop
    private boolean exit;

    // Default-Constructor
    public Client(){
    }
    
    /**
     * This method needs to be calles to start the Client.
     * It runs in a while Loop and listens for Instructions from the Server-Thread.
     * @param ip    Server-IP
     * @param port  Server-Port
     * @author      Jean-Carl Kremser
     * @version     2020.07.05
     */
    public void startClient(String ip, int port) {

        // Tries to connect with Server socket
        try (Socket socket = new Socket(ip, port)){
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

            // creates new GUI
            this.gui = new GUI(this.id);

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
                }
                else if(serverInput.equals("receiveGameResult")){
                    receiveGameResult();
                    serverInput = null;
                }
                // Instruction for sending the Bet
                else if(serverInput.equals("sendBet")){
                    sendBet();
                    serverInput = null;
                }
                // Instruction to send the next Move
                else if(serverInput.equals("sendMove")){
                    sendMove();
                    serverInput = null;
                }
                // Instruction to send the Name
                else if(serverInput.equals("sendName")){
                    sendName();
                    serverInput = null;
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
    private void sendHandshake(String clientHandshake) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(clientHandshake);
    }


    // This Method receives a Handshake from the Server and compares it with the expected handshake
    // @return boolean stating if the Server-Handshake matches the expected Handshake
    private boolean receiveHandshake(String serverHandshake) throws IOException {
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
    private int receiveID() throws IOException, ClassNotFoundException{
        do {
            ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
            Integer id = (Integer)objectIn.readObject();
            if(id != null){
                return id;
            }
        } while (true);    
    }


    // This Methods requests the GUI to ask for the Players name and sends it to the Server Thread
    private void sendName() throws IOException, ClassNotFoundException{
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(gui.getName());    
    }


    // This Method requests the GUI to ask for the Players bet and send it to the Server Thread.
    private void sendBet() throws IOException, ClassNotFoundException{
        Integer cash = null;
        
        // Stuff needed to send messages to the Server-Thread
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("readyToReceiveCash");

        // receives Players cash to check whether the bet is valid
        do {
            ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
            cash = (Integer)objectIn.readObject();
            if(cash == 0){
                // Player has no cash (should never happen, but just in case)
                // System.out.println("Player has no cash");
            }
        } while (cash == null);

        // gets bet from GUI
        Integer bet = gui.getBet(cash);

        // sends bet
        ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectOutput.writeObject(bet);

    }


    // This methods request the GUI to get the Players next move and sends it to the Server-Thread
    private void sendMove() throws IOException{
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(gui.getMove());
    }


    // This method receives the Game-State from the Server and orders the GUI to display it
    private void receiveGameState() throws IOException, ClassNotFoundException{
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

        // orders GUI to print the received Data
        gui.printGameState(players, dealer);
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
                gui.printGameResult("win");
                break;
            }
            else if(result.equals("lose")){
                gui.printGameResult("lose");
                break;
            }
            else if(result.equals("fold")){
                gui.printGameResult("fold");
                break;
            }
            else if(result.equals("draw")){
                gui.printGameResult("draw");
                break;
            }
        } while (true);
    }

    // this Method exits the Programm
    private void exit(){
        gui.exit();
        exit = true;
    }
}