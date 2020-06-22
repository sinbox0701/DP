/**
 * Created on Dec 3, 2015
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.rmi;

import java.rmi.*;
import java.net.*;

public class RMIEchoServer {

	private static final String HOST = "localhost";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		System.setProperty("java.security.policy", "java.policy");
		if (System.getSecurityManager() == null)
			System.setSecurityManager ( new RMISecurityManager() );
		*/
		try {
			String rmiObjectName = "rmi://" + HOST + "/RMIEcho";
			RMIEcho echoer = new RMIEchoImpl();
			Naming.rebind(rmiObjectName, echoer);
			System.out.println("RMIEcho Server is Ready.");
		}
		catch (RemoteException ex) {
			System.out.println("RMIEcho Remote Exception" + ex);
		} catch (MalformedURLException ex) {
			System.out.println("MalformedURL Exception" + ex);
		}

	}

}
