package com.yfny.utilscommon.util;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ExcelUtil导入导出工具类
 * Created by zileShi on 2019/4/12.
 **/
public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 导出Excel
     * @param wb            导出需要的对象
     * @param xlsxSheet     导出需要的对象
     * @param request       请求
     * @param response      回应
     * @param fileName      文件名
     */
    public static void exportExcel(HSSFWorkbook wb, HSSFSheet xlsxSheet, HttpServletRequest request, HttpServletResponse response, String fileName) {
        //创建文件临时存储地址
        String serverUrl = request.getSession().getServletContext()
                .getRealPath("/");
        String uploadTo = serverUrl + "temp" + File.separator;
        File file1 = new File(uploadTo);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String url = uploadTo + UUID.randomUUID() + ".xls";


        //创建HSSFRow对象
        HSSFRow row = xlsxSheet.createRow(0);
        //创建HSSFCell对象
        HSSFCell cell = row.createCell(0);

        //设置第一行第一列单元格内容
        cell.setCellValue(fileName + "信息表");

        //合并单元格，构造参数依次表示起始行，截止行，起始列，截止列
        xlsxSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
        //第二行创建导出数据表列头
        HSSFRow row2 = xlsxSheet.createRow(1);

        //针对需求单系统的columns字段
        List<String> xqdColumns = getColumns("xqd");

        for (int i = 0; i < xqdColumns.size(); i++) {
            row2.createCell(i).setCellValue(xqdColumns.get(i));
        }


        String result = fileName;


        //写到输出流中，输出Excel文件
        OutputStream out = null;
        InputStream fis = null;
        try {
            //转化格式
            result = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

            out = new FileOutputStream(url);
            wb.write(out);
            fis = new BufferedInputStream(new FileInputStream(url));

            File file = new File(url);

            //根据类型返回回应
            response = returnRespondByType(response,"vue",fileName,file);

            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());


            byte[] buffer = new byte[2046];
            int j = -1;
            while ((j = fis.read(buffer)) != -1) {
                toClient.write(buffer, 0, j);
            }

            fis.close();
            toClient.flush();
            toClient.close();
            out.close();
            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.info("导出需求单信息出错");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("导出需求单信息出错");
        }


    }


    /**
     * 该方法默认针对需求单系统的columns字段
     *
     * @param name 标识符
     * @return
     */
    private static List<String> getColumns(String name) {
        List<String> list = new ArrayList<>();
        if (name == "xqd") {
            list.add("需求单状态");
            list.add("需求单号");
            list.add("申请单位/业务部门");
            list.add("申请人");
            list.add("申请人联系方式");
            list.add("创建时间");
            list.add("需求单名称");
            list.add("需求单综述");
            list.add("期望完成时间");
            list.add("审核人");
//            list.add("省审核人");
//            list.add("网审核人");

        }
        return list;
    }


    /**
     * 根据类型返回回应
     * @param response  回应
     * @param type  类型，是根据vue.js还是普通请求
     * @param fileName  文件名
     * @param file  流
     */
    private static HttpServletResponse returnRespondByType(HttpServletResponse response,String type,String fileName,File file){
        if (type.equals("vue")){
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
        }else {
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

            //设置header
            response.addHeader("Content-Length", "" + file.length());

            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        }

        return response;
    }

}
