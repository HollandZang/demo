package com.holland.poi

import com.google.common.collect.Maps
import com.holland.util.FileUtil
import org.apache.poi.EncryptedDocumentException
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.openxml4j.exceptions.InvalidFormatException
import org.apache.poi.poifs.crypt.Decryptor
import org.apache.poi.poifs.crypt.EncryptionInfo
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


//fun main() {
//    val strings = FileUtil.readFile("C:\\Users\\xd\\Desktop", "新建文本文档.txt")
//    strings.forEachIndexed { index, s ->
//            if (s.startsWith("http")) {
//
//            } else {
//                FileUtil.newLine2File(s, "C:\\Users\\xd\\Desktop", "新建文本文档(2).txt")
//            }
//            println("line-----------------------------------------------------------------------------$index\tend")
//    }
//
//}

fun main() {
    val fileName= "廖武书单.xlsx"
    val fs = POIFSFileSystem(FileInputStream("C:\\\\Users\\\\xd\\\\Desktop\\\\$fileName"))
    val info = EncryptionInfo(fs)
    val d = Decryptor.getInstance(info)
    d.verifyPassword("xlysoft")
    val workbook = XSSFWorkbook(d.getDataStream(fs))

    val sheet = workbook!!.getSheetAt(0)
    val physicalNumberOfRows = sheet.physicalNumberOfRows
    val list = arrayListOf<String>()
    for (i in 1 until physicalNumberOfRows) {
        val row: Row = sheet.getRow(i)
        try {
            val cell: Cell = row.getCell(0)
            list.add(cell.stringCellValue)
        } catch (e: Exception) {
            println("ERR line_$i: ${e.message}")
            continue
        }
    }

    val result = list.filter { it.isNotBlank() }
        .map {
            filterPre(it)
        }
        .map { trim(it) }

    val result1 = arrayListOf<String>()
    for (i in result.indices) {
        if (result1.find { it.contains(result[i]) } == null) {
            result1.add(result[i])
        }
    }

    FileUtil.newLine2File(result1.joinToString("\n"), "C:\\Users\\xd\\Desktop", "$fileName.txt")

}

fun trim(string: String):String {
    if (string.contains("Rigor_ How Conceptual")) {
        val i=0
    }
    if (string.contains("  ")) {
        return filterPre(string.replace("  "," "))
    } else {
        return string
    }
}

fun filterPre(string: String): String {
    if (string[0].isDigit() || string[0] == '-') {
        return filterPre(string.substring(1))
    } else {
        return string
    }
}

/**
 * Excel操作工具类
 * @author ChaiXY
 */
object ExcelUtils {
    // @Value("${file_base_path}")
    // private static String fileBasePath;//文件的基础路径
    // private static String fileBasePath = System.getProperty("user.dir") + File.separator + "excel" + File.separator;;//文件的基础路径
    const val OFFICE_EXCEL_XLS = "xls"
    const val OFFICE_EXCEL_XLSX = "xlsx"

    /**
     * 读取指定Sheet也的内容
     * @param filepath filepath 文件全路径
     * @param sheetNo sheet序号,从0开始,如果读取全文sheetNo设置null
     */
    @Throws(
        EncryptedDocumentException::class,
        InvalidFormatException::class,
        IOException::class
    )
    fun readExcel(filepath: String, sheetNo: Int?): String {
        val sb = StringBuilder()
        val workbook = getWorkbook(filepath)
        if (workbook != null) {
            if (sheetNo == null) {
                val numberOfSheets = workbook.numberOfSheets
                for (i in 0 until numberOfSheets) {
                    val sheet = workbook.getSheetAt(i) ?: continue
                    sb.append(readExcelSheet(sheet))
                }
            } else {
                val sheet = workbook.getSheetAt(sheetNo)
                if (sheet != null) {
                    sb.append(readExcelSheet(sheet))
                }
            }
        }
        return sb.toString()
    }

