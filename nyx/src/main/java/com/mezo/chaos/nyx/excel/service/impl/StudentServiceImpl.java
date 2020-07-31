package com.mezo.chaos.nyx.excel.service.impl;

import com.mezo.chaos.nyx.excel.mapper.StudentMapper;
import com.mezo.chaos.nyx.excel.domain.Student;
import com.mezo.chaos.nyx.excel.service.StudentService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 小卖铺的老爷爷
 *
 */
@Service
public class StudentServiceImpl implements StudentService {
    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";

    @Autowired
    private StudentMapper studentMapper;
    /**
     * 导入Excel，兼容xls和xlsx
     */
    @Override
    @SuppressWarnings("resource")
    public Integer importExcel(MultipartFile myFile) throws Exception {
//        1、用HSSFWorkbook打开或者创建“Excel文件对象”
//        2、用HSSFWorkbook对象返回或者创建Sheet对象
//        3、用Sheet对象返回行对象，用行对象得到Cell对象
//        4、对Cell对象读写。
        //获得文件名
        Workbook workbook ;
        String fileName = myFile.getOriginalFilename();
        if(fileName.endsWith(XLS)){
            //2003
            workbook = new HSSFWorkbook(myFile.getInputStream());
        }else if(fileName.endsWith(XLSX)){
            //2007
            workbook = new XSSFWorkbook(myFile.getInputStream());
        }else{
            throw new Exception("文件不是Excel文件");
        }
        Sheet sheet = workbook.getSheet("Sheet1");
        // 指的行数，一共有多少行+
        int rows = sheet.getLastRowNum();
        if(rows==0){
            throw new Exception("请填写数据");
        }
        for (int i = 1; i <= rows+1; i++) {
            // 读取左上端单元格
            Row row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                // **读取cell**
                Student student = new Student();
                //姓名
                String name = getCellValue(row.getCell(0));
                student.setName(name);
                //班级
                String classes = getCellValue(row.getCell(1));
                student.setClasses(classes);
                //分数
                String scoreString = getCellValue(row.getCell(2));
                if (!StringUtils.isEmpty(scoreString)) {
                    Integer score = Integer.parseInt(scoreString);
                    student.setScore(score);
                }
                //考试时间,小写的mm表示的是分钟
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = getCellValue(row.getCell(3));
                if (!StringUtils.isEmpty(dateString)) {
                    Date date=sdf.parse(dateString);
                    student.setTime(date);
                }
                studentMapper.insert(student);
            }
        }
        return rows-1;
    }

    /**
     * 获得Cell内容
     *
     * @param cell 单元格
     * @return 返回单元格值
     */
    public String getCellValue(Cell cell) {
        String value = "";
        if (cell != null) {
            // 以下是判断数据的类型
            switch (cell.getCellType()) {
                // 数字
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if (date != null) {
                            value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        } else {
                            value = "";
                        }
                    } else {
                        value = new DecimalFormat("0").format(cell.getNumericCellValue());
                    }
                    break;
                // 字符串
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                // Boolean
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue() + "";
                    break;
                // 公式
                case HSSFCell.CELL_TYPE_FORMULA:
                    value = cell.getCellFormula() + "";
                    break;
                // 空值
                case HSSFCell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                // 故障
                case HSSFCell.CELL_TYPE_ERROR:
                    value = "非法字符";
                    break;
                default:
                    value = "未知类型";
                    break;
            }
        }
        return value.trim();
    }
    /**
     * 导出excel文件
     */
    @Override
    public void exportExcel(HttpServletResponse response) throws Exception {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("Sheet1");
        //单元格合并 依次表示起始行，截至行，起始列， 截至列
//        sheet.addMergedRegion(new CellRangeAddress(0,2,0,10));
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 设置单元格的横向和纵向对齐方式，具体参数就不列了，参考HSSFCellStyle
        style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        /*
          设置单元格的填充方式，以及前景颜色和背景颜色
          三点注意：
          1.如果需要前景颜色或背景颜色，一定要指定填充方式，两者顺序无所谓；
          2.如果同时存在前景颜色和背景颜色，前景颜色的设置要写在前面；
          3.前景颜色不是字体颜色。
         */
        //设置填充方式(填充图案)
        style.setFillPattern(HSSFCellStyle.DIAMONDS);
        //设置前景色
        style.setFillForegroundColor(HSSFColor.RED.index);
        //设置背景颜色
        style.setFillBackgroundColor(HSSFColor.LIGHT_YELLOW.index);
        // 设置单元格底部的边框及其样式和颜色
        // 这里仅设置了底边边框，左边框、右边框和顶边框同理可设
        style.setBorderBottom(HSSFCellStyle.BORDER_SLANTED_DASH_DOT);
        style.setBottomBorderColor(HSSFColor.DARK_RED.index);
        //设置日期型数据的显示样式
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        HSSFCell cell = row.createCell(0);
        //设置单元格的行高和列宽
        //设置指定列的列高
        sheet.setDefaultRowHeightInPoints(10);
        //设置指定列的列宽，256 * 50这种写法是因为width参数单位是单个字符的256分之一
        sheet.setColumnWidth(cell.getColumnIndex(), 256 * 50);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("班级");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("分数");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("时间");
        cell.setCellStyle(style);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        List<Student> list = studentMapper.selectAll();

        for (int i = 0; i < list.size(); i++){
            row = sheet.createRow(i + 1);
            Student stu = list.get(i);
            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue(stu.getName());
            row.createCell(1).setCellValue(stu.getClasses());
            row.createCell(2).setCellValue(stu.getScore());
            cell = row.createCell(3);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(stu.getTime()));
        }
        //第六步,输出Excel文件
        OutputStream output=response.getOutputStream();
        response.reset();
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        // new Date()为获取当前系统时间
        String fileName = df.format(new Date());
        response.setHeader("Content-disposition", "attachment; filename="+fileName+".xls");
        response.setContentType("application/msexcel");
        wb.write(output);
        output.close();
    }

}
