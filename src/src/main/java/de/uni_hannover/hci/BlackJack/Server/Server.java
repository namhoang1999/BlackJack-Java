package src.main.java.de.uni_hannover.hci.BlackJack.Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import src.main.java.de.uni_hannover.hci.BlackJack.Server.ServerThread;
import src.main.java.de.uni_hannover.hci.BlackJack.GameLogic.Game;

/**
 * This Class represents the Server. It has to be executed with two parameters: port and max Number of player. 
 * It verifies Clients via Handshake and hands them to a ServerThread. Then it gives every Client an ID.
 * As of right now, it doesn't do anything after that, but in the future it is suppossed to start the game Simulation.
 * @author Jean-Carl Kremser
 * @version 2020.06.29
 */
public class Server {

    // Server Information
    InetAddress host;
    String hostname;
    String ip;
    int port;
    int maxClients;

    // Default Constructor
    public Server(){}

    /**
     * This methods needs to be called to start the Server.
     * @param port          Server port
     * @param maxClients    max Number of clients (Game only starts if the max number of clients connected)
     * @author              Jean-Carl Kremser
     * @version             2020.06.25
     */
    public void run(int port, int maxClients){
        this.port = port;
        this.maxClients = maxClients;

        // Server tries to initialize a new Socket with given port
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            // Server-Information
            host = InetAddress.getLocalHost();
            hostname = host.getHostName();
            ip = host.getHostAddress();
            
            // Prints Information. Just for Convinience.
            System.out.println("Server is hosted on : " + hostname);
            System.out.println("With  the  IP-Adress: " + ip);
            System.out.println("Listening  to   port:  " + port + "\n");
 
            // Array for the Threads that are made for the Clients.
            ArrayList<ServerThread> threads = new ArrayList<ServerThread>();

            // Makes a Thread for every Client that connects, until maxClients is reached, then the Program continues.
            int clients = 0;
            while (clients < maxClients) {
                Socket socket = serverSocket.accept();

                // Waiting for handshake
                String handshake;
                do{
                    // Stuff to get Client input
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    handshake = reader.readLine();
                    if(handshake.equals("handshake")){
                        // Send handshake back
                        OutputStream output = socket.getOutputStream();
                        PrintWriter writer = new PrintWriter(output, true);
                        writer.println("handshake");

                        // Adding Client to Client-List
                        threads.add((ServerThread) new ServerThread(socket, clients));
                        System.out.println("Client " + clients + " connected.");
                        threads.get(clients).start();
                        
                        clients++;    
                        break;            
                    }
                    else {
                        // In case the handshake is wrong the socket is closed.
                        System.out.println("Received wrong Handshake.");
                        OutputStream output = socket.getOutputStream();
                        PrintWriter writer = new PrintWriter(output, true);
                        writer.println("invalid handshake");
                        socket.close();
                        break;
                    }
                }  while(true);
            }

            System.out.println("All Clients connected!\n");

            // close socket so nobody can connect anymore
            serverSocket.close();

            // Sends ID's to Clients
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).sendID();
            }

            // Creates a new Game and starts it with the now connected Players
            Game game = new Game(threads);
            //game.gameLoop();

            // (Debug) Just stops the Server from Exiting when it's finished.
            //while (true);
            
        // In Case Socket-Initializatin-Fails (should not happen)
        } catch(IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}