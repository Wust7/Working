package com.water.environment.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.water.environment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//水环境模块
@RestController
@RequestMapping("/environment")
public class WaterEnvironmentController {
    @Autowired
    private ShipGraphService shipGraphService;

    @Autowired
    private RainfallService rainfallService;

    @Autowired
    private BWSN2008Service bwsn2008Service;

    @Autowired
    private Reservoir1521Service reservoir1521Service;

    @Autowired
    private WaterStation20Service waterStation20Service;

    @Autowired
    private WaterStation1519Service waterStation1519Service;

    @Autowired
    private WaterStationHour2021Service waterStationHour2021Service;


    /**
     * 雨洪相关web接口
     * @return
     */

    @GetMapping("shipGraph")
    public R findShip(@RequestParam("shipName")String shipName){
        Class<?> aClass = null;
        try {
            aClass = Class.forName("com.cug.water.resource.dao."+shipName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<JSONObject> data = shipGraphService.findshipGraph(aClass);
        System.out.println("shipGraph"+shipName+data.isEmpty());
        return R.ok().put("data",data);
    }

    @GetMapping("rainfall")
    public R findRainFall(@RequestParam("page")String page){
        int pageNum = Integer.parseInt(page);
        List<ArrayList<String>> data = rainfallService.findRainFall(pageNum);
        return R.ok().put("data",data);
    }


//    @GetMapping("test")
//    public ServerResponse test(){
//        String s="[[2349834], [2012090100, 阳江市江城区, 潮表, 2], [2012090100, 仁化县, 澌溪河, 1.5], [2012090100, 新丰县, 下河洞, 0.5], [2012090100, 乐昌市, 九峰, 0.5], [2012090100, 台山市, 岐山, 0.5], [2012090100, 台山市, 田坑, 0.5], [2012090100, 茂名市茂港区, 水东, 0.5], [2012090100, 电白县, 电白, 0.5], [2012090100, 惠东县, 港口, 0.5], [2012090100, 佛山市南海区, 官窑镇水利所, 0.3], [2012090100, 佛冈县, 烟岭镇政府, 0.1], [2012090100, 清新县, 水电局, 0.1], [2012090101, 阳西县, 山塘, 10.5], [2012090101, 普宁市, 流沙, 2.5]]";
//        List<ArrayList<String>> Data=new ArrayList<>();
//        ArrayList<String> t1 = new ArrayList<>();
//        t1.add("1");
//        ArrayList<String> t2 = new ArrayList<>();
//        t2.add("2012090100");
//        t2.add("阳江市江城区");
//        t2.add("潮表");
//        t2.add("2");
//        ArrayList<String> t3 = new ArrayList<>();
//        t3.add("2012090100");
//        t3.add("仁化县");
//        t3.add("澌溪河");
//        t3.add("1.5");
//        Data.add(t1);
//        Data.add(t2);
//        Data.add(t3);
//        System.out.println(Data);
//        System.out.println("测试1");
//        return ServerResponse.createBySuccess(Data);
//    }

    @GetMapping("selectBWSN2008PipeTest")
    public R pipeTest(){
        JSONArray res = bwsn2008Service.pipeTest();
        return R.ok().put("data",res);
    }

    @GetMapping("selectBWSN2008")
    public R selectBWSN2008(){
        List<JSONObject> bwsn2008Data = bwsn2008Service.select();
        return R.ok().put("data",bwsn2008Data);
    }

    @GetMapping("selectReservoir1521")
    public R selectReservoir1521(){
        Set<JSONObject> data = reservoir1521Service.select();
        return R.ok().put("data",data);
    }


    @GetMapping("selectWaterStation20")
    public R selectWaterStation20(){
        Set<JSONObject> data = waterStation20Service.select();
        return R.ok().put("data",data);
    }

    @GetMapping("selectWaterStation1519")
    public R selectWaterStation1519(){
        Set<JSONObject> data = waterStation1519Service.select();
        return R.ok().put("data",data);
    }


    @GetMapping("selectWaterStationHour2021")
    public R selectWaterStationHour2021(){
        Set<JSONObject> data = waterStationHour2021Service.select();
        return R.ok().put("data",data);
    }

}
