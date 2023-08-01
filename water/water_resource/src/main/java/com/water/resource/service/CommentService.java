package com.water.resource.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface CommentService {
    ArrayList<String> findListName();

    ArrayList<ArrayList<String>> findAllData(String driverName, String monitorPoint);

    ArrayList<String> findMonitorTime(String driverName, String monitorPoint);

    List<String> callEvaluate(String resultString);

    List<String> findDataIndex(String driverName, String monitorPoint, String index);

    List<String> callPrediction(String resultString, String index);

    HashSet<ArrayList<String>> findGnotes();

    HashSet<ArrayList<String>> findAll();
}