    /**
     * 根据文件路径获取Workbook对象
     * @param filepath 文件全路径
     */
    @Throws(
        EncryptedDocumentException::class,
        InvalidFormatException::class,
        IOException::class
    )
    fun getWorkbook(filepath: String): Workbook? {
        var `is`: InputStream? = null
        var wb: Workbook? = null
        require(filepath.isNotBlank()) { "文件路径不能为空" }
        val suffiex = getSuffiex(filepath)
        require(suffiex.isNotBlank()) { "文件后缀不能为空" }
        if (OFFICE_EXCEL_XLS == suffiex || OFFICE_EXCEL_XLSX == suffiex) {
            try {
                `is` = FileInputStream(filepath)
                org.apache.poi.hssf.record.crypto.Biff8EncryptionKey.setCurrentUserPassword("xlysoft");
                wb = WorkbookFactory.create(`is`)
            } finally {
                `is`?.close()
                wb?.close()
            }
        } else {
            throw IllegalArgumentException("该文件非Excel文件")
        }
        return wb
    }

    /**
     * 获取后缀
     * @param filepath filepath 文件全路径
     */
    private fun getSuffiex(filepath: String): String {
        if (filepath.isBlank()) {
            return ""
        }
        val index = filepath.lastIndexOf(".")
        return if (index == -1) {
            ""
        } else filepath.substring(index + 1, filepath.length)
    }

    private fun readExcelSheet(sheet: Sheet?): String {
        val sb = StringBuilder()
        if (sheet != null) {
            val rowNos = sheet.lastRowNum // 得到excel的总记录条数
            for (i in 0..rowNos) { // 遍历行
                val row = sheet.getRow(i)
                if (row != null) {
                    val columNos = row.lastCellNum.toInt() // 表头总共的列数
                    for (j in 0 until columNos) {
                        val cell = row.getCell(j)
                        if (cell != null) {
                            cell.cellType = CellType.STRING
                            sb.append(cell.stringCellValue + " ")
                            // System.out.print(cell.getStringCellValue() + " ");
                        }
                    }
                    // System.out.println();
                }
            }
        }
        return sb.toString()
    }

    /**
     * 读取指定Sheet页的表头
     * @param filepath filepath 文件全路径
     * @param sheetNo sheet序号,从0开始,必填
     */
    @Throws(IOException::class, EncryptedDocumentException::class, InvalidFormatException::class)
    fun readTitle(filepath: String, sheetNo: Int): Row? {
        var returnRow: Row? = null
        val workbook = getWorkbook(filepath)
        if (workbook != null) {
            val sheet = workbook.getSheetAt(sheetNo)
            returnRow = readTitle(sheet)
        }
        return returnRow
    }

    /**
     * 读取指定Sheet页的表头
     */
    @Throws(IOException::class)
    fun readTitle(sheet: Sheet): Row? {
        var returnRow: Row? = null
        val totalRow = sheet.lastRowNum // 得到excel的总记录条数
        for (i in 0 until totalRow) { // 遍历行
            val row = sheet.getRow(i) ?: continue
            returnRow = sheet.getRow(0)
            break
        }
        return returnRow
    }

