package hufs.ces.grimtalk.command;

public interface Command {
	
	public void execute();
	public void undo();		
}
