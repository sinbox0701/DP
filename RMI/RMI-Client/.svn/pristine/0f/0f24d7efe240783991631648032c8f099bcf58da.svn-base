/**
 * Created on Dec 3, 2015
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.rmi;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.rmi.RemoteException;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class RMIVisualMessangerClient extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String HOST = "localhost";

	private static RMIMessanger rmiObj;

	private RMIVisualMessangerClient thisClass = this;
	private String clientID = null;
	private String partnerID = null;
	private ReceiverThread receiver = null;

	private JPanel jContentPane = null;

	private JButton jbtConnect = null;

	private JScrollPane jScrollPane1 = null;

	private JTextArea jtaMessage = null;

	private JPanel jpMessages = null;

	private JPanel jpTextArea = null;

	private JLabel jLabel1 = null;

	private JTextField jtfSender = null;

	private JPanel jpLabels = null;

	private JButton jbtRegister = null;

	/**
	 * This method initializes jbtConnect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtConnect() {
		if (jbtConnect == null) {
			jbtConnect = new JButton();
			jbtConnect.setFont(new Font("Consolas", Font.BOLD, 20));
			jbtConnect.setText("Connect");
			jbtConnect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					try {
						String id = rmiObj.connect(clientID);
						if (id != null) {
							partnerID = id;

							jtaMessage.append("You connected to ID:" + partnerID + "\n");
							jbtConnect.setEnabled(false);

							receiver = new ReceiverThread(rmiObj);
							receiver.start();
						}
						else
							jtaMessage.append("You can not connect. Try again\n");

					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

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
			jtaMessage.setFont(new Font("Monospaced", Font.PLAIN, 20));
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
			jLabel1.setFont(new Font("Consolas", Font.BOLD, 20));
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
			jtfSender.setFont(new Font("Monospaced", Font.PLAIN, 20));
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
						rmiObj.write(clientID, theLine);
					} 
					catch (RemoteException e1) {
						e1.printStackTrace();

					}
				}
			});
		}
		return jtfSender;
	}

	/**
	 * This method initializes jpLabels	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpLabels() {
		if (jpLabels == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(1);
			jpLabels = new JPanel();
			jpLabels.setLayout(gridLayout1);
			jpLabels.add(getJbtRegister(), null);
			jpLabels.add(getJbtConnect(), null);
		}
		return jpLabels;
	}

	/**
	 * This method initializes jbtRegister	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtRegister() {
		if (jbtRegister == null) {
			jbtRegister = new JButton();
			jbtRegister.setFont(new Font("Consolas", Font.BOLD, 20));
			jbtRegister.setText("Register");
			jbtRegister.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String id = JOptionPane.showInputDialog(thisClass, "Enter Your ID");
					if (id==null) return;
					try {
						if (rmiObj.register(id) ) {
							clientID = id; 
							jtaMessage.append("Your ID " + id + " registered\n");
							jbtRegister.setEnabled(false);
							thisClass.setTitle("ID <"+id+"> ");
						}
						else {
							jtaMessage.append("Invalid ID:" + id + "\n");
						}
					} 
					catch (RemoteException e1) {
						e1.printStackTrace();

					}
				}
			});
		}
		return jbtRegister;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.setSecurityManager(new RMISecurityManager());
		Object obj = null;
		Context namingContext = null;
		String rmiObjectName = "rmi://" + HOST + "/RMIMessanger";
		try {
			namingContext = new InitialContext();
			System.out.println("RMI registry bindings: ");
			Enumeration<NameClassPair> e = namingContext.list("rmi://localhost/");
			while (e.hasMoreElements())
				System.out.println(e.nextElement().getName());
			
			obj = namingContext.lookup(rmiObjectName);
			rmiObj = (RMIMessanger) obj;
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				RMIVisualMessangerClient thisClass = new RMIVisualMessangerClient();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});

	}

	/**
	 * This is the default constructor
	 */
	public RMIVisualMessangerClient() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		setBounds(new Rectangle(20, 20, 400, 300));
		this.setSize(373, 319);
		this.setContentPane(getJContentPane());
		this.setTitle("Messanger Client");
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
			jContentPane.add(getJpLabels(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	class ReceiverThread extends Thread {

		private RMIMessanger messanger;

		public ReceiverThread(RMIMessanger messanger) {
			this.messanger = messanger;
		}

		public void run() {

			while (true) {
				try {
					String theLine;

					theLine = messanger.read(clientID);
					if (theLine.equals("END")) break;

					jtaMessage.append(">>"+theLine +"\n");

					Thread.yield();
				} 
				catch (RemoteException e) {
					e.printStackTrace();
				}

			}  

		}

	}

}  //  @jve:decl-index=0:visual-constraint="183,38"
