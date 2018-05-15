package com.monyrama.ui.dialogs;

import com.monyrama.controller.IncomeItemController;
import com.monyrama.controller.IncomeItemController;
import com.monyrama.entity.PIncome;
import com.monyrama.ui.dialogs.IncomeItemDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.IncomeItemValidator;

class EditIncomeItemDialog extends IncomeItemDialog {

    public EditIncomeItemDialog() {
        super();
        setTitle(Resources.getString("dialogs.titles.editIncomeitem"));
    }

    @Override
    protected void handleOk() {
        PIncome editIncomeItem = new PIncome();        
        editIncomeItem.setId(incomeItem.getId());
        editIncomeItem.setIncomeSource(incomeItem.getIncomeSource());
        editIncomeItem.setAccount(getSelectedAccount());
        editIncomeItem.setSumStr(Trimmer.trim(sumField.getText()));
        editIncomeItem.setLastChangeDate(dateChooser.getDate());
        editIncomeItem.setComment(commentField.getText());

        EntityValidator validator = new IncomeItemValidator(editIncomeItem);

        if (validator.validate()) {
            IncomeItemController.instance().update(incomeItem, editIncomeItem);
            setVisible(false);
        } else {
            MyDialogs.showWarningDialog(this, validator.message());
        }

    }

}
