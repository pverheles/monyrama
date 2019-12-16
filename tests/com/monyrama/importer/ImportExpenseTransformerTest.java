package com.monyrama.importer;

import static org.junit.Assert.assertEquals;

import com.monyrama.entity.PExpense;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImportExpenseTransformerTest {

    private ImportExpenseTransformer transformer;

    @Before
    public void setUp() {
        transformer = new ImportExpenseTransformer();
    }

    @Test
    public void transform()
            throws ParseException {
        ImportMoneyMovement importMoneyMovement = new ImportMoneyMovement();

        BigDecimal sum = new BigDecimal(RandomUtils.nextInt(1, 100000)).movePointLeft(2);
        importMoneyMovement.setSum(sum);

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        importMoneyMovement.setDate(dateFormat.format(date));
        importMoneyMovement.setTime(timeFormat.format(date));

        String importCategory = RandomStringUtils.randomAlphanumeric(10);
        importMoneyMovement.setCategory(importCategory);

        String description = RandomStringUtils.randomAlphanumeric(100);
        importMoneyMovement.setDescription(description);

        PExpense pExpense = transformer.transform(importMoneyMovement);

        DateFormat dateFormatNoSeconds = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        assertEquals(sum, pExpense.getSumm());
        assertEquals(dateFormatNoSeconds.parse(dateFormatNoSeconds.format(date)), pExpense.getLastChangeDate());
        assertEquals(description, pExpense.getComment());
    }

}
