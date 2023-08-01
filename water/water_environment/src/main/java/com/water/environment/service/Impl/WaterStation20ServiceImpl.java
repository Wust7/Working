package com.water.environment.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.water.environment.dao.WaterStation20Dao;
import com.water.environment.service.WaterStation20Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("WaterStation20Service")
public class WaterStation20ServiceImpl implements WaterStation20Service {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Set<JSONObject> select() {
        List<WaterStation20Dao> allData = mongoTemplate.findAll(WaterStation20Dao.class);
        System.out.println(allData.get(0));
        Set<JSONObject> res = new HashSet<>();
        for (WaterStation20Dao item : allData) {
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
