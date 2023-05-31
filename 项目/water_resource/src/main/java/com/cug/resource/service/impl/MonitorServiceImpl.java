package com.cug.resource.service.impl;

import com.cug.resource.dao.MonitorDao;
import com.cug.resource.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("MonitorService")
public class MonitorServiceImpl implements MonitorService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public HashSet<ArrayList<String>> queryPage() {
        List<MonitorDao> list = mongoTemplate.findAll(MonitorDao.class);
        HashSet<ArrayList<String>> monitorData = new HashSet<>();
        for (MonitorDao monitorDao :list){
            ArrayList<String> row = new ArrayList<>();
            row.add(monitorDao.getDriverName());
            row.add(monitorDao.getMonitorPoint());
            row.add(monitorDao.getMonitorTime());
            row.add(monitorDao.getWaterTemperature());
            row.add(monitorDao.getPHValue());
            row.add(monitorDao.getDissolvedOxygen());
            row.add(monitorDao.getPermanganateIndex());
            row.add(monitorDao.getCOD());
            row.add(monitorDao.getBOD());
            // features处理成统一格式
            {
                String str = monitorDao.getFeature();
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
                    row.addAll(Arrays.asList(sNum));

                }
            }
            row.add(monitorDao.getGnotes());
            monitorData.add(row);
            System.out.println(row);
        }
        return monitorData;
    }
}
