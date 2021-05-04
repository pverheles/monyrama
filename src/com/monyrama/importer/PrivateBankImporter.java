package com.monyrama.importer;

import static com.monyrama.db.enumarations.EntityStates.ACTIVE;
import static java.util.stream.Collectors.toList;

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
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class PrivateBankImporter {

  private PExpensePlanItem defaultExpensePlanItem;
  private PAccount account;

  long accountId = 1561114229197L;

  public void importExpenses(Component parent, PExpensePlan expensePlan, File file) {
    List<PExpensePlanItem> expensePlanItems = ExpensePlanItemController.instance()
        .listByExpensePlan(expensePlan).stream().filter(x -> StringUtils.isEmpty(x.getName()))
        .collect(
            toList());

    Optional<PExpensePlanItem> defaultExpensePlanItemOptional = expensePlanItems.stream()
        .filter(x -> x.getCategory().getIsDefault()).findFirst();

    if (defaultExpensePlanItemOptional.isPresent()) {
      defaultExpensePlanItem = defaultExpensePlanItemOptional.get();
    } else {
      // should not ever happen
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
          .peek(imm -> imm.setDescription(
              imm.getDescription().substring(0, Math.min(100, imm.getDescription().length()))))
          .map(transformer::transform)
          .peek(exp -> enrichExpense(exp, expensePlanItems))
          .forEach(expenseController::create);
    }
  }

  private void enrichExpense(PExpense expense,
      List<PExpensePlanItem> expensePlanItems) {
    expense.setState(ACTIVE.getCode());
    expense.setAccount(account);

    PExpensePlanItem expensePlanItem = expensePlanItems.stream()
        .filter(x -> isExpensePlanItemForExpense(x, expense)).findFirst()
        .orElse(defaultExpensePlanItem);

    expense.setExpensePlanItem(expensePlanItem);

  }

  private boolean isExpensePlanItemForExpense(PExpensePlanItem expensePlanItem, PExpense expense) {
    return expensePlanItem.getCategory().keywordsSetLower().stream()
        .anyMatch(x -> StringUtils.trimToEmpty(expense.getComment()).toLowerCase().contains(x));
  }
}
