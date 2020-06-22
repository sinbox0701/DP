/**
 * Created on Dec 3, 2015
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIEchoImpl extends UnicastRemoteObject implements RMIEcho {
	
	private volatile Buffer<String> echoDataBuffer;

	protected RMIEchoImpl() throws RemoteException {
		super();
		echoDataBuffer = new CircularBuffer<String>();
	}

	public String read( ) throws RemoteException {
		String s = echoDataBuffer.read();
		System.out.println("Read:" + s);
		return s;

	}

	public void write(String s) throws RemoteException {
		System.out.println("Write:" + s);
		echoDataBuffer.write(s);

	}

}
