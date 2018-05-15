package com.monyrama.ui.dialogs;

import java.awt.Component;

import javax.swing.JPanel;

public class WizardDialog extends EscapeDialog {
	private WizardDialog dialog;
	private WizardPage currentPage;
	
	private WizardDialog(Component parentWindow, WizardPage currentPage) {
		this.currentPage = currentPage; 
		setModal(true);
		setLocationRelativeTo(parentWindow);
		
		
	}
		
	@Override
	protected void actionOnEscape() {
		this.dispose();
	}
	
	public static abstract class WizardPage extends JPanel {
		private WizardPage previousPage;
		private WizardPage nextPage;
		private Object data;
		
		public WizardPage() {}
		
		public void setData(Object data) {
			this.data = data;
		}
		
		public Object getData() {
			return data;
		}
		
		protected abstract boolean validateData();
		protected abstract String getValidationDataError();
		protected abstract boolean hasNext();
		protected abstract void performOnNextOrFinish();
		protected abstract void performOnPrevious();
		
		public WizardPage getNextPage() {
			return nextPage;
		}
		
		public WizardPage getPreviousPage() {
			return previousPage;
		}
	}

	public static void main(String[] args) {
//		WizardDialog dialog = new WizardDialog(null);
//		String test = (String)dialog.openDialog();
//		System.out.println(test);
	}
	
}
