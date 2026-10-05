package commands;

import shapes.Triangle;

public class CmdUpdateTriangle implements Command {
	private Triangle oldState;
	private Triangle newState;
	private Triangle originalState;

	
	public CmdUpdateTriangle(Triangle oldState, Triangle newState)
	{
		this.oldState=oldState;
		this.newState=newState;
	}
	@Override
	public void execute() {
		originalState = oldState.clone();
		oldState.setLeftPoint(newState.getLeftPoint().clone());
		oldState.setBase(newState.getBase());
		oldState.setHeight(newState.getHeight());
		oldState.setColor(newState.getColor());
		oldState.setInteriorColor(newState.getInteriorColor());
		
	}

	@Override
	public void unexecute() {
		
		oldState.setLeftPoint(originalState.getLeftPoint());
		oldState.setBase(originalState.getBase());
		oldState.setHeight(originalState.getHeight());
		oldState.setColor(originalState.getColor());
		oldState.setInteriorColor(originalState.getInteriorColor());
	}
}
