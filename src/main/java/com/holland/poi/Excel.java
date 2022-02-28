package com.holland.poi;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author zhn
 * @date 2021/12/10 10:55
 */
public interface Excel {

    /**
     * 获取文件本名和扩展名
     *
     * @param workbook eg: workbook.xlsx
     * @return [0]: workbook, [1]: xlsx
     */
    String[] getWorkbookNameAndExtensionName(String workbook);

    default String getFullName(String[] workbookNameAndExtensionName) {
        return workbookNameAndExtensionName[0] + '.' + workbookNameAndExtensionName[1];
    }

    /**
     * 创建工作簿
     */
    Workbook createWorkbook(String workbook);

    /**
     * 创建工作表，覆盖写入
     */
    Sheet createSheet(String sheetName, String workbook);

}
