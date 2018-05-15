package com.monyrama.ui.dialogs;

import com.monyrama.controller.ExpenseController;
import com.monyrama.controller.SavedExpenseCommentController;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.NewExpenseValidator;

class NewExpenseDialog extends ExpenseDialog {

    public NewExpenseDialog() {
        super();
        setTitle(Resources.getString("dialogs.titles.newexpense"));
    }

    @Override
    protected void handleOk() {
        PExpense newExpense = new PExpense();
        PExpensePlanItem item = getSelectedItem();
        newExpense.setExpensePlanItem(item);
        newExpense.setAccount(getSelectedAccount());
        newExpense.setSumStr(Trimmer.trim(priceField.getText()));
        newExpense.setLastChangeDate(dateChooser.getDate());
        newExpense.setComment(commentField.getText() == null ? null : commentField.getText().trim());

        EntityValidator validator = new NewExpenseValidator(newExpense, expensePlan);

        if (validator.validate()) {
        	ExpenseController.instance().create(newExpense);
            SavedExpenseCommentController.instance().save(newExpense.getComment());
            MyPreferences.save(PrefKeys.LAST_EXPENSE_ACCOUNT_ID, newExpense.getAccount().getId());
            MyPreferences.save(PrefKeys.LAST_EXPENSE_ENVELOPE_ID, newExpense.getExpensePlanItem().getId());
            setVisible(false);
        } else {
            MyDialogs.showWarningDialog(this, validator.message());
        }

    }

}
