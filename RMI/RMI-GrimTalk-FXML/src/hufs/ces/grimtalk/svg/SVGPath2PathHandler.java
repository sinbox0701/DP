package hufs.ces.grimtalk.svg;

import org.apache.batik.parser.DefaultPathHandler;
import org.apache.batik.parser.ParseException;

import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;


public class SVGPath2PathHandler extends DefaultPathHandler {

	private float absX = 0;
	private float absY = 0;
	private float savX2 = 0;
	private float savY2 = 0;
	
	private Path path = null;  //  @jve:decl-index=0:
	
	public SVGPath2PathHandler(Path path) {
		this.path = path;
	}

	@Override
	public void startPath() throws ParseException {
		
	}

	@Override
	public void movetoRel(float x, float y) throws ParseException {
		// m x y
		absX += x;
		absY += y;
		path.getElements().add(new MoveTo(absX, absY));
		
	}

	@Override
	public void movetoAbs(float x, float y) throws ParseException {
		// M x y
		absX = x;
		absY = y;
		path.getElements().add(new MoveTo(absX, absY));
		
	}

	@Override
	public void endPath() throws ParseException {
		// end path
	}

	@Override
	public void closePath() throws ParseException {
		path.getElements().add(new ClosePath());
	}

	@Override
	public void linetoRel(float x, float y) throws ParseException {
		// l x y
		absX += x;
		absY += y;
		path.getElements().add(new LineTo(absX, absY));
		
	}

	@Override
	public void linetoAbs(float x, float y) throws ParseException {
		// L x y
		absX = x;
		absY = y;
		path.getElements().add(new LineTo(absX, absY));
	}

	@Override
	public void linetoHorizontalRel(float x) throws ParseException {
		// h x
		absX += x;
		path.getElements().add(new LineTo(absX, absY));
	}

	@Override
	public void linetoHorizontalAbs(float x) throws ParseException {
		// H x
		absX = x;
		path.getElements().add(new LineTo(absX, absY));
	}

	@Override
	public void linetoVerticalRel(float y) throws ParseException {
		// v y
		absY += y;
		path.getElements().add(new LineTo(absX, absY));
	}

	@Override
	public void linetoVerticalAbs(float y) throws ParseException {
		// V y
		absY = y;
		path.getElements().add(new LineTo(absX, absY));
	}

	@Override
	public void curvetoCubicRel(float x1, float y1, 
			float x2, float y2, 
			float x, float y) throws ParseException {
		// c x1 y1 x2 y2 x y
		path.getElements().add(new CubicCurveTo(absX+x1, absY+y1, absX+x2, absY+y2, absX+x, absY+y));
		savX2 = absX+x2;
		savY2 = absY+y2;
		absX += x;
		absY += y;
	}

	@Override
	public void curvetoCubicAbs(float x1, float y1, 
			float x2, float y2, 
			float x, float y) throws ParseException {
		// C x1 y1 x2 y2 x y
		path.getElements().add(new CubicCurveTo(x1, y1, x2, y2, x, y));
		savX2 = x2;
		savY2 = y2;
		absX = x;
		absY = y;
	}

	@Override
	public void curvetoCubicSmoothRel(float x2, float y2, 
			float x, float y) throws ParseException {
		// s x2 y2 x y
		float x1 = absX-savX2;
		float y1 = absY-savY2;
		path.getElements().add(new CubicCurveTo(absX+x1, absY+y1, absX+x2, absY+y2, absX+x, absY+y));
		savX2 = absX+x2;
		savY2 = absY+y2;
		absX += x;
		absY += y;
	}

	@Override
	public void curvetoCubicSmoothAbs(float x2, float y2, 
			float x, float y) throws ParseException {
		// S x2 y2 x y
		float x1 = absX+(absX-savX2);
		float y1 = absY+(absY-savY2);
		path.getElements().add(new CubicCurveTo(x1, y1, x2, y2, x, y));
		savX2 = x2;
		savY2 = y2;
		absX = x;
		absY = y;
	}

	@Override
	public void curvetoQuadraticRel(float x1, float y1, 
			float x, float y) throws ParseException {
		// q x1 y1 x y
		// quadTo(double x1, double y1, double x2, double y2)
		path.getElements().add(new QuadCurveTo(absX+x1, absY+y1, absX+x, absY+y));
		savX2 = absX+x1;
		savY2 = absY+y1;
		absX += x;
		absY += y;
	}

	@Override
	public void curvetoQuadraticAbs(float x1, float y1, 
			float x, float y) throws ParseException {
		// Q x1 y1 x y
		path.getElements().add(new QuadCurveTo(x1, y1,x, y));
		savX2 = x1;
		savY2 = y1;
		absX = x;
		absY = y;
	}

	@Override
	public void curvetoQuadraticSmoothRel(float x, float y)
	throws ParseException {
		// t x y
		float x1 = absX-savX2;
		float y1 = absY-savY2;
		path.getElements().add(new QuadCurveTo(absX+x1, absY+y1, absX+x, absY+y));
		savX2 = absX+x1;
		savY2 = absY+y1;
		absX += x;
		absY += y;
	}

	@Override
	public void curvetoQuadraticSmoothAbs(float x, float y)
	throws ParseException {
		// T x y
		float x1 = absX+(absX-savX2);
		float y1 = absY+(absY-savY2);
		path.getElements().add(new QuadCurveTo(x1, y1, x, y));
		savX2 = x1;
		savY2 = y1;
		absX = x;
		absY = y;
	}

	@Override
	public void arcRel(float rx, float ry, 
			float xAxisRotation, 
			boolean largeArcFlag, boolean sweepFlag, 
			float x, float y) throws ParseException {
		// a rx ry xRot (0|1) (0|1) x y
		path.getElements().add(new ArcTo(rx, ry, xAxisRotation, absX+x, absY+y, largeArcFlag, sweepFlag));
		absX += x;
		absY += y;
	}

	@Override
	public void arcAbs(float rx, float ry, 
			float xAxisRotation, 
			boolean largeArcFlag, boolean sweepFlag, 
			float x, float y) throws ParseException {
		// A rx ry xRot (0|1) (0|1) x y
		path.getElements().add(new ArcTo(rx, ry, xAxisRotation, x, y, largeArcFlag, sweepFlag));
		absX = x;
		absY = y;
	}

	public Path getPath() {
		return path;
	}
}


