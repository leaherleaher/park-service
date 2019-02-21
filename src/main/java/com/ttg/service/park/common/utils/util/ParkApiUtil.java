package com.ttg.service.park.common.utils.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ttg.service.park.common.exception.BusiException;
import com.ttg.service.park.config.properties.ParkProperties;
import com.ttg.service.park.dto.api.SyncOrderDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>Title: ParkApiUtil</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2019-01-02 16:14
 */
@Component
public class ParkApiUtil {

    private static Logger logger = LoggerFactory.getLogger(ParkApiUtil.class);

    @Autowired
    private ParkProperties parkApiProperties;

    @Autowired
    private RestTemplate restTemplate;

    public static ParkApiUtil parkApiUtil;

    @PostConstruct
    public void init() {
        parkApiUtil = this;
    }

    /**
     * @return com.alibaba.fastjson.JSONObject
     * @Description //TODO 停车费账单支付同步接口
     * @Param [map]
     **/
    public static JSONObject syncOrder(SyncOrderDto syncOrderDto) {

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("orderNo", syncOrderDto.getOrderNo());
        //判断字段不为空
        if (syncOrderDto.getAmount() != null && syncOrderDto.getPayType() != null && syncOrderDto.getPayMethod() != null) {
            params.put("amount", syncOrderDto.getAmount().toString());
            params.put("payType", syncOrderDto.getPayType().toString());
            params.put("payMethod", syncOrderDto.getPayMethod().toString());
            params.put("freeMoney",syncOrderDto.getFreeMoney().toString());
            params.put("freeTime",syncOrderDto.getFreeTime().toString());
            params.put("freeDetail","[]");
        }else{
            throw new BusiException("缴费订单同步失败！");
        }
        //参数生成String->加日期和secret生成新的String->md5加密
        String key = ParkUtil.createKey(params, parkApiUtil.parkApiProperties.getAppSecret());
        params.put("appId", parkApiUtil.parkApiProperties.getAppId());
        params.put("key", key);

        String result = parkApiUtil.restTemplate.postForEntity(parkApiUtil.parkApiProperties.getSyncOrderUrl(), params, String.class).getBody();
        JSONObject jsonData = JSON.parseObject(result);
        return jsonData;
    }

    public static JSONObject getPayDetail(Map<String,Object> map){
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        //根据车牌号和停车场id去调用停车API 获取停车缴费信息
        String parkId = (String) map.get("parkId");
        String plateNo = (String) map.get("plateNo");

        if (StringUtils.isNotBlank((String) map.get("parkId"))
            || StringUtils.isNotBlank((String) map.get("plateNo"))){
            params.put("parkId", parkId);
            params.put("plateNo", plateNo);
        }else{
            throw new BusiException("停车费信息查询失败！");
        }

        //参数生成String->加日期和secret生成新的String->md5加密
        String key = ParkUtil.createKey(params, parkApiUtil.parkApiProperties.getAppSecret());
        params.put("appId", parkApiUtil.parkApiProperties.getAppId());
        params.put("key", key);
        try {
            String result = parkApiUtil.restTemplate.postForEntity(parkApiUtil.parkApiProperties.getPaymentInfoUrl(), params, String.class).getBody();
            JSONObject jsonData = JSON.parseObject(result);
            return jsonData;
        }catch(Exception e){
            logger.error("interface request failure:{}",e.getStackTrace());
            throw new BusiException("停车缴费明细查询接口请求异常");
        }
    }
}
