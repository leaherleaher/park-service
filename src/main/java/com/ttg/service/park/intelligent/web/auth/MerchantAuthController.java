package com.ttg.service.park.intelligent.web.auth;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.ttg.service.park.common.exception.BusiException;
import com.ttg.service.park.common.i18n.LocalMessageSourceService;
import com.ttg.service.park.config.properties.PayProperties;
import com.ttg.service.park.config.properties.TlinxProperties;
import com.ttg.service.park.dto.constant.BusinessConstant;
import com.ttg.service.park.dto.tlinx.TlinxAppTokenInfo;
import com.ttg.service.park.dto.tlinx.TlinxGatewayResp;
import com.ttg.service.park.dto.user.UserInfo;
import com.ttg.service.park.intelligent.security.UserPrincipal;
import com.ttg.service.park.intelligent.service.IPTlinxAuthService;
import com.ttg.service.park.intelligent.service.auth.IAuthService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: MerchantAuthController</p>
 * <p>Description: </p>
 *  商户入口
 * @Author yangtao
 * @Date 2018/12/6 11:14
 */
@Controller
@RequestMapping("/merchant/auth")
public class MerchantAuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantAuthController.class);

    @Autowired
    private TlinxProperties tlinxProperties;

    @Autowired
    private PayProperties payProperties;

    @Autowired
    private IAuthService authService;

    @Autowired
    private IPTlinxAuthService ipTlinxAuthService;


    @Autowired
    private LocalMessageSourceService messageSourceService;

    /**
     * @Description //TODO 商户进入页面 调tlinx根据app_id生成token 然后回调应用url 将token传入
     * @Param [trade_type, token, data, request]
     * @return com.ttg.service.park.dto.tlinx.TlinxGatewayResp
     **/
    @RequestMapping("/gateway")
    @ResponseBody
    public TlinxGatewayResp gateway(String trade_type, String token, String data, HttpServletRequest request) {

        LOGGER.info("request:{}", request.getParameterMap(),request.getCookies());
        TlinxGatewayResp tlinxGatewayResp = new TlinxGatewayResp();
        try {

            // 先取code
            String appKey = tlinxProperties.getAppId();
            String appSecret = tlinxProperties.getAppSecret();
            LOGGER.info("appKey:{},appScret:{},data:{},trade_type:{},token:{}", appKey, appSecret, trade_type, token, data);

            TlinxAppTokenInfo tlinxAppTokenInfo = authService.handleGateway(trade_type, token, data);

            tlinxGatewayResp.setData(tlinxAppTokenInfo.getData());
            tlinxGatewayResp.setErrcode(messageSourceService.getMessage(BusinessConstant.CODE_S));
            tlinxGatewayResp.setMsg(messageSourceService.getMessage(BusinessConstant.CODE_OK_MSG));

        } catch (Exception e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
            tlinxGatewayResp.setErrcode(messageSourceService.getMessage(BusinessConstant.CODE_E));
            if (e instanceof BusiException) {
                tlinxGatewayResp.setMsg(e.getMessage());
            } else {
                tlinxGatewayResp.setMsg(messageSourceService.getMessage(BusinessConstant.ERROR_10003_MSG));
            }
        }
        return tlinxGatewayResp;
    }

    @RequestMapping(value = "/jump")
    public ModelAndView jump(String app_token, String data, HttpServletRequest request){
        LOGGER.info("re:{}",request.getParameterMap());

        ModelAndView modelAndView = new ModelAndView();
        //TLINX商户云平台授权用户信息接口
        try {
            UserInfo userInfo = ipTlinxAuthService.getUserInfo(tlinxProperties.getAppId(), app_token);
            //如果商户存在，并且已经授权。调到登录
            if(Optional.fromNullable(userInfo).isPresent()){
                modelAndView.addObject("username",userInfo.getUserId());
                modelAndView.addObject("password",payProperties.getMerchantDefaultPassword());
                modelAndView.setViewName("auth/goto");
            }else{
                modelAndView.setViewName("auth/nouserinfo");
            }
        }catch(Exception e){
            LOGGER.error("商户云品台授权信息失败！");
            modelAndView.setViewName("auth/nouserinfo");
        }
        return modelAndView;

    }
    @RequestMapping("/login")
    public String login(){
        return "auth/login";
    }


    @RequestMapping("/{error}")
    public ModelAndView error(@PathVariable("error") String error){
        ModelAndView modelAndView = new ModelAndView("auth/"+error);
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        /*if (StringUtils.isBlank(userPrincipal.getPaymentTypeId())){
            modelAndView.addObject(WebAttributes.ACCESS_DENIED_403,"获取商户对应缴费类型的授权信息失败，请联系机构进行授权");
        }*/
        return modelAndView;
    }

    @RequestMapping(value="/test")
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView("auth/denied");
        return modelAndView;

    }
}
