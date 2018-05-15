package com.monyrama.ui.view;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.monyrama.ui.resources.Resources;

public class StatusBar extends JPanel {
	private static StatusBar instance;
	
	private JLabel serverStateLabel;
	
	private StatusBar() {
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);		
		setLayout(flowLayout);
		Border loweredetchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		setBorder(loweredetchedBorder);
		serverStateLabel = new JLabel();
		add(serverStateLabel);		
	}
	
	public static StatusBar instance() {
		if(instance == null) {
			instance = new StatusBar();
		}
		return instance;
	}
	
	public void setServerState(boolean running, String ip, int port, boolean hasConnection) {
		if(running) {
			serverStateLabel.setIcon(Resources.getIcon("start_server.png"));
			StringBuilder res = new StringBuilder();
			res.append("<html>");
			res.append(Resources.getString("server.state.running"));
			res.append(" (");
			res.append(Resources.getString("labels.server.ip"));
			res.append(": ");
			res.append(ip);
			res.append(", ");
			res.append(Resources.getString("labels.server.port"));
			res.append(": ");
			res.append(port);
			res.append(")");
			if(!hasConnection) {
				res.append(" | <strong><font color='red'>");
				res.append(Resources.getString("server.state.noconnection"));
				res.append("</font></strong>");
			}
			res.append("</html>");
			serverStateLabel.setText(res.toString());
		} else {
			serverStateLabel.setIcon(Resources.getIcon("stop_server.png"));
			serverStateLabel.setText(Resources.getString("server.state.stopped"));			
		}
	}
}
