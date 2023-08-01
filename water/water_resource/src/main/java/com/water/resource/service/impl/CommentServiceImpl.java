package com.water.resource.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.water.resource.dao.CommentDO;
import com.water.resource.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("CommentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 水资源接口
     */
    private String[] dataEnum = {
            "dissolvedOxygen","permanganateIndex","COD","BOD","AD","ZL",
            "ZD","Cu","Zn","FX","Se","As","Hg","Cd","Cr6+","Pb","QHW","HHS",
            "SYL","LAS","SHW","FC"
    };

    //水温到最后的项名
    @Override
    public ArrayList<String> findListName() {
        String[] names= {"溶解氧","高锰酸盐指数","化学需氧量","生化需氧量","氨氮","总磷",
                "总氮","铜","锌","氟化物","硒","砷","汞","镉","六价铬","铅","氰化物","挥发酚",
                "石油类","阴离子表面活性剂","硫化物","粪大肠菌群"};
        return new ArrayList<>(Arrays.asList(names));
    }

    @Override
    public ArrayList<ArrayList<String>> findAllData(String driverName, String monitorPoint) {
        Query query =Query.query(Criteria.where("driverName").is(driverName)
                .and("monitorPoint").is(monitorPoint));
        List<CommentDO> commentDOList = mongoTemplate.find(query,CommentDO.class);
        ArrayList<ArrayList<String>> allData = new ArrayList<>();
        for(CommentDO commentDO :commentDOList){
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(commentDO.getDissolvedOxygen());
            arrayList.add(commentDO.getPermanganateIndex());
            arrayList.add(commentDO.getCOD());
            arrayList.add(commentDO.getBOD());

            JSONObject jsonObject = JSONObject.parseObject(commentDO.getFeature());//用不了JSONObject有可能是导入其他模块时未加入版本号
            for(int i = 4;i<dataEnum.length;i++){
                arrayList.add((String) jsonObject.get(dataEnum[i]));
            }
            allData.add(arrayList);
        }
        return allData;
    }

    @Override
    public ArrayList<String> findMonitorTime(String driverName, String monitorPoint) {
        Query query =Query.query(Criteria.where("driverName").is(driverName)
                .and("monitorPoint").is(monitorPoint));
        List<CommentDO> commentDOList = mongoTemplate.find(query,CommentDO.class);
        ArrayList<String> arrayList = new ArrayList<>();
        for(CommentDO commentDO:commentDOList){
            arrayList.add(commentDO.getMonitorTime());
        }
        return arrayList;
    }

//    @Value("${python.evaluate.path}")
//    private String evaluatePath;
    @Override
    public List<String> callEvaluate(String data) {
        System.out.println("enter evaluate python!");
        String path = "D:\\PyCode\\waterInvironment\\evaluate.py";
//        path=evaluatePath;
//        System.out.println("evaluatePath:"+evaluatePath);
//        String path = "evaluate.py";
//        String path = "test.py";
        List<String> list = new ArrayList<>();
        try {
            String[] args1=new String[]{"python",path,data};
            Process pr=Runtime.getRuntime().exec(args1);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("****Evaluate****"+line);
                list.add(line);
            }
            in.close();
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("evaluate python over!");
        return list;
    }

    @Override
    //河流名称、检测点位、指标
    public List<String>findDataIndex(String driverName ,String monitorPoint ,String index){
        Query query = Query.query(Criteria.where("driverName").is(driverName).
                and("monitorPoint").is(monitorPoint));
        List<CommentDO> commentDOList = mongoTemplate.find(query,CommentDO.class);
        ArrayList<String> indexData = new ArrayList<>();
        for (CommentDO commentDO : commentDOList){
            if(index.equals(dataEnum[0])){
                indexData.add(commentDO.getDissolvedOxygen());
            } else if(index.equals(dataEnum[1])){
                indexData.add(commentDO.getPermanganateIndex());
            } else if(index.equals(dataEnum[2])){
                indexData.add(commentDO.getCOD());
            } else if(index.equals(dataEnum[3])){
                indexData.add(commentDO.getBOD());
            } else {
                JSONObject jsonObject = JSONObject.parseObject(commentDO.getFeature());
                indexData.add((String)jsonObject.get(index));
            }
        }
        return indexData;
    }

//    @Value("${python.predict.path}")
//    private String predictPath;

    @Override
    public List<String> callPrediction(String resultString,String  factor) {
        String path = "D:\\PyCode\\waterInvironment\\prediction.py";
//        String path = "prediction.py";
//        path=predictPath;
        List<String> list = new ArrayList<>();

        try {

            String[] args1=new String[]{"python",path,resultString,factor};
            Process pr=Runtime.getRuntime().exec(args1);

            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                list.add(line);
            }
            for(String s : list){
                System.out.println("py传回数据："+s);
            }

            in.close();
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public HashSet<ArrayList<String>> findGnotes() {
        List<CommentDO> commentDOList = mongoTemplate.findAll(CommentDO.class);
        HashSet<ArrayList<String>> gnotes = new HashSet<>();
        for (CommentDO commentDO :commentDOList){
            ArrayList<String> oneGnotes = new ArrayList<>();
            oneGnotes.add(commentDO.getDriverName());
            oneGnotes.add(commentDO.getMonitorPoint());
            oneGnotes.add(commentDO.getGnotes());
            gnotes.add(oneGnotes);
        }
        return gnotes;
    }

    @Override
// 返回水资源所有数据
    public HashSet<ArrayList<String>> findAll(){
        List<CommentDO> commentDOList = mongoTemplate.findAll(CommentDO.class);
        HashSet<ArrayList<String>> unit = new HashSet<>();
        for (CommentDO commentDO :commentDOList){
            ArrayList<String> comment = new ArrayList<>();
            comment.add(commentDO.getDriverName());
            comment.add(commentDO.getMonitorPoint());
            comment.add(commentDO.getMonitorTime());
            comment.add(commentDO.getWaterTemperature());
            comment.add(commentDO.getPHValue());
            comment.add(commentDO.getDissolvedOxygen());
            comment.add(commentDO.getPermanganateIndex());
            comment.add(commentDO.getCOD());
            comment.add(commentDO.getBOD());
            // features处理成统一格式
            {
                String str = commentDO.getFeature();
                if(!str.equals("")){
                    String[] replace = {"\",\"QHW\":\"","\",\"AD\":\"","\",\"SHW\":\"","\",\"Cr6\\+\":\"",
                            "\",\"SYL\":\"","\",\"ZD\":\"","\",\"ZL\":\"","\",\"Cu\":\"","\",\"FX\":\"","\",\"Se\":\"",
                            "\",\"As\":\"","\",\"Pb\":\"","\",\"HHS\":\"","\",\"Zn\":\"","\",\"FC\":\"","\",\"Hg\":\"",
                            "\",\"LAS\":\""};
                    str = str.replaceAll("\\{\"Cd\":\"","");
                    str = str.replaceAll("\"}","");
                    for(String repS:replace){
                        str = str.replaceAll(repS," ");
                    }
                    String[] sNum= str.split(" ");
                    comment.addAll(Arrays.asList(sNum));

                }
            }
            comment.add(commentDO.getGnotes());
            unit.add(comment);
            System.out.println(comment);
        }
        return unit;
    }
}
