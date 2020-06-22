package hufs.ces.cirbuf.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CircularBufferSimulationMain extends Application {

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Circular Buffer Simulation");
		primaryStage.setOnCloseRequest(e ->{
			Platform.exit();
			System.exit(0);
		});		
		CircularBufferSimController root = new CircularBufferSimController();
		root.parentStage = primaryStage;
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
