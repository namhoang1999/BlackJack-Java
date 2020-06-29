package BlackJack;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayerPanel extends JPanel {
	private JButton hitButton, standButton, foldButton;
	private JPanel buttonPanel, cardPanel, infoPanel;
	private Players player;
	
	/**
	 * Create the panel. SIZE: 450x320
	 */
	public PlayerPanel(Players p) {
		this.player = p;
		
		setLayout(new BorderLayout(0, 0));
		
		buttonPanel = new JPanel();
		cardPanel = new CardPanel(p);		// new Class for card Panel
		infoPanel = new JPanel();
		
		hitButton = new JButton("(H)it");
		standButton = new JButton("(S)tand");
		foldButton = new JButton("(F)old");
		
		hitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("HIT");
				// TODO: add Hit action
			}
		});
		standButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("STAND");
				// TODO: add Stand action
			}
		});
		foldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("FOLD");
				// TODO: add Fold action
			}
		});
		
		draw();
	}
	
	/**
	 * Disable buttons when it's not the player's turn
	 * or
	 * Enable buttons when it is the player's turn
	 */
	public void clickableButtons(boolean b) {
		hitButton.setEnabled(b);
		foldButton.setEnabled(b);
		standButton.setEnabled(b);
	}
	
	/**
	 * Draw and redraw the components
	 */
	public void draw() {
		infoPanel.add(new JLabel("Name: " + this.player.getName() + 
								 ", Balance: " + this.player.getCash() + 
								 "$, Score: " + this.player.getTotalPoints()), BorderLayout.NORTH);		  
	    
		buttonPanel.setLayout(new GridLayout(1, 0, 0, 0));
		add(buttonPanel, BorderLayout.SOUTH);
		add(infoPanel, BorderLayout.NORTH);
		add(cardPanel, BorderLayout.CENTER);
		
		TitledBorder cardBorder = BorderFactory.createTitledBorder("Cards");
		cardBorder.setTitleColor(Color.WHITE);
		
	    cardPanel.setBorder(cardBorder);
	    infoPanel.setBorder(BorderFactory.createTitledBorder("Player"));
	    
	    if (this.player instanceof Player) {
		    buttonPanel.setBorder(BorderFactory.createTitledBorder("Options"));
			buttonPanel.add(foldButton);
			buttonPanel.add(hitButton);
			buttonPanel.add(standButton);
	    }
	}
}
