package command;

import java.awt.Graphics;
import java.util.ArrayList;

import geometrija.HexagonAdapter;
import geometrija.Shape;
import geometrija.Square;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class CmdUpdateShape implements Command{
	DrawingModel model;
	Shape s; Shape shape; Graphics graphics;
	ArrayList<Shape> sel;

	public CmdUpdateShape(DrawingModel model,Shape s,Shape shape, Graphics graphics,ArrayList<Shape> sel) {
		// TODO Auto-generated constructor stub
		this.model=model;
		this.s=s;
		this.shape=shape;
		this.graphics=graphics;
		this.sel=sel;
	}

	@Override
	public void execute() {
		for(int i=0;i<model.getShapes().size();i++) {
			if(model.getShapes().get(i).isSelected()) {
				model.getShapes().set(i, s);
				model.getShapes().get(i).setSelected(true);
				
				sel.clear();
				sel.add(s);
				
				
				
			}
		}
		DrawingFrame.getTxtArea().append("Modifikovan "+s+"\n");
		
			
			
		   
		
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		for(int i=0;i<model.getShapes().size();i++) {
			if(model.getShapes().get(i).isSelected()) {
				
				model.getShapes().set(i, shape);
				model.getShapes().get(i).setSelected(true);
				sel.clear();
				sel.add(shape);
				
				
			}
		}
		DrawingFrame.getTxtArea().append("Modifikovan "+shape+"\n");
		    
		
	}

}
