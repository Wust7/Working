package com.water.environment.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.water.environment.dao.Reservoir1521Dao;
import com.water.environment.service.Reservoir1521Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("Reservoir1521Service")
public class Reservoir1521ServiceImpl implements Reservoir1521Service {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public Set<JSONObject> select() {
        List<Reservoir1521Dao> allData = mongoTemplate.findAll(Reservoir1521Dao.class);
        System.out.println(allData.get(0));
        Set<JSONObject> res = new HashSet<>();
        for (Reservoir1521Dao item : allData) {
            JSONObject row=new JSONObject();
            String reservoirName = item.getReservoirName();
            String waterIntake = item.getWaterIntake();
            row.put("reservoirName",reservoirName);
            row.put("waterIntake",waterIntake);
            res.add(row);
        }
        return res;
    }
}
