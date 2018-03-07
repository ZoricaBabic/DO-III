package geometrija;

import java.awt.Color;
import java.awt.Graphics;


import hexagon.Hexagon;



public class HexagonAdapter extends Shape  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  Hexagon hexagon;

	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	public HexagonAdapter(Hexagon h) {
		// TODO Auto-generated constructor stub
		this.hexagon=h;
	}

	public HexagonAdapter() {
		// TODO Auto-generated constructor stub
	}

	
	public int compareTo(Object o) {
		if(o instanceof Hexagon){
			Hexagon pomocna = (Hexagon) o;
			return (int) (hexagon.getR()-pomocna.getR());
		}
		else
			return 0;
	}

	@Override
	public void drawShape(Graphics g) {
		// TODO Auto-generated method stub
		hexagon.paint(g);
		
		
	}
	public String toString(){
		String color = Integer.toString(hexagon.getBorderColor().getRGB());
		String colorInside = Integer.toString(hexagon.getAreaColor().getRGB());
		return "Heksagon:("+hexagon.getX()+","+hexagon.getY()+"),"+hexagon.getR()+" rgb boja-"+ color+":"+colorInside;
	}

	@Override
	public void selected(Graphics g) {
		// TODO Auto-generated method stub
		hexagon.setSelected(true);
		setSelected(true);
		
	}
	@Override
	public void setSelected(boolean b) {
		super.setSelected(b);
		hexagon.setSelected(b);
		
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		
		return hexagon.doesContain(x, y);
	}
	public boolean equals(Object obj){
		if(obj instanceof HexagonAdapter){
			HexagonAdapter pomocna = (HexagonAdapter) obj;
			Point t = new Point(hexagon.getX(),hexagon.getY());
			Point t1 = new Point(pomocna.hexagon.getX(),pomocna.hexagon.getY());
			if(t.equals(t1) && hexagon.getR()==pomocna.getHexagon().getR())
				return true;
			else
				return false;

		}
		else
			return false;
	}
	public static String pronadjiBoju(Color boja){
		if(boja.equals(Color.BLACK))
			return "crna";
		else if(boja.equals(Color.WHITE))
			return "bela";
		else if(boja.equals(Color.BLUE))
			return "plava";
		else if(boja.equals(Color.RED))
			return "crvena";
		else if(boja.equals(Color.GREEN))
			return "zelena";
		else if(boja.equals(Color.YELLOW))
			return "žuta";
		else if(boja.equals(Color.PINK))
			return "pink";
		else
			return "crna";
		
	}
	

}
