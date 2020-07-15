package src.main.java.de.uni_hannover.hci.BlackJack.Server;

/**
 * This class is used to start the Server, 
 * without having to use the GUI, which is how it is supposed to be started later.
 * Has to be started with to parameters: Server-port and number of players.
 * @author Jean-Carl Kremser
 * @version 2020.06.29
 */
public class ServerTest {

    public static void main(String[] args) {
        
        // exit if wrong numbers of parameters
        if(args.length != 2){
            System.out.println("Client was executed with wrong number of parameters.");
            System.exit(0);
        }   

        // Server port
        int port = Integer.parseInt(args[0]);
        // max number of players
        int maxClients = Integer.parseInt(args[1]);

        // Starting Server
        Server server = new Server();
        server.run(port, maxClients);
    }
}