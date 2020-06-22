package hufs.ces.grimtalk.state;

import javax.swing.JOptionPane;

import hufs.ces.grimtalk.core.GrimTalkModel;
import hufs.ces.grimtalk.core.ShapeFactory;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class RemoveBuilderState implements EditState {

	ShapeFactory sf = null;	
	GrimTalkModel model = null;
	
	public RemoveBuilderState(GrimTalkModel model, ShapeFactory sf){
		this.model = model;
		this.sf = sf;
	}
	@Override
	public int getStateType() {
		model.removeShapeAction();
		return EditState.EDIT_REMOVE;
	}
	@Override
	public void performMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
}
