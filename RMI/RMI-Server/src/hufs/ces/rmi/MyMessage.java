/**
 * Created on Dec 3, 2015
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.rmi;

import java.io.Serializable;

public class MyMessage implements Serializable {

	   private String sender;   // person sending message  //  @jve:decl-index=0:
	   private String message;  // message being sent
	   
	   // construct empty ChatMessage
	   public MyMessage() 
	   { 
	      this( "", "" ); 
	   }
	   
	   // construct ChatMessage with sender and message values
	   public MyMessage( String sender, String message )
	   {
	      setSender( sender );
	      setMessage( message );
	   }
	   
	   // set name of person sending message
	   public void setSender( String senderid ) 
	   { 
	      this.sender = senderid; 
	   }
	   
	   // get name of person sending message 
	   public String getSender() 
	   { 
	      return sender; 
	   }
	   
	   // set message being sent
	   public void setMessage( String messageBody ) 
	   { 
	      message = messageBody; 
	   }
	     
	   // get message being sent
	   public String getMessage() 
	   { 
	      return message; 
	   }   
	   
	   // String representation of ChatMessage
	   public String toString() 
	   {
	      return getSender() + "> " + getMessage();
	   }
	}
