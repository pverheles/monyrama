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
    public List<ImportMoneyMovement> read(File file) {
        List<ImportMoneyMovement> importMoneyMovements = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(new File(file.getAbsolutePath()));
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 2; i < sheet.getLastRowNum() + 1; i++) {
                ImportMoneyMovement importMoneyMovement = new ImportMoneyMovement();
                String dateTimeStr = sheet.getRow(i).getCell(0).getStringCellValue();
                importMoneyMovement.setDate(dateTimeStr.split(" ")[0]);
                importMoneyMovement.setTime(dateTimeStr.split(" ")[1]);
                importMoneyMovement.setCategory(sheet.getRow(i).getCell(1).getStringCellValue());
                importMoneyMovement.setDescription(sheet.getRow(i).getCell(3).getStringCellValue());
                importMoneyMovement.setSum(new BigDecimal(sheet.getRow(i).getCell(4).getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
                importMoneyMovements.add(importMoneyMovement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return importMoneyMovements;
    }

}
