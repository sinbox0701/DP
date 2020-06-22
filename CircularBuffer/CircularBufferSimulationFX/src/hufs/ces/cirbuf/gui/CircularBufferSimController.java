package hufs.ces.cirbuf.gui;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Random;

import hufs.ces.cirbuf.CircularBuffer;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CircularBufferSimController extends AnchorPane {

	private final static int DEFAULT_BUFFER_COUNT = 10;
	
	public Stage parentStage = null;

	volatile CircularBuffer<String> cirbuf = null;
	volatile CircularBuffer<String> cirbuf1 = null;
	volatile CircularBuffer<String> cirbuf2 = null;
	volatile Iterator<BigInteger> fibGen = null;
	BufferShape[] bufShapes = null;
	BufferShape[] bufShapes1 = null;
	BufferShape[] bufShapes2 = null;
	
	//volatile IntegerProperty bufUseCount = new SimpleIntegerProperty(0);
	

    @FXML
    private AnchorPane root;

    @FXML
    private TextField tfBufSize;

    @FXML
    private Label lblCount;

    @FXML
    private Button btnStart;

    @FXML
    private Pane drawPane;
    
    @FXML
    private Pane drawPane1;
    
    @FXML
    private Pane drawPane2;
    
    @FXML
    private Label lblP;
    
    @FXML
    private Label lblC;


    @FXML
    void handleBtnStart(ActionEvent event) {
    	if (cirbuf==null) {
    		initCircularBuffer(DEFAULT_BUFFER_COUNT);
    	}
		Thread prod = new Thread(new ProducerTask());
		Thread cons = new Thread(new ConsumerTask());
		Thread cons1 = new Thread(new ConsumerTask());
		Thread cons2 = new Thread(new ConsumerTask1());
		prod.start();
		cons.start();
		cons1.start();
		cons2.start();
//		
//		Thread prod2 = new Thread(new ProducerTask());
//		Thread cons2 = new Thread(new ConsumerTask());
//		prod2.start();
//		cons2.start();
    }

    @FXML
    void handleBufSizeIn(ActionEvent event) {
    	int siz  = Integer.parseInt(tfBufSize.getText());
		initCircularBuffer(siz);
    }
    
	public CircularBufferSimController() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cirbuf.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialize();
	}

	private void initialize() {
		fibGen = new BigFibonacciIterator();
	}
	void initCircularBuffer(int siz) {
    	//bufUseCount = new SimpleIntegerProperty(0);
		cirbuf =  new CircularBuffer<String>(siz);
		bufShapes = new BufferShape[cirbuf.getBufSize()];
		
		cirbuf1 =  new CircularBuffer<String>(siz);
		bufShapes1 = new BufferShape[cirbuf1.getBufSize()];
		
		cirbuf2 =  new CircularBuffer<String>(siz);
		bufShapes2 = new BufferShape[cirbuf2.getBufSize()];
		
		buildCircularBufferShape();
	}
	void buildCircularBufferShape() {
		drawPane.getChildren().clear();
		drawPane1.getChildren().clear();
		drawPane2.getChildren().clear();
		
		double cx = drawPane.getWidth()/2;
		double cy = drawPane.getHeight()/2;
		//System.out.println("cx="+cx+" cy="+cy);

		double cx1 = drawPane1.getWidth()/2;
		double cy1 = drawPane1.getHeight()/2;
		//System.out.println("cx="+cx+" cy="+cy);
		
		double cx2 = drawPane2.getWidth()/2;
		double cy2 = drawPane2.getHeight()/2;
		//System.out.println("cx="+cx+" cy="+cy);
		
		double outrad = cx*0.4;
		
		double outrad1 = cx1*0.4;
		
		double outrad2 = cx2*0.4;
		
		int siz = cirbuf.getBufSize(); 
		double angl = 2*Math.PI/siz;
		
		int siz1 = cirbuf1.getBufSize(); 
		double angl1 = 2*Math.PI/siz1;
		
		int siz2 = cirbuf2.getBufSize(); 
		double angl2 = 2*Math.PI/siz2;
		
		for (int i=0; i<siz; ++i) {
			bufShapes[i] = new BufferShape();
			bufShapes[i].setBufPath(cx, cy, outrad, angl);
			bufShapes[i].setText(String.valueOf(i), cx, cy, outrad, angl);
			bufShapes[i].setRot(i*Math.toDegrees(angl), cx, cy);
			bufShapes[i].setBackground(Color.SNOW);
			drawPane.getChildren().add(bufShapes[i]);
		}
		
		for (int i=0; i<siz1; ++i) {
			bufShapes1[i] = new BufferShape();
			bufShapes1[i].setBufPath(cx1, cy1, outrad1, angl1);
			bufShapes1[i].setText(String.valueOf(i), cx1, cy1, outrad1, angl1);
			bufShapes1[i].setRot(i*Math.toDegrees(angl1), cx1, cy1);
			bufShapes1[i].setBackground(Color.SNOW);
			drawPane1.getChildren().add(bufShapes1[i]);
		}
		
		for (int i=0; i<siz2; ++i) {
			bufShapes2[i] = new BufferShape();
			bufShapes2[i].setBufPath(cx2, cy2, outrad2, angl2);
			bufShapes2[i].setText(String.valueOf(i), cx2, cy2, outrad2, angl2);
			bufShapes2[i].setRot(i*Math.toDegrees(angl2), cx2, cy2);
			bufShapes2[i].setBackground(Color.SNOW);
			drawPane2.getChildren().add(bufShapes2[i]);
		}

	}
	
	void setBufferShapeColor() {
		int siz = cirbuf.getBufSize();
		int front = cirbuf.getFront();
		int rear = cirbuf.getRear();
		for (int i=0; i<siz; ++i) {
			bufShapes[i].setBackground(Color.SNOW);
		}
		int bp = front;
		for (int count=1; count <= cirbuf.getOccupiedBufferCount(); ++count) {
			bufShapes[bp].setBackground(Color.CYAN);
			bp = (bp + 1) % siz;
		}
		if (front!=rear ) {
			bufShapes[front].setBackground(Color.GREEN);
			bufShapes[rear].setBackground(Color.BLUE);
		}
		else if (cirbuf.getOccupiedBufferCount()>0) {
			bufShapes[front].setBackground(Color.GREEN);
		}
		else {
			bufShapes[front].setBackground(Color.CYAN);
		}
		
		int siz1 = cirbuf1.getBufSize();
		int front1 = cirbuf1.getFront();
		int rear1 = cirbuf1.getRear();
		for (int i=0; i<siz1; ++i) {
			bufShapes1[i].setBackground(Color.SNOW);
		}
		int bp1 = front1;
		for (int count=1; count <= cirbuf1.getOccupiedBufferCount(); ++count) {
			bufShapes1[bp1].setBackground(Color.CYAN);
			bp1 = (bp1 + 1) % siz1;
		}
		if (front1!=rear1 ) {
			bufShapes1[front1].setBackground(Color.GREEN);
			bufShapes1[rear1].setBackground(Color.BLUE);
		}
		else if (cirbuf1.getOccupiedBufferCount()>0) {
			bufShapes1[front1].setBackground(Color.GREEN);
		}
		else {
			bufShapes1[front1].setBackground(Color.CYAN);
		}
		
		int siz2 = cirbuf2.getBufSize();
		int front2 = cirbuf2.getFront();
		int rear2 = cirbuf2.getRear();
		for (int i=0; i<siz2; ++i) {
			bufShapes2[i].setBackground(Color.SNOW);
		}
		int bp2 = front2;
		for (int count=1; count <= cirbuf2.getOccupiedBufferCount(); ++count) {
			bufShapes2[bp2].setBackground(Color.CYAN);
			bp2 = (bp2 + 1) % siz2;
		}
		if (front2!=rear2 ) {
			bufShapes2[front2].setBackground(Color.GREEN);
			bufShapes2[rear2].setBackground(Color.BLUE);
		}
		else if (cirbuf2.getOccupiedBufferCount()>0) {
			bufShapes2[front2].setBackground(Color.GREEN);
		}
		else {
			bufShapes2[front2].setBackground(Color.CYAN);
		}
		
	}
	private class ProducerTask implements Runnable {
		public void run() {
			try {
				while (true) {
					Random rand = new Random();
					int rn = rand.nextInt(2);
					if(rn==0) {
						cirbuf.write(String.valueOf(fibGen.next()));
//						int front = cirbuf.getFront();
//						int rear = cirbuf.getRear();
//						if(front == rear) {
//							cirbuf.buffer_flush();
//						}
						Platform.runLater(()->{
							setBufferShapeColor();
							//lblP.setText(String.valueOf(cirbuf.getOccupiedBufferCount()));
							lblP.setText((rn+1)+"|"+String.valueOf(fibGen.next()));
							double ratio = (double)cirbuf.getOccupiedBufferCount()/cirbuf.getBufSize();
							Utils.setBackground(lblCount, Utils.getRatioColor(ratio));
							//System.out.println("lblCount Style = "+lblCount.getStyle());
						});
					}
					else {
						cirbuf1.write(String.valueOf(fibGen.next()));
						Platform.runLater(()->{
							setBufferShapeColor();
							//lblCount.setText(String.valueOf(cirbuf1.getOccupiedBufferCount()));
							lblP.setText((rn+1)+"|"+String.valueOf(fibGen.next()));
							double ratio = (double)cirbuf1.getOccupiedBufferCount()/cirbuf1.getBufSize();
							Utils.setBackground(lblCount, Utils.getRatioColor(ratio));
							//System.out.println("lblCount Style = "+lblCount.getStyle());
						});
					}
					//System.out.println("Producer writes " + i);
					
					// Put the thread into sleep
					Thread.sleep((int)(Math.random() * 250));
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	// A task for reading and deleting an int from the buffer
	private class ConsumerTask implements Runnable {
		public void run() {
			try {
				while (true) {
					Random rand = new Random();
					int rn = rand.nextInt(2);
					if(rn==0) {
						cirbuf2.write(cirbuf.read());
//						int front = cirbuf.getFront();
//						int rear = cirbuf.getRear();
//						if(front == rear) {
//							cirbuf.buffer_flush();
//						}
						Platform.runLater(()->{
							setBufferShapeColor();
							//lblCount.setText(String.valueOf(cirbuf.getOccupiedBufferCount()));
							lblC.setText((rn+1)+"|"+cirbuf.read());
							double ratio = (double)cirbuf.getOccupiedBufferCount()/cirbuf.getBufSize();
							Utils.setBackground(lblCount, Utils.getRatioColor(ratio));
							//System.out.println("lblCount Style = "+lblCount.getStyle());
						});
					}
					else {
						cirbuf2.write(cirbuf1.read());
						Platform.runLater(()->{
							setBufferShapeColor();
							//lblCount.setText(String.valueOf(cirbuf1.getOccupiedBufferCount()));
							lblC.setText((rn+1)+"|"+cirbuf1.read());
							double ratio = (double)cirbuf1.getOccupiedBufferCount()/cirbuf1.getBufSize();
							Utils.setBackground(lblCount, Utils.getRatioColor(ratio));
							//System.out.println("lblCount Style = "+lblCount.getStyle());
						});
					}
					// Put the thread into sleep
					Thread.sleep((int)(Math.random() * 500));
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private class ConsumerTask1 implements Runnable {
		public void run() {
			try {
				while (true) {
					String sval = cirbuf2.read();
					System.out.println("\t\t\tConsumer reads " + sval);
					Platform.runLater(()->{
						setBufferShapeColor();
						//lblCount.setText(String.valueOf(cirbuf2.getOccupiedBufferCount()));
						lblC.setText("3|"+cirbuf2.read());
						double ratio = (double)cirbuf2.getOccupiedBufferCount()/cirbuf2.getBufSize();
						Utils.setBackground(lblCount, Utils.getRatioColor(ratio));
						//System.out.println("lblCount Style = "+lblCount.getStyle());
					});
					// Put the thread into sleep
					Thread.sleep((int)(Math.random() * 2500));
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

}
