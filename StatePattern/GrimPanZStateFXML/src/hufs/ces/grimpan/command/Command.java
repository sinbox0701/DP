package hufs.ces.grimpan.command;

public interface Command {
	
	public void execute();
	public void undo();		
}
