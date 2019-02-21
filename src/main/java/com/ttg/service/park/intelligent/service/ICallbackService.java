package com.ttg.service.park.intelligent.service;

/**
 * <p>Title: ICallbackService</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018-12-29 16:37
 */
public interface ICallbackService {
    void savePayBack(String out_no, String status, String ord_no,Long timestamp);

    //void savePgPayBack(String out_no, String status, String ord_no);

}
