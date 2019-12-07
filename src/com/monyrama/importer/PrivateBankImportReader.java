package com.monyrama.importer;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PrivateBankImportReader implements ImportReader {

    @Override
    public List<ImportExpense> read(File file) {
        List<ImportExpense> importExpenses = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(new File(file.getAbsolutePath()));
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 2; i < sheet.getLastRowNum(); i++) {
                ImportExpense importExpense = new ImportExpense();
                importExpense.setDate(sheet.getRow(i).getCell(0).getStringCellValue());
                importExpense.setTime(sheet.getRow(i).getCell(1).getStringCellValue());
                importExpense.setCategory(sheet.getRow(i).getCell(2).getStringCellValue());
                importExpense.setDescription(sheet.getRow(i).getCell(4).getStringCellValue());
                importExpense.setSum(new BigDecimal(sheet.getRow(i).getCell(5).getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
                importExpenses.add(importExpense);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return importExpenses;
    }

}
