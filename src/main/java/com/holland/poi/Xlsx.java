package com.holland.poi;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 2007及以后版本
 *
 * @author zhn
 * @date 2021/12/10 11:01
 */
public class Xlsx implements Excel {

    public static void main(String[] args) {
        final Excel excel = new Xlsx();
        String workbook = "workbook.xlsx";

//        final Workbook workbook1 = excel.createWorkbook(workbook);
        excel.createSheet("asaad啊", workbook);
    }

    @Override
    public String[] getWorkbookNameAndExtensionName(String workbook) {
        final char[] chars = workbook.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if ('.' == chars[i]) {
                return new String[]{workbook.substring(0, i), workbook.substring(i + 1)};
            }
        }
        return new String[]{workbook, "xlsx"};
    }

    @Override
    public Workbook createWorkbook(String workbook) {
        final String[] name = getWorkbookNameAndExtensionName(workbook);
        workbook = getFullName(name);

        Workbook wb = new XSSFWorkbook();
        try (OutputStream fileOut = new FileOutputStream(workbook)) {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    @Override
    public Sheet createSheet(String sheetName, String workbook) {
        final String[] name = getWorkbookNameAndExtensionName(workbook);
        workbook = getFullName(name);

        Workbook wb = new XSSFWorkbook();
// Note that sheet name is Excel must not exceed 31 characters
// and must not contain any of the any of the following characters:
// 0x0000
// 0x0003
// colon (:)
// backslash (\)
// asterisk (*)
// question mark (?)
// forward slash (/)
// opening square bracket ([)
// closing square bracket (])
// You can use org.apache.poi.ss.util.WorkbookUtil#createSafeSheetName(String nameProposal)}
// for a safe way to create valid names, this utility replaces invalid characters with a space (' ')
//        String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); // returns " O'Brien's sales   "
        String safeName = WorkbookUtil.createSafeSheetName(sheetName);
        Sheet sheet = wb.createSheet(safeName);
        try (OutputStream fileOut = new FileOutputStream(workbook)) {
            wb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }

}
