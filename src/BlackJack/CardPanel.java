package BlackJack;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import BlackJack.Deck.Rank;
import BlackJack.Deck.Suit;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CardPanel extends JPanel {
	private static final int CARD_WIDTH = 93;
	private static final int CARD_HEIGHT = 150;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	/**
	 * Create the panel.
	 */
	public CardPanel(Players p) {
		this.hand = p.getHand();
		setBackground(new Color(9, 74, 50));
		
		int width = CARD_WIDTH*hand.size()/3;
		setSize(width, CARD_HEIGHT + 50);
		setLayout(null);
		
		int x = hand.size();
		for (Card c : hand) {
			JLabel lb = generateCard(c);
			lb.setBounds(CARD_WIDTH*(x-1)/3 + 20, 20, CARD_WIDTH, CARD_HEIGHT);
			add(lb);
			x--;
		}
	}
	
	public JLabel generateCard(Card c) {
		JLabel lb = new JLabel();
		lb.setSize(CARD_WIDTH, CARD_HEIGHT);
		BufferedImage front = null, back = null;
		try {
		    front = ImageIO.read(new File("Icon/front.png"));
		    back  = ImageIO.read(new File("Icon/back.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image fimg = front.getScaledInstance(lb.getWidth(), lb.getHeight(),
											Image.SCALE_SMOOTH);
		Image bimg = back.getScaledInstance(lb.getWidth(), lb.getHeight(),
		                					Image.SCALE_SMOOTH);
		if (c.isUp()) {
			lb.setIcon(new ImageIcon(fimg));
		} else {
			lb.setIcon(new ImageIcon(bimg));
		}
		return lb;
	}
}
