package com.monyrama.importer;

import java.io.File;
import java.util.List;

public interface ImportReader {
    List<ImportMoneyMovement> read(File file);
}
