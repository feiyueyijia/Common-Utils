/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yfny.utilscommon.annotation.excel;

import com.google.common.collect.Lists;
import com.yfny.utilscommon.util.Encodes;
import com.yfny.utilscommon.util.ReflectUtils;
import com.yfny.utilscommon.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）
 *
 * @author ThinkGem
 * @version 2013-04-21
 */
public class ExportExcel {

    private static Logger log = LoggerFactory.getLogger(ExportExcel.class);

    /**
     * 工作薄对象
     */
    private SXSSFWorkbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 当前行号
     */
    private int rownum;

    /**
     * 注解列表（Object[]{ ExcelField, Field/Method }）
     */
    List<Object[]> annotationList = Lists.newArrayList();

    /**
     * 构造函数
     *
     * @param title     表格标题，传“空值”，表示无标题
     * @param listClass 实体对象，通过annotation.ExportField获取标题
     */
    public ExportExcel(String[] title, List<Class<?>> listClass) {
        this(title, listClass, 1);
    }

    /**
     * 构造函数
     *
     * @param title  表格标题，传“空值”，表示无标题
     * @param list   实体对象，通过annotation.ExportField获取标题
     * @param type   导出类型（1:导出数据；2：导出模板）
     * @param groups 导入分组
     */
    public ExportExcel(String[] title, List<Class<?>> list, int type, int... groups) {
        this.wb = new SXSSFWorkbook(500);
        for (int in = 0; in < list.size(); in++) {
            Class<?> cls = list.get(in);
            // Get annotation field
            Field[] fs = cls.getDeclaredFields();
            for (Field f : fs) {
                ExcelField ef = f.getAnnotation(ExcelField.class);
                if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                    if (groups != null && groups.length > 0) {
                        boolean inGroup = false;
                        for (int g : groups) {
                            if (inGroup) {
                                break;
                            }
                            for (int efg : ef.groups()) {
                                if (g == efg) {
                                    inGroup = true;
                                    annotationList.add(new Object[]{ef, f});
                                    break;
                                }
                            }
                        }
                    } else {
                        annotationList.add(new Object[]{ef, f});
                    }
                }
            }
            // Get annotation method
            Method[] ms = cls.getDeclaredMethods();
            for (Method m : ms) {
                ExcelField ef = m.getAnnotation(ExcelField.class);
                if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                    if (groups != null && groups.length > 0) {
                        boolean inGroup = false;
                        for (int g : groups) {
                            if (inGroup) {
                                break;
                            }
                            for (int efg : ef.groups()) {
                                if (g == efg) {
                                    inGroup = true;
                                    annotationList.add(new Object[]{ef, m});
                                    break;
                                }
                            }
                        }
                    } else {
                        annotationList.add(new Object[]{ef, m});
                    }
                }
            }

