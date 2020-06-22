package hufs.ces.grimpanz;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.JColorChooser;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class GrimPanFXController extends AnchorPane {

	public Stage parentStage;
	private GrimPanModel model;
	private ShapeFactory sf;
	
	DoubleProperty widthProp = new SimpleDoubleProperty();
	DoubleProperty heightProp = new SimpleDoubleProperty();
	
	ColorPicker fcolorPicker = new ColorPicker(Color.WHITE);
	ColorPicker scolorPicker = new ColorPicker(Color.BLACK);
	
	public GrimPanFXController() {

		model = GrimPanModel.getInstance();
		sf = new ShapeFactory(model);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("grimpan0.fxml"));
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
		
		widthProp.bind(drawPane.widthProperty());
		widthProp.addListener((obs, oldVal, newVal) -> {
			double val = ((ObservableDoubleValue)obs).get();
			System.out.format("drawPane w=%s newVal=%s \n", val, newVal);
		});
		heightProp.bind(drawPane.heightProperty());
		heightProp.addListener((obs, oldVal, newVal) -> {
			double val = ((ObservableDoubleValue)obs).get();
			System.out.format("drawPane h=%s newVal=%s \n", val, newVal);
		});
		
		model.shapeList.addListener((ListChangeListener<Shape>) c-> {
	        while (c.next()) {
	            if (c.wasAdded()) {
	            	System.out.println("Shape Count ="+model.shapeList.size());
	            }
	            if (c.wasRemoved()) {
	            	System.out.println("Shape Count ="+model.shapeList.size());
	            }
	        }
		});
		
		initDrawPane();
	}
	void initDrawPane() {
		model.shapeList.clear();
		model.curDrawShape = null;
		clearDrawPane();
		
		System.out.format("drawPane w=%s h=%s\n", drawPane.getWidth(), drawPane.getHeight());
		System.out.format("drawPane Property w=%s h=%s\n", widthProp.get(), heightProp.get());
	}
	
	void clearDrawPane() {
		drawPane.getChildren().clear();
	}
	void redrawDrawPane() {
		clearDrawPane();
		//System.out.println("Shape Count="+model.shapeList.size());
		for (Shape sh:model.shapeList){
			drawPane.getChildren().add(sh);
		}
		if (model.curDrawShape!=null) {
			drawPane.getChildren().add(model.curDrawShape);
		}
	}

    @FXML
    private AnchorPane root;

    @FXML
    private Pane drawPane;

	
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
    private Label lblWinSize;

    @FXML
    private Label lblEditState;

    @FXML
    private Label lblShapeCount;

    @FXML
    void handleMenuAbout(ActionEvent event) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(parentStage);   
		alert.setTitle("About");
		alert.setHeaderText("GrimPan Ver 0.1.1");
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
		model.setEditState(Utils.SHAPE_LINE);
		redrawDrawPane();
    }

    @FXML
    void handleMenuMove(ActionEvent event) {

    }

    @FXML
    void handleMenuNew(ActionEvent event) {
		initDrawPane();
    }

    @FXML
    void handleMenuPencil(ActionEvent event) {
		model.setEditState(Utils.SHAPE_PENCIL);
		redrawDrawPane();
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

    @FXML
    void handleMouseEntered(MouseEvent event) {
    	//model.setMouseInside(true);
    }

    @FXML
    void handleMouseExited(MouseEvent event) {
    	//model.setMouseInside(false);
    }

    @FXML
    void handleMouseDragged(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));

		if (event.getButton()==MouseButton.PRIMARY){
			model.setPrevMousePosition(model.getCurrMousePosition());
			model.setCurrMousePosition(p1);

			switch (model.getEditState()){
			case Utils.SHAPE_LINE:
				model.curDrawShape = sf.createMousePointedLine();
				break;
			case Utils.SHAPE_PENCIL:
				((Path)model.curDrawShape).getElements().add(new LineTo(p1.getX(), p1.getY()));
				break;
			case Utils.EDIT_MOVE:
				break;

			}
		}
		redrawDrawPane();
    }

    @FXML
    void handleMousePressed(MouseEvent event) {
		//System.out.format("drawPane w=%s h=%s\n", drawPane.getWidth(), drawPane.getHeight());
		//System.out.format("drawPane Property w=%s h=%s\n", widthProp.get(), heightProp.get());
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));

		if (event.getButton()==MouseButton.PRIMARY){
			model.setStartMousePosition(p1);
			model.setCurrMousePosition(p1);
			model.setPrevMousePosition(p1);				
			switch (model.getEditState()){
			case Utils.SHAPE_LINE:
				model.curDrawShape = sf.createMousePointedLine();
				break;
			case Utils.SHAPE_PENCIL:
				model.curDrawShape = sf.createPaintedShape(new Path(new MoveTo(p1.getX(), p1.getY())));
				break;
			case Utils.EDIT_MOVE:
				break;
			}
		}
		redrawDrawPane();
    }

    @FXML
    void handleMouseReleased(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		//System.out.println("Mouse Released at "+p1);

		if (event.getButton()==MouseButton.PRIMARY){
			model.setPrevMousePosition(model.getCurrMousePosition());
			model.setCurrMousePosition(p1);

			switch (model.getEditState()){
			case Utils.SHAPE_LINE:
				model.curDrawShape = sf.createMousePointedLine();
				if (model.curDrawShape != null){
					model.shapeList.add(model.curDrawShape);
					model.curDrawShape = null;
				}
				break;
			case Utils.SHAPE_PENCIL:
				((Path)model.curDrawShape).getElements().add(new LineTo(p1.getX(), p1.getY()));
				if (model.curDrawShape != null){
					model.shapeList.add(model.curDrawShape);
					model.curDrawShape = null;
				}
				break;
			case Utils.EDIT_MOVE:
				break;

			}
		}
    }

}
