package com.ttg.service.park.controller;

import com.ttg.service.park.common.exception.R;
import com.ttg.service.park.common.utils.util.ParkUtil;
import com.ttg.service.park.config.properties.ParkProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>Title: Payment</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/10 9:22
 */
@Controller
@RequestMapping(value = "/api/park")
public class Payment {

    @Autowired
    private ParkProperties parkApiProperties;

    @GetMapping(value = "/getPaymentInfo")
    public R getPaymentInfo(@RequestParam Map<String, Object> map) {
        String parkId = (String)map.get("parkId");
        String plateNo = (String)map.get("plateNo");

        //判断请求参数是否为空  page、limit、sidx(排序字段) order(排序方式)
        if (!StringUtils.isEmpty((String) map.get("parkId"))
                && !StringUtils.isEmpty((String) map.get("plateNo"))) {
            //获取到Page关键信息 封装成json数据并返回
        } else {
            return R.error("请求参数不能为空!");
        }

        LinkedHashMap<String,String> params = new LinkedHashMap<>();
        params.put("parkId",parkId);
        params.put("plateNo",plateNo);

        String key = ParkUtil.createKey(params,parkApiProperties.getAppSecret());
        params.put("key",key);
        params.put("appId", parkApiProperties.getAppId());

        //post请求调用接口
        //String orderStr = HttpUtils.doPost(parkApiProperties.getParkPaymentInfoUrl(),payXml,4000);
        return null;

    }

}
