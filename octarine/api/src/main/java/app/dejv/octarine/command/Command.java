package app.dejv.octarine.command;

public interface Command {
	
	public void execute();
	
	public void undo();

}
