package command;

import java.awt.Graphics;

import geometrija.Shape;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class CmdDeselectShape implements Command {
	private DrawingModel model;
	private Shape shape;
	private Graphics g;
	
	public CmdDeselectShape(DrawingModel model, Shape shape, Graphics g) {
		this.model = model;
		this.shape = shape;
		this.g=g;
	}
	
	@Override
	public void execute() {
		try {
		model.get(model.getShapes().indexOf(shape)).setSelected(false);
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println(e);
		}
		/*if(DrawingController.getSelectedList().contains(shape)) {
			DrawingController.getSelectedList().remove(shape);
		}*/
		DrawingFrame.getTxtArea().append("Deselektovan " + shape+"\n");
		
		
	}

	@Override
	public void unexecute() {
		try {
		model.get(model.getShapes().indexOf(shape)).selected(g);
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println(e);
		}
		/*if(!DrawingController.getSelectedList().contains(shape)) {
			DrawingController.getSelectedList().add(shape);
		}*/
		DrawingFrame.getTxtArea().append("Selektovan " + shape+"\n");
		
	}
}
