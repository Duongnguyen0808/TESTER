package framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import framework.model.LoginCase;

public final class ExcelReader {

    private ExcelReader() {
    }

    public static String getCellValue(Cell cell, DataFormatter formatter, FormulaEvaluator evaluator) {
        if (cell == null) {
            return "";
        }

        CellType type = cell.getCellType();
        switch (type) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return formatter.formatCellValue(cell).trim();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()).trim();
            case FORMULA:
                return formatter.formatCellValue(cell, evaluator).trim();
            case BLANK:
            default:
                return "";
        }
    }

    public static List<LoginCase> readSheet(String relativePathFromResources, String sheetName) {
        Path excelPath = Path.of("src", "test", "resources").resolve(relativePathFromResources);

        try (InputStream input = Files.newInputStream(excelPath); Workbook workbook = WorkbookFactory.create(input)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            DataFormatter formatter = new DataFormatter();

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return List.of();
            }

            Map<String, Integer> headerIndex = new HashMap<>();
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                String header = getCellValue(headerRow.getCell(c), formatter, evaluator).toLowerCase();
                if (!header.isBlank()) {
                    headerIndex.put(header, c);
                }
            }

            List<LoginCase> cases = new ArrayList<>();
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                String username = valueByHeader(row, "username", headerIndex, formatter, evaluator);
                String password = valueByHeader(row, "password", headerIndex, formatter, evaluator);
                String expectedUrl = valueByHeader(row, "expected_url", headerIndex, formatter, evaluator);
                String expectedError = valueByHeader(row, "expected_error", headerIndex, formatter, evaluator);
                String description = valueByHeader(row, "description", headerIndex, formatter, evaluator);

                if (username.isBlank() && password.isBlank() && description.isBlank()) {
                    continue;
                }

                cases.add(new LoginCase(sheetName, username, password, expectedUrl, expectedError, description));
            }
            return cases;
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot read Excel file", ex);
        }
    }

    public static List<LoginCase> readAllSheets(String relativePathFromResources, String... sheetNames) {
        List<LoginCase> all = new ArrayList<>();
        for (String sheetName : sheetNames) {
            all.addAll(readSheet(relativePathFromResources, sheetName));
        }
        return all;
    }

    private static String valueByHeader(Row row, String header, Map<String, Integer> headerIndex,
                                        DataFormatter formatter, FormulaEvaluator evaluator) {
        Integer idx = headerIndex.get(header);
        return idx == null ? "" : getCellValue(row.getCell(idx), formatter, evaluator);
    }
}
