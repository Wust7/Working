package com.water.environment.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.water.environment.dao.WaterStation1519Dao;
import com.water.environment.service.WaterStation1519Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("WaterStation1519Service")
public class WaterStation1519ServiceImpl implements WaterStation1519Service {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public Set<JSONObject> select() {
        List<WaterStation1519Dao> allData = mongoTemplate.findAll(WaterStation1519Dao.class);
        System.out.println(allData.get(0));
        Set<JSONObject> res = new HashSet<>();
        for (WaterStation1519Dao item : allData) {
            JSONObject row=new JSONObject();
            String waterStationName = item.getWaterStationName();
            String location = item.getLocation();
            row.put("waterStationName",waterStationName);
            row.put("location",location);
            res.add(row);
        }
        return res;
    }
}
