package com.ttg.service.park.intelligent.service.impl;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.ttg.service.park.common.exception.BusiException;
import com.ttg.service.park.dto.tlinx.TlinxAuthInfo;
import com.ttg.service.park.dto.user.UserInfo;
import com.ttg.service.park.intelligent.entity.PSysBranch;
import com.ttg.service.park.intelligent.entity.PSysMerchant;
import com.ttg.service.park.intelligent.entity.PSysUser;
import com.ttg.service.park.intelligent.entity.PTlinxAuth;
import com.ttg.service.park.intelligent.mapper.PSysBranchMapper;
import com.ttg.service.park.intelligent.mapper.PSysMerchantMapper;
import com.ttg.service.park.intelligent.mapper.PSysUserMapper;
import com.ttg.service.park.intelligent.mapper.PTlinxAuthMapper;
import com.ttg.service.park.intelligent.service.IPTlinxAuthService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * T-Linx应用授权信息表 服务实现类
 * </p>
 *
 * @author YangTao
 * @since 2018-12-06
 */
@Service
public class PTlinxAuthServiceImpl extends ServiceImpl<PTlinxAuthMapper, PTlinxAuth> implements IPTlinxAuthService {

    @Autowired
    private PTlinxAuthMapper pTlinxAuthMapper;
    @Autowired
    private PSysUserMapper pSysUserMapper;

    @Autowired
    private PSysBranchMapper pSysBranchMapper;
    @Autowired
    private PSysMerchantMapper pSysMerchantMapper;

    @Override
    public void verifyAppToken(TlinxAuthInfo tlinxAuthInfo) {

        PTlinxAuth tlinxAuth = pTlinxAuthMapper.selectByAppToken(tlinxAuthInfo.getAppId(), tlinxAuthInfo.getAppToken());

        if (Optional.fromNullable(tlinxAuth).isPresent()) {

            tlinxAuth.setAppId(tlinxAuthInfo.getAppId()); // 应用ID
            tlinxAuth.setAppToken(tlinxAuthInfo.getAppToken()); // 用户标识令牌
            tlinxAuth.setExchangeToken(tlinxAuthInfo.getExchangeToken()); // 交换令牌
            tlinxAuth.setToken(tlinxAuthInfo.getToken()); // 令牌
            tlinxAuth.setAesKey(tlinxAuthInfo.getAesKey()); // AES加密密钥
            tlinxAuth.setUserId(tlinxAuthInfo.getUserId()); // 用户编号
            tlinxAuth.setIsvalid("1"); // 是否有效(1是0否)
            pTlinxAuthMapper.updateById(tlinxAuth);

        } else {
            tlinxAuth = new PTlinxAuth();
            tlinxAuth.setAppId(tlinxAuthInfo.getAppId()); // 应用ID
            tlinxAuth.setAppToken(tlinxAuthInfo.getAppToken()); // 用户标识令牌
            tlinxAuth.setExchangeToken(tlinxAuthInfo.getExchangeToken()); // 交换令牌
            tlinxAuth.setToken(tlinxAuthInfo.getToken()); // 令牌
            tlinxAuth.setAesKey(tlinxAuthInfo.getAesKey()); // AES加密密钥
            tlinxAuth.setUserId(tlinxAuthInfo.getUserId()); // 用户编号
            tlinxAuth.setIsvalid("1"); // 是否有效(1是0否)
            pTlinxAuthMapper.insert(tlinxAuth);
        }
    }

    @Override
    public UserInfo getUserInfo(String appId, String apptoken) {
        UserInfo userInfo = null;

        PTlinxAuth byAppToken = pTlinxAuthMapper.selectByAppToken(appId,apptoken);


        //先获取用户的基础信息
        if (!Optional.fromNullable(byAppToken).isPresent() || byAppToken.getIsvalid().equals("0")) {
            throw new BusiException("获取APP_TOKEN信息失败，用户授权失败");
        }

        PSysUser user = pSysUserMapper.selectByUserId(String.valueOf(byAppToken.getUserId()));

        if (Optional.fromNullable(user).isPresent()) {
            //获取用户所在的机构信息
            PSysBranch branch = pSysBranchMapper.selectByBranchId(user.getBranchId());

            PSysMerchant merchant = pSysMerchantMapper.selectByMerId(user.getUserRelation());
            userInfo = new UserInfo();

            userInfo.setUserId(user.getUserId());
            userInfo.setUserName(user.getUserName());
            userInfo.setUserPwd(user.getPassword());
            userInfo.setBranchId(user.getBranchId());
            userInfo.setBranchName(branch.getBranchName());
            userInfo.setUserStatus(user.getUserStatus());
            userInfo.setUserType(user.getUserType());
            userInfo.setUserStatus(user.getUserStatus());
            userInfo.setUserRelation(user.getUserRelation());
            userInfo.setMerchantName(merchant.getMerName());
            userInfo.setMerchantId(merchant.getMerId());
            //获取商户对应的业务类型
//            List<MerchantAttribute> merchantAttributes = merchantAttributeMapper.findAttributeByMerId(user.getUserRelation());
//            MerchantAttribute merchantAttribute = Iterables.get(merchantAttributes, 0, new MerchantAttribute());
//            userInfo.setPaymentTypeId(merchantAttribute.getPaymentTypeId());
//            userInfo.setPaymentTypeName(merchantAttribute.getPaymentTypeName());
//            userInfo.setPaymentTypeStatus(merchantAttribute.getStatus());
        }


        return userInfo;
    }

    @Override
    public UserInfo getUserInfoById(String userId) {
        UserInfo userInfo = null;
        PSysUser user = pSysUserMapper.selectByUserId(userId);
        if (Optional.fromNullable(user).isPresent()) {
            //获取用户所在的机构信息
            PSysBranch branch = pSysBranchMapper.selectByBranchId(user.getBranchId());
            PSysMerchant merchant = pSysMerchantMapper.selectByMerId(user.getUserRelation());

            userInfo = new UserInfo();

            userInfo.setUserId(user.getUserId());
            userInfo.setUserName(user.getUserName());
            userInfo.setUserPwd(user.getPassword());
            userInfo.setBranchId(user.getBranchId());
            userInfo.setBranchName(branch.getBranchName());
            userInfo.setUserStatus(user.getUserStatus());
            userInfo.setUserType(user.getUserType());
            userInfo.setUserStatus(user.getUserStatus());
            userInfo.setUserRelation(user.getUserRelation());

            if(null!=merchant){
                userInfo.setMerchantName(merchant.getMerName());
                userInfo.setMerchantId(merchant.getMerId());
            }


            /*//获取商户对应的业务类型
            List<MerchantAttribute> merchantAttributes = merchantAttributeMapper.findAttributeByMerId(user.getUserRelation());
            MerchantAttribute merchantAttribute = Iterables.get(merchantAttributes, 0, new MerchantAttribute());
            userInfo.setPaymentTypeId(merchantAttribute.getPaymentTypeId());
            userInfo.setPaymentTypeName(merchantAttribute.getPaymentTypeName());
            userInfo.setPaymentTypeStatus(merchantAttribute.getStatus());*/
        }
        return userInfo;
    }

    @Override
    public PTlinxAuth findByAppToken(String appId, String appToken) {
        return pTlinxAuthMapper.selectByAppToken(appId,appToken);
    }
}
