package com.monyrama.importer;

import static com.monyrama.db.enumarations.EntityStates.ACTIVE;

import com.monyrama.controller.AccountController;
import com.monyrama.controller.ExpenseController;
import com.monyrama.controller.ExpensePlanItemController;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlanItem;

import java.io.File;
import java.util.List;

public class PrivateBankTemporaryImporter {

    private PExpensePlanItem expensePlanItem;
    private PAccount account;

    long expensePlanItemId = 1L;
    long accountId = 1L;

    public void importExpenses(File file) {
        expensePlanItem = ExpensePlanItemController.instance().getById(expensePlanItemId);
        account = AccountController.instance().getById(accountId);

        ImportReader importReader = new PrivateBankImportReader();
        List<ImportMoneyMovement> importMoneyMovements = importReader.read(file);

        ImportExpenseTransformer transformer = new ImportExpenseTransformer();

        if (importMoneyMovements != null) {
            ExpenseController expenseController = ExpenseController.instance();
            importMoneyMovements.stream().map(transformer::transform)
                                .map(this::enrichExpense)
                                .forEach(expenseController::create);
        }
    }

    private PExpense enrichExpense(PExpense pExpense) {
        pExpense.setState(ACTIVE.getCode());

        pExpense.setExpensePlanItem(expensePlanItem);
        pExpense.setAccount(account);

        return pExpense;
    }
}
