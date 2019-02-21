package com.ttg.service.park.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Title: Test</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2019-01-03 14:36
 */
public class Test {
    public static void main(String[] args) {
        LinkedHashMap<String,String> map = new LinkedHashMap<>();

        map.put("1","one");
        map.put("3","three");
        map.put("2","two");
        map.put("4","four");
        Iterator iter = map.keySet().iterator();
        String str = "";
        while (iter.hasNext()) {
        Object key = iter.next();
        Object val = map.get(key);
        str += val;
        }
        System.out.println(str);

    }


}
