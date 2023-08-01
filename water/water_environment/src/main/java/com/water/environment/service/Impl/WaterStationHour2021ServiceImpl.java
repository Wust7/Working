package com.water.environment.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.water.environment.dao.WaterStationHour2021Dao;
import com.water.environment.service.WaterStationHour2021Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("WaterStationHour2021Service")
public class WaterStationHour2021ServiceImpl implements WaterStationHour2021Service {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public Set<JSONObject> select() {
        List<WaterStationHour2021Dao> allData = mongoTemplate.findAll(WaterStationHour2021Dao.class);
        System.out.println(allData.get(0));
        Set<JSONObject> res = new HashSet<>();
        for (WaterStationHour2021Dao item : allData) {
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
