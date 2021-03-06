package hufs.ces.grimtalk.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Optional;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import hufs.ces.grimtalk.state.EditState;
import hufs.ces.grimtalk.svg.SVGGrimShape;
import hufs.ces.grimtalk.svg.SaxSVGPathParseHandler;
import hufs.ces.rmi.RMIMessanger;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GrimTalkFXController extends AnchorPane {

	private static final String HOST = "localhost";
	private RMIMessanger rmiObj;
	
	private String clientID = null;
	private String partnerID = null;
	private ReceiverThread receiver = null;

	private DrawPane drawPane;
	
	public Stage parentStage;
	private GrimTalkModel model;
	
	DoubleProperty widthProp = new SimpleDoubleProperty();
	DoubleProperty heightProp = new SimpleDoubleProperty();
	
	ColorPicker fcolorPicker = new ColorPicker(Color.WHITE);
	ColorPicker scolorPicker = new ColorPicker(Color.BLACK);
	
	public GrimTalkFXController() {

		model = GrimTalkModel.getInstance();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/grimtalk.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fcolorPicker.setOnAction(t->{
			Color color = fcolorPicker.getValue();
			model.setShapeFillColor(color);
		});
		Label fcolorLabel =  new Label("", fcolorPicker);
		fcolorLabel.setContentDisplay(ContentDisplay.RIGHT);
		fcolorLabel.setStyle("-fx-padding: 0 0 0 15;");
		menuFillColor.setGraphic(fcolorLabel);

		scolorPicker.setOnAction(t->{
			Color color = scolorPicker.getValue();
			model.setShapeStrokeColor(color);
		});
		Label scolorLabel =  new Label("", scolorPicker);
		scolorLabel.setContentDisplay(ContentDisplay.RIGHT);
		scolorLabel.setStyle("-fx-padding: 0 0 0 15;");
		menuStrokeColor.setGraphic(scolorLabel);
		
		drawPane = new DrawPane();
		drawPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		drawPane.setOnMousePressed(e->handleMousePressed(e));
		drawPane.setOnMouseReleased(e->handleMouseReleased(e));
		drawPane.setOnMouseDragged(e->handleMouseDragged(e));
		drawFramePane.setCenter(drawPane);

		widthProp.bind(drawPane.widthProperty());
		widthProp.addListener((obs, oldVal, newVal) -> {
			double val = ((ObservableDoubleValue)obs).get();
			//System.out.format("drawPane w=%s newVal=%s \n", val, newVal);
		});
		heightProp.bind(drawPane.heightProperty());
		heightProp.addListener((obs, oldVal, newVal) -> {
			double val = ((ObservableDoubleValue)obs).get();
			//System.out.format("drawPane h=%s newVal=%s \n", val, newVal);
		});
		
		model.shapeList.addListener((ListChangeListener<SVGGrimShape>) c-> {
	        while (c.next()) {
	            if (c.wasAdded()) {
	            	//System.out.println("Shape Count ="+model.shapeList.size());
	            }
	            if (c.wasRemoved()) {
	            	//System.out.println("Shape Count ="+model.shapeList.size());
	            }
	        }
		});
		
		initDrawPane();
		
		initRMIObj();
		
	}
	
	void initRMIObj() {
		//System.setSecurityManager(new RMISecurityManager());
		Object obj = null;
		Context namingContext = null;
		String rmiObjectName = "rmi://" + HOST + "/RMIMessanger";
		try {
			namingContext = new InitialContext();
			System.out.println("RMI registry bindings: ");
			Enumeration<NameClassPair> e = namingContext.list("rmi://localhost/");
			while (e.hasMoreElements())
				System.out.println(e.nextElement().getName()+" is in naming Context List");
			
			obj = namingContext.lookup(rmiObjectName);
			rmiObj = (RMIMessanger) obj;
		} catch (NamingException e1) {
			System.err.println("Could not find the requested remote object on the server");
			e1.printStackTrace();
		}
	}
	
	void initDrawPane() {		
		model.shapeList.clear();
		model.curDrawShape = null;
		drawPane.redraw();
	}
	
    @FXML
    private AnchorPane root;

    @FXML
    private BorderPane drawFramePane;

	
    @FXML
    private MenuItem menuNew;

    @FXML
    private MenuItem menuExit;

    @FXML
    private RadioMenuItem menuLine;

    @FXML
    private RadioMenuItem menuPencil;

    @FXML
    private MenuItem menuMove;

    @FXML
    private MenuItem menuDelete;

    @FXML
    private MenuItem menuRemove;
    
    @FXML
    private MenuItem menuStrokeWidth;

    @FXML
    private MenuItem menuStrokeColor;

    @FXML
    private MenuItem menuFillColor;

    @FXML
    private CheckMenuItem menuCheckStroke;

    @FXML
    private CheckMenuItem menuCheckFill;

    @FXML
    private MenuItem menuAbout;

    
    @FXML
    private Label lblMessage;

    @FXML
    private Label lblEditState;

    @FXML
    private Label lblShapeCount;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnSend;
    
    @FXML 
    private ImageView RimgView;
    
    @FXML 
    private ImageView CimgView;
    
    @FXML 
    private ImageView SimgView;

    @FXML
    void handleMenuAbout(ActionEvent event) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(parentStage);   
		alert.setTitle("About");
		alert.setHeaderText("GrimTalk Ver 0.1.1");
		alert.setContentText("Programmed by cskim, ces, hufs.ac.kr");

		alert.showAndWait();
    }

    @FXML
    void handleMenuCheckFill(ActionEvent event) {
		CheckMenuItem checkFill = (CheckMenuItem)event.getSource();
		if (checkFill.isSelected())
			model.setShapeFill(true);
		else
			model.setShapeFill(false);
    }

    @FXML
    void handleMenuCheckStroke(ActionEvent event) {
		CheckMenuItem checkStroke = (CheckMenuItem)event.getSource();
		if (checkStroke.isSelected())
			model.setShapeStroke(true);
		else
			model.setShapeStroke(false);
    }

    @FXML
    void handleMenuDelete(ActionEvent event) {
    	changeEditState(model.STATE_DELETE);
		drawPane.redraw();
    }

    @FXML
    void handleMenuExit(ActionEvent event) {
		Platform.exit();
    }

    @FXML
    void handleMenuFillColor(ActionEvent event) {
    }

    @FXML
    void handleMenuLine(ActionEvent event) {
    	changeEditState(model.STATE_LINE);
		drawPane.redraw();
    }

    @FXML
	void handleMenuUndo(ActionEvent event) {
		model.undoAction();
		drawPane.redraw();
	}

    @FXML
	void handleMenuRemove(ActionEvent event) {
    	changeEditState(model.STATE_REMOVE);
		model.removeShapeAction();
		drawPane.redraw();
	}
    
    @FXML
    void handleMenuMove(ActionEvent event) {
    	changeEditState(model.STATE_MOVE);
		if (model.curDrawShape != null){
			model.shapeList.add(model.curDrawShape);
			model.curDrawShape = null;
		}
		drawPane.redraw();
    }

    @FXML
    void handleMenuNew(ActionEvent event) {
		initDrawPane();
    }

    @FXML
    void handleMenuPencil(ActionEvent event) {
    	changeEditState(model.STATE_PENCIL);
		drawPane.redraw();
    }


    @FXML
    void handleMenuStrokeColor(ActionEvent event) {
    }

    @FXML
    void handleMenuStrokeWidth(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("10");
		dialog.initOwner(parentStage);
		dialog.setTitle("Set Stroke Width");
		dialog.setHeaderText("Enter Stroke Width Value");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String inputVal = result.get();
			model.setShapeStrokeWidth(Float.parseFloat(inputVal));
		}

    }

	// Mouse Event Handler
    void handleMouseEntered(MouseEvent event) {
    	//model.setMouseInside(true);
    }

    void handleMouseExited(MouseEvent event) {
    	//model.setMouseInside(false);
    }

    void handleMousePressed(MouseEvent event) {

		if (event.getButton()==MouseButton.PRIMARY){
			model.editState.performMousePressed(event);
		}		
		drawPane.redraw();

	}    

    void handleMouseReleased(MouseEvent event) {

		if (event.getButton()==MouseButton.PRIMARY){
			model.editState.performMouseReleased(event);
		}
		drawPane.redraw();
		
	}

	void handleMouseDragged(MouseEvent event) {

		if (event.getButton()==MouseButton.PRIMARY){
			model.editState.performMouseDragged(event);
		}
		drawPane.redraw();
	}
	
	void changeEditState(EditState state) {
		// Todo lblEditState Setting
		System.out.format("editState=%s\n", state);
		model.setEditState(state);
	}
	
    @FXML
    void handleConnectBtn(ActionEvent event) {
		try {
			String id = rmiObj.connect(clientID);
			System.out.println(id);
			if (id != null) {
				partnerID = id;

				lblMessage.setText("You connected to ID:" + partnerID + "\n");
				btnConnect.setDisable(true);
				

				receiver = new ReceiverThread(rmiObj);
				receiver.start();
			}
			else
				lblMessage.setText("You can not connect. Try again\n");

		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

	
    @FXML
    void handleRegisterBtn(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(parentStage);
		dialog.setTitle("Register ID");
		dialog.setHeaderText("Enter Your ID");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String id  = result.get();
			try {
				if (rmiObj.register(id) ) {
					clientID = id; 
					btnRegister.setDisable(true);
					parentStage.setTitle("GrimTalk -- ID <"+id+"> ");
				}
				else {
					lblMessage.setText("Invalid ID:" + id + "\n");
				}
			} 
			catch (RemoteException e1) {
				e1.printStackTrace();

			}
		}
    }

    @FXML
    void handleSendBtn(ActionEvent event) {
		sendGrimAction();
    }
	void sendGrimAction(){
		StringBuilder sb = new StringBuilder();
		
		//sb.append("<?xml version='1.0' encoding='utf-8' standalone='no'?> \n");
		sb.append("<svg xmlns:svg='http://www.w3.org/2000/svg' ");
		sb.append("     xmlns='http://www.w3.org/2000/svg' \n");
		sb.append(String.format("width='%f.0' ", widthProp.getValue()));
		sb.append(String.format("height='%f.0' ", heightProp.getValue()));
		sb.append("overflow='visible' xml:space='preserve'> \n");
		for (SVGGrimShape gs:model.shapeList){
			sb.append("    "+gs.getSVGShapeString());
			sb.append('\n');
		}
		sb.append("</svg>\n");

		String theLines = sb.toString();
		//System.out.println(theLines);

		try {
			rmiObj.write(clientID, theLines);
		} 
		catch (RemoteException e1) {
			e1.printStackTrace();

		}
	}

	class ReceiverThread extends Thread {

		private RMIMessanger messanger;

		public ReceiverThread(RMIMessanger messanger) {
			this.messanger = messanger;
		}

		public void run() {

			while (true) {
				
				convertSVGText2SVGGrimShapeList();	
				
				Thread.yield();

			}  

		}
		void convertSVGText2SVGGrimShapeList() {
			SaxSVGPathParseHandler saxTreeHandler = new SaxSVGPathParseHandler(); 

			try {
				String theLines = messanger.read(clientID);
				//System.out.println("received svg="+theLines);
				InputStream grimStream = new ByteArrayInputStream(theLines.getBytes());

				SAXParserFactory saxf = SAXParserFactory.newInstance();
				SAXParser saxParser = saxf.newSAXParser();
				
				saxParser.parse(new InputSource(grimStream), saxTreeHandler);
			}
			catch(Exception e){
				e.printStackTrace();
			}

			model.shapeList.clear();
			ObservableList<SVGGrimShape> gshapeList = saxTreeHandler.getPathList();
			for (SVGGrimShape gsh:gshapeList) {
				model.shapeList.add(gsh);
			}
			
			Platform.runLater(()->{
				drawPane.redraw();
			});

		}

	}
	
}
