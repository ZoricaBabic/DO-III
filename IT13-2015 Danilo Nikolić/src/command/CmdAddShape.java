package command;

import geometrija.Point;
import geometrija.Shape;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class CmdAddShape implements Command {
	
		private DrawingModel model;
		private Shape shape;
		
		public CmdAddShape(DrawingModel model, Shape shape) {
			this.model = model;
			this.shape = shape;
		}
		
		@Override
		public void execute() {
			model.add(shape);
			

			
			DrawingFrame.getTxtArea().append("Novi " + shape+"\n");
		}

		@Override
		public void unexecute() {
			model.remove(shape);
			DrawingFrame.getTxtArea().append("Obrisan " + shape+"\n");
			
		}

	
	

}
