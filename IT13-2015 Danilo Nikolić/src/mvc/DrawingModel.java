package mvc;

import java.io.Serializable;
import java.util.ArrayList;

import geometrija.Shape;

public class DrawingModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();

	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	public void add(Shape shape) {
		shapes.add(shape);
	}
	public Shape get(int i) {
		return shapes.get(i);
	}
	public boolean remove(Shape s) {
		return shapes.remove(s);
	}
}
