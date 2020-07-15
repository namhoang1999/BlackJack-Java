package src.main.java.de.uni_hannover.hci.BlackJack.Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.Player;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.Players;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.*;

/**
 * This Class is a Server-Thread that extends the Thread class.
 * It is responsible for communication with it's give Client and is closely tied to the Game-Logic
 * It also probably is the biggest Mess in the entire Programm.
 * @author Jean-Carl Kremser
 * @version 2020.07.05
 */
public class ServerThread extends Thread {
    
    // Socket the connects Server and Client
    private Socket socket;

    // This Threads/Players id
    private int id;

    // Player Class where the for the GameLogic important Information is stored
    private Player player;

    // boolean whether the Player is ready for the next step in the GameLogic 
    // (primarily for MultiThreading and Inter-Thread-Communication)
    private boolean ready;

    // Data for Inter-Thread-Communication
    private int bet;
    private String name;
    private String move;

    // Probably gets deleted and replaced with booleans soon
    private String method;

    // Booleans for Multi-Threading and Inter-Thread-Communication
    private boolean callReceiveBet;
    private boolean callSendGameState;
    private boolean callSendGameResult;
    private boolean callReceiveMove;
    private boolean callReceiveName;
    private boolean callExit;

    // boolean to let the run-Method know it is supposed to stop
    private boolean exit;

    // more or less a temp variable for the Game-Information
    private ArrayList<ServerThread> threads;
    private Dealer dealer;
    private String result;

    // Constructor
    public ServerThread(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
        this.player = new Player(("Player " + id), 10000);
        this.method = "";
        this.exit = false;
    }

    // Run-Method this method starts a new Thread so multithreading is only possible with this method
    @Override
    public void run() {
        try {            
            while(!exit){
                Thread.sleep(50);
                if(callSendGameState){
                    callSendGameState = false;
                    sendGameState(threads, dealer);
                }
                else if(callSendGameResult){
                    callSendGameResult = false;
                    sendGameResult(result);
                }
                else if(callReceiveBet){
                    callReceiveBet = false;
                    receiveBet();
                }
                else if(callReceiveMove){
                    callReceiveMove = false;
                    receiveMove();
                }
                else if(callReceiveName){
                    callReceiveName = false;
                    receiveName();
                }
                else if(callExit){
                    callExit = false;
                    exit();
                }
            }                
        } catch (IOException ex) {
            System.out.println("io");
        } catch (ClassNotFoundException ex){
            System.out.println("class");
        } catch (InterruptedException ex){
            System.out.println("interrupted");
        }
    }

    /**
     * This method sends the GameState to the Client
     * @param threads   ArrayList<ServerThread> containing all threads and the players
     * @param dealer    Dealer 
     * @throws IOException  
     */
    public void sendGameState(ArrayList<ServerThread> threads, Dealer dealer) throws IOException{

        // sets ready to false (obsolete because it is optimally already set false before being called, but just in case)
        ready = false;

        // ArrayList to extract the players from the ServerThreadArray
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < threads.size(); i++) {
            players.add((Player) threads.get(i).getPlayer());
        }

        // sends request to receive GameState
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("receiveGameState");
        writer.flush();

        // waits for confirmation
        do {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String clientInput = reader.readLine();
            if(clientInput.equals("readyToReceivePlayers")){
                break;
            }
        } while (true);

        // sends players
        ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectOutput.writeObject(players);

        // waits for confirmation
        do {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String clientInput = reader.readLine();
            if(clientInput.equals("readyToReceiveDealer")){
                break;
            }
        } while (true);

        // sends Dealer
        objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectOutput.writeObject(dealer);

        // waits for confirmation
        do {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String clientInput = reader.readLine();
            if(clientInput.equals("complete")){
                break;
            }
        } while (true);

