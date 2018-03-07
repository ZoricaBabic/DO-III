package command;

import java.awt.Graphics;

import geometrija.HexagonAdapter;
import geometrija.Shape;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class CmdSelectShape implements Command {
	private DrawingModel model;
	private Shape shape;
	Graphics g;
	
	public CmdSelectShape(DrawingModel model, Shape shape, Graphics g) {
		this.model = model;
		this.shape = shape;
		this.g=g;
	}
	
	@Override
	public void execute() {
		for(Shape s:model.getShapes()) {
			if(shape.equals(s)) {
				s.selected(g);
				
			}
		}
		
		
		
		/*if(!DrawingController.getSelectedList().contains(shape)) {
			DrawingController.getSelectedList().add(shape);
		}*/
		DrawingFrame.getTxtArea().append("Selektovan " + shape+"\n");
		
	}

	@Override
	public void unexecute() {
		model.get(model.getShapes().indexOf(shape)).setSelected(false);
		/*if(DrawingController.getSelectedList().contains(shape)) {
			DrawingController.getSelectedList().remove(shape);
		}*/
		DrawingFrame.getTxtArea().append("Deselektovan " + shape+"\n");
		
		
	}

}
