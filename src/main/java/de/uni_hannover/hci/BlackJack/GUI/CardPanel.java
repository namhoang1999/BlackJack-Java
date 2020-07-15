package src.main.java.de.uni_hannover.hci.BlackJack.GUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import src.main.java.de.uni_hannover.hci.BlackJack.SerializableData.*;;
public class CardPanel extends Label {
	private final int CARD_WIDTH = 90;
	private final int CARD_LENGTH = 140;
	
	public CardPanel(Card c) {
		try {
			setGraphic(new ImageView(new Image(new FileInputStream(c.generateFilePath()))));
		} catch (FileNotFoundException e) {
			System.err.println("CANNOT FIND THE FILE WTF");
			e.printStackTrace();
		}
	    minWidth(CARD_WIDTH);
	    minHeight(CARD_LENGTH);
	}
}