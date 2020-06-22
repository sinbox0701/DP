/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimtalk.state;

import hufs.ces.grimtalk.core.GrimTalkModel;
import hufs.ces.grimtalk.core.ShapeFactory;
import hufs.ces.grimtalk.svg.SVGGrimPath;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
/**
 * @author cskim
 *
 */
public class PencilBuilderState implements EditState {

	ShapeFactory sf = null;	
	GrimTalkModel model = null;
	
	public PencilBuilderState(GrimTalkModel model, ShapeFactory sf){
		this.model = model;
		this.sf = sf;
	}
	@Override
	public int getStateType() {
		return EditState.SHAPE_PENCIL;
	}
	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMousePressed(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setStartMousePosition(p1);
		model.setCurrMousePosition(p1);
		model.setPrevMousePosition(p1);		

		model.curDrawShape = new SVGGrimPath((Path)(sf.createPaintedShape(new Path(new MoveTo(p1.getX(), p1.getY())))));

	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseReleased(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setPrevMousePosition(model.getCurrMousePosition());
		model.setCurrMousePosition(p1);

		((Path)model.curDrawShape.getShape()).getElements().add(new LineTo(p1.getX(), p1.getY()));
		if (model.curDrawShape != null){
			model.shapeList.add(model.curDrawShape);
			model.curDrawShape = null;
			model.addShapeAction();
		}

	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseDragged(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setPrevMousePosition(model.getCurrMousePosition());
		model.setCurrMousePosition(p1);

		((Path)model.curDrawShape.getShape()).getElements().add(new LineTo(p1.getX(), p1.getY()));

	}

}
