package com.monyrama.ui.dialogs;

import com.monyrama.controller.ExpenseController;
import com.monyrama.controller.SavedExpenseCommentController;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EditExpenseValidator;
import com.monyrama.validator.EntityValidator;

class EditExpenseDialog extends ExpenseDialog {

    public EditExpenseDialog() {
        super();
        setTitle(Resources.getString("dialogs.titles.editexpens"));
    }

    @Override
    protected void handleOk() {
        PExpense editExpense = new PExpense();
        editExpense.setId(expense.getId());
        PExpensePlanItem item = getSelectedItem();
        editExpense.setExpensePlanItem(item);
        editExpense.setAccount(getSelectedAccount());
        editExpense.setSumStr(Trimmer.trim(priceField.getText()));
        editExpense.setLastChangeDate(dateChooser.getDate());
        editExpense.setComment(commentField.getText() == null ? null : commentField.getText().trim());

        EntityValidator validator = new EditExpenseValidator(editExpense, expensePlan);

        if (validator.validate()) {
        	ExpenseController.instance().update(expense, editExpense);
            SavedExpenseCommentController.instance().save(editExpense.getComment());
            setVisible(false);
        } else {
            MyDialogs.showWarningDialog(this, validator.message());
        }
    }
}
