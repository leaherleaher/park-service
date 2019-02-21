package com.ttg.service.park.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.ttg.service.park.common.sys.GetUserPrincipal;
import com.ttg.service.park.intelligent.security.UserPrincipal;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>Title: MyMetaObjectHandler</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/10 16:01
 */
@Component
public class MyMetaObjectHandler extends MetaObjectHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyMetaObjectHandler.class);
    @Override
    public void insertFill(MetaObject metaObject) {

        logger.info("start insert fill ....");
        //获取当前登录用户
        UserPrincipal userPrincipal = GetUserPrincipal.getUserId();
        //避免使用metaObject.setValue()
        //局部配置写法
        if(userPrincipal==null){
            this.setFieldValByName("createTime",new Date(), metaObject);
            this.setFieldValByName("lastUpdatedTime",new Date(), metaObject);
        }else{
            this.setFieldValByName("createBy",userPrincipal.getUsername(), metaObject);
            this.setFieldValByName("createTime",new Date(), metaObject);
            this.setFieldValByName("lastUpdatedBy",userPrincipal.getUsername(), metaObject);
            this.setFieldValByName("lastUpdatedTime",new Date(), metaObject);
        }



        /*Object createBy = metaObject.getValue("createBy");
        Object createTime = metaObject.getValue("createTime");

        Object lastUpdatedBy = metaObject.getValue("lastUpdatedBy");
        Object lastUpdateTime = metaObject.getValue("lastUpdatedTime");
        //获取当前登录用户
        UserPrincipal userPrincipal = GetUserPrincipal.getUserId();
        if (null == createBy) {
            //全局配置写法
            metaObject.setValue("createBy", userPrincipal.getUsername());
        }
        if (null == createTime) {
            metaObject.setValue("createTime", new Date());
        }
        if (null == lastUpdatedBy) {
            metaObject.setValue("lastUpdatedBy", userPrincipal.getUsername());
        }
        if (null == lastUpdateTime) {
            metaObject.setValue("lastUpdateTime", new Date());
        }*/
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        logger.info("start update fill ....");
        //获取当前登录用户
        UserPrincipal userPrincipal = GetUserPrincipal.getUserId();
        if(userPrincipal==null) {
            this.setFieldValByName("lastUpdatedTime",new Date(), metaObject);
        }else{

            this.setFieldValByName("lastUpdatedBy",userPrincipal.getUsername(), metaObject);
            this.setFieldValByName("lastUpdatedTime",new Date(), metaObject);
        }

        /*Object lastUpdatedBy = metaObject.getValue("lastUpdatedBy");
        Object lastUpdateTime = metaObject.getValue("lastUpdatedTime");
        //获取当前登录用户
        UserPrincipal userPrincipal = GetUserPrincipal.getUserId();

        if (null == lastUpdatedBy) {
            metaObject.setValue("lastUpdatedBy", userPrincipal.getUsername());
        }

        if (null == lastUpdateTime) {
            metaObject.setValue("lastUpdateTime", new Date());
        }*/
    }
}
