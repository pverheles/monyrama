package com.monyrama.importer;

import static com.monyrama.db.enumarations.EntityStates.ACTIVE;

import com.monyrama.controller.AccountController;
import com.monyrama.controller.ExpenseController;
import com.monyrama.controller.ExpensePlanItemController;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;

import com.monyrama.ui.utils.MyDialogs;
import java.awt.Component;
import java.io.File;
import java.util.List;

public class PrivateBankTemporaryImporter {

    private PExpensePlanItem defaultExpensePlanItem;
    private PAccount account;

    long expensePlanItemId = 1615057155460L;
    long accountId = 1561114229197L;

    public void importExpenses(Component parent, PExpensePlan expensePlan, File file) {
        List<PExpensePlanItem> expensePlanItems = ExpensePlanItemController.instance()
            .listByExpensePlan(expensePlan);

        for (PExpensePlanItem expensePlanItem : expensePlanItems) {
            if (expensePlanItem.getCategory().getIsDefault()) {
                defaultExpensePlanItem = expensePlanItem;
                break;
            }
        }

        if (defaultExpensePlanItem == null) {
            MyDialogs.showWarningDialog(parent, "Couldn't find default expense plan item");
            return;
        }

        account = AccountController.instance().getById(accountId);

        ImportReader importReader = new PrivateBankImportReader();
        List<ImportMoneyMovement> importMoneyMovements = importReader.read(file);

        ImportExpenseTransformer transformer = new ImportExpenseTransformer();

        if (importMoneyMovements != null) {
            ExpenseController expenseController = ExpenseController.instance();
            importMoneyMovements.stream()
                                .filter(imm -> imm.getSum().signum() == -1)
                                .peek(imm -> imm.setSum(imm.getSum().negate()))
                                .peek(imm -> imm.setDescription(imm.getDescription().substring(0, Math.min(100, imm.getDescription().length()))))
                                .map(transformer::transform)
                                .peek(this::enrichExpense)
                                .forEach(expenseController::create);
        }
    }

    private void enrichExpense(PExpense pExpense) {
        pExpense.setState(ACTIVE.getCode());
        pExpense.setExpensePlanItem(defaultExpensePlanItem);
        pExpense.setAccount(account);
    }
}
