package com.pilidili.api;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RESTfulApi {

    private final Map<Integer, Map<String, Object>> dataMap;

    public RESTfulApi() {
        dataMap = new HashMap<>();
        for(int i = 0; i < 2; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", i);
            data.put("name", "name" + i);
            dataMap.put(i, data);
        }
    }

    @GetMapping("/objects/{id}")
    public Map<String, Object> getData(@PathVariable Integer id) {
        System.out.println(id);
        System.out.println(dataMap);
        return dataMap.get(id);
    }

    @DeleteMapping("/objects/{id}")
    public String deleteData(Integer id) {
        dataMap.remove(id);
        return "deleted";
    }

    @PostMapping("/objects")
    public String postData(@RequestBody  Map<String, Object> data) {
        Integer[] idArr = dataMap.keySet().toArray(new Integer[0]);
        Arrays.sort(idArr);
        int nextId = idArr[idArr.length - 1] + 1;
        dataMap.put(nextId, data);
        System.out.println(dataMap);
        return "posted";
    }

    @PutMapping("/objects")
    public String putData(@RequestBody Map<String, Object> data) {
        Integer id = Integer.valueOf(String.valueOf(data.get("id")));
        Map<String, Object> containedData = dataMap.get(id);
        if(containedData == null) {
            Integer[] idArr = dataMap.keySet().toArray(new Integer[0]);
            Arrays.sort(idArr);
            int nextId = idArr[idArr.length - 1] + 1;
            dataMap.put(nextId, data);
        } else {
            dataMap.put(id, data);
        }
        return "put success";
    }
}
