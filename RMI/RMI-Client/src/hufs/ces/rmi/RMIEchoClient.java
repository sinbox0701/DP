/**
 * Created on Dec 3, 2015
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.rmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class RMIEchoClient {

	private static final String HOST = "localhost";

	public static void main(String[] args) {
		/*
		System.setProperty("java.security.policy", "java.policy");
		if (System.getSecurityManager() == null)
	    {
	        System.setSecurityManager   (new RMISecurityManager());
	    }
		*/
		try {
			String rmiObjectName = "rmi://" + HOST + "/RMIEcho";
			Object obj = Naming.lookup(rmiObjectName);
			//System.out.println("Try to Bind "+rmiObjectName+" Obj="+obj);
			RMIEcho echoObj = (RMIEcho) obj;

			SenderThread sender = new SenderThread(echoObj);
			sender.start();

			ReceiverThread receiver = new ReceiverThread(echoObj);
			receiver.start();

			sender.join();
			receiver.join();
		}
		catch (MalformedURLException ex) {
			System.err.println(args[0] + " is not a valid RMI URL");
		}
		catch (RemoteException ex) {
			System.err.println("Remote object threw exception " + ex);
		}
		catch (NotBoundException ex) {
			System.err.println(
					"Could not find the requested remote object on the server");
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
class SenderThread extends Thread {

	private RMIEcho echo;

	public SenderThread(RMIEcho echo) {
		this.echo = echo;
	}

	public void run() {

		try {
			BufferedReader userInput 
			= new BufferedReader(new InputStreamReader(System.in));

			while (true) {

				String theLine = userInput.readLine();

				if (theLine.equals(".")) break;

				echo.write(theLine);
				Thread.yield();
			}
			echo.write("END"); // End Marker
		}  // end try
		catch (IOException ex) {
			ex.printStackTrace();
		}


	}  // end run

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
				
				System.out.println(theLine);

				Thread.yield();
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}

		}  

	}

}


