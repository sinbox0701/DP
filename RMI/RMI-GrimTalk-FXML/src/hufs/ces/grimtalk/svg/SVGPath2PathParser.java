package hufs.ces.grimtalk.svg;

import java.io.StringReader;

import org.apache.batik.parser.DefaultPathHandler;
import org.apache.batik.parser.PathParser;

import javafx.scene.shape.Path;

public class SVGPath2PathParser {

	private Path path = null;  //  @jve:decl-index=0:

	public SVGPath2PathParser(String spath) {
		
		path = new Path();

		PathParser pp = new PathParser();
		DefaultPathHandler pathHandler = new SVGPath2PathHandler(path);
		pp.setPathHandler(pathHandler);

		pp.parse(new StringReader(spath));

	}

	public Path getPath() {
		return path;
	}
}
