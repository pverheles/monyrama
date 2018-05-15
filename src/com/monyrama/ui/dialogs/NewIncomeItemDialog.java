package com.monyrama.ui.dialogs;

import com.monyrama.controller.IncomeItemController;
import com.monyrama.entity.PIncome;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.IncomeItemValidator;

class NewIncomeItemDialog extends IncomeItemDialog {

    public NewIncomeItemDialog() {
        super();
        setTitle(Resources.getString("dialogs.titles.newincomeitem"));
    }

    @Override
    protected void handleOk() {
        PIncome newIncomeItem = new PIncome();
        newIncomeItem.setSumStr(Trimmer.trim(sumField.getText()));
        newIncomeItem.setAccount(getSelectedAccount());
        newIncomeItem.setLastChangeDate(dateChooser.getDate());
        newIncomeItem.setComment(commentField.getText());
        newIncomeItem.setIncomeSource(incomeSource);

        EntityValidator validator = new IncomeItemValidator(newIncomeItem);

        if (validator.validate()) {
            incomeItem = newIncomeItem;
            IncomeItemController.instance().create(incomeItem);
            setVisible(false);
        } else {
            MyDialogs.showWarningDialog(this, validator.message());
        }

    }

}