        // lets the other Threads know it is ready
        ready = true;
    }

    /**
     * This Method send the players Game Result to the Client
     * @param result    String representing the Game-result
     * @throws IOException
     */
    public void sendGameResult(String result) throws IOException{
        if(result.equals("win") || result.equals("lose") || result.equals("fold") || result.equals("draw")){
            // sends request to receive GameResult
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println("receiveGameResult");
            writer.flush();            

            // waits for confiramation
            do {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String clientInput = reader.readLine();
                if(clientInput.equals("readyToReceiveGameResult")){
                    break;
                }
            } while (true);

            // sends Game-Result
            output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            writer.println(result);
            writer.flush();

        }
        else {
            // this should not happen if the method is called correctly
            System.out.println("Invalid result");
        }
    }


    // This Method sends this ServerThreads id to the Client
    public void sendID() throws IOException {
        ObjectOutputStream objectOutput= new ObjectOutputStream(socket.getOutputStream());
        objectOutput.writeObject(this.id);   
    }


    // This Methods sends Instructions to the Client to send the Players bet
    public void receiveBet() throws IOException, ClassNotFoundException{
        // sets ready to false (obsolete because it is optimally already set false before being called, but just in case)
        ready = false;

        // send request to send bet
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("sendBet");
        writer.flush();
        
        // tmp Integer to store bet
        Integer tmpbet = null;

        // waits for Client to send Bet
        do {
            ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
            tmpbet = (Integer)objectIn.readObject();
            if(tmpbet == 0){
                System.out.println("Player bets nothing");
            }
        } while (tmpbet == null);

        // sets this.bet to tmpbet(clients bet)
        this.bet = tmpbet;

        // lets the other Threads know it is ready
        ready = true;
    }


    // This Methods sends Instructions to the Client to send the Players next Move
    public void receiveMove() throws IOException{   
        // sets ready to false (obsolete because it is optimally already set false before being called, but just in case)
        ready = false;
        
        // where the move is stored.
        String clientInput = null;
        
        // sends request to send move
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("sendMove");
        writer.flush();

        // waits for move
        do {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            clientInput = reader.readLine();
            if(clientInput.equals("hit") || clientInput.equals("stand") || clientInput.equals("fold")){
                break;
            }
        } while (true);
        
        // sets this.move to clientInput(the next move)
        this.move = clientInput;

        // lets the other Threads know it is ready
        ready = true;
    }


    // This Methods sends Instructions to the Client to send the Players name
    public void receiveName() throws IOException{
        
        // sets ready to false (obsolete because it is optimally already set false before being called, but just in case)
        ready = false;   
        
        // where the name is stored
        String clientInput;
        
        // sends request to send name
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("sendName");
        writer.flush();

        // waits for name
        do {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            clientInput = reader.readLine();
            if(clientInput != null){
                break;
            }
        } while (true);

        // sets this name to clientInput(the clients name)
        this.name = clientInput;

        // lets the other Threads know it is ready
        ready = true;
    }

    // This Method send an Exit-Message to the Client and shuts this Thread down.
    public void exit() throws IOException{
        try {
            System.out.println("exit!");
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println("exit");
            writer.flush();  
            socket.close();
            this.exit = true;
            //System.exit(0);
        } catch (Exception e) {
            socket.close();
            //System.exit(0);
        }
    }

    // Getter for player
    public Player getPlayer(){
        return this.player;
    }

    // Getter for id
    public int getID(){
        return this.id;
    }

    // Getter for ready
    public boolean getReady(){
        return this.ready;
    }

    // Getter for bet
    public int getBet(){
        return this.bet;
    }

    // Getter for name (getName is already used in the java Thread)
    public String getPlayerName(){
        return this.name;
    }

    // Getter for move
    public String getMove(){
        return this.move;
    }

    // Setter for ready
    public void setReady(boolean ready){
        this.ready = ready;
    }

    // Setter for method (obsolete in the near future)
    public void setMethod(String method){
        this.method = method;
    }

    // Sets move to null
    public void setMove(){
        this.move = null;
    }

    public void setResult(String result){
        this.result = result;
    }

    // Method to change callReceiveBet to let the Thread in run() know, 
    // that it's supossed to call the receiveBet() method (call this for Multi-Threading only)
    public void callReceiveBet(){
        this.callReceiveBet = true;
    }

    // Method to change callReceiveGameState to let the Thread in run() know, 
    // that it's supossed to call the receiveBet() method (call this for Multi-Threading only)
    public void callSendGameState(ArrayList<ServerThread> threads, Dealer dealer){
        this.threads = threads;
        this.dealer = dealer;
        this.callSendGameState = true;
    }

    // Method to change callReceiveMove to let the Thread in run() know, 
    // that it's supossed to call the receiveBet() method (call this for Multi-Threading only)
    public void callReceiveMove(){
        this.callReceiveMove = true;
    }

    // Method to change callReceiveName to let the Thread in run() know, 
    // that it's supossed to call the receiveBet() method (call this for Multi-Threading only)
    public void callReceiveName(){
        this.callReceiveName = true;
    }

    // Method to change callSendGameResult to let the Thread in run() know, 
    // that it's supossed to call the sendGameResult method (call this for Multi-Threading only)
    public void callSendGameResult(){
        this.callSendGameResult = true;
    }
}