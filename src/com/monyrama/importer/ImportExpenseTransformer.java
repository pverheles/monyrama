package com.monyrama.importer;

import com.monyrama.entity.PExpense;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImportExpenseTransformer {

    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyyHH:mm");

    public PExpense transform(ImportMoneyMovement importMoneyMovement) {
        PExpense pExpense = new PExpense();

        pExpense.setSumm(importMoneyMovement.getSum());
        pExpense.setComment(importMoneyMovement.getDescription());
        try {
            pExpense.setLastChangeDate(dateFormat.parse(importMoneyMovement.getDate() + importMoneyMovement.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            pExpense.setLastChangeDate(new Date());
        }

        return pExpense;
    }
}
