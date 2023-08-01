package com.water.intrusion.controller;

//马伟强负责的课题
//涉及接口：
//  1.读取excel表格数据 一个接口
//  2.调用exe程序获取数据 一个接口

import com.water.intrusion.service.ParallelComputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.R;

import java.util.ArrayList;

@RestController
@RequestMapping("parallel")
public class ParallelComputeController {

    @Autowired
    ParallelComputeService parallelComputeService;

    //数据展示接口，传入表格名，返回对应表格数据
    @GetMapping("/list")
    public R get(){
        ArrayList<ArrayList<ArrayList<String>>> data= parallelComputeService.get();
        return R.ok().put("data",data);

    }
}
