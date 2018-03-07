package command;

import geometrija.Point;
import geometrija.Shape;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class CmdRemoveShape implements Command{
	
	private DrawingModel model;
	private Shape shape;
	
	public CmdRemoveShape(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	
	@Override
	public void execute() {
		model.remove(shape);
		DrawingFrame.getTxtArea().append("Obrisan " + shape+"\n");
		
		
	}

	@Override
	public void unexecute() {
		model.add(shape);
		
		DrawingFrame.getTxtArea().append("Novi " + shape+"\n");
		
	}
	

}
