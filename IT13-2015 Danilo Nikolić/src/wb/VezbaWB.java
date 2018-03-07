package wb;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JToggleButton;

import java.awt.Insets;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class VezbaWB extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	DefaultListModel dlm =  new DefaultListModel();
	private JTextField txtIgrac;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VezbaWB frame = new VezbaWB();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VezbaWB() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel pnlJug = new JPanel();
		contentPane.add(pnlJug, BorderLayout.SOUTH);
		GridBagLayout gbl_pnlJug = new GridBagLayout();
		gbl_pnlJug.columnWidths = new int[]{0, 0};
		gbl_pnlJug.rowHeights = new int[]{0, 0};
		gbl_pnlJug.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlJug.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlJug.setLayout(gbl_pnlJug);

		JButton btnTestKlik = new JButton("Test klik");
		btnTestKlik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Kliknuto je na dugme!", "Poruka", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		GridBagConstraints gbc_btnTestKlik = new GridBagConstraints();
		gbc_btnTestKlik.gridx = 0;
		gbc_btnTestKlik.gridy = 0;
		pnlJug.add(btnTestKlik, gbc_btnTestKlik);

		JPanel pnlCentar = new JPanel();
		contentPane.add(pnlCentar, BorderLayout.CENTER);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(pnlCentar, popupMenu);
		
		JMenuItem mntmSelektovaneStavke = new JMenuItem("Selektovane stavke");
		popupMenu.add(mntmSelektovaneStavke);
		GridBagLayout gbl_pnlCentar = new GridBagLayout();
		gbl_pnlCentar.columnWidths = new int[]{0, 0, 0, 0};
		gbl_pnlCentar.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_pnlCentar.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlCentar.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlCentar.setLayout(gbl_pnlCentar);

		JToggleButton tglbtnIvanovic = new JToggleButton("Ivanovi\u0107");
		buttonGroup.add(tglbtnIvanovic);

		GridBagConstraints gbc_tglbtnIvanovic = new GridBagConstraints();
		gbc_tglbtnIvanovic.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnIvanovic.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnIvanovic.gridx = 0;
		gbc_tglbtnIvanovic.gridy = 0;
		pnlCentar.add(tglbtnIvanovic, gbc_tglbtnIvanovic);

		JLabel lblIvanovic = new JLabel("Branislav Ivanović");
		lblIvanovic.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblIvanovic = new GridBagConstraints();
		gbc_lblIvanovic.insets = new Insets(0, 0, 5, 5);
		gbc_lblIvanovic.gridx = 1;
		gbc_lblIvanovic.gridy = 0;
		pnlCentar.add(lblIvanovic, gbc_lblIvanovic);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 0;
		pnlCentar.add(scrollPane, gbc_scrollPane);
		
		JList lstIgraci = new JList();
		lstIgraci.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(lstIgraci);
		lstIgraci.setModel(dlm);

		JToggleButton tglbtnKolarov = new JToggleButton("Kolarov");
		buttonGroup.add(tglbtnKolarov);
		GridBagConstraints gbc_tglbtnKolarov = new GridBagConstraints();
		gbc_tglbtnKolarov.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnKolarov.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnKolarov.gridx = 0;
		gbc_tglbtnKolarov.gridy = 1;
		pnlCentar.add(tglbtnKolarov, gbc_tglbtnKolarov);

		JLabel lblKolarov = new JLabel("Aleksandar Kolarov");
		lblKolarov.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblKolarov = new GridBagConstraints();
		gbc_lblKolarov.insets = new Insets(0, 0, 5, 5);
		gbc_lblKolarov.gridx = 1;
		gbc_lblKolarov.gridy = 1;
		pnlCentar.add(lblKolarov, gbc_lblKolarov);

		JToggleButton tglbtnMatic = new JToggleButton("Matić");
		buttonGroup.add(tglbtnMatic);
		GridBagConstraints gbc_tglbtnMatic = new GridBagConstraints();
		gbc_tglbtnMatic.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnMatic.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnMatic.gridx = 0;
		gbc_tglbtnMatic.gridy = 2;
		pnlCentar.add(tglbtnMatic, gbc_tglbtnMatic);

		JLabel lblMatic = new JLabel("Nemanja Matić");
		lblMatic.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblMatic = new GridBagConstraints();
		gbc_lblMatic.insets = new Insets(0, 0, 5, 5);
		gbc_lblMatic.gridx = 1;
		gbc_lblMatic.gridy = 2;
		pnlCentar.add(lblMatic, gbc_lblMatic);
		
		JCheckBox chckbxUnesiIgraca = new JCheckBox("Unesi igrača");
		
		GridBagConstraints gbc_chckbxUnesiIgraca = new GridBagConstraints();
		gbc_chckbxUnesiIgraca.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxUnesiIgraca.gridx = 0;
		gbc_chckbxUnesiIgraca.gridy = 3;
		pnlCentar.add(chckbxUnesiIgraca, gbc_chckbxUnesiIgraca);
		
		txtIgrac = new JTextField();
		
		txtIgrac.setEditable(false);
		GridBagConstraints gbc_txtIgrac = new GridBagConstraints();
		gbc_txtIgrac.insets = new Insets(0, 0, 0, 5);
		gbc_txtIgrac.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIgrac.gridx = 1;
		gbc_txtIgrac.gridy = 3;
		pnlCentar.add(txtIgrac, gbc_txtIgrac);
		txtIgrac.setColumns(10);
		
		JComboBox cbxIgraci = new JComboBox();
		cbxIgraci.setModel(new DefaultComboBoxModel(new String[] {"Tirke", "Moša", "Bek"}));
		GridBagConstraints gbc_cbxIgraci = new GridBagConstraints();
		gbc_cbxIgraci.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbxIgraci.gridx = 2;
		gbc_cbxIgraci.gridy = 3;
		pnlCentar.add(cbxIgraci, gbc_cbxIgraci);

		tglbtnIvanovic.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(tglbtnIvanovic.isSelected()){
					lblIvanovic.setForeground(Color.RED);
					dlm.addElement(lblIvanovic.getText());
				}
				else
					lblIvanovic.setForeground(Color.BLUE);
			}
		});
		tglbtnKolarov.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(tglbtnKolarov.isSelected()){
					lblKolarov.setForeground(Color.RED);
					dlm.addElement(lblKolarov.getText());
				}
				else
					lblKolarov.setForeground(Color.BLUE);
			}
		});
		tglbtnMatic.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(tglbtnMatic.isSelected()){
					lblMatic.setForeground(Color.RED);
					dlm.addElement(lblMatic.getText());
				}					
				else
					lblMatic.setForeground(Color.BLUE);
			}
		});
		chckbxUnesiIgraca.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(chckbxUnesiIgraca.isSelected()){
					txtIgrac.setEditable(true);
					txtIgrac.requestFocus();
				}
				else{
					txtIgrac.setEditable(false);
					txtIgrac.setText("");
				}
			}
		});
		txtIgrac.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					dlm.addElement(txtIgrac.getText());
					txtIgrac.setText("");
				}
			}
		});
		mntmSelektovaneStavke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,"Selektovane stavke su:\nPadajuća lista: "+cbxIgraci.getSelectedItem()+"\nLista: "+lstIgraci.getSelectedValue(), "Poruka", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
