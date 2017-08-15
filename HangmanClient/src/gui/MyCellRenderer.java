package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import clients.Client;

public class MyCellRenderer implements ListCellRenderer {

	private JPanel p;
	private JTextArea ta;

	public MyCellRenderer() {
		p = new JPanel();
		p.setLayout(new BorderLayout());

		// text
		ta = new JTextArea();
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setMargin(new Insets(5, 5, 0, 5));
		ta.setBackground(Color.DARK_GRAY);
		ta.setForeground(Color.WHITE);
		
		String msg = ta.getText();
		//ta.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		if(msg.startsWith(Client.getOpponent())) {
			ta.setBackground(Color.BLACK);
		} else if (msg.startsWith(Client.getOpponent())) {
			//ta.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		
//		ta.setBorder(BorderFactory.createCompoundBorder(
//		        ta.getBorder(), 
//		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		p.add(ta, BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(final JList list,
			final Object value, final int index, final boolean isSelected,
			final boolean hasFocus) {

		ta.setText((String) value);
		int width = list.getWidth();
		int scrollbar = ((Integer)UIManager.get("ScrollBar.width")).intValue();
		if (width > 0)
			ta.setSize(width-scrollbar, Short.MAX_VALUE);
		return p;
	}

}