    /**
     * 创建Excel文件
     * @param filepath filepath 文件全路径
     * @param sheetName 新Sheet页的名字
     * @param titles 表头
     * @param values 每行的单元格
     */
    @Throws(IOException::class)
    fun writeExcel(
        filepath: String, sheetName: String?, titles: List<String>,
        values: List<Map<String?, Any?>>
    ): Boolean {
        var success = false
        var outputStream: OutputStream? = null
        return if (filepath.isBlank()) {
            throw IllegalArgumentException("文件路径不能为空")
        } else {
            val suffiex = getSuffiex(filepath)
            require(suffiex.isNotBlank()) { "文件后缀不能为空" }
            val workbook: Workbook
            workbook = if ("xls" == suffiex.toLowerCase()) {
                HSSFWorkbook()
            } else {
                XSSFWorkbook()
            }
            // 生成一个表格
            val sheet: Sheet
            sheet = if (sheetName!!.isBlank()) {
                // name 为空则使用默认值
                workbook.createSheet()
            } else {
                workbook.createSheet(sheetName)
            }
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth(15)
            // 生成样式
            val styles = createStyles(workbook)
            // 创建标题行
            var row = sheet.createRow(0)
            // 存储标题在Excel文件中的序号
            val titleOrder: MutableMap<String, Int> = Maps.newHashMap()
            for (i in titles.indices) {
                val cell = row.createCell(i)
                cell.cellStyle = styles["header"]
                val title = titles[i]
                cell.setCellValue(title)
                titleOrder[title] = i
            }
            // 写入正文
            val iterator = values.iterator()
            // 行号
            var index = 1
            while (iterator.hasNext()) {
                row = sheet.createRow(index)
                val value = iterator.next()
                for ((title, `object`) in value) {
                    // 获取列名
                    // 根据列名获取序号
                    val i: Int = titleOrder.get(title)!!
                    // 在指定序号处创建cell
                    val cell = row.createCell(i)
                    // 设置cell的样式
                    if (index % 2 == 1) {
                        cell.cellStyle = styles["cellA"]
                    } else {
                        cell.cellStyle = styles["cellB"]
                    }
                    // 获取列的值
                    // 判断object的类型
                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    if (`object` is Double) {
                        cell.setCellValue((`object` as Double?)!!)
                    } else if (`object` is Date) {
                        val time = simpleDateFormat.format(`object` as Date?)
                        cell.setCellValue(time)
                    } else if (`object` is Calendar) {
                        val calendar = `object` as Calendar
                        val time = simpleDateFormat.format(calendar.time)
                        cell.setCellValue(time)
                    } else if (`object` is Boolean) {
                        cell.setCellValue((`object` as Boolean?)!!)
                    } else {
                        if (`object` != null) {
                            cell.setCellValue(`object`.toString())
                        }
                    }
                }
                index++
            }
            try {
                outputStream = FileOutputStream(filepath)
                workbook.write(outputStream)
                success = true
            } finally {
                outputStream?.close()
                if (workbook != null) {
                    workbook.close()
                }
            }
            success
        }
    }

    /**
     * 设置格式
     */
    private fun createStyles(wb: Workbook): Map<String, CellStyle> {
        val styles: MutableMap<String, CellStyle> = Maps.newHashMap()

        // 标题样式
        val titleStyle = wb.createCellStyle() as XSSFCellStyle
        titleStyle.alignment = HorizontalAlignment.CENTER // 水平对齐
        titleStyle.verticalAlignment = VerticalAlignment.CENTER // 垂直对齐
        titleStyle.locked = true // 样式锁定
        titleStyle.fillForegroundColor = IndexedColors.LIGHT_YELLOW.getIndex()
        val titleFont = wb.createFont()
        titleFont.fontHeightInPoints = 16.toShort()
        titleFont.bold = true
        titleFont.fontName = "微软雅黑"
        titleStyle.setFont(titleFont)
        styles["title"] = titleStyle

        // 文件头样式
        val headerStyle = wb.createCellStyle() as XSSFCellStyle
        headerStyle.alignment = HorizontalAlignment.CENTER
        headerStyle.verticalAlignment = VerticalAlignment.CENTER
        headerStyle.fillForegroundColor = IndexedColors.LIGHT_BLUE.getIndex() // 前景色
        headerStyle.fillPattern = FillPatternType.SOLID_FOREGROUND // 颜色填充方式
        headerStyle.wrapText = true
        headerStyle.borderRight = BorderStyle.THIN // 设置边界
        headerStyle.rightBorderColor = IndexedColors.BLACK.getIndex()
        headerStyle.borderLeft = BorderStyle.THIN
        headerStyle.leftBorderColor = IndexedColors.BLACK.getIndex()
        headerStyle.borderTop = BorderStyle.THIN
        headerStyle.topBorderColor = IndexedColors.BLACK.getIndex()
        headerStyle.borderBottom = BorderStyle.THIN
        headerStyle.bottomBorderColor = IndexedColors.BLACK.getIndex()
        val headerFont = wb.createFont()
        headerFont.fontHeightInPoints = 12.toShort()
        headerFont.color = IndexedColors.WHITE.getIndex()
        titleFont.fontName = "微软雅黑"
        headerStyle.setFont(headerFont)
        styles["header"] = headerStyle
        val cellStyleFont = wb.createFont()
        cellStyleFont.fontHeightInPoints = 12.toShort()
        cellStyleFont.color = IndexedColors.BLUE_GREY.getIndex()
        cellStyleFont.fontName = "微软雅黑"

        // 正文样式A
        val cellStyleA = wb.createCellStyle() as XSSFCellStyle
        cellStyleA.alignment = HorizontalAlignment.CENTER // 居中设置
        cellStyleA.verticalAlignment = VerticalAlignment.CENTER
        cellStyleA.wrapText = true
        cellStyleA.borderRight = BorderStyle.THIN
        cellStyleA.rightBorderColor = IndexedColors.BLACK.getIndex()
        cellStyleA.borderLeft = BorderStyle.THIN
        cellStyleA.leftBorderColor = IndexedColors.BLACK.getIndex()
        cellStyleA.borderTop = BorderStyle.THIN
        cellStyleA.topBorderColor = IndexedColors.BLACK.getIndex()
        cellStyleA.borderBottom = BorderStyle.THIN
        cellStyleA.bottomBorderColor = IndexedColors.BLACK.getIndex()
        cellStyleA.setFont(cellStyleFont)
        styles["cellA"] = cellStyleA

        // 正文样式B:添加前景色为浅黄色
        val cellStyleB = wb.createCellStyle() as XSSFCellStyle
        cellStyleB.alignment = HorizontalAlignment.CENTER
        cellStyleB.verticalAlignment = VerticalAlignment.CENTER
        cellStyleB.fillForegroundColor = IndexedColors.LIGHT_YELLOW.getIndex()
        cellStyleB.fillPattern = FillPatternType.SOLID_FOREGROUND
        cellStyleB.wrapText = true
        cellStyleB.borderRight = BorderStyle.THIN
        cellStyleB.rightBorderColor = IndexedColors.BLACK.getIndex()
        cellStyleB.borderLeft = BorderStyle.THIN
        cellStyleB.leftBorderColor = IndexedColors.BLACK.getIndex()
        cellStyleB.borderTop = BorderStyle.THIN
        cellStyleB.topBorderColor = IndexedColors.BLACK.getIndex()
        cellStyleB.borderBottom = BorderStyle.THIN
        cellStyleB.bottomBorderColor = IndexedColors.BLACK.getIndex()
        cellStyleB.setFont(cellStyleFont)
        styles["cellB"] = cellStyleB
        return styles
    }

