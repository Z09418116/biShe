package com.example.bishe.config;


import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.bishe.config.Constants.*;


/**
 * @ClassName ExcelUtils
 * @Description Excel工具类
 * @Author Matt.Lu
 * @DATE 2022/2/14
 */
public class ExcelUtils {
    public ExcelUtils() {
    }

    //2003- 版本的excel
    private static final String EXCEL2003L = ".xls";
    //2007+ 版本的excel
    private static final String EXCEL2007U = ".xlsx";

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws IOException {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf('.'));
        if (EXCEL2003L.equals(fileType)) {
            //2003-
            wb = new HSSFWorkbook(inStr);
        } else if (EXCEL2007U.equals(fileType)) {
            //2007+
            wb = new XSSFWorkbook(inStr);
        } else {
            throw new IOException("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws IOException {
        List<List<Object>> list = null;
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new IOException("创建Excel工作薄为空！");
        }
        //页数
        Sheet sheet = null;
        //行数
        Row row = null;
        //列数
        Cell cell = null;

        list = new ArrayList<>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                //遍历所有的列
                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < 18; y++) {
                    cell = row.getCell(y);
                    li.add(this.getValue(cell));
                }
                list.add(li);
            }
        }
        return list;

    }

    /**
     * 获取日期
     * @param in
     * @param fileName
     * @return
     * @throws IOException
     */
    public String getDateByExcel(InputStream in, String fileName) throws IOException {
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new IOException("创建Excel工作薄为空！");
        }
        return this.getValue(work.getSheetAt(0).getRow(1).getCell(1));
    }

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @param rowNum      行数
     * @return
     * @throws IOException
     */
    public List<List<Object>> getListByExcel(InputStream in, String fileName, Integer rowNum) throws IOException {
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new IOException("创建Excel工作薄为空！");
        }
        //页数
        Sheet sheet = null;
        //行数
        Row row = null;
        //列数
        Cell cell = null;
        //打断标识符
        Boolean breakFlag = Boolean.FALSE;

        list = new ArrayList<>();
        try {
            List<Object> dateList = new ArrayList<>();
            dateList.add(this.getValue(work.getSheetAt(0).getRow(0).getCell(0)));
            dateList.add(this.getValue(work.getSheetAt(0).getRow(1).getCell(0)));
            dateList.add(this.getValue(work.getSheetAt(0).getRow(1).getCell(1)));
            list.add(dateList);
            //遍历Excel中所有的sheet
            for (int i = 0; i < work.getNumberOfSheets(); i++) {
                sheet = work.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }

                //从第rowNum行遍历当前sheet
                for (int j = rowNum; j <= sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if (row == null || row.getFirstCellNum() == j) {
                        continue;
                    }

                    //遍历所有的列
                    List<Object> li = new ArrayList<>();
                    for (int y = row.getFirstCellNum(); y <= row.getLastCellNum(); y++) {
                        cell = row.getCell(y);
                        li.add(this.getValue(cell));
                    }
                    list.add(li);
                }
            }
        } catch (Exception e) {
            throw new ImportException("导入的excel表格与模板不一致，请重新确认！");
        }
        return list;

    }

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象,如果序号为空则停止
     *
     * @param in,fileName 文件名
     * @param rowNum      行数
     * @return
     * @throws IOException
     */
    public List<List<Object>> getListByExcelRemovalEmptyNum(InputStream in, String fileName, Integer rowNum) throws IOException {
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new IOException("创建Excel工作薄为空！");
        }
        //页数
        Sheet sheet = null;
        //行数
        Row row = null;
        //列数
        Cell cell = null;

        list = new ArrayList<>();
        List<Object> dateList = new ArrayList<>();
        dateList.add(this.getValue(work.getSheetAt(0).getRow(0).getCell(0)));
        list.add(dateList);
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            //从第rowNum行遍历当前sheet
            for (int j = rowNum; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j || "0".equals(getValue(row.getCell(NUM_ZERO)))) {
                    continue;
                }
                //遍历所有的列
                List<Object> li = new ArrayList<>();
                for (int y = 0; y <= row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(this.getValue(cell));
                }
                list.add(li);
            }
        }
        return list;

    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    //解决 excel 类型问题，获得数值
    public String getValue(Cell cell) {
        String value = "";
        if (null == cell) {
            return value;
        }
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            long longVal = Math.round(cell.getNumericCellValue());
            Double doubleVal = cell.getNumericCellValue();
            if (Double.parseDouble(longVal + ".0") == doubleVal){
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
        }
        switch (cell.getCellTypeEnum()) {
            //数值型
            case NUMERIC:
                value = getCellType(cell);
                break;
            //字符串类型
            case STRING:
                value = cell.getStringCellValue();
                break;
            // 公式类型
            case FORMULA:
                //读公式计算值
                value = String.valueOf(new BigDecimal(cell.getNumericCellValue()));
                // 如果获取的数据值为非法值,则转换为获取字符串
                if (value.equals("NaN")) {
                    value = cell.getStringCellValue();
                }
                break;
            // 布尔类型
            case BOOLEAN:
                value = " " + cell.getBooleanCellValue();
                break;
            default:
                value = "0";
        }
        return value;
    }

    /**
     * 判断 cell 数据类型
     *
     * @param cell
     */
    private String getCellType(Cell cell) {
        String value = "";
        if (HSSFDateUtil.isCellDateFormatted(cell)) {
            //如果是date类型则 ，获取该cell的date值
            Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            value = format.format(date);
        } else {// 纯数字
            BigDecimal big = BigDecimal.valueOf(cell.getNumericCellValue());
            value = big.toString();
            //解决1234.0  去掉后面的.0
            if (null != value && !"".equals(value.trim())) {
                String[] item = value.split("[.]");
                if (1 < item.length && "0".equals(item[1])) {
                    value = item[0];
                }
            }
        }
        return value;
    }

    /**
     * 设置样式
     */
    public static void setRowStyle(Sheet sheet, CellRangeAddress rangeAddress, CellStyle style) {
        for (int i = rangeAddress.getFirstRow(); i <= rangeAddress.getLastRow(); i++) {

            Row row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            for (int j = rangeAddress.getFirstColumn(); j <= rangeAddress.getLastColumn(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);
                    cell.setCellValue("");
                }
                cell.setCellStyle(style);
            }
        }
    }

    /**
     * 设置字体
     */
    public static void setFontAndStyle(final Sheet sheet, CellStyle style, int num, boolean bold) {
        final Workbook workbook = sheet.getWorkbook();
        final DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));
        //下边框
        style.setBorderBottom(BorderStyle.THIN);
        //左边框
        style.setBorderLeft(BorderStyle.THIN);
        //上边框
        style.setBorderTop(BorderStyle.THIN);
        //右边框
        style.setBorderRight(BorderStyle.THIN);
        //设置左右剧中
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置上下剧中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        final Font styleFont = workbook.createFont();
        styleFont.setFontName("宋体");
        // 粗体显示
        styleFont.setBold(bold);
        //字号
        styleFont.setFontHeightInPoints((short) num);
        style.setFont(styleFont);
        style.setWrapText(true);
    }

    public static CellStyle createCellStyle(Workbook workbook) {
        final CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static Font createFont(Workbook workbook) {
        final Font font = workbook.createFont();
        font.setFontName("宋体");
        // 粗体显示
        font.setBold(true);
        font.setFontHeight((short) 500);
        return font;
    }




    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @param rowNum 行数
     * @return
     * @throws IOException
     */
    public List<List<Object>> getListByExcelToBeNullCheckModel(InputStream in, String fileName, Integer rowNum,String [] title) throws IOException {
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new IOException("创建Excel工作薄为空！");
        }
        Sheet sheet = null;  //页数
        Row row = null;  //行数
        Cell cell = null;  //列数

        List<String> titleList = new ArrayList<>();
        sheet = work.getSheetAt(0);

        row = sheet.getRow(0);


        //遍历所有的列
        for (int y = 0; y <= NUM_TWENTY; y++) {
            cell = row.getCell(y);
            titleList.add(this.getValue(cell));
        }

        for(int i = 0;i < title.length;i++){
            if(!title[i].equals(titleList.get(i))){
                throw new ImportException("请选择正确的表格导入!");
            }
        }


        list = new ArrayList<>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            //从第rowNum行遍历当前sheet
            for (int j = rowNum; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                //遍历所有的列
                List<Object> li = new ArrayList<>();
                for (int y = 0; y <= NUM_SEVEN; y++) {
                    cell = row.getCell(y);
                    li.add(this.getValueTOBeNull(cell));
                }
                list.add(li);
            }
        }
        return list;

    }



    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @param rowNum 行数
     * @return
     * @throws IOException
     */
    public List<List<Object>> getListByExcelToBeNull(InputStream in, String fileName, Integer rowNum) throws IOException {
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new IOException("创建Excel工作薄为空！");
        }
        Sheet sheet = null;  //页数
        Row row = null;  //行数
        Cell cell = null;  //列数

        list = new ArrayList<>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //从第rowNum行遍历当前sheet
            for (int j = rowNum; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                //遍历所有的列
                List<Object> li = new ArrayList<>();
                for (int y = 0; y <= NUM_TWENTY; y++) {
                    cell = row.getCell(y);
                    li.add(this.getValue(cell));
                }
                list.add(li);
            }
        }
        return list;

    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    //解决 excel 类型问题，获得数值
    public String getValueTOBeNull(Cell cell) {
        String value = "";
        if (null == cell) {
            return value;
        }
        switch (cell.getCellTypeEnum()) {
            //数值型
            case NUMERIC:
                value = getCellType(cell);
                break;
            //字符串类型
            case STRING:
                value = cell.getStringCellValue();
                break;
            // 公式类型
            case FORMULA:
                //读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                // 如果获取的数据值为非法值,则转换为获取字符串
                if (value.equals("NaN")) {
                    value = cell.getStringCellValue();
                }
                break;
            // 布尔类型
            case BOOLEAN:
                value = " " + cell.getBooleanCellValue();
                break;
            default:
                value = "";
        }
        return value;
    }

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @param rowNum 行数
     * @return
     * @throws IOException
     */
    public List<List<Object>> chekExcelIsTrue(InputStream in, String fileName, Integer rowNum,String [] title1,String [] title2,String [] title3) throws IOException {
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new IOException("创建Excel工作薄为空！");
        }
        Sheet sheet = null;  //页数
        Row row = null;  //行数
        Cell cell = null;  //列数

        List<String> titleList = new ArrayList<>();
        List<String> titleList2 = new ArrayList<>();
        List<String> titleList3 = new ArrayList<>();
        sheet = work.getSheetAt(0);


        row = sheet.getRow(0);


        //遍历所有的列
        for (int y = 0; y <= NUM_THREE; y++) {
            cell = row.getCell(y);
            titleList.add(this.getValueTOBeNull(cell));
        }

        for(int i = 0;i < title1.length;i++){
            if(!title1[i].equals(titleList.get(i))){
                throw new ImportArrayException("请选择正确的表格导入！");
            }
        }

        row = sheet.getRow(1);
        //遍历所有的列
        for (int y = 0; y <= NUM_TWENTY_FOUR; y++) {
            cell = row.getCell(y);
            titleList2.add(this.getValueTOBeNull(cell));
        }

        for(int i = 0;i < title2.length;i++){
            if(!title2[i].equals(titleList2.get((2*i)+1))){
                throw new ImportException("请选择正确的表格导入！");
            }
        }

        row = sheet.getRow(2);
        //遍历所有的列
        for (int y = 0; y <= NUM_TWENTY_TWO; y++) {
            cell = row.getCell(y);
            titleList3.add(this.getValueTOBeNull(cell));
        }

        for(int i = 0;i < title3.length;i++){
            if(!title3[i].equals(titleList3.get(i+1))){
                throw new ImportException("请选择正确的表格导入！");
            }
        }

        list = new ArrayList<>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //从第rowNum行遍历当前sheet
            for (int j = rowNum; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                //遍历所有的列
                List<Object> li = new ArrayList<>();
                for (int y = 0; y <= NUM_TWENTY_FOUR; y++) {
                    cell = row.getCell(y);
                    li.add(this.getValue(cell));
                }
                list.add(li);
            }
        }
        return list;



    }
}
