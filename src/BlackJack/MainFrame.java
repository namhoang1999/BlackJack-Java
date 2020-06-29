package BlackJack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private GridBagConstraints c;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1850, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);
	    c = new GridBagConstraints();
	    gbl.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
	    gbl.rowHeights = new int[]{0, 0, 0};
	    gbl.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	    gbl.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		
	    Deck d = new Deck();
	    Dealer dealer = new Dealer(d);
		Players p = new Player("Player 1", 1000);
		Players p1 = new Player("Player 2", 1000);
		Players p2 = new Player("Player 3", 1000);
		Players p3 = new Player("Player 4", 1000);
		dealer.deal(dealer, true);
		dealer.deal(dealer, false);
		dealer.deal(p, true);
		dealer.deal(p, true);
		dealer.deal(p1, true);
		dealer.deal(p1, true);
		dealer.deal(p2, true);
		dealer.deal(p2, true);
		dealer.deal(p3, true);
		dealer.deal(p3, true);
		
		JPanel panel_0 = new JPanel();
		JPanel panel = new PlayerPanel(dealer);
		panel.setPreferredSize(new Dimension(450,320));
		
		panel_0.add(panel, BorderLayout.CENTER);
		panel_0.setBorder(BorderFactory.createTitledBorder("border"));
		setGrid(0,0);
		c.gridwidth = 4;
		contentPane.add(panel_0, c);
		

		c.gridwidth = 1;
		JPanel panel_2 = new PlayerPanel(p);				// Player 1
		panel_2.setPreferredSize(new Dimension(450,320));
		setGrid(0,1);
		contentPane.add(panel_2, c);
		
		JPanel panel_3 = new PlayerPanel(p1);				// Player 2
		panel_3.setPreferredSize(new Dimension(450,320));
		setGrid(1,1);
		contentPane.add(panel_3, c);
		
		JPanel panel_4 = new PlayerPanel(p2);				// Player 3
		panel_4.setPreferredSize(new Dimension(450,320));
		setGrid(2,1);
		contentPane.add(panel_4, c);
		
		JPanel panel_5 = new PlayerPanel(p3);				// Player 4
		panel_5.setPreferredSize(new Dimension(450,320));
		setGrid(3,1);
		contentPane.add(panel_5, c);
		
//		JPanel panel_6 = new PlayerPanel(p);				// Player 4
////		panel_6.setPreferredSize(new Dimension(450,320));
//		setGrid(4,1);
//		contentPane.add(panel_6, c);
	}

	private void setGrid(int x, int y) {
		c.gridx = x;
		c.gridy = y;
	}
}
