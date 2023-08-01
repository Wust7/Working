package com.water.resource.controller;

import com.water.resource.dao.Pair;
import com.water.resource.dao.Pair2;
import com.water.resource.dao.Pair3;
import com.water.resource.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.R;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("resource")
public class WaterResourceController {
    @Autowired
    CommentService commentService;

    @GetMapping("/histogram")
    public R histogram(@RequestParam("driverName")String driverName ,
                       @RequestParam("monitorPoint")String monitorPoint, @RequestParam(value = "index")String index){
        List<String > indexList =commentService.findDataIndex(driverName,monitorPoint,index);
        ArrayList<String> l= commentService.findMonitorTime(driverName,monitorPoint);
        Pair2 pair = new Pair2();
        List<Pair2> ll = new ArrayList<>();
        for (int i=0;i<l.size();i++){
            pair.setTime(l.get(i));
            pair.setFactor(indexList.get(i));
            ll.add(pair);
            pair = new Pair2();
        }
        return R.ok().put("data",ll);

    }

    @GetMapping ("/factorName")
    public R getFactorName(){
        ArrayList<String> list = commentService.findListName();
        return R.ok().put("data",list);
    }

    @GetMapping ("/evaluate")
    public R evaluate(@RequestParam("driverName")String driverName , @RequestParam("monitorPoint")String monitorPoint){
        ArrayList<ArrayList<String>> commentDOList =commentService.findAllData(driverName,monitorPoint);
        ArrayList<String>  l= commentService.findMonitorTime(driverName,monitorPoint);
        System.out.println("evaluate:"+l);
        StringBuilder resultString = new StringBuilder();
        for (int i=0;i<commentDOList.size();i++){
            for(int j=0;j<commentDOList.get(i).size();j++){
                if (i==commentDOList.size()-1&&j==commentDOList.get(i).size()-1)
                    resultString.append(commentDOList.get(i).get(j));
                else
                    resultString.append(commentDOList.get(i).get(j)).append(",");
            }
        }
        List<String> list = commentService.callEvaluate(resultString.toString());
        System.out.println("evaluate:\nresultString:"+resultString+"\nlist:"+list);

        StringBuilder sb=new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        String S=sb.toString();
        String regExp="[\\[\\]]";
        //2. 这里是将特殊字符换为空字符串,""代表直接去掉
        String replace = "";
        //3. 要处理的字符串
        S = S.replaceAll(regExp,replace);
        S =S+" ";
        System.out.println("S:"+S);
        String[] array1=S.split(". ");
        List<Pair> ll = new ArrayList<>();
        Pair pair = new Pair();
        for (int i=0;i<array1.length;i++){
            pair.setTime(l.get(i));
            pair.setEvaluate(array1[i]);
            ll.add(pair);
            pair = new Pair();
        }
        System.out.println("evaluate传出数据："+ll);
        System.out.println("evaluate over!");
        return R.ok().put("data",ll);
    }


    @GetMapping ("/prediction")
    public R prediction(@RequestParam("driverName")String driverName ,
                        @RequestParam("monitorPoint")String monitorPoint,
                        @RequestParam(value = "index")String index){
        ArrayList<ArrayList<String>> commentDOList =commentService.findAllData(driverName,monitorPoint);//
        ArrayList<String>  l= commentService.findMonitorTime(driverName,monitorPoint);//获取数据对应的日期
        //生成预测未来2020年12个月的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = l.get(l.size()-1);
        List<String> l2=new ArrayList<>();
        System.out.println("prediction:"+l);
        try {
            Date date = df.parse(dateString);
            for (int i=1;i<13;i++){
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.MONTH, i);
                Date d=c.getTime();
                SimpleDateFormat  format= new SimpleDateFormat("yyyy-MM");
                String enddate = format.format(d);
                //先写死
                enddate=enddate.replace("00","20");
                l2.add(enddate);
                System.out.println(enddate);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


        StringBuilder resultString = new StringBuilder();
        for (int i=0;i<commentDOList.size();i++){
            for(int j=0;j<commentDOList.get(i).size();j++){
                if (i==commentDOList.size()-1&&j==commentDOList.get(i).size()-1)
                    resultString.append(commentDOList.get(i).get(j));
                else
                    resultString.append(commentDOList.get(i).get(j)).append(",");
            }
        }
//        System.out.println(resultString+"***"+index);
        //将数据库中的值处理成字符串传到python
        List<String> list = commentService.callPrediction(resultString.toString(),index);
        System.out.println(list);
        System.out.println(list.size());

        StringBuilder sb=new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        String S=sb.toString();
        S = S.replaceAll("\\[  ","");
        String regExp="[\\[\\]]";
        //这里是将特殊字符换为空字符串,""代表直接去掉
        String replace = "";
        //要处理的字符串
        S = S.replaceAll(regExp,replace);
        S = S.replaceAll("  "," ");
        String[] array1=S.split(". ",-1);
        //将日期和对应的值拼接起来
        List<Pair3> ll = new ArrayList<>();
        Pair3 pair = new Pair3();
        for (int i=0;i<l2.size();i++){
            pair.setTime(l2.get(i));
            pair.setPrediction(array1[i]);
            ll.add(pair);
            pair = new Pair3();
        }
        System.out.println("prediction over!");
        return R.ok().put("data",ll);
    }




    @GetMapping ("/longitudeAndLatitude")
    public R position(){
        HashSet<ArrayList<String>> set = commentService.findGnotes();
        System.out.println("LongitudeAndLatitude:"+set);
        return R.ok().put("data",set);
    }

    // 返回水环境全部资源
    @GetMapping ("/allComment")
    public R allComment(){
        HashSet<ArrayList<String>> set = commentService.findAll();
        return R.ok().put("data",set);

    }
}
