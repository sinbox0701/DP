/**
 * Created on Dec 3, 2015
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.rmi;


import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridLayout;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class RMIVisualEchoClient extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String HOST = "localhost";


	private static RMIEcho echoObj;
	private ReceiverThread receiver = null;

	private JPanel jContentPane = null;

	private JButton jbtConnect = null;

	private JScrollPane jScrollPane1 = null;

	private JTextArea jtaMessage = null;

	private JPanel jpMessages = null;

	private JPanel jpTextArea = null;

	private JLabel jLabel1 = null;

	private JTextField jtfSender = null;

	/**
	 * This method initializes jbtConnect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtConnect() {
		if (jbtConnect == null) {
			jbtConnect = new JButton();
			jbtConnect.setText("Connect");
			jbtConnect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					jtaMessage.setText("connected\n");
					jbtConnect.setEnabled(false);
					
					receiver = new ReceiverThread(echoObj);
					receiver.start();

				}
			});
		}
		return jbtConnect;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane1.setViewportView(getJtaMessage());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jtaMessage	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJtaMessage() {
		if (jtaMessage == null) {
			jtaMessage = new JTextArea();
			jtaMessage.setLineWrap(true);
			jtaMessage.setEditable(false);
		}
		return jtaMessage;
	}

	/**
	 * This method initializes jpMessages	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpMessages() {
		if (jpMessages == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			gridLayout.setHgap(4);
			gridLayout.setVgap(4);
			gridLayout.setColumns(2);
			jpMessages = new JPanel();
			jpMessages.setLayout(gridLayout);
			jpMessages.add(getJpTextArea(), null);
		}
		return jpMessages;
	}

	/**
	 * This method initializes jpTextArea	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpTextArea() {
		if (jpTextArea == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Messanger");
			jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			jpTextArea = new JPanel();
			jpTextArea.setLayout(new BorderLayout());
			jpTextArea.add(getJScrollPane1(), BorderLayout.CENTER);
			jpTextArea.add(jLabel1, BorderLayout.NORTH);
			jpTextArea.add(getJtfSender(), BorderLayout.SOUTH);
		}
		return jpTextArea;
	}

	/**
	 * This method initializes jtfSender	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtfSender() {
		if (jtfSender == null) {
			jtfSender = new JTextField();
			jtfSender.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String theLine = jtfSender.getText();

					jtaMessage.append("<<"+theLine +"\n");					
					jtfSender.setText("");

					if (theLine.equals(".")) {
						theLine = "END";
						jtfSender.setEnabled(false);
						jtaMessage.append("disconnected\n");
					}

					try {
						echoObj.write(theLine);
					} 
					catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();

					}
				}
			});
		}
		return jtfSender;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String rmiObjectName = "rmi://" + HOST + "/RMIEcho";
		Object obj = null;
		try {
			obj = Naming.lookup(rmiObjectName);
			echoObj = (RMIEcho) obj;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				RMIVisualEchoClient thisClass = new RMIVisualEchoClient();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});

	}

	/**
	 * This is the default constructor
	 */
	public RMIVisualEchoClient() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(373, 319);
		this.setContentPane(getJContentPane());
		this.setTitle("Echo Client");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJpMessages(), BorderLayout.CENTER);
			jContentPane.add(getJbtConnect(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	class ReceiverThread extends Thread {

		private RMIEcho echo;

		public ReceiverThread(RMIEcho echo) {
			this.echo = echo;
		}

		public void run() {

			while (true) {
				try {
					String theLine;

					theLine = echo.read();
					if (theLine.equals("END")) break;
					
					jtaMessage.append(">>"+theLine +"\n");

					Thread.yield();
				} 
				catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}  

		}

	}


}  //  @jve:decl-index=0:visual-constraint="186,-6"
