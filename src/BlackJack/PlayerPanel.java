package BlackJack;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayerPanel extends JPanel {
	private JButton hitButton, standButton, foldButton;
	/**
	 * Create the panel. SIZE: 450x320
	 */
	public PlayerPanel(Players p) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		JPanel cardPanel = new CardPanel(p);		// TODO: new Class for card Panel
		JPanel infoPanel = new JPanel();
		
		infoPanel.add(new JLabel("Name: " + p.getName() + ", Balance: " + p.getCash() + "$, Score: " + p.getTotalPoints()), BorderLayout.NORTH);
		
		buttonPanel.setLayout(new GridLayout(1, 0, 0, 0));
		add(buttonPanel, BorderLayout.SOUTH);
		add(infoPanel, BorderLayout.NORTH);
		add(cardPanel, BorderLayout.CENTER);
		

	    buttonPanel.setBorder(BorderFactory.createTitledBorder("Options"));
	    cardPanel.setBorder(BorderFactory.createTitledBorder("Cards"));
	    infoPanel.setBorder(BorderFactory.createTitledBorder("Player"));
	    		  
	    
		this.hitButton = new JButton("(H)it");
		this.standButton = new JButton("(S)tand");
		this.foldButton = new JButton("(F)old");
		
		this.hitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("HIT");
				// TODO: add Hit action
			}
		});
		this.standButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("STAND");
				// TODO: add Stand action
			}
		});
		this.foldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("FOLD");
				// TODO: add Fold action
			}
		});
		
		buttonPanel.add(foldButton);
		buttonPanel.add(hitButton);
		buttonPanel.add(standButton);
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
}
