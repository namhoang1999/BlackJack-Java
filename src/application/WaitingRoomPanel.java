package application;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Transform;


public class WaitingRoomPanel extends Pane{
	private ArrayList<WaitingPlayerPane> list = new ArrayList<WaitingPlayerPane>();
	public void addPlayer(Player p) {
		list.add(new WaitingPlayerPane(p));
	}
	
	public boolean readyCheck() {
		boolean allReady = true;
		for(WaitingPlayerPane w : list) {
			allReady = allReady&w.getIsReady();
		}
		return allReady;
	}
		
	public void draw() {
		getChildren().clear();
		for(WaitingPlayerPane w: list) {
			w.getTransforms().add(Transform.translate(0, 200*list.size()));
			getChildren().add(w);
		}
	}
	
	public WaitingRoomPanel() {
		setPrefSize(1362, 898);
		draw();
	}
}
