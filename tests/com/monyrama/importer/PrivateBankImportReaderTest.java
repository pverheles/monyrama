package com.monyrama.importer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public class PrivateBankImportReaderTest {

    private ImportReader importReader;

    @Before
    public void setUp() {
        importReader = new PrivateBankImportReader();
    }

    @Test
    public void readSuccessfully() {
        File file = new File(PrivateBankImportReaderTest.class.getResource("/com/monyrama/importer/statements.xls").getPath());
        List<ImportExpense> importExpenses = importReader.read(file);
        assertEquals(34, importExpenses.size());
        ImportExpense importExpense = importExpenses.get(4);
        assertEquals("06.12.2019", importExpense.getDate());
        assertEquals("14:24", importExpense.getTime());
        assertEquals("Інше", importExpense.getCategory());
        assertEquals("Покупка, МАГАЗИНЫ МОЛОЧНЫХ ПРОДУКТОВ: m.Kyiv", importExpense.getDescription());
        assertEquals(0, new BigDecimal("-347.9").compareTo(importExpense.getSum()));
        assertEquals(0, new BigDecimal("-924.41").compareTo(importExpenses.get(7).getSum()));
        assertEquals(0, new BigDecimal("40000").compareTo(importExpenses.get(0).getSum()));
    }

}
