package mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileReader;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;


import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import command.CmdAddShape;
import command.CmdBringToBack;
import command.CmdBringToFront;
import command.CmdDeselectShape;
import command.CmdOneToBack;
import command.CmdOneToFront;
import command.CmdRemoveShape;
import command.CmdSelectShape;
import command.CmdUpdateCircle;
import command.CmdUpdateHexagon;
import command.CmdUpdateLine;
import command.CmdUpdatePoint;
import command.CmdUpdateRectangle;
import command.CmdUpdateShape;
import command.CmdUpdateSquare;
import command.CommandLog;
import command.CommandStack;
import geometrija.Circle;
import geometrija.HexagonAdapter;
import geometrija.Line;
import geometrija.Point;
import geometrija.Rectangle;
import geometrija.Shape;
import geometrija.Square;
import hexagon.Hexagon;
import observer.Observer;
import observer.Subject;
import strategy.SaveLog;
import strategy.SaveManager;
import strategy.SavePainting;
import strategy.SaveStrategy;
import wb.DlgKrug;
import wb.DlgKvadrat;
import wb.DlgLinija;
import wb.DlgPravougaonik;
import wb.DlgTacka;

public class DrawingController implements Subject  {
	private CommandStack list = new CommandStack();
	private ArrayList<String> backList = new ArrayList<String>();
	private boolean pro=false;
	private int pp;
	private int current;
	private ArrayList<Shape> selectedList = new ArrayList<Shape>();
	private ArrayList<Shape> selList = new ArrayList<Shape>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private int check=0;
	public CommandStack getList() {
		return list;
	}

	private DrawingModel model;
	private DrawingFrame frame;
	private DlgKvadrat dlgK = new DlgKvadrat();
	private DlgPravougaonik dlgP = new DlgPravougaonik();
	private DlgKrug dlgKr = new DlgKrug();
	private DlgTacka dlgT = new DlgTacka();
	private DlgLinija dlgL = new DlgLinija();
	public SaveStrategy save;

