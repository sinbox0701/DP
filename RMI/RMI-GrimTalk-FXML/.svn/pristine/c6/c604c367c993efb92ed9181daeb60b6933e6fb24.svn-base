package hufs.ces.grimtalk.svg;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class SVGUtils {
	
	static public SVGPath convertShapeToSVGPath(SVGGrimShape gshape) {
		SVGPath svgPath = new SVGPath();
		String style = getSVGStyleString(gshape.getShape());
		paintShapeWithSVGStyle(svgPath, style);
		Path2D pa2d = gshape.getPath2DShape();
		String content = getSVGPathFromSwingShape(pa2d);
		svgPath.setContent(content);
		return svgPath;
	}
	static public Path convertSVGPathToPath(SVGPath svgPath) {
		String content = svgPath.getContent();
		SVGPath2PathParser p2lParser = new SVGPath2PathParser(content);
		Path path = p2lParser.getPath();
		
		path.setFill(svgPath.getFill());
		path.setStroke(svgPath.getStroke());
		path.setStrokeWidth(svgPath.getStrokeWidth());
		
		return path;
	}
	static public String getSVGElementFromSVGPath(SVGPath svgPath) {
		StringBuilder svgSB = new StringBuilder();
		svgSB.append("<path ");
		
		svgSB.append("d=\"");
		svgSB.append(svgPath.getContent());
		svgSB.append('"');
		svgSB.append(' ');
		
		
		svgSB.append(getSVGStyleAttribute(svgPath));
		svgSB.append(" />");
		return svgSB.toString();
		
	}
	static public Path2D getPath2DFromSwingShape(java.awt.Shape sh) {
		
		Path2D pa = new Path2D.Double();
		PathIterator iter = sh.getPathIterator(null);
		
		double[] seg = new double[6];
		while (!iter.isDone()) {
			int segType = iter.currentSegment(seg);
			switch (segType) {
			case PathIterator.SEG_MOVETO:
				pa.moveTo(seg[0], seg[1]);
				break;
			case PathIterator.SEG_LINETO:
				pa.lineTo(seg[0], seg[1]);
				break;
			case PathIterator.SEG_QUADTO:
				pa.quadTo(seg[0], seg[1], seg[2], seg[3]);
				break;
			case PathIterator.SEG_CUBICTO:
				pa.curveTo(seg[0], seg[1], seg[2], seg[3], seg[4], seg[5]);
				break;
			case PathIterator.SEG_CLOSE:
				pa.closePath();
				break;
			}
			iter.next();
		}
		return pa;
	}
	static public String getSVGPathFromSwingShape(java.awt.Shape sh) {
		
		StringBuilder defString = new StringBuilder();
		// defString d="M 10 10 L 20 10 L 20 30 M 40 40 L 55 35"
		
		PathIterator iter = sh.getPathIterator(null);
		double[] seg = new double[6];
		while (!iter.isDone()) {
			String segStr = null;
			int segType = iter.currentSegment(seg);
			switch (segType) {
			case PathIterator.SEG_MOVETO:
				segStr = String.format("M%.1f %.1f ", seg[0], seg[1]);
				break;
			case PathIterator.SEG_LINETO:
				segStr = String.format("L%.1f %.1f ", seg[0], seg[1]);
				break;
			case PathIterator.SEG_QUADTO:
				segStr = String.format("Q%.1f %.1f %.1f %.1f ",	seg[0], seg[1], seg[2], seg[3]); 
				break;
			case PathIterator.SEG_CUBICTO:
				segStr = String.format("C%.1f %.1f %.1f %.1f %.1f %.1f ",
						seg[0], seg[1], seg[2], seg[3], seg[4], seg[5]); 
				break;
			case PathIterator.SEG_CLOSE:
				segStr = "Z";
				break;
			}
			defString.append(segStr);
			iter.next();
		}
		
		return defString.toString();
	}
	
	static public String getSVGStyleAttribute(Shape shape) {
		StringBuilder styleSB = new StringBuilder("style=\"");
		
		styleSB.append(getSVGStyleString(shape));
		styleSB.append('"');
		
		//System.out.println(styleSB.toString());
		return styleSB.toString();
	}
	static public String getSVGStyleString(Shape shape) {
		StringBuilder styleSB = new StringBuilder();
		Paint fill = shape.getFill();
		if (fill instanceof Color) {
			Color fcolor = (Color)fill;
			if (fcolor == Color.TRANSPARENT) {
				styleSB.append(" fill:none;");
			}			
			else {
				styleSB.append(" fill:"+rgbColor(fcolor)+";");
			}
		}
		else if (fill != null){
			styleSB.append(" fill:white;");
		}
		Paint stroke = shape.getStroke();
		if (stroke instanceof Color) {
			Color scolor = (Color)stroke;
			if (scolor == Color.TRANSPARENT) {
				styleSB.append(" stroke:none;");
			}
			else {
				styleSB.append(" stroke:"+rgbColor(scolor)+";");
				Double strokeWidth = shape.getStrokeWidth();
				String widthStr = String.format("%.1f", strokeWidth);
				styleSB.append(" stroke-width:"+widthStr+";");
			}
		}
		else if (stroke != null){
			styleSB.append(" stroke:white;");
		}

		return styleSB.toString();
	}
	static public void paintShapeWithSVGStyle(Shape shape, String styleDefString) {

		Color fillColor = Color.BLACK;
		Color strokeColor = Color.TRANSPARENT;
		double strokeWidth = 1;
		
		if (styleDefString!=null && !styleDefString.equals("")){
			String[] styleAttrs = styleDefString.split(";");
			
			for (String attr:styleAttrs) {
				//System.out.println("attr="+attr);
				if (attr.indexOf("fill") >=0) {
					fillColor = getColorFromStyleString(attr);
				}
				else if (attr.indexOf("stroke-width") >=0) {
					strokeWidth = getStrokeWidth(attr);
				}
				else if (attr.indexOf("stroke") >=0) {
					strokeColor = getColorFromStyleString(attr);
				}
			}
		}
		//System.out.println("fcolor="+fillColor+" scolor="+strokeColor+" swidth="+strokeWidth);
		shape.setFill(fillColor);
		shape.setStroke(strokeColor);
		shape.setStrokeWidth(strokeWidth);
		
	}

	static Color getColorFromStyleString (String attr) {
		Color color = null;
		int pos = attr.indexOf("rgb");
		if (pos != -1)
			color = getColorFromRGBString(attr.substring(pos).trim());
		else {
			pos = attr.indexOf("#");
			if (pos != -1)
				color = Color.web(attr.substring(pos).trim());
			else
				color = Color.TRANSPARENT;
		}
		return color;
	}
	static Color getColorFromRGBString(String rgbStr){
		Color res = null;
		try {
			int istart = rgbStr.indexOf("rgb(");
			if (istart == -1) {
				throw new Exception("rgb Color Format Error");
			}
			int iend = rgbStr.indexOf(")");
			if (iend == -1) throw new Exception("rgb Color Format Error");;
			//System.out.println("rgbStr="+rgbStr+" trimed="+rgbStr.substring(istart+4, iend));
			String[] srgb = rgbStr.substring(istart+4, iend).split(",");
			if (srgb.length != 3) throw new Exception("rgb Color Format Error");;

			res = Color.rgb(Integer.parseInt(srgb[0]), Integer.parseInt(srgb[1]), Integer.parseInt(srgb[2]));
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return res;
	}
	static double getStrokeWidth(String widStr){
		int istart = widStr.indexOf(":");
		if (istart == -1) return 1;
		String numStr = widStr.substring(istart+1).trim();

		return Double.parseDouble(numStr);
	}
	static String rgbColor(Color color){
		return String.format("rgb(%d,%d,%d)", 
			(int)(color.getRed()*255), (int)(color.getGreen()*255), (int)(color.getBlue()*255));
	}

}
