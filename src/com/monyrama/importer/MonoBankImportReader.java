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

public class MonoBankImportReader implements ImportReader {

    @Override
    public List<ImportMoneyMovement> read(File file) {
        List<ImportMoneyMovement> importMoneyMovements = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(new File(file.getAbsolutePath()));
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 22; i < sheet.getLastRowNum(); i++) {
                ImportMoneyMovement importMoneyMovement = new ImportMoneyMovement();
                String[] dateTime = sheet.getRow(i).getCell(0).getStringCellValue().split(" ");
                importMoneyMovement.setDate(dateTime[0]);
                String time = dateTime[1];
                String[] hhMMss = time.split(":");
                importMoneyMovement.setTime(hhMMss[0] + ":" + hhMMss[1]);
                importMoneyMovement.setDescription(sheet.getRow(i).getCell(1).getStringCellValue());
                importMoneyMovement.setSum(new BigDecimal(sheet.getRow(i).getCell(3).getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
                importMoneyMovements.add(importMoneyMovement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return importMoneyMovements;
    }

}
