package hufs.ces.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {

	protected HelloImpl() throws RemoteException {
		super();
	}

	@Override
	public String getGreeting() throws RemoteException {
		return ("Hello there!");
	}

}
