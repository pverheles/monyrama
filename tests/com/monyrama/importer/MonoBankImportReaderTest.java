package com.monyrama.importer;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MonoBankImportReaderTest {

    private ImportReader importReader;

    @Before
    public void setUp() {
        importReader = new MonoBankImportReader();
    }

    @Test
    public void readSuccessfully() {
        File file = new File(MonoBankImportReaderTest.class.getResource("/com/monyrama/importer/report_monobank.xls").getPath());
        List<ImportMoneyMovement> importMoneyMovements = importReader.read(file);
        assertEquals(27, importMoneyMovements.size());
        ImportMoneyMovement importMoneyMovement = importMoneyMovements.get(0);
        assertEquals("24.09.2022", importMoneyMovement.getDate());
        assertEquals("09:50", importMoneyMovement.getTime());
        assertNull(importMoneyMovement.getCategory());
        assertEquals("Apteka24", importMoneyMovement.getDescription());
        assertEquals(0, new BigDecimal("-54.4").compareTo(importMoneyMovement.getSum()));
        assertEquals(0, new BigDecimal("-1403.73").compareTo(importMoneyMovements.get(4).getSum()));
        assertEquals(0, new BigDecimal("30000").compareTo(importMoneyMovements.get(24).getSum()));
    }

}
