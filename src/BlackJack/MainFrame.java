package BlackJack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.util.ArrayList;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private GridBagConstraints c;
	private final Dimension D = new Dimension(450,320);
	private ArrayList<Players> players;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
	    Game g = new Game();
	    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, (g.playerSize()-1)*450+10, 720);
		setResizable(false);
		
		// Content Pane
		contentPane = new JPanel();
		contentPane.setBackground(new Color(9, 74, 50));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);
	    
		// Grid Bag Constraints (set to default)
		c = new GridBagConstraints();
	    gbl.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
	    gbl.rowHeights = new int[]{0, 0, 0};
	    gbl.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	    gbl.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		
	    draw(g);
	}
	
	private JPanel bigPanel, dealerPanel, playerPanel;
	private JPanel[] panels = new JPanel[4];
	
	/**
	 * Draw the panel
	 * @param g the Game
	 */
	private void draw(Game g) {
		this.players = g.getPlayers();
		
		for (int i = 0; i < this.players.size(); i++) {
			if (players.get(i) instanceof Dealer) {							 // Dealer
				bigPanel = new JPanel();
				dealerPanel = new PlayerPanel(players.get(i));
				dealerPanel.setPreferredSize(D);
				
				bigPanel.add(dealerPanel, BorderLayout.CENTER);
				bigPanel.setBorder(BorderFactory.createTitledBorder("Dealer"));
				
				setGrid(0,0,4);
				contentPane.add(bigPanel, c);		
			} else {
				if (players.get(i).getCash() >= 0) {
					playerPanel = new PlayerPanel(players.get(i));				// Player 
					playerPanel.setPreferredSize(D);
					setGrid(i,1,1);
					contentPane.add(playerPanel, c);
					panels[i] = playerPanel;
				}
			}
		}
	}

	private void setGrid(int x, int y, int w) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = w;
	}
}
