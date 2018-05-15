package com.monyrama.ui.tables.model;

import com.monyrama.entity.PTransfer;
import com.monyrama.ui.tables.columns.TransferColumnEnum;
import com.monyrama.ui.utils.MyFormatter;

/**
 * Model for the table of transfers
 * 
 * @author Petro_Verheles
 *
 */
public class TransferTableModel extends AbstractIdableTableModel<PTransfer> {
		
	@Override
	public int getColumnCount() {
		return TransferColumnEnum.values().length;
	}


	@Override
	public Object getValueAt(int row, int column) {
		PTransfer transfer = data.values().toArray(new PTransfer[data.size()])[row];

		if(column == TransferColumnEnum.ID.getIndex()) {
			return transfer.getId();
		}

		if(column == TransferColumnEnum.DATE.getIndex()) {
			return transfer.getLastChangeDate();
		}
		
		if(column == TransferColumnEnum.FROMACCOUNT.getIndex()) {
			return transfer.getTransferOut().getAccount().toString();
		}
		
		if(column == TransferColumnEnum.FROMSUM.getIndex()) {
			return MyFormatter.formatNumberToLocal(transfer.getTransferOut().getSumm().toPlainString());
		}
		
		if(column == TransferColumnEnum.TOACCOUNT.getIndex()) {
			return transfer.getTransferIn().getAccount().toString();
		}
		
		if(column == TransferColumnEnum.TOSUM.getIndex()) {
			return MyFormatter.formatNumberToLocal(transfer.getTransferIn().getSumm().toPlainString());
		}		
		
		System.out.println("Invalid column index: " + column);

		return null;
	}
	
	@Override
	public String getColumnName(int col) {
        return TransferColumnEnum.values()[col].getName();
    }
}
