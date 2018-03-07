package command;

import geometrija.Shape;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class CmdBringToBack implements Command {
	private DrawingModel model;
	
	
	public CmdBringToBack(DrawingModel model) {
		this.model=model;
		
	}

	@Override
	public void execute() {
		
		for(int j = model.getShapes().size()-1;j>=0;j--){
			if(model.getShapes().get(j).isSelected()) {
				if(j==0) {
					DrawingFrame.getTxtArea().append("Pomeren skroz nazad selektovani objekat\n");
					return;
				}else {
				
				Shape s = model.getShapes().get(j-1);
				model.getShapes().set(j-1, model.getShapes().get(j));
				model.getShapes().set(j, s);
				
				}
			}
		
		}
	
	}

	@Override
	public void unexecute() {
		
		for(int j = 0;j<=model.getShapes().size()-1;j++){
			if(model.getShapes().get(j).isSelected()) {
				if(j==model.getShapes().size()-1 ) {
					DrawingFrame.getTxtArea().append("Pomeren skroz napred selektovani objekat\n");
					return;
				}else {
				
				Shape s = model.getShapes().get(j+1);
				model.getShapes().set(j+1, model.getShapes().get(j));
				model.getShapes().set(j, s);
			
				}
			}
			
			
			
		}
		
	}

}
