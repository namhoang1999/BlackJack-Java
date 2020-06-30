package application;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class TablePanel extends Pane {
	public TablePanel() {
		File file = new File("Icon/xskat/Table.png");
		Image image = new Image(file.toURI().toString());
	    ImageView iv = new ImageView(image);
//	    setPrefSize(1362,898);
	    setMinWidth(1362);
	    setMinHeight(898);
	    getChildren().add(iv);
	}
}
