package com.water.environment.service;

import java.util.ArrayList;
import java.util.List;

public interface RainfallService {
    List<ArrayList<String>> findRainFall(int pageNum);
}
