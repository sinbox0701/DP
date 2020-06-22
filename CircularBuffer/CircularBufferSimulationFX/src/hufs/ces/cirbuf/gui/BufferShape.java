package hufs.ces.cirbuf.gui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class BufferShape extends Group {

	private Path bufShape = null;
	private Text bufText = null;
	private Rotate rot = null;
	
	public BufferShape() {
		bufShape = new Path();
		bufShape.setFill(Color.SNOW);
		bufText = new Text();
		rot = new Rotate();
		this.getChildren().add(bufShape);
		this.getChildren().add(bufText);
		this.getTransforms().add(rot);
	}
	
	public void setBackground(Color bg) {
		this.bufShape.setFill(bg);
	}
	public Path getBufShape() {
		return bufShape;
	}
	public void setBufPath(double cx, double cy, double outrad, double angl) {
		double inrad = outrad * 0.5;
		MoveTo m1 = new MoveTo(cx, cy-outrad);
		double outX = cx + outrad * Math.cos(Math.PI/2 - angl);
		double outY = cy - outrad * Math.sin(Math.PI/2 - angl);
		ArcTo a1 = new ArcTo(outrad, outrad, 0, outX, outY, false, true);
		double inX = cx + inrad * Math.cos(Math.PI/2 - angl);
		double inY = cy - inrad * Math.sin(Math.PI/2 - angl);
		LineTo m2 = new LineTo(inX, inY);
		ArcTo a2 = new ArcTo(inrad, inrad, 0, cx, cy-inrad, false, false);
		ClosePath cp = new ClosePath();
		bufShape.getElements().addAll(m1, a1, m2, a2, cp);
	}
	public Text getBufText() {
		return bufText;
	}
	public void setText(String text, double cx, double cy, double outrad, double angl) {
		bufText.setText(text);
		bufText.setX(cx);
		bufText.setY(cy-outrad-5);
		Rotate ro = new Rotate(Math.toDegrees(angl)*0.4, cx, cy);
		bufText.getTransforms().add(ro);
	}
	public void setText(String text) {
		bufText.setText(text);
	}
	public Rotate getRot() {
		return rot;
	}
	public void setRot(double angl, double cx, double cy) {
		rot.setAngle(angl);
		rot.setPivotX(cx);
		rot.setPivotY(cy);		
	}
	
}
