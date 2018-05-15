package com.monyrama.ui.dialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import com.monyrama.controller.ServerOptionsController;
import com.monyrama.server.PBVPServer;
import com.monyrama.ui.components.JTextFieldLimited;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.constants.ServerConstants;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.ui.view.StatusBar;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.PortValidator;


public class ServerOptionsDialog extends EscapeDialog {
	
	private JLabel noConnectionLabel;
	
	private JLabel ipValueLabel;
	private JTextFieldLimited portField;
	private JCheckBox autoStartCheckBox;
	
	private static ServerOptionsDialog dialog;
	private TwoButtonsPanel buttonPanel;	

	/**
	 * Constructor
	 * 
	 * Paints the dialog
	 *  
	 */
	private ServerOptionsDialog() {
		super();			
		
		setTitle(Resources.getString("dialogs.titles.serveroptions"));
		
		final GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		
		noConnectionLabel = new JLabel();
		noConnectionLabel.setText(Resources.getString("labels.server.noConnection") + ":");
		final GridBagConstraints gridBagConstraints_118 = new GridBagConstraints();
		gridBagConstraints_118.insets = new Insets(10, 10, 10, 10);		
		gridBagConstraints_118.anchor = GridBagConstraints.WEST;
		gridBagConstraints_118.gridy = 0;
		gridBagConstraints_118.gridx = 1;
		gridBagConstraints_118.gridwidth = 2;
		getContentPane().add(noConnectionLabel, gridBagConstraints_118);
		
		final JLabel ipLabel = new JLabel();
		ipLabel.setText(Resources.getString("labels.server.ip") + ":");
		final GridBagConstraints gridBagConstraints_117 = new GridBagConstraints();
		gridBagConstraints_117.insets = new Insets(10, 10, 10, 0);		
		gridBagConstraints_117.anchor = GridBagConstraints.WEST;
		gridBagConstraints_117.gridy = 1;
		gridBagConstraints_117.gridx = 1;
		getContentPane().add(ipLabel, gridBagConstraints_117);
		
		ipValueLabel = new JLabel();
		ipValueLabel.setPreferredSize(new Dimension(120, 20));
		final GridBagConstraints gridBagConstraints_18 = new GridBagConstraints();
		gridBagConstraints_18.insets = new Insets(10, 5, 10, 10);
		gridBagConstraints_18.anchor = GridBagConstraints.WEST;
		gridBagConstraints_18.gridy = 1;
		gridBagConstraints_18.gridx = 2;
		getContentPane().add(ipValueLabel, gridBagConstraints_18);		
		
		final JLabel portLabel = new JLabel();
		portLabel.setText(Resources.getString("labels.server.port") + ":");
		final GridBagConstraints gridBagConstraints_17 = new GridBagConstraints();
		gridBagConstraints_17.insets = new Insets(5, 10, 15, 0);		
		gridBagConstraints_17.anchor = GridBagConstraints.WEST;
		gridBagConstraints_17.gridy = 3;
		gridBagConstraints_17.gridx = 1;
		getContentPane().add(portLabel, gridBagConstraints_17);

		portField = new JTextFieldLimited(ServerConstants.PORT_LENGTH);
		portField.setPreferredSize(new Dimension(120, 20));
		final GridBagConstraints gridBagConstraints_80 = new GridBagConstraints();
		gridBagConstraints_80.insets = new Insets(0, 5, 10, 10);
		gridBagConstraints_80.anchor = GridBagConstraints.WEST;
		gridBagConstraints_80.gridy = 3;
		gridBagConstraints_80.gridx = 2;
		getContentPane().add(portField, gridBagConstraints_80);
		
		final JLabel autoStartLabel = new JLabel();
		autoStartLabel.setText(Resources.getString("labels.server.autostart") + ":");
		final GridBagConstraints gridBagConstraints_171 = new GridBagConstraints();
		gridBagConstraints_171.insets = new Insets(5, 10, 15, 0);		
		gridBagConstraints_171.anchor = GridBagConstraints.WEST;
		gridBagConstraints_171.gridy = 4;
		gridBagConstraints_171.gridx = 1;
		getContentPane().add(autoStartLabel, gridBagConstraints_171);

		autoStartCheckBox = new JCheckBox();
		final GridBagConstraints gridBagConstraints_810 = new GridBagConstraints();
		gridBagConstraints_810.insets = new Insets(0, 5, 10, 10);
		gridBagConstraints_810.anchor = GridBagConstraints.WEST;
		gridBagConstraints_810.gridy = 4;
		gridBagConstraints_810.gridx = 2;
		getContentPane().add(autoStartCheckBox, gridBagConstraints_810);		
		
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_15.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_15.gridwidth = 3;
		gridBagConstraints_15.gridy = 5;
		gridBagConstraints_15.gridx = 0;
		getContentPane().add(sep, gridBagConstraints_15);
				
		buttonPanel = new TwoButtonsPanel();
		buttonPanel.setApproveButtonText(Resources.getString("dialogs.save"));
		buttonPanel.setApproveButtonIcon(Resources.getIcon("save.png"));
        buttonPanel.setApproveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String portValue = null;
				if(portField.getText() != null) {
					portValue = portField.getText().trim();
				}
				EntityValidator portValidator = new PortValidator(portField.getText());
				if(portValidator.validate()) {
					int portIntValue = Integer.valueOf(portValue);
					int previousPortValue = ServerOptionsController.instance().getServerPort();					
					ServerOptionsController.instance().saveAutoStart(autoStartCheckBox.isSelected());
					if(portIntValue != previousPortValue) {
						ServerOptionsController.instance().saveServerPort(portIntValue);
						if(PBVPServer.getServer().isRunning()) {
							try {
								PBVPServer.getServer().restartWithNewPort();
								actionOnEscape();
							} catch (Exception e1) {
								MyDialogs.showErrorDialog(ServerOptionsDialog.this, Resources.getString("dialogs.errors.faildToRestartServer"));
							} finally {
								StatusBar.instance().setServerState(PBVPServer.getServer().isRunning(), ServerOptionsController.instance().getServerIP(),
										ServerOptionsController.instance().getServerPort(), ServerOptionsController.instance().hasConnection());
							}
						} else {
							actionOnEscape();
						}
					} else {
						actionOnEscape();
					}
				} else {
					MyDialogs.showWarningDialog(ServerOptionsDialog.this, portValidator.message());
				}
			}        	
        });
        buttonPanel.setCancelListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionOnEscape();
			}
		});
        
        final GridBagConstraints gridBagConstraints_16 = new java.awt.GridBagConstraints();
        gridBagConstraints_16.gridx = 0;
        gridBagConstraints_16.gridy = 6;
        gridBagConstraints_16.gridwidth = 3;
        gridBagConstraints_16.anchor = GridBagConstraints.CENTER;
        gridBagConstraints_16.insets = new java.awt.Insets(0, 0, 10, 0);
		getContentPane().add(buttonPanel, gridBagConstraints_16);
		
		pack();
		setModal(true);
		setResizable(false);	
	}
	
	public static void openDialog() {
		if(dialog == null) {
			dialog = new ServerOptionsDialog();
		}
		
		dialog.ipValueLabel.setText(ServerOptionsController.instance().getServerIP());
		dialog.portField.setText(Integer.toString(ServerOptionsController.instance().getServerPort()));
		dialog.autoStartCheckBox.setSelected(ServerOptionsController.instance().getAutoStart());
		
		if(!ServerOptionsController.instance().hasConnection()) {
			dialog.noConnectionLabel.setVisible(true);			
		} else {
			dialog.noConnectionLabel.setVisible(false);
		}
				
		dialog.getRootPane().setDefaultButton(dialog.buttonPanel.getApproveButton());
		
		dialog.pack();
		
		dialog.showIt();				
	}

	@Override
	protected void actionOnEscape() {
		setVisible(false);
	}
}