            //Fixme 使用 valueOf 方法替换 直接new的方式更高效
            // Field sorting
            Collections.sort(annotationList, new Comparator<Object[]>() {
                public int compare(Object[] o1, Object[] o2) {
                    return new Integer(((ExcelField) o1[0]).sort()).compareTo(
                            new Integer(((ExcelField) o2[0]).sort()));
                }

                ;
            });
            // Initialize
            List<String> headerList = Lists.newArrayList();
            for (Object[] os : annotationList) {
                String t = ((ExcelField) os[0]).title();
                // 如果是导出，则去掉注释
                if (type == 1) {
                    String[] ss = StringUtils.split(t, "**", 2);
                    if (ss.length == 2) {
                        t = ss[0];
                    }
                }
                headerList.add(t);
            }
            initialize(title[in], headerList, 1);
            annotationList.clear();

        }
    }

    /**
     * 构造函数
     *
     * @param title   表格标题，传“空值”，表示无标题
     * @param headers 表头数组
     */
    public ExportExcel(String title, String[] headers) {
        this.wb = new SXSSFWorkbook(500);
        initialize(title, Lists.newArrayList(headers), 1);
    }

    /**
     * 构造函数
     *
     * @param title      表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    public ExportExcel(String title, List<String> headerList) {
        this.wb = new SXSSFWorkbook(500);
        initialize(title, headerList, 1);
    }

    /**
     * 构造函数
     *
     * @param titles     表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    public ExportExcel(String[] titles, List<String> headerList, String type) {
        this.wb = new SXSSFWorkbook(500);
        int no = 1;
        for (String title : titles) {
            initialize(title, headerList, no);
            no++;
        }
    }

    /**
     * 初始化函数
     *
     * @param title      表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     * @param no         工作表数量
     */
    private void initialize(String title, List<String> headerList, int no) {
        rownum = 0;
        if (StringUtils.isNotBlank(title)) {
            this.sheet = wb.createSheet(title);
        } else {
            this.sheet = wb.createSheet("Sheet" + no);
        }

        this.styles = createStyles(wb);
        // Create title

//        if (StringUtils.isNotBlank(title)) {
//            rownum = 0;
//            Row titleRow = sheet.createRow(rownum++);
//            titleRow.setHeightInPoints(30);
//            Cell titleCell = titleRow.createCell(0);
//            titleCell.setCellStyle(styles.get("title"));
//            titleCell.setCellValue(title);
//            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
//                    titleRow.getRowNum(), titleRow.getRowNum(), headerList.size() - 1));
//        }
        // Create header
        if (headerList == null) {
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styles.get("header"));

            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
            if (ss.length == 2) {
                cell.setCellValue(ss[0]);
                Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
                        new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                comment.setString(new XSSFRichTextString(ss[1]));
                cell.setCellComment(comment);
            } else {
                cell.setCellValue(headerList.get(i));
            }
            //sheet.autoSizeColumn(i);
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }
        log.debug("Initialize success.");
    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        style.setFont(titleFont);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        style.setWrapText(true);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        styles.put("data1", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        styles.put("data2", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        styles.put("data3", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
		//style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        return styles;
    }

    /**
     * 添加一行
     *
     * @return 行对象
     */
    public Row addRow() {
        return sheet.createRow(rownum++);
    }


    /**
     * 添加一行
     *
     * @return 行对象
     */
    public Row addRow(Sheet sheet) {
        return sheet.createRow(rownum++);
    }

    /**
     * 添加一个单元格
     *
     * @param row    添加的行
     * @param column 添加列号
     * @param val    添加值
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val) {
        return this.addCell(row, column, val, 0, Class.class);
    }

    /**
     * 添加一个单元格
     *
     * @param row    添加的行
     * @param column 添加列号
     * @param val    添加值
     * @param align  对齐方式（1：靠左；2：居中；3：靠右）
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType) {
        Cell cell = row.createCell(column);
        CellStyle style = styles.get("data" + (align >= 1 && align <= 3 ? align : ""));
        try {
            if (val == null) {
                cell.setCellValue("");
            } else if (val instanceof String) {
                cell.setCellValue((String) val);
            } else if (val instanceof Integer) {
                cell.setCellValue((Integer) val);
            } else if (val instanceof Long) {
                cell.setCellValue((Long) val);
            } else if (val instanceof Double) {
                cell.setCellValue((Double) val);
            } else if (val instanceof Float) {
                cell.setCellValue((Float) val);
            } else if (val instanceof Date) {
                DataFormat format = wb.createDataFormat();
                style.setDataFormat(format.getFormat("yyyy-MM-dd"));
                cell.setCellValue((Date) val);
            } else {
                if (fieldType != Class.class) {
                    cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, val));
                } else {
                    cell.setCellValue((String) Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
                            "fieldtype." + val.getClass().getSimpleName() + "Type")).getMethod("setValue", Object.class).invoke(null, val));
                }
            }
        } catch (Exception ex) {
            log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
            cell.setCellValue(val.toString());
        }
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * 添加数据（通过annotation.ExportField添加数据）
     *
     * @return list 数据列表
     */
    public <E> ExportExcel setDataList(List<E> list) {
        for (E e : list) {
            int colunm = 0;
            Row row = this.addRow();
            StringBuilder sb = new StringBuilder();
            for (Object[] os : annotationList) {
                ExcelField ef = (ExcelField) os[0];
                Object val = null;
                String fieldName = "";
                // Get entity value
                try {
                    if (StringUtils.isNotBlank(ef.value())) {
                        fieldName = ef.value();
                    } else {
                        if (os[1] instanceof Field) {
                            fieldName = ((Field) os[1]).getName();
                        } else if (os[1] instanceof Method) {
                            String methodName = ((Method) os[1]).getName();
                            if ("get".equals(methodName.substring(0, 3))) {
                                fieldName = StringUtils.substringAfter(methodName, "get");
                            }
                        }
                    }
                    // If is dict, get dict label
                    //if (StringUtils.isNotBlank(ef.dictType())) {
                    //    val = DictUtils.getDictLabel(val == null ? "" : val.toString(), ef.dictType(), "");
                    //}
                    if (StringUtils.isNotBlank(fieldName)) {
                        val = ReflectUtils.getFieldValue(e, ef.value());
                    }
                } catch (Exception ex) {
                    // Failure to ignore
                    log.info(ex.toString());
                    val = "";
                }
                this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
                sb.append(val + ", ");
            }
            log.debug("Write success: [" + row.getRowNum() + "] " + sb.toString());
        }
        return this;
    }

    /**
     * 输出数据流
     *
     * @param os 输出数据流
     */
    public ExportExcel write(OutputStream os) throws IOException {
        try {
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
        }
        return this;
    }

    /**
     * 输出到客户端
     *
     * @param fileName 输出文件名
     */
    public ExportExcel write(HttpServletResponse response, String fileName) throws IOException {
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
            write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
        }

        return this;
    }

    /**
     * 输出到文件
     *
     * @param fileName 输出文件名
     */
    public ExportExcel writeFile(String fileName) throws FileNotFoundException, IOException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(fileName);
            this.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
        }
        return this;
    }

    /**
     * 清理临时文件
     */
    public ExportExcel dispose() {
        wb.dispose();
        return this;
    }

    public SXSSFWorkbook getWb() {
        return wb;
    }

    public void setWb(SXSSFWorkbook wb) {
        this.wb = wb;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    /**
     * 导出测试
     */
    public static void main(String[] args) throws Throwable {

        List<String> headerList = Lists.newArrayList();
        for (int i = 1; i <= 10; i++) {
            headerList.add("表头" + i);
        }

        List<String> dataRowList = Lists.newArrayList();
        for (int i = 1; i <= headerList.size(); i++) {
            if (i == 10) {
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add("数据" + i);
                arrayList.add("数据" + i);
                String row = String.join("\n", arrayList);
                dataRowList.add(row);
            } else {
                dataRowList.add("数据" + i);
            }
        }

        List<List<String>> dataList = Lists.newArrayList();
        for (int i = 1; i <= 100; i++) {
            dataList.add(dataRowList);
        }

        String[] titles = {"Sheet1", "Sheet2"};
        ExportExcel ee = new ExportExcel(titles, headerList, "");
        for (int s = 0; s < ee.wb.getNumberOfSheets(); s++) {
            ee.rownum = 1;
            for (int i = 0; i < dataList.size(); i++) {
                Row row = ee.addRow(ee.wb.getSheetAt(s));
                for (int j = 0; j < dataList.get(i).size(); j++) {
                    ee.addCell(row, j, dataList.get(i).get(j));
                }
            }
        }

        ee.writeFile("target/export.xlsx");

        ee.dispose();

        log.debug("Export success.");

    }

}
