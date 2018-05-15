package com.monyrama.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;

import com.monyrama.ui.resources.Resources;


/**
 * The panel with the information about coloring of the table rows (or maybe in future
 * with some other information.
 * Should be a part of JSplitPane
 * 
 * @author Petro_Verheles
 *
 */
public class InfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel contentPanel;
	
	private final Dimension ZERO_DIMENSION = new Dimension(0, 0);
	
	private JSplitPane parent;
	
	private int lastDividerLocation;
	
	private static final int TITLE_HEIGHT = 20;
	
	private boolean elapsed = false;
	
	/**
	 * Constructor
	 * 
	 * @param par - parent JSplitPane
	 */
	public InfoPanel(JSplitPane par) {
		this.parent = par;
		
		JPanel headerPanel = new JPanel();
		contentPanel = new JPanel();
		
		contentPanel.setMinimumSize(ZERO_DIMENSION);		
		
		setLayout(new BorderLayout());
		
		JScrollPane contentScrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentScrollPane.setMinimumSize(ZERO_DIMENSION);
		
		add(headerPanel, BorderLayout.NORTH);
		add(contentScrollPane, BorderLayout.CENTER);
		
		//Implementing Header Panel
		headerPanel.setLayout(new GridBagLayout());
		headerPanel.setBackground(new Color(230, 230, 250));
		
		JLabel infoLabel = new JLabel(Resources.getString("labels.info"));
		infoLabel.setIcon(Resources.getIcon("info.png"));
		infoLabel.setForeground(SystemColor.activeCaption);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		headerPanel.add(infoLabel, gridBagConstraints);
		
		JButton hideShowButton = new JButton();
		Dimension dim = new Dimension(TITLE_HEIGHT, 15);
		hideShowButton.setPreferredSize(dim);
		hideShowButton.setMaximumSize(dim);
		hideShowButton.setMinimumSize(dim);
		hideShowButton.setBackground(new Color(230, 230, 250));
		hideShowButton.setOpaque(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		headerPanel.add(hideShowButton, gridBagConstraints);
		
		hideShowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!elapsed) {
					lastDividerLocation = parent.getDividerLocation();
					elapsed = true;
					int location = parent.getSize().height - TITLE_HEIGHT;
					parent.setDividerLocation(location);				
				} else {
					parent.setDividerLocation(lastDividerLocation);
				}							
			}			
		});
		
		JSeparator separator = new JSeparator();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        headerPanel.add(separator, gridBagConstraints);
                
        parent.addPropertyChangeListener(propertyChangeListener);        
	}
	
	private PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent changeEvent) {
			JSplitPane sourceSplitPane = (JSplitPane) changeEvent.getSource();
			String propertyName = changeEvent.getPropertyName();
			if (propertyName.equals(JSplitPane.LAST_DIVIDER_LOCATION_PROPERTY)) {
				int dividerLocation = sourceSplitPane.getDividerLocation();
				int elapsedLocation = parent.getSize().height - TITLE_HEIGHT;
				if(dividerLocation != elapsedLocation) {
					elapsed = false;	
				}				
			}
		}
	};
	
	public JPanel getContentPanel() {
		return contentPanel;
	}
}
