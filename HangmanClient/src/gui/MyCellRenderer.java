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
import javax.swing.text.DefaultCaret;

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
		ta.setMargin(new Insets(5, 5, 5, 5));
		ta.setBackground(Color.DARK_GRAY);
		ta.setForeground(Color.WHITE);
		
		p.add(ta, BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(final JList list,
			final Object value, final int index, final boolean isSelected,
			final boolean hasFocus) {

		ta.setText((String) value);
		
		DefaultCaret caret = (DefaultCaret)ta.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		if(value.toString().startsWith(Client.getUsername())) {
			ta.setForeground(new Color(221, 160, 221));		
		} else if(value.toString().startsWith(Client.getOpponent())) {
			ta.setForeground(Color.WHITE);
		}
		
		int width = list.getWidth();
		int scrollbar = ((Integer)UIManager.get("ScrollBar.width")).intValue();
		if (width > 0)
			ta.setSize(width-scrollbar, Short.MAX_VALUE);
		
		p.repaint();
		return p;
	}

}
