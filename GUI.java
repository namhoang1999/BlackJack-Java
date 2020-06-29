package BlackJack;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.JFrame;


public class GUI extends JFrame{
	int aH = 1280;
	int aW = 800;
	public GUI() {
		this.setSize(aW, aH);
		this.setTitle("BlackJack");
		this.setVisible(true);
	}
}
