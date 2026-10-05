package commands;

import shapes.Ellipse;

public class CmdUpdateEllipse implements Command {

	private Ellipse oldState;
	private Ellipse newState;
	private Ellipse originalState;

	public CmdUpdateEllipse(Ellipse oldState, Ellipse newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {

		originalState = oldState.clone();
		oldState.setCenter(newState.getCenter().clone());
		oldState.setWidth(newState.getWidth());
		oldState.setHeight(newState.getHeight());
		oldState.setColor(newState.getColor());
		oldState.setInteriorColor(newState.getInteriorColor());
	}

	@Override
	public void unexecute() {

		oldState.setCenter(originalState.getCenter());
		oldState.setWidth(originalState.getWidth());
		oldState.setHeight(originalState.getHeight());
		oldState.setColor(originalState.getColor());
		oldState.setInteriorColor(originalState.getInteriorColor());
	}
}
