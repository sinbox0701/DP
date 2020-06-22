package hufs.ces.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class HelloServer {

	private static final String HOST = "localhost";
	//private static final String HOST = "127.0.0.1";
	
	// External Tools Configurations
	// C:\pub\Java\jdk1.8.0_211\bin\rmiregistry.exe
	// ${workspace_loc:/RMI-Server/bin}
	public static void main(String[] args)
			throws Exception {
		//Create a reference to an
		//implementation object..
		Hello temp = new HelloImpl();
		System.out.println(temp);
		//Create the string URL holding the
		//object's name..
		String rmiObjectName = "rmi://" + HOST + "/Hello";
		System.out.println(rmiObjectName);
		//(Could omit host name here, since 'localhost'
		//would be assumed by default.)
		//'Bind' the object reference to the name...
		LocateRegistry.createRegistry(1099);
		Naming.rebind(rmiObjectName,temp);
		//Display a message so that we know the process
		//has been completed...
		System.out.println("Binding complete...\n");
	}
}
