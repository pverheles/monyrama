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

    long expensePlanItemId = 1576528200036L;
    long accountId = 1561114229197L;

    public void importExpenses(File file) {
        expensePlanItem = ExpensePlanItemController.instance().getById(expensePlanItemId);
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
        pExpense.setExpensePlanItem(expensePlanItem);
        pExpense.setAccount(account);
    }
}