	private int x,y;
	private int currentUndo=0;
	private int counter = 0;
	private Object o;
	public boolean checkUndo;
	private int pr;
	private File fileToLoad;
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
	}

	



	private static boolean enabledDelete=false;
	private static boolean enabled=false;
	//IZMENA
	public void viewButtonModifyClicked(ActionEvent arg0) {
		if(frame.getTglbtnIzmeniOblik().isSelected()){

			for(int i = model.getShapes().size()-1;i>=0;i--){

				if(model.getShapes().get(i) instanceof Point){

					Point helper = (Point)model.getShapes().get(i);

					if(helper.isSelected()){
						dlgT.setTitle("Izmena taèke: ");
						dlgT.getTxtX().setText(Integer.toString(helper.getX()));
						dlgT.getTxtY().setText(Integer.toString(helper.getY()));
						dlgT.getPnlBojaIvice().setBackground(helper.getColor());
						dlgT.setVisible(true);
						try{
							Color boja = dlgT.getPnlBojaIvice().getBackground();

							Point t1 = new Point(Integer.parseInt(dlgT.getTxtX().getText()),Integer.parseInt(dlgT.getTxtY().getText()),boja);
							if(dlgT.isProvera()){
								CmdUpdatePoint cmd = new CmdUpdatePoint(helper,t1);
								cmd.execute();
								list.getList().add(cmd);


								/*model.getShapes().remove(i);
							model.getShapes().add(t1);*/
							}


							dlgT.getTxtX().setText("");
							dlgT.getTxtY().setText("");
							dlgT.getPnlBojaIvice().setBackground(Color.WHITE);
							frame.getView().repaint();
							frame.getBtnUndo().setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"Niste uneli odgovarajuæu vrednost");
							dlgT.getTxtX().setText("");
							dlgT.getTxtY().setText("");
							dlgT.getPnlBojaIvice().setBackground(Color.WHITE);


						}






					}

				}
				else if(model.getShapes().get(i) instanceof HexagonAdapter){

					HexagonAdapter helper = (HexagonAdapter)model.getShapes().get(i);

					if(helper.isSelected()){
						dlgKr.setTitle("Izmena hexagona: ");
						dlgKr.getTxtX().setText(Integer.toString(helper.getHexagon().getX()));
						dlgKr.getTxtY().setText(Integer.toString(helper.getHexagon().getY()));
						dlgKr.getTxtPoluprecnik().setText(Integer.toString(helper.getHexagon().getR()));
						dlgKr.getPnlBojaUnutrasnjosti().setBackground(helper.getHexagon().getAreaColor());
						dlgKr.getPnlBojaIvice().setBackground(helper.getHexagon().getBorderColor());
						dlgKr.getTxtX().setEditable(true);
						dlgKr.getTxtY().setEditable(true);
						dlgKr.setVisible(true);
						try{

							int x=Integer.parseInt(dlgKr.getTxtX().getText());
							int y=Integer.parseInt(dlgKr.getTxtY().getText());
							Color bojaU = dlgKr.getPnlBojaUnutrasnjosti().getBackground();
							Color boja=dlgKr.getPnlBojaIvice().getBackground();
							int r = Integer.parseInt(dlgKr.getTxtPoluprecnik().getText());



							Hexagon k = new Hexagon(x,y,r);
							k.setAreaColor(bojaU);
							k.setBorderColor(boja);
							HexagonAdapter ha = new HexagonAdapter(k);


							if(dlgKr.isProvera()){

								CmdUpdateHexagon cmd = new CmdUpdateHexagon(helper,ha);
								cmd.execute();
								list.getList().add(cmd);

							}
							dlgKr.getTxtX().setText("");
							dlgKr.getTxtY().setText("");
							dlgKr.getTxtPoluprecnik().setText("");



							dlgKr.getPnlBojaIvice().setBackground(Color.WHITE);
							dlgKr.getPnlBojaUnutrasnjosti().setBackground(Color.WHITE);
							frame.getView().repaint();
							frame.getBtnUndo().setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"Niste uneli odgovarajuæu vrednost");
							dlgKr.getTxtX().setText("");
							dlgKr.getTxtY().setText("");
							dlgKr.getTxtPoluprecnik().setText("");
							dlgKr.getPnlBojaIvice().setBackground(Color.WHITE);
							dlgKr.getPnlBojaUnutrasnjosti().setBackground(Color.WHITE);

						}


					}
				}
				else if(model.getShapes().get(i) instanceof Line){

					Line helper = (Line)model.getShapes().get(i);

					if(helper.isSelected()){
						dlgL.setTitle("Izmena linije:");
						dlgL.getTxtXPocetne().setText(Integer.toString(helper.gettPocetna().getX()));
						dlgL.getTxtYPocetne().setText(Integer.toString(helper.gettPocetna().getY()));
						dlgL.getTxtXKrajnje().setText(Integer.toString(helper.gettKrajnja().getX()));
						dlgL.getTxtYKrajnje().setText(Integer.toString(helper.gettKrajnja().getY()));
						dlgL.getPnlBojaIvice().setBackground(helper.getColor());
						dlgL.setVisible(true);
						try{
							Color boja = dlgL.getPnlBojaIvice().getBackground();
							int pocetnaX=Integer.parseInt(dlgL.getTxtXPocetne().getText());
							int pocetnaY=Integer.parseInt(dlgL.getTxtYPocetne().getText());
							int krajnjeX=Integer.parseInt(dlgL.getTxtXKrajnje().getText());
							int krajnjeY=Integer.parseInt(dlgL.getTxtYKrajnje().getText());
							Line l1 = new Line(new Point(pocetnaX,pocetnaY),new Point(krajnjeX,krajnjeY),boja);
							if(dlgL.isProvera()){


								CmdUpdateLine cmd = new CmdUpdateLine(helper,l1);
								cmd.execute();
								list.getList().add(cmd);

							}
							dlgL.getTxtXPocetne().setText("");
							dlgL.getTxtYPocetne().setText("");
							dlgL.getTxtXKrajnje().setText("");
							dlgL.getTxtYKrajnje().setText("");
							dlgL.getPnlBojaIvice().setBackground(Color.WHITE);


							frame.getView().repaint();
							frame.getBtnUndo().setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"Niste uneli odgovarajuæu vrednost");
							dlgL.getTxtXPocetne().setText("");
							dlgL.getTxtYPocetne().setText("");
							dlgL.getTxtXKrajnje().setText("");
							dlgL.getTxtYKrajnje().setText("");
							dlgL.getPnlBojaIvice().setBackground(Color.WHITE);


						}





					}
				}
				else if(model.getShapes().get(i) instanceof Rectangle){

					Rectangle helper = (Rectangle)model.getShapes().get(i);

					if(helper.isSelected()){

						Exception exp = null;
						dlgP.setTitle("Izmena pravougaonika:");
						dlgP.getTxtX().setText(Integer.toString(helper.getGoreLevo().getX()));
						dlgP.getTxtY().setText(Integer.toString(helper.getGoreLevo().getY()));
						dlgP.getTxtDuzinaStranice().setText(Integer.toString(helper.getDuzinaStranica()));
						dlgP.getTxtSirinaStranice().setText(Integer.toString(helper.getVisina()));
						dlgP.getPnlBojaIvice().setBackground(helper.getColor());
						dlgP.getPnlBojaUnutrasnjosti().setBackground(helper.getFillColor());
						dlgP.getTxtX().setEditable(true);
						dlgP.getTxtY().setEditable(true);
						dlgP.setVisible(true);
						try{
							Color boja = dlgP.getPnlBojaIvice().getBackground();
							Color bojaU =dlgP.getPnlBojaUnutrasnjosti().getBackground();
							int x=Integer.parseInt(dlgP.getTxtX().getText());
							int y=Integer.parseInt(dlgP.getTxtY().getText());
							int duzina = Integer.parseInt(dlgP.getTxtDuzinaStranice().getText());
							int sirina = Integer.parseInt(dlgP.getTxtSirinaStranice().getText());
							if(duzina<=0||sirina<=0){
								throw exp;
							}

							Rectangle p = new Rectangle(new Point(x,y),duzina,sirina,boja,bojaU);


							if(dlgP.isProvera()){

								CmdUpdateRectangle cmd = new CmdUpdateRectangle(helper,p);
								cmd.execute();
								list.getList().add(cmd);

							}
							dlgP.getTxtX().setText("");
							dlgP.getTxtY().setText("");
							dlgP.getTxtDuzinaStranice().setText("");
							dlgP.getTxtSirinaStranice().setText("");
							dlgP.getPnlBojaIvice().setBackground(Color.WHITE);
							dlgP.getPnlBojaUnutrasnjosti().setBackground(Color.WHITE);
							frame.getView().repaint();
							frame.getBtnUndo().setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"Niste uneli odgovarajuæu vrednost");
							dlgP.getTxtX().setText("");
							dlgP.getTxtY().setText("");
							dlgP.getTxtDuzinaStranice().setText("");
							dlgP.getTxtSirinaStranice().setText("");
							dlgP.getPnlBojaIvice().setBackground(Color.WHITE);
							dlgP.getPnlBojaUnutrasnjosti().setBackground(Color.WHITE);


						}

					}
				}
				else if(model.getShapes().get(i) instanceof Square){

					Square helper = (Square)model.getShapes().get(i);

					if(helper.isSelected()){

						Exception exp=null;
						dlgK.setTitle("Izmena kvadrata: ");
						dlgK.getTxtX().setText(Integer.toString(helper.getGoreLevo().getX()));
						dlgK.getTxtY().setText(Integer.toString(helper.getGoreLevo().getY()));
						dlgK.getTxtDuzinaStranice().setText(Integer.toString(helper.getDuzinaStranica()));
						dlgK.getPnlBojaUnutrasnjosti().setBackground(helper.getFillColor());
						dlgK.getPnlBojaIvice().setBackground(helper.getColor());
						dlgK.getTxtX().setEditable(true);
						dlgK.getTxtY().setEditable(true);
						dlgK.setVisible(true);
						try{

							Color boja = dlgK.getPnlBojaIvice().getBackground();
							Color bojaU =dlgK.getPnlBojaUnutrasnjosti().getBackground();
							int x=Integer.parseInt(dlgK.getTxtX().getText());
							int y=Integer.parseInt(dlgK.getTxtY().getText());
							int duzina = Integer.parseInt(dlgK.getTxtDuzinaStranice().getText());
							if(duzina<=0){
								throw exp;
							}


							Square k = new Square(new Point(x,y),duzina,boja,bojaU);


							if(dlgK.isProvera()){

								CmdUpdateSquare cmd = new CmdUpdateSquare(helper,k);
								cmd.execute();
								list.getList().add(cmd);

							}
							dlgK.getTxtX().setText("");
							dlgK.getTxtY().setText("");
							dlgK.getTxtDuzinaStranice().setText("");

							dlgK.getPnlBojaIvice().setBackground(Color.WHITE);
							dlgK.getPnlBojaUnutrasnjosti().setBackground(Color.WHITE);


							frame.getView().repaint();
							frame.getBtnUndo().setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"Niste uneli odgovarajuæu vrednost");
							dlgK.getTxtX().setText("");
							dlgK.getTxtY().setText("");
							dlgK.getTxtDuzinaStranice().setText("");
							dlgK.getPnlBojaIvice().setBackground(Color.WHITE);
							dlgK.getPnlBojaUnutrasnjosti().setBackground(Color.WHITE);


						}


					}
				}

				else if(model.getShapes().get(i) instanceof Circle){

					Circle helper = (Circle)model.getShapes().get(i);

					if(helper.isSelected()){
						Exception exp=null;
						dlgKr.setTitle("Izmena kruga: ");
						dlgKr.getTxtX().setText(Integer.toString(helper.getCentar().getX()));
						dlgKr.getTxtY().setText(Integer.toString(helper.getCentar().getY()));
						dlgKr.getTxtPoluprecnik().setText(Integer.toString(helper.getR()));
						dlgKr.getPnlBojaUnutrasnjosti().setBackground(helper.getFillColor());
						dlgKr.getPnlBojaIvice().setBackground(helper.getColor());
						dlgKr.getTxtX().setEditable(true);
						dlgKr.getTxtY().setEditable(true);
						dlgKr.setVisible(true);
						try{

							int x=Integer.parseInt(dlgKr.getTxtX().getText());
							int y=Integer.parseInt(dlgKr.getTxtY().getText());
							Color bojaU = dlgKr.getPnlBojaUnutrasnjosti().getBackground();
							Color boja=dlgKr.getPnlBojaIvice().getBackground();
							int r = Integer.parseInt(dlgKr.getTxtPoluprecnik().getText());
							if(r<=0){
								throw exp;
							}


							Circle k = new Circle(new Point(x,y),r,boja,bojaU);


							if(dlgKr.isProvera()){

								CmdUpdateCircle cmd = new CmdUpdateCircle(helper,k);
								cmd.execute();
								list.getList().add(cmd);

							}
							dlgKr.getTxtX().setText("");
							dlgKr.getTxtY().setText("");
							dlgKr.getTxtPoluprecnik().setText("");



							dlgKr.getPnlBojaIvice().setBackground(Color.WHITE);
							dlgKr.getPnlBojaUnutrasnjosti().setBackground(Color.WHITE);
							frame.getView().repaint();
							frame.getBtnUndo().setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"Niste uneli odgovarajuæu vrednost");
							dlgKr.getTxtX().setText("");
							dlgKr.getTxtY().setText("");
							dlgKr.getTxtPoluprecnik().setText("");
							dlgKr.getPnlBojaIvice().setBackground(Color.WHITE);
							dlgKr.getPnlBojaUnutrasnjosti().setBackground(Color.WHITE);

						}


					}
				}
			}

			frame.getView().repaint();	
		}
	}

	//CRTANJE	

	@SuppressWarnings("null")
	public void viewMouseClicked(MouseEvent e) {
		if(frame.getTglbtnTacka().isSelected()){


			x = e.getX();
			y = e.getY();
			Color boja = frame.getBtnColor().getBackground();
			Point t1 = new Point(x,y,boja);
			CmdAddShape cmd = new CmdAddShape(model,t1);
			cmd.execute();
			list.getList().add(cmd);
			frame.getBtnUndo().setEnabled(true);
		}
		else if(frame.getTglbtnHexagon().isSelected()) {


			x=e.getX();
			y=e.getY();
			dlgKr.setTitle("Osobine novog hexagona: ");
			dlgKr.getTxtX().setText(Integer.toString(x));
			dlgKr.getTxtY().setText(Integer.toString(y));
			dlgKr.getTxtX().setEditable(false);
			dlgKr.getTxtY().setEditable(false);
			dlgKr.getPnlBojaIvice().setVisible(false);
			dlgKr.getPnlBojaUnutrasnjosti().setVisible(false);
			dlgKr.getLblBojaIvice().setVisible(false);
			dlgKr.getLblBojaUnutrasnjosti().setVisible(false);


			dlgKr.setVisible(true);
			Exception exp=null;

			try{
				if(dlgKr.isProvera()){

					//x = Integer.parseInt(dlgK.getTxtX().getText());
					// y = Integer.parseInt(dlgK.getTxtX().getText());
					int r = Integer.parseInt(dlgKr.getTxtPoluprecnik().getText());
					Color boja=frame.getBtnColor().getBackground();
					Color bojaU=frame.getBtnFillColor().getBackground();
					if(r<0){
						throw exp;
					}
					Hexagon hex = new Hexagon(x,y,r);
					hex.setBorderColor(boja);
					hex.setAreaColor(bojaU);
					HexagonAdapter ha= new HexagonAdapter(hex);
					CmdAddShape cmd = new CmdAddShape(model,ha);
					cmd.execute();
					frame.getBtnUndo().setEnabled(true);
					list.getList().add(cmd);

					dlgKr.getTxtX().setText("");
					dlgKr.getTxtY().setText("");
					dlgKr.getTxtPoluprecnik().setText("");
					dlgKr.getPnlBojaIvice().setVisible(true);
					dlgKr.getPnlBojaUnutrasnjosti().setVisible(true);
					dlgKr.getLblBojaIvice().setVisible(true);
					dlgKr.getLblBojaUnutrasnjosti().setVisible(true);

				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Niste uneli odgovarajuæu vrednost");
				dlgKr.getTxtX().setText("");
				dlgKr.getTxtY().setText("");
				dlgKr.getTxtPoluprecnik().setText("");
				dlgKr.getPnlBojaIvice().setVisible(true);
				dlgKr.getPnlBojaUnutrasnjosti().setVisible(true);
				dlgKr.getLblBojaIvice().setVisible(true);
				dlgKr.getLblBojaUnutrasnjosti().setVisible(true);


			}


		}
		else if(frame.getTglbtnLinija().isSelected()){
			if(counter == 0 ){

				o= new Line(new Point(e.getX(),e.getY()),new Point(0,0));
				counter ++;
				return;
			}else {
				Line l1 = (Line)o;


				l1.settKrajnja(new Point(e.getX(),e.getY()));
				Color boja = frame.getBtnColor().getBackground();
				l1.setColor(boja);

				CmdAddShape cmd = new CmdAddShape(model,l1);
				cmd.execute();
				frame.getBtnUndo().setEnabled(true);
				list.getList().add(cmd);


				counter = 0;
			}



			//Linija l1 = new Linija(new Tacka(x,y),new Tacka(x1,y1));
			//l1.crtajSe(pnlCrtez.getGraphics());


		}
		else if(frame.getTglbtnKvadrat().isSelected())
		{


			Exception exp=null;
			try{
				x=e.getX();
				y=e.getY();
				dlgK.setTitle("Osobine za novi kvadrat:");
				dlgK.getTxtX().setText(Integer.toString(x));
				dlgK.getTxtY().setText(Integer.toString(y));
				dlgK.getTxtX().setEditable(false);
				dlgK.getTxtY().setEditable(false);
				dlgK.getPnlBojaIvice().setVisible(false);
				dlgK.getPnlBojaUnutrasnjosti().setVisible(false);
				dlgK.getLblBojaIvice().setVisible(false);
				dlgK.getLblBojaUnutrasnjosti().setVisible(false);
				dlgK.setVisible(true);

				if(dlgK.isProvera()){

					//x = Integer.parseInt(dlgK.getTxtX().getText());
					// y = Integer.parseInt(dlgK.getTxtX().getText());
					int duzinaStranice = Integer.parseInt(dlgK.getTxtDuzinaStranice().getText());
					Color boja=frame.getBtnColor().getBackground();
					Color bojaU=frame.getBtnFillColor().getBackground();
					if(duzinaStranice<=0){
						throw exp;
					}
					Square k = new Square(new Point(x,y),duzinaStranice,boja,bojaU);

					CmdAddShape cmd = new CmdAddShape(model,k);

					cmd.execute();
					frame.getBtnUndo().setEnabled(true);
					list.getList().add(cmd);
					dlgK.getTxtX().setText("");
					dlgK.getTxtY().setText("");
					dlgK.getTxtDuzinaStranice().setText("");
					dlgK.getPnlBojaIvice().setVisible(true);
					dlgK.getPnlBojaUnutrasnjosti().setVisible(true);
					dlgK.getLblBojaIvice().setVisible(true);
					dlgK.getLblBojaUnutrasnjosti().setVisible(true);

				}
			}catch(Exception ex){

				JOptionPane.showMessageDialog(null, "Niste uneli odgovarajuæu vrednost");
				dlgK.getTxtX().setText("");
				dlgK.getTxtY().setText("");
				dlgK.getTxtDuzinaStranice().setText("");

				dlgK.getPnlBojaIvice().setVisible(true);
				dlgK.getPnlBojaUnutrasnjosti().setVisible(true);
				dlgK.getLblBojaIvice().setVisible(true);
				dlgK.getLblBojaUnutrasnjosti().setVisible(true);



			}	
		}
		else if(frame.getTglbtnPravougaonik().isSelected()){
			Exception exp=null;
			x=e.getX();
			y=e.getY();
			dlgP.setTitle("Osobine novog pravougaonika: ");
			dlgP.getTxtX().setText(Integer.toString(x));
			dlgP.getTxtY().setText(Integer.toString(y));
			dlgP.getTxtX().setEditable(false);
			dlgP.getTxtY().setEditable(false);
			dlgP.getPnlBojaIvice().setVisible(false);
			dlgP.getPnlBojaUnutrasnjosti().setVisible(false);
			dlgP.getLblBojaIvice().setVisible(false);
			dlgP.getLblBojaUnutrasnjosti().setVisible(false);

			dlgP.setVisible(true);
			try{
				if(dlgP.isProvera()){

					//x = Integer.parseInt(dlgP.getTxtX().getText());
					// y = Integer.parseInt(dlgP.getTxtX().getText());
					int duzinaStranice = Integer.parseInt(dlgP.getTxtDuzinaStranice().getText());
					int sirinaStranice = Integer.parseInt(dlgP.getTxtSirinaStranice().getText());
					Color boja=frame.getBtnColor().getBackground();
					Color bojaU=frame.getBtnFillColor().getBackground();
					if(duzinaStranice<=0||sirinaStranice<=0){
						throw exp;
					}
					Rectangle p = new Rectangle(new Point(x,y),duzinaStranice,sirinaStranice,boja,bojaU);

					CmdAddShape cmd = new CmdAddShape(model,p);
					cmd.execute();
					frame.getBtnUndo().setEnabled(true);
					list.getList().add(cmd);

					dlgP.getTxtX().setText("");
					dlgP.getTxtY().setText("");
					dlgP.getTxtDuzinaStranice().setText("");
					dlgP.getTxtSirinaStranice().setText("");

					dlgP.getPnlBojaIvice().setVisible(true);
					dlgP.getPnlBojaUnutrasnjosti().setVisible(true);
					dlgP.getLblBojaIvice().setVisible(true);
					dlgP.getLblBojaUnutrasnjosti().setVisible(true);

				}

			}catch(Exception x){

				JOptionPane.showMessageDialog(null, "Niste uneli odgovarajuæu vrednost");
				dlgP.getTxtX().setText("");
				dlgP.getTxtY().setText("");
				dlgP.getTxtDuzinaStranice().setText("");
				dlgP.getTxtSirinaStranice().setText("");

			}









		}
		else if(frame.getTglbtnKrug().isSelected()){

			x=e.getX();
			y=e.getY();
			dlgKr.setTitle("Osobine novog kruga: ");
			dlgKr.getTxtX().setText(Integer.toString(x));
			dlgKr.getTxtY().setText(Integer.toString(y));
			dlgKr.getTxtX().setEditable(false);
			dlgKr.getTxtY().setEditable(false);
			dlgKr.getPnlBojaIvice().setVisible(false);
			dlgKr.getPnlBojaUnutrasnjosti().setVisible(false);
			dlgKr.getLblBojaIvice().setVisible(false);
			dlgKr.getLblBojaUnutrasnjosti().setVisible(false);


			dlgKr.setVisible(true);
			Exception exp=null;

			try{
				if(dlgKr.isProvera()){

					//x = Integer.parseInt(dlgK.getTxtX().getText());
					// y = Integer.parseInt(dlgK.getTxtX().getText());
					int poluprecnik = Integer.parseInt(dlgKr.getTxtPoluprecnik().getText());
					Color boja=frame.getBtnColor().getBackground();
					Color bojaU=frame.getBtnFillColor().getBackground();
					if(poluprecnik<0){
						throw exp;
					}
					Circle kr = new Circle(new Point(x,y),poluprecnik,boja,bojaU);
					CmdAddShape cmd = new CmdAddShape(model,kr);
					cmd.execute();
					frame.getBtnUndo().setEnabled(true);
					list.getList().add(cmd);

					dlgKr.getTxtX().setText("");
					dlgKr.getTxtY().setText("");
					dlgKr.getTxtPoluprecnik().setText("");
					dlgKr.getPnlBojaIvice().setVisible(true);
					dlgKr.getPnlBojaUnutrasnjosti().setVisible(true);
					dlgKr.getLblBojaIvice().setVisible(true);
					dlgKr.getLblBojaUnutrasnjosti().setVisible(true);

				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Niste uneli odgovarajuæu vrednost");
				dlgKr.getTxtX().setText("");
				dlgKr.getTxtY().setText("");
				dlgKr.getTxtPoluprecnik().setText("");
				dlgKr.getPnlBojaIvice().setVisible(true);
				dlgKr.getPnlBojaUnutrasnjosti().setVisible(true);
				dlgKr.getLblBojaIvice().setVisible(true);
				dlgKr.getLblBojaUnutrasnjosti().setVisible(true);


			}





		}
		//SELEKTOVANJE

		if(frame.getTglbtnSelektuj().isSelected()){
			x=e.getX();
			y=e.getY();
			check=0;


			for(int i = model.getShapes().size()-1;i>=0;i--){

				if(model.getShapes().get(i) instanceof Point){
					Point helper = (Point)model.getShapes().get(i);
					if(helper.contains(x, y)&& model.getShapes().get(i).isSelected()) {
						selectedList.remove(helper);
						CmdDeselectShape cmd = new CmdDeselectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);

						notifyAllObservers();
						return;

					}
					else if(helper.contains(x, y)){
						selectedList.add(helper);
						CmdSelectShape cmd = new CmdSelectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);

						notifyAllObservers();
						return;

					}else {
						check++;

					}
				}
				else if(model.getShapes().get(i) instanceof HexagonAdapter){
					HexagonAdapter  helper= (HexagonAdapter)model.getShapes().get(i);
					if(helper.contains(x, y)&& model.getShapes().get(i).isSelected()) {
						selectedList.remove(helper);
						CmdDeselectShape cmd = new CmdDeselectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);

						notifyAllObservers();
						return;

					}
					else if(helper.contains(x, y)){
						selectedList.add(helper);
						CmdSelectShape cmd = new CmdSelectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);
						notifyAllObservers();
						return;
					}else {
						check++;
					}
				}

				else if(model.getShapes().get(i) instanceof Rectangle){
					Rectangle helper = (Rectangle)model.getShapes().get(i);
					if(helper.contains(x, y)&& model.getShapes().get(i).isSelected()) {
						selectedList.remove(helper);
						CmdDeselectShape cmd = new CmdDeselectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);
						notifyAllObservers();
						return;

					}
					else if(helper.contains(x, y)){
						selectedList.add(helper);
						CmdSelectShape cmd = new CmdSelectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);
						notifyAllObservers();
						return;

					}else {
						check++;
					}
				}
				else if(model.getShapes().get(i) instanceof Square){
					Square helper = (Square)model.getShapes().get(i);
					if(helper.contains(x, y)&& model.getShapes().get(i).isSelected()) {
						selectedList.remove(helper);
						CmdDeselectShape cmd = new CmdDeselectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);
						notifyAllObservers();
						return;

					}
					else if(helper.contains(x, y)){
						selectedList.add(helper);
						CmdSelectShape cmd = new CmdSelectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);
						notifyAllObservers();
						return;

					}else {
						check++;
					}
				}

				else if(model.getShapes().get(i) instanceof Circle){
					Circle helper = (Circle)model.getShapes().get(i);
					if(helper.contains(x, y)&& model.getShapes().get(i).isSelected()) {
						selectedList.remove(helper);
						CmdDeselectShape cmd = new CmdDeselectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);
						notifyAllObservers();
						return;

					}
					else if(helper.contains(x, y)){
						selectedList.add(helper);
						CmdSelectShape cmd = new CmdSelectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);
						notifyAllObservers();
						return;

					}else {
						check++;
					}
				}
				else if(model.getShapes().get(i) instanceof Line){
					Line helper = (Line)model.getShapes().get(i);
					if(helper.contains(x, y)&& model.getShapes().get(i).isSelected()) {
						selectedList.remove(helper);
						CmdDeselectShape cmd = new CmdDeselectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);
						notifyAllObservers();
						return;

					}
					else if(helper.contains(x, y)){
						selectedList.add(helper);
						CmdSelectShape cmd = new CmdSelectShape(model,helper,frame.getView().getGraphics());
						cmd.execute();
						list.getList().add(cmd);
						notifyAllObservers();
						return;

					}else {
						check++;
					}
				}


			}
			if(check==model.getShapes().size()) {
				for(int j = selectedList.size()-1;j>=0;j--){
					selectedList.get(j).setSelected(false);
					CmdDeselectShape cmd = new CmdDeselectShape(model,selectedList.get(j),frame.getView().getGraphics());
					cmd.execute();
					list.getList().add(cmd);

				}
				selectedList.clear();
				check=0;
			}

		}
		notifyAllObservers();


	}
	//BRISANJE
	public void viewButtonDeleteClicked(ActionEvent arg0) {
		int n = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete selektovani objekat?");
		if(frame.getTglbtnObrisiOblik().isSelected()){
			for(int j = selectedList.size()-1;j>=0;j--){
				if(n==0){

					CmdRemoveShape cmd = new CmdRemoveShape(model,selectedList.get(j));
					cmd.execute();
					list.getList().add(cmd);
					frame.getView().repaint();
					frame.getBtnUndo().setEnabled(true);
					selectedList.remove(j);
					notifyAllObservers();
				}

			}



		}

	}
	public void undo() {

		list.undo();


		notifyAllObservers();

	}
	public void redo() {

		list.redo();

		notifyAllObservers();



	}
	public void bringToBack() {


		CmdBringToBack cmd = new CmdBringToBack(model);
		cmd.execute();
		list.getList().add(cmd);




	}
	public ArrayList<Shape> getSelectedList() {
		return selectedList;
	}
	public void setSelectedList(ArrayList<Shape> selectedList) {
		this.selectedList = selectedList;
	}
	public void oneToBack() {

		CmdOneToBack cmd = new CmdOneToBack(model);
		cmd.execute();
		list.getList().add(cmd);


	}
	public void oneToFront() {

		CmdOneToFront cmd = new CmdOneToFront(model);
		cmd.execute();
		list.getList().add(cmd);

	}
	public void bringToFront() {

		CmdBringToFront cmd = new CmdBringToFront(model);
		cmd.execute();
		list.getList().add(cmd);


	}
	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);


	}
	@Override
	public void removeObserver(Observer observer) {

		observers.remove(observer);

	}
	@Override
	public void notifyAllObservers() {
		int c=0;
		for(int i=0;i<model.getShapes().size();i++) {
			if(model.getShapes().get(i).isSelected()) {

				c++;
			}
		}

		if(c==1) {
			enabled=true;
			enabledDelete=true;
		}else if(c>1) {
			enabled=false;
			enabledDelete=true;
		}else {
			enabled=false;
			enabledDelete=false;
		}
		for(Observer o:observers) {
			o.update(enabled,enabledDelete);
		}
	}
	public static boolean isEnabled() {
		return enabled;
	}
	public static void setEnabled(boolean e) {
		enabled = e;
	}
	public static boolean isEnabledDelete() {
		return enabledDelete;
	}
	public static void setEnabledDelete(boolean enabledDelete) {
		DrawingController.enabledDelete = enabledDelete;
	}
	public void save(File fileToSave, File fileToSaveLog) throws IOException {
		SaveManager savePainting = new SaveManager(new SavePainting());
		SaveManager saveLog = new SaveManager(new SaveLog());

		savePainting.save(model,fileToSave);
		saveLog.save(frame,fileToSaveLog);
	}

	@SuppressWarnings("unchecked")
	public void load(File fileToLoad) throws ClassNotFoundException, IOException {
		DrawingFrame.getTxtArea().setText("");
		File f= new File(fileToLoad.getAbsolutePath().replaceAll("bin", "txt"));
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;

		while ((line = br.readLine()) != null) {

			DrawingFrame.getTxtArea().append(line+'\n');
		}
		br.close();

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToLoad));
		try
		{
			model.getShapes().clear();
			list.getList().clear();
			selectedList.clear();
			list.getUndo().clear();
			frame.getBtnUndo().setEnabled(false);
			frame.getBtnRedo().setEnabled(false);
			frame.getView().repaint();

			model.getShapes().addAll((ArrayList<Shape>)ois.readObject());

			frame.getView().repaint();
		}
		catch (SocketTimeoutException exc)
		{
			// you got the timeout
		}catch(InvalidClassException ex) {

		}
		catch (EOFException exc)
		{
			ois.close();

			// end of stream
		}
		catch (IOException exc)
		{
			// some other I/O error: print it, log it, etc.
			exc.printStackTrace(); // for example
		}
		for(int i=0;i<model.getShapes().size();i++) {
			if(model.getShapes().get(i).isSelected()) {
				selectedList.add(model.getShapes().get(i));
			}
		}
		notifyAllObservers();
		ois.close();		
	}
	//LOAD ONE BY ONE
	public void loadOneByOne(File fileToLoad) throws  IOException {

		//this.fileToLoad=fileToLoad;
		list.getList().clear();
		list.getUndo().clear();
		frame.getBtnUndo().setEnabled(false);
		frame.getBtnRedo().setEnabled(false);
		selectedList.clear();
		selList.clear();

		model.getShapes().clear();
		notifyAllObservers();
		backList.clear();
		DrawingFrame.getTxtArea().setText("");
		current=-1;
		currentUndo=-1;
		list.setCurrent(0);
		pro=true;
		pp=0;


		BufferedReader br = new BufferedReader(new FileReader(fileToLoad));
		String line;
		Shape s = null;

		while ((line = br.readLine()) != null) {

			backList.add(line);
		}


		for(int i=0;i<backList.size();i++) {
			line=backList.get(i);
			if(line.contains("Tacka")) { 		  
				int x = Integer.parseInt(line.substring(line.indexOf("(")+1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf(",")+1,line.indexOf(")")));
				String color = line.substring(line.lastIndexOf(":")+1);
				Color c = Color.BLACK;
				c=new Color(Integer.parseInt(color));
				s = new Point(x,y,c); 
			}

			else if(line.contains("Linija")) {

				int x = Integer.parseInt(line.substring(line.indexOf("(")+1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf(",")+1,line.indexOf(")")));
				int x1 = Integer.parseInt(line.substring(line.lastIndexOf("(")+1, line.lastIndexOf(",")));
				int y1 = Integer.parseInt(line.substring(line.lastIndexOf(",")+1,line.lastIndexOf(")")));
				String color = line.substring(line.lastIndexOf(":")+1);
				Color c = Color.BLACK;

				c=new Color(Integer.parseInt(color));

				s = new Line(new Point(x,y),new Point(x1,y1),c);

			}
			else if(line.contains("Kvadrat")) {



				int x = Integer.parseInt(line.substring(line.indexOf("(")+1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf(",")+1,line.indexOf(")")));
				int length = Integer.parseInt(line.substring(line.lastIndexOf(")")+2,line.lastIndexOf("r")-1));

				String color = line.substring(line.indexOf("-")+1,line.lastIndexOf("-")-1); 
				String color1 = line.substring(line.lastIndexOf("-"));
				Color c = Color.BLACK;
				Color c1 = Color.WHITE;

				c=new Color(Integer.parseInt(color));
				c1=new Color(Integer.parseInt(color1));

				s = new Square(new Point(x,y),length,c,c1);

			}
			else if(line.contains("Pravougaonik")) {

				int x = Integer.parseInt(line.substring(line.indexOf("(")+1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf(",")+1,line.indexOf(")")));
				int length = Integer.parseInt(line.substring(line.lastIndexOf(")")+2,line.indexOf("/")));
				int height = Integer.parseInt(line.substring(line.indexOf("/")+1,line.lastIndexOf("r")-1));

				String color = line.substring(line.indexOf("-")+1,line.lastIndexOf("-")-1);

				String color1 = line.substring(line.lastIndexOf("-"));
				Color c = Color.BLACK;
				Color c1 = Color.WHITE;

				c=new Color(Integer.parseInt(color));
				c1=new Color(Integer.parseInt(color1));

				s = new Rectangle(new Point(x,y),length,height,c,c1);
			}
			else if(line.contains("Heksagon")) {
				int x = Integer.parseInt(line.substring(line.indexOf("(")+1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf(",")+1,line.indexOf(")")));

				int r = Integer.parseInt(line.substring(line.lastIndexOf(")")+2,line.indexOf(" rgb boja-")));
				String color =line.substring(line.indexOf("-")+1,line.lastIndexOf(":"));

				String color1 = line.substring(line.lastIndexOf("-"));
				Color c = Color.BLACK;
				Color c1 = Color.WHITE;

				c=new Color(Integer.parseInt(color));
				c1=new Color(Integer.parseInt(color1));

				HexagonAdapter hex = new HexagonAdapter(new Hexagon(x,y,r));

				hex.getHexagon().setBorderColor(c);
				hex.getHexagon().setAreaColor(c1);
				s=hex;

			}
			else if(line.contains("Krug")) {

				int x = Integer.parseInt(line.substring(line.indexOf("(")+1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf(",")+1,line.indexOf(")")));
				int r = Integer.parseInt(line.substring(line.lastIndexOf(")")+2,line.indexOf(" rgb boja-")));
				String color = line.substring(line.indexOf("-")+1,line.lastIndexOf(":"));
				String color1 = line.substring(line.lastIndexOf("-"));
				Color c = Color.BLACK;
				Color c1 = Color.WHITE;
				c=new Color(Integer.parseInt(color));
				c1=new Color(Integer.parseInt(color1));

				Circle cr = new Circle(new Point(x,y),r);
				cr.setColor(c);
				cr.setFillColor(c1);
				s=cr;


			}

			if(line.contains("Nov")) {
				CmdAddShape cmd = new CmdAddShape(model,s);

				//   backList.getList().add(cmd); 
				list.getList().add(cmd);

				current++;


			}else if(line.contains("Selektovan")) {

				selList.add(s);

				CmdSelectShape cmd = new CmdSelectShape(model,s,frame.getView().getGraphics());
				// backList.getList().add(cmd);
				list.getList().add(cmd);

				current++;

			}else if(line.contains("Deselektovan")) {
				CmdDeselectShape cmd = new CmdDeselectShape(model,s,frame.getView().getGraphics());
				selList.remove(s);
				list.getList().add(cmd);
				// backList.getList().add(cmd);

				current++;

			}else if(line.contains("Modifikovan")){

				CmdUpdateShape cmd = new CmdUpdateShape(model,s,selList.get(0),frame.getGraphics(),selList);

				list.getList().add(cmd);

				/* selectedList.remove(0);

	    		   selectedList.add(s);*/

				current++;


			}else if(line.contains("Obrisan")) {
				selList.clear();

				CmdRemoveShape cmd = new CmdRemoveShape(model,s);

				list.getList().add(cmd);
				//backList.getList().add(cmd);
				s.setSelected(true);

				current++;

			}else if(line.contains("Pomeren ispred")) {
				CmdOneToFront cmd = new CmdOneToFront(model);
				//backList.getList().add(cmd);
				list.getList().add(cmd);

				current++;

			}else if(line.contains("Pomeren iza")) {
				CmdOneToBack cmd = new CmdOneToBack(model);
				//backList.getList().add(cmd);
				list.getList().add(cmd);

				current++;

			}
			else if(line.contains("Pomeren skroz nazad")) {
				CmdBringToBack cmd = new CmdBringToBack(model);
				list.getList().add(cmd);
				// backList.getList().add(cmd);

				current++;

			}
			else if(line.contains("Pomeren skroz napred")) {
				CmdBringToFront cmd = new CmdBringToFront(model);
				// backList.getList().add(cmd);
				list.getList().add(cmd);

				current++;

			}

		}

		br.close();	
	}

	public void go() throws ClassNotFoundException, IOException {

		list.go();
		if(backList.get(list.getCurrent()-1).contains("Obrisan")){
			selList.clear();
		}

		if(list.getList().size()==list.getCurrent()) {
			frame.getBtnGo().setEnabled(false);
			selectedList.clear();
			for(int i=0;i<model.getShapes().size();i++) {
				if(model.getShapes().get(i).isSelected()) {
					selectedList.add(model.getShapes().get(i));

				}
			}
			frame.getView().repaint();
			notifyAllObservers();
			list.getList().clear();
			frame.getBtnUndo().setEnabled(false);      
			frame.getBtnRedo().setEnabled(false);		   																								      //File f= new File(fileToLoad.getAbsolutePath().replaceAll("txt", "bin"));
			// load(f);

		}


	}
	public ArrayList<String> getBackList() {
		return backList;
	}
	public void setBackList(ArrayList<String> backList) {
		this.backList = backList;
	}
	public void backup() {
		frame.getBtnGo().setEnabled(false);
		selectedList.clear();
		for(int i=0;i<model.getShapes().size();i++) {
			if(model.getShapes().get(i).isSelected()) {
				selectedList.add(model.getShapes().get(i));
			}
		}
		frame.getView().repaint();
		notifyAllObservers();
		list.getList().clear();

	}

}
