package command;

import geometrija.Shape;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class CmdOneToFront implements Command {

	private DrawingModel model;

	
	public CmdOneToFront(DrawingModel model) {
		this.model=model;
	}

	@Override
	public void execute() {
		for(int j = 0;j<=model.getShapes().size()-1;j++){
			if(model.getShapes().get(j).isSelected()) {
				if(j==model.getShapes().size()-1 ) {
					return;
				}else {
					Shape s = model.getShapes().get(j+1);
					model.getShapes().set(j+1, model.getShapes().get(j));
					model.getShapes().set(j, s);
					DrawingFrame.getTxtArea().append("Pomeren ispred selektovani objekat\n");
				return;
				}
			}
			
			
			
		}
		
		
		
	}

	@Override
	public void unexecute() {
		for(int j = 0;j<=model.getShapes().size()-1;j++){
			if(model.getShapes().get(j).isSelected()) {
				if(j==0) {
					return;
				}else {
					Shape s = model.getShapes().get(j-1);
					model.getShapes().set(j-1, model.getShapes().get(j));
					model.getShapes().set(j, s);
				}
			}
		}
		DrawingFrame.getTxtArea().append("Pomeren iza selektovani objekat\n");
		
		
		
	}
		
		
	}


