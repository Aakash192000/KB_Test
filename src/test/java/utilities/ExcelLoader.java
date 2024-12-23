package utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelLoader {

    public static List<Map<String, String>> loadTestCases(String filePath) {
        List<Map<String, String>> testCaseList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
            Row headerRow = sheet.getRow(0); // Assuming the first row contains headers

            // Debugging: Print out the header names
            System.out.println("Headers:");
            for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                System.out.print(headerRow.getCell(j).getStringCellValue() + " ");
            }
            System.out.println(); // Line break after headers

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Iterate rows (excluding header)
                Row currentRow = sheet.getRow(i);
                Map<String, String> testCase = new HashMap<>();

                for (int j = 0; j < currentRow.getLastCellNum(); j++) { // Iterate columns
                    String header = headerRow.getCell(j).getStringCellValue().trim(); // Trim spaces
                    String value = "";

                    if (currentRow.getCell(j) != null) {
                        switch (currentRow.getCell(j).getCellType()) {
                            case STRING:
                                value = currentRow.getCell(j).getStringCellValue().trim();
                                break;
                            case NUMERIC:
                                value = String.valueOf(currentRow.getCell(j).getNumericCellValue());
                                break;
                            case BOOLEAN:
                                value = String.valueOf(currentRow.getCell(j).getBooleanCellValue());
                                break;
                            default:
                                value = "";
                        }
                    }

                    // Debugging: Print the column value and header
                    System.out.println("Header: " + header + " Value: " + value);

                    testCase.put(header, value); // Store the value in the test case map
                }

                // Handle missing values gracefully and log a warning
                if (testCase.get("Action") == null || testCase.get("Input") == null || testCase.get("ExpectedResult") == null) {
                    System.out.println("Warning: Missing values in the test case. Skipping: " + testCase);
                    continue; // Skip test case with missing values
                }

                testCaseList.add(testCase); // Add the populated test case to the list
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test cases from Excel: " + e.getMessage());
        }

        // Debugging: Print the loaded test cases
        System.out.println("Loaded test cases: " + testCaseList);

        return testCaseList;
    }
}
