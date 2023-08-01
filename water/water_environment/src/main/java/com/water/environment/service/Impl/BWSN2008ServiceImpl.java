package com.water.environment.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.water.environment.dao.BWSN2008Dao;
import com.water.environment.service.BWSN2008Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("BWSN2008Service")
public class BWSN2008ServiceImpl implements BWSN2008Service {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public JSONArray pipeTest() {
        File file = new File("C:\\Users\\17822\\Desktop\\QQ下载\\pipe.json");
        String jsonStr = null;
        try{
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            int ch = 0;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            jsonStr = sb.toString();
            fileReader.close();
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONArray res = new JSONArray();
        HashMap<String,String> row = new HashMap<>();
        row.put("type","GeometryCollection");
        res.add(row);
        res.add(JSON.parseObject(jsonStr));
        return res;
    }

    @Override
    public List<JSONObject> select() {
        List<BWSN2008Dao> selectList = mongoTemplate.findAll(BWSN2008Dao.class);
        List<JSONObject> res = new ArrayList<>();
        JSONObject typeObject=new JSONObject();
        typeObject.put("type","GeometryCollection");
        res.add(typeObject);
        List<Map<String,Object>> allPoint = new ArrayList<>();
        for (BWSN2008Dao row : selectList) {
            Map<String,Object> rowData = new HashMap<>();
            rowData.put("type",row.getType());
            rowData.put("coordinates",row.getCoordinates());
            allPoint.add(rowData);
        }
        JSONObject allPointJSON=new JSONObject();
        allPointJSON.put("geometries",allPoint);
        res.add(allPointJSON);
        return res;
    }
}