    /**
     * 将源文件的内容复制到新Excel文件(可供理解Excel使用,使用价值不大)
     * @param srcFilepath 源文件全路径
     * @param desFilepath 目标文件全路径
     */
    @Throws(IOException::class, EncryptedDocumentException::class, InvalidFormatException::class)
    fun writeExcel(srcFilepath: String, desFilepath: String) {
        var outputStream: FileOutputStream? = null
        val suffiex = getSuffiex(desFilepath)
        require(suffiex.isNotBlank()) { "文件后缀不能为空" }
        val workbook_des: Workbook
        workbook_des = if ("xls" == suffiex.toLowerCase()) {
            HSSFWorkbook()
        } else {
            XSSFWorkbook()
        }
        val workbook = getWorkbook(srcFilepath)
        if (workbook != null) {
            val numberOfSheets = workbook.numberOfSheets
            for (k in 0 until numberOfSheets) {
                val sheet = workbook.getSheetAt(k)
                val sheet_des = workbook_des.createSheet(sheet!!.sheetName)
                if (sheet != null) {
                    val rowNos = sheet.lastRowNum
                    for (i in 0..rowNos) {
                        val row = sheet.getRow(i)
                        val row_des = sheet_des.createRow(i)
                        if (row != null) {
                            val columNos = row.lastCellNum.toInt()
                            for (j in 0 until columNos) {
                                val cell = row.getCell(j)
                                val cell_des = row_des.createCell(j)
                                if (cell != null) {
                                    cell.cellType = CellType.STRING
                                    cell_des.cellType = CellType.STRING
                                    cell_des.setCellValue(cell.stringCellValue)
                                }
                            }
                        }
                    }
                }
            }
        }
        try {
            outputStream = FileOutputStream(desFilepath)
            workbook_des.write(outputStream)
        } finally {
            outputStream?.close()
            if (workbook != null) {
                workbook_des.close()
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
    }
}