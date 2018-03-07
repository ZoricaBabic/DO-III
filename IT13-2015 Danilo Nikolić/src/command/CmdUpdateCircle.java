package command;

import geometrija.Circle;
import geometrija.Point;
import mvc.DrawingFrame;

public class CmdUpdateCircle implements Command {
	private Circle original;
	private Circle newState;
	private Circle oldState = new Circle();
	
	public CmdUpdateCircle(Circle original, Circle newState) {
		this.original = original;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		oldState.setCentar(new Point(original.getCentar()));
		oldState.setR(original.getR());
		oldState.setColor(original.getColor());
		oldState.setFillColor(original.getFillColor());
		
		original.setCentar(new Point(newState.getCentar()));
		original.setR(newState.getR());
		original.setColor(newState.getColor());
		original.setFillColor(newState.getFillColor());
		DrawingFrame.getTxtArea().append("Modifikovan "+newState+"\n");
	}

	@Override
	public void unexecute() {
		original.setCentar(new Point(oldState.getCentar()));
		original.setR(oldState.getR());
		original.setColor(oldState.getColor());
		original.setFillColor(oldState.getFillColor());
		DrawingFrame.getTxtArea().append("Modifikovan "+oldState+"\n");
	}
}
