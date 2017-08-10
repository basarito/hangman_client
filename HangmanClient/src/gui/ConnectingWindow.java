package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import clients.Client;

import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConnectingWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtFindASpecific;
	private JPanel panel;
	private JLabel lblWelcomeUser;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblOr;
	private JButton btnRandom;
	private JPanel panel_4;
	private JPanel panel_5;
	private JLabel lblPleaseSelectAn;
	private JPanel panel_6;
	private JPanel panel_7;
	private JLabel lblYouCanAlso;
	private JList list;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel dtm;
	
	//private JTable table;
	//private JScrollPane spTable;
	
	
	/**
	 * Create the frame.
	 */
	public ConnectingWindow() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIControler.closeApp2();
			}
		});
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConnectingWindow.class.getResource("/icons/h.png")));
		setTitle("Hangman");
		setMinimumSize(new Dimension(450, 320));
		setSize(new Dimension(800, 600));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.NORTH);
		getPanel().add(getLblWelcomeUser());
		contentPane.add(getPanel_1(), BorderLayout.WEST);
		contentPane.add(getPanel_2(), BorderLayout.EAST);
		contentPane.add(getPanel_3(), BorderLayout.SOUTH);
		getPanel_3().add(getTxtFindASpecific());
		getPanel_3().add(getLblOr());
		getPanel_3().add(getBtnRandom());
		contentPane.add(getPanel_4(), BorderLayout.CENTER);
		getPanel_4().add(getPanel_5(), BorderLayout.NORTH);
		getPanel_5().add(getLblPleaseSelectAn());
		getPanel_4().add(getPanel_6(), BorderLayout.CENTER);
		getPanel_6().add(getPanel_7(), BorderLayout.SOUTH);
		getPanel_7().add(getLblYouCanAlso());
		
	}
	
	public JPanel getPanel() {
		if(panel == null){
			panel = new JPanel();
		}
		
		return panel;
	}
		
	public JLabel getLblWelcomeUser() {
		if(lblWelcomeUser == null){
			lblWelcomeUser = GUIControler.welcomeUser();
			lblWelcomeUser.setForeground(new Color(153, 50, 204));
			lblWelcomeUser.setFont(new Font("Arial", Font.BOLD, 18));
			
		}
		
		return lblWelcomeUser;
	}
		
	public JPanel getPanel_1() {
		if(panel_1 == null){
			panel_1 = new JPanel();
		}
		
		return panel_1;
	}

	public JPanel getPanel_2() {
		if(panel_2 == null){
			panel_2 = new JPanel();
			panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		}

		return panel_2;
	}

	public JPanel getPanel_3() {
		if(panel_3 == null){
			panel_3 = new JPanel();
			panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		}

		return panel_3;
	}
	
	public JTextField getTxtFindASpecific() {
		if(txtFindASpecific == null){
			txtFindASpecific = new JTextField();
			txtFindASpecific.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent arg0) {
					filter(txtFindASpecific.getText().toLowerCase());
				}
			});
			
			txtFindASpecific.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					txtFindASpecific.setText("");
					txtFindASpecific.setForeground(new Color(0, 0, 0));
					txtFindASpecific.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {
							if(e.getKeyCode() == KeyEvent.VK_ENTER){
								GUIControler.typeAUser(getTxtFindASpecific().getText());
							}
						}

					});
				}
			});
			txtFindASpecific.setForeground(new Color(216, 191, 216));
			txtFindASpecific.setFont(new Font("Arial", Font.ITALIC, 12));
			txtFindASpecific.setText("Find a specific user...");
			txtFindASpecific.setColumns(14);
		}
		
		return txtFindASpecific;
	}

	public JLabel getLblOr() {
		if(lblOr == null){
			lblOr = new JLabel("OR");
		}
		
		return lblOr;
	}

	public JButton getBtnRandom() {
		if(btnRandom == null){
			btnRandom = new JButton("RANDOM");
			btnRandom.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					GUIControler.chooseRandom();
				}
			});
			btnRandom.setBackground(new Color(221, 160, 221));
			btnRandom.setForeground(new Color(255, 255, 255));
			btnRandom.setFont(new Font("Arial", Font.BOLD, 13));
		}
		
		return btnRandom;
	}
		
	public JPanel getPanel_4() {
		if(panel_4 == null){
			panel_4 = new JPanel();
			panel_4.setLayout(new BorderLayout(0, 0));
		}
		
		return panel_4;
	}
	public JPanel getPanel_5() {
		if(panel_5 == null){
			panel_5 = new JPanel();
		}

		return panel_5;
	}

	public JLabel getLblPleaseSelectAn() {
		if(lblPleaseSelectAn == null){
			lblPleaseSelectAn = new JLabel("Please select an online player from the list bellow:");
			lblPleaseSelectAn.setForeground(new Color(186, 85, 211));
			lblPleaseSelectAn.setFont(new Font("Arial", Font.PLAIN, 13));
		}
		
		return lblPleaseSelectAn;
	}
		
	public JPanel getPanel_6() {
		if(panel_6 == null){
			panel_6 = new JPanel();
			panel_6.setLayout(new BorderLayout(0, 0));
			panel_6.add(getScrollPane(), BorderLayout.CENTER);
		}
		
		return panel_6;
	}

	public JPanel getPanel_7() {
		if(panel_7 == null){
			panel_7 = new JPanel();

		}

		return panel_7;
	}

	public JLabel getLblYouCanAlso() {
		if(lblYouCanAlso == null){
			lblYouCanAlso = new JLabel("You can also search for a specific player, or find one random:");
			lblYouCanAlso.setForeground(new Color(186, 85, 211));
			lblYouCanAlso.setFont(new Font("Arial", Font.PLAIN, 13));
		}
		
		return lblYouCanAlso;
	}
	
	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	public JTable getTable() {
		if (table == null) {
			GUIControler.onlineLista = Client.getOnlineList();
			String [][] data = new String[GUIControler.onlineLista.size()][1];
			 
			for (int i=0; i< GUIControler.onlineLista.size(); i++) {
				/*if(GUIControler.onlineLista.get(i).equals(GUIControler.playerUsername)) {
					continue;
				}*/
				data[i][0]=GUIControler.onlineLista.get(i);
			} 
			
			dtm = new DefaultTableModel(data,
			      new Object[] { "" });
			table=new JTable(dtm);
			table.setPreferredScrollableViewportSize(new Dimension(100, 100));
			table.setFillsViewportHeight(true);
			
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
						int row=table.rowAtPoint(e.getPoint());
						if(row>-1)
							//GUIControler.choose((String)table.getModel().getValueAt(row, 0));
							GUIControler.choose(table.getModel().getValueAt(table.
			                          convertRowIndexToModel(row), 0).toString());
					  }
				
			});
			
		}
		
		return table;
	}
	
	public void filter(String txt){
		TableRowSorter<DefaultTableModel> trs = new TableRowSorter<DefaultTableModel>(dtm);
		table.setRowSorter(trs);
		trs.setRowFilter(RowFilter.regexFilter(txt));
		
		
		
	}
	
}
