package hufs.ces.grimtalk.core;
	
import hufs.ces.rmi.RMIMessanger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GrimTalkFXMLMain extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("GrimTalk");
		primaryStage.setOnCloseRequest(e ->{
			Platform.exit();
			System.exit(0);
		});		

		GrimTalkFXController root = new GrimTalkFXController();
		root.parentStage = primaryStage;
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
	
}
