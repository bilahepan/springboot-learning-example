package demo.springboot.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    public static void writeExcel(List<List<String>> list, String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        Workbook workbook = null;
        if (fileName.endsWith("xls")) {
            workbook = hssfWriteExcel(list);
        } else if (fileName.endsWith("xlsx")) {
            workbook = xssfWriteExcel(list);
        }
        //将文件保存到指定的位置
        try {
            OutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            //System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Excel读取 操作
     */
    public static List<List<String>> readExcel(String filePath) {
        //System.out.println("原表数据 =====>" );
        File file = new File(filePath);
        String filename = file.getName();
        Workbook wb = null;
        if (filename.endsWith("xls")) {
            wb = new HSSFWorkbook();
        } else if (filename.endsWith("xlsx")) {
            wb = new XSSFWorkbook();
        }

        try {
            InputStream is = new FileInputStream(file.getAbsolutePath());
            wb = WorkbookFactory.create(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        /** 得到第一个sheet */
        Sheet sheet = wb.getSheetAt(0);
        /** 得到Excel的行数 */
        int totalRows = sheet.getPhysicalNumberOfRows();

        //System.out.println("totalRows" + totalRows);

        /** 得到Excel的列数 */
        int totalCells = 0;
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        //System.out.println("totalCells" + totalCells);
        List<List<String>> dataLst = new ArrayList<List<String>>();
        /** 循环Excel的行 */
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<String>();
            /** 循环Excel的列 */
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                if (cell != null) {
                    String value = cell.getCellType() == Cell.CELL_TYPE_FORMULA ? "" : cell.toString();
                    rowLst.add(value);
                }
            }
            //System.out.println();
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        return dataLst;
    }

    /**
     * 将数据写入到excel中
     */
    private static HSSFWorkbook hssfWriteExcel(List<List<String>> result) {

        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("数据");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第五步，写入数据
        for (int i = 0; i < result.size(); i++) {

            List<String> oneData = result.get(i);
            HSSFRow row1 = sheet.createRow(i + 1);
            for (int j = 0; j < oneData.size(); j++) {

                //创建单元格设值
                row1.createCell(j).setCellValue(oneData.get(j));
            }
        }
        return workbook;
    }

    /**
     * 把内容写入Excel
     *
     * @param list 传入要写的内容，此处以一个List内容为例，先把要写的内容放到一个list中
     */
    private static XSSFWorkbook xssfWriteExcel(List<List<String>> list) {
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建工作表
        XSSFSheet xssfSheet = workbook.createSheet();

        //创建行
        XSSFRow xssfRow;

        //创建列，即单元格Cell
        XSSFCell xssfCell;

        //把List里面的数据写到excel中
        for (int i = 0; i < list.size(); i++) {
            //从第一行开始写入
            xssfRow = xssfSheet.createRow(i);
            //创建每个单元格Cell，即列的数据
            List sub_list = list.get(i);
            for (int j = 0; j < sub_list.size(); j++) {
                xssfCell = xssfRow.createCell(j); //创建单元格
                xssfCell.setCellValue((String) sub_list.get(j)); //设置单元格内容
            }
        }
        return workbook;
    }


}