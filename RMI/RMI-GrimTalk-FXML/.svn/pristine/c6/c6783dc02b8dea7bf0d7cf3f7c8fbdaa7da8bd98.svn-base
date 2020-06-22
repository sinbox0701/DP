/**
 * Created on Dec 3, 2015
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIMessanger extends Remote {
	public String read(String id) throws RemoteException;
	public void write(String id, String data) throws RemoteException;
	public boolean register(String id) throws RemoteException;
	public String connect(String id) throws RemoteException;
}
