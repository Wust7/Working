package com.water.intrusion.service.Impl;


import com.water.intrusion.service.ParallelComputeService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

@Service("ParallelComputeService")
public class ParallelComputeServiceImpl implements ParallelComputeService {

    @Override
    public ArrayList<ArrayList<ArrayList<String>>> get() {
        //获取excel表中所有页
        ArrayList<ArrayList<ArrayList<String>>> totalExcel = new ArrayList<>();
        try{
            //TODO 上线需修改路径文件路径
            Workbook workbook = Workbook.getWorkbook(
                    new File("C:\\Users\\17822\\Desktop\\并行程序结果分析.xls"));
            //获取整个表中间的页
            for(int i = 0;i<workbook.getNumberOfSheets();i++){
                Sheet sheet = workbook.getSheet(i);
                //获取一页中的每一行
                ArrayList<ArrayList<String>> sheetData = new ArrayList<>();
                for(int j = 0;j<sheet.getRows();j++){
                    //获取一行中的每个元素
                    ArrayList<String> rowData = new ArrayList<>();
                    for(int k =0;k<sheet.getColumns();k++){
                        rowData.add(sheet.getCell(k,j).getContents());
                    }
                    sheetData.add(rowData);
                }
                totalExcel.add(sheetData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return totalExcel;
    }
}
