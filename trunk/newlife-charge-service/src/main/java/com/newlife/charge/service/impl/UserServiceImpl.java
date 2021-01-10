package com.newlife.charge.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.*;
import com.newlife.charge.common.security.Digests;
import com.newlife.charge.common.sms.RedisNoTokenSmsUtils;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.*;
import com.newlife.charge.core.domain.exModel.RolePermissionEx;
import com.newlife.charge.core.domain.exModel.UserEx;
import com.newlife.charge.core.domain.exModel.WeiChatData;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.*;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.*;
import com.newlife.charge.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Transactional
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserWeixinMapper userWeixinMapper;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private RoleUserRefMapper roleUserRefMapper;

    @Autowired
    private RolePermissionRefMapper rolePermissionRefMapper;


    @Autowired
    private Mapper dozer;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private StationUserRefMapper stationUserRefMapper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public User get(Integer id) {
        return this.userMapper.get(id);
    }

    @Override
    public PageInfo<UserEx> page(int pageNo, int pageSize, Integer userType, String mobile, String nickName) {
        PageHelper.startPage(pageNo, pageSize);

        Page<UserEx> page = this.userMapper.page(userType, mobile, nickName);

        return new PageInfo<>(pageNo, pageSize, page.getTotal(), page.getResult());
    }

    @Override
    public PageInfo<GeneralUserOut> generalPage(int pageNo, int pageSize, Integer userType, String nickName) {
        PageHelper.startPage(pageNo, pageSize);
        Page<GeneralUserOut> page = this.userMapper.generalPage(userType, nickName);

        return new PageInfo<>(pageNo, pageSize, page.getTotal(), page.getResult());
    }


    @Override
    public PageInfo<GeneralCarUserPageOut> generalCarUserPage(int pageNo, int pageSize, Integer userType, String mobile) {
        PageHelper.startPage(pageNo, pageSize);

        Page<UserEx> page = this.userMapper.page(userType, mobile, null);

        List<UserEx> list = page.getResult();
        List<GeneralCarUserPageOut> outList = BeanMapper.mapList(list, GeneralCarUserPageOut.class);
        return new PageInfo<>(pageNo, pageSize, page.getTotal(), outList);
    }

    @Override
    public List<GeneralIndexCarUserListOut> generalIndexCarUserList(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<UserEx> page = this.userMapper.page(UserTypeEnum.CAR.getValue(), null, null);
        List<UserEx> list = page.getResult();
        List<GeneralIndexCarUserListOut> outList = null;
        if(list!=null&&list.size()>0){
            outList = BeanMapper.mapList(list, GeneralIndexCarUserListOut.class);
        }
        return outList;
    }

    @Override
    public PageInfo<GeneralCompanyUserPageOut> generalCompanyUserPageList(int pageNo, int pageSize, GeneralStationUserPageIn pageIn) {
        PageHelper.startPage(pageNo, pageSize);

        Page<GeneralCompanyUserPageOut> page = this.userMapper.generalCompanyUserPageList(pageIn.getCompanyName(), pageIn.getMobile(), pageIn.getManagerName(), pageIn.getAuditStatus());
        List<GeneralCompanyUserPageOut> list = page.getResult();
        if (list != null && list.size() > 0) {
            Iterator<GeneralCompanyUserPageOut> it = list.iterator();
            GeneralCompanyUserPageOut info;
            Integer auditStatus;
            while (it.hasNext()) {
                info = it.next();
                auditStatus = info.getAuditStatus();
                if (auditStatus != null) {
                    info.setAuditStatusName(AuditStatusEnum.getDescriptionByValue(auditStatus));
                }
            }
        }


        return new PageInfo<>(pageNo, pageSize, page.getTotal(), page.getResult());
    }



    @Override
    public GeneralUserOut generalInfo(Integer id) {
        return this.userMapper.generalInfo(id);
    }

    @Override
    public UserEx loginByMobile(String mobile) {
        List<UserEx> userExes = this.userMapper.loginByMobile(mobile);
        return Collections3.isNotEmpty(userExes) ? userExes.get(0) : null;
    }

    @Override
    public UserEx loginByMobileAndUserType(String mobile, Integer userType) {

        List<UserEx> userExes = this.userMapper.loginByMobileAndUserType(mobile,new Integer[]{userType});
        return Collections3.isNotEmpty(userExes) ? userExes.get(0) : null;
    }

    @Override
    public List<User> getByMobile(String mobile) {
        return this.userMapper.getByMobile(mobile);
    }


    @Override
    public User getStationMainUser(Integer stationId) {
        return this.userMapper.getStationMainUser(stationId);
    }

    @Override
    public User getByMobileAndUserType(String mobile, Integer userType) {
        return this.userMapper.getByMobileAndUserType(mobile, userType);
    }

    @Override
    public List<UserEx> getByMobileAndUserType(String mobile, Integer[] userTypes) {
        return this.userMapper.loginByMobileAndUserType(mobile, userTypes);
    }

    @Override
    public UserEx loginByMobileCheck(String loginSessionId, String loginCaptchaCode, String userName, String password, UserTypeEnum userTypeEnum) {
        return this.loginCheck(loginSessionId, loginCaptchaCode, userName, password, userTypeEnum);
    }

    @Override
    public UserEx carUserLoginByMobileCheck(String mobile, String code, UserTypeEnum userTypeEnum) {
        //验证短信验证码
        //1.校验短信验证码是否失效
        String smsCode = RedisNoTokenSmsUtils.getValidateCode(SmsTypeEnum.CAR_MOBILE_LOGIN.getValue(), mobile);
        if (StringUtils.isBlank(smsCode)) {
            throw new BizException(ERROR.SMS_CODE_EXPIRED);
        }
        //2.校验短信验证码是否正确
        if (!code.equalsIgnoreCase(smsCode)) {
            throw new BizException(ERROR.SMS_CODE_ERR);
        }

        //3.检测车主用户是否存在，不存在则添加一个车主用户,密码为短信验证码
        CarUserloginByMobile(mobile,code,userTypeEnum);

        return this.loginCheck(null, null, mobile, code, userTypeEnum);
    }

    /**
     * 车主端通过手机号判断用户是否存在，没有则新增
     * @param mobile 电话号码
     * @param code 用于新增时生成密码
     * @param userTypeEnum 用户类型
     */
    private User CarUserloginByMobile(String mobile, String code, UserTypeEnum userTypeEnum) {
        User carUser = this.getByMobileAndUserType(mobile, userTypeEnum.getValue());
        if (carUser == null) {
            //添加一个车主用户
            Date now = new Date();
            //2.保存账号信息
            User user = new User();
            user.setAvatarUrl("");
            user.setBalance(BigDecimal.ZERO);
            user.setCreateTime(now);

            user.setCreateUser("GeneralAdmin");
            user.setEmail("");
            user.setGender(SexEnum.BOY.getValue());
            user.setIp("127.0.0.1");
            user.setLiftBanTime(now);
            user.setId(null);
            user.setUserName(mobile);
            user.setLoginTime(now);
            user.setMobile(mobile);
            user.setNickName(mobile);

            Digests.Password password = Digests.encryptPwd(code);
            user.setPassword(password.getEncryptPwd());
            user.setSalt(password.getSalt());

            user.setRealName(mobile);
            user.setStatus(UserStatusEnum.ENABLED.getValue());
            user.setUserType(UserTypeEnum.CAR.getValue());
            user.setVerifyEmail(YesNoEnum.NO.getValue());
            user.setVerifyMobile(YesNoEnum.YES.getValue());
            this.userMapper.insert(user);
            // 判断是否存在account信息，没有则添加（针对微信用户）
            UserAccount account = userAccountMapper.getByUserId(user.getId());
            if(account == null){
                account = new UserAccount();
                account.setUserId(user.getId());
                account.setCreateTime(new Date());
                account.setChargeBalance(new BigDecimal(0));
                account.setGiveBalance(new BigDecimal(0));
                account.setCouponChargeBalance(new BigDecimal(0));
                userAccountMapper.insert(account);

            }


            return user;

        } else {
            //更新车主用户密码为新的短信验证码
            Digests.Password password = Digests.encryptPwd(code);
            carUser.setPassword(password.getEncryptPwd());
            carUser.setSalt(password.getSalt());
            this.userMapper.update(carUser);
        }
        return carUser;
    }

    @Override
    public UserEx loginCheck(String loginSessionId, String loginCaptchaCode, String userName, String password, UserTypeEnum userTypeEnum) {

        UserEx userEx = null;
        /**验证图形验证码*/
        if (StringUtils.isNotBlank(loginCaptchaCode) && StringUtils.isNotBlank(loginSessionId)) {
            String captchaCode = CaptchaImageUtils.getValidateCode(loginSessionId);
            if (StringUtils.isBlank(captchaCode)) {
                throw new BizException(ERROR.VERIFY_CODE_EXPIRED);
            }
            if (!StringUtils.equalsIgnoreCase(loginCaptchaCode, captchaCode)) {
                throw new BizException(ERROR.VERIFY_CODE_ERR);
            }
        }

        /**手机号登录*/
        if (StringUtils.isNotBlank(userName)) {
            userEx=this.loginByMobileAndUserType(userName,userTypeEnum.getValue());
        }

        /**非null校验*/
        if (userEx == null) {
            throw new BizException(ERROR.NOT_FOUND_USER);
        }

        /**用户类型校验*/
        if(userTypeEnum.getValue().intValue()!=userEx.getUserType()){
            throw new BizException(ERROR.NOT_FOUND_USER);
        }


        /**基于当前明文密码加密成新的密码*/
        if (StringUtils.isNotBlank(password)) {
            if (!Digests.validatePassword(password, userEx.getSalt(), userEx.getPassword())) {
                throw new AuthenticationException(ERROR.UN_AUTHORIZED.message());
            }
        }

        /**用户状态校验*/
        Integer status = userEx.getStatus();
        if(UserStatusEnum.DISABLE.getValue().intValue()==status){
            //该用户已被禁用
            throw new BizException(ERROR.USER_STATUS_DISABLE);
        }
        /**锁定状态校验*/
        if(UserStatusEnum.LOCKED.getValue().intValue()==status){
            //该用户已被禁用
            throw new BizException(ERROR.USER_STATUS_DISABLE);
        }
        return userEx;
    }


    @Override
    public void updateLoginTime(User user) {
        this.update(user);
    }

    @Override
    public void update(User user) {
        this.userMapper.update(user);
    }

    @Override
    public List<String> getUserPermissions(Integer userId) {
        List<String> result = new ArrayList<>();
        List<RoleUserRef> roleUserRefs = this.roleUserRefMapper.getByUserId(userId);

        List<RolePermissionEx> rolePermissionExes;
        Integer roleId;
        RoleUserRef roleUserRef;
        List<Integer> roleIdList = new ArrayList<>();
        if (Collections3.isNotEmpty(roleUserRefs)) {
            Iterator<RoleUserRef> it = roleUserRefs.iterator();
            while (it.hasNext()) {
                roleUserRef = it.next();
                roleId = roleUserRef.getRoleId();
                roleIdList.add(roleId);
            }
            rolePermissionExes = rolePermissionRefMapper.getByRoleIdList(roleIdList);
            if (Collections3.isNotEmpty(rolePermissionExes)) {
                result = rolePermissionExes.stream().filter(rolePermissionEx -> rolePermissionEx.getPermissionSname() != null).map(RolePermissionEx::getPermissionSname).collect(Collectors.toList());
            }
        }
        return result;
    }


    @Override
    public void generalSave(GeneralUserSaveIn generalUserSaveIn) {

        //1.检查手机号是否已经添加为后台账号
        User adminUser = this.getByMobileAndUserType(generalUserSaveIn.getMobile(), UserTypeEnum.ADMIN.getValue());
        if (adminUser != null) {
            throw new BizException(ERROR.MOBILE_REPEAT_ADMIN);
        }

        Date now = new Date();
        //2.保存账号信息
        User user = new User();
        user.setAvatarUrl("");
        user.setBalance(BigDecimal.ZERO);
        user.setCreateTime(now);

        user.setCreateUser(UserContext.getMobile());
        user.setEmail("");
        user.setGender(SexEnum.BOY.getValue());
        user.setIp("127.0.0.1");
        user.setLiftBanTime(now);
        user.setId(null);
        user.setUserName(generalUserSaveIn.getMobile());
        user.setLoginTime(now);
        user.setMobile(generalUserSaveIn.getMobile());
        user.setNickName(generalUserSaveIn.getNickName());

        Digests.Password password = Digests.encryptPwd(generalUserSaveIn.getPassword());
        user.setPassword(password.getEncryptPwd());
        user.setSalt(password.getSalt());

        user.setRealName(generalUserSaveIn.getRealName());
        user.setStatus(UserStatusEnum.ENABLED.getValue());
        user.setUserType(UserTypeEnum.ADMIN.getValue());
        user.setVerifyEmail(YesNoEnum.NO.getValue());
        user.setVerifyMobile(YesNoEnum.NO.getValue());
        this.userMapper.insert(user);

        //3.保存角色账号关联信息

        RoleUserRef roleUserRef = new RoleUserRef();
        roleUserRef.setCreateTime(now);
        roleUserRef.setRoleId(generalUserSaveIn.getRoleId());
        roleUserRef.setUserId(user.getId());
        this.roleUserRefMapper.insert(roleUserRef);

    }

    @Override
    public void generalUpdateStatus(Integer userId, UserStatusEnum userStatusEnum) {

        //不能禁用自己的账号
        if (userId == UserContext.getUserId().intValue()) {
            throw new BizException("不能禁用自己的账号");
        }

        User user_db = this.userMapper.get(userId);
        if (user_db == null) {
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        user_db.setStatus(userStatusEnum.getValue());
        this.update(user_db);

    }

    @Override
    public void generalUpdate(GeneralUserUpdateIn generalUserUpdateIn) {
        User user_db = this.userMapper.get(generalUserUpdateIn.getId());

        //1.校验
        //1.1 手机号唯一性校验
        String mobileNew = generalUserUpdateIn.getMobile();//新手机号
        User hasUserDb = this.getByMobileAndUserType(mobileNew, UserTypeEnum.ADMIN.getValue());
        if (hasUserDb != null && user_db.getId().intValue() != hasUserDb.getId().intValue()) {
            //新的手机号已经是总后台账号（非当前账号）
            throw new BizException(ERROR.MOBILE_UPDATE_ADMIN);
        }

        //2.更新账号信息
        user_db.setMobile(generalUserUpdateIn.getMobile());
        user_db.setUserName(generalUserUpdateIn.getMobile());
        user_db.setNickName(generalUserUpdateIn.getNickName());
        String passwordStr = generalUserUpdateIn.getPassword();
        if(StringUtils.isNotBlank(passwordStr)){
            Digests.Password password = Digests.encryptPwd(generalUserUpdateIn.getPassword());
            user_db.setPassword(password.getEncryptPwd());
            user_db.setSalt(password.getSalt());
        }

        user_db.setRealName(generalUserUpdateIn.getRealName());

        this.userMapper.update(user_db);

        //3.更新角色账号关联信息
        //3.1删除旧的关联数据
        this.roleUserRefMapper.deleteByUserId(user_db.getId());
        //3.2添加新的关联数据
        RoleUserRef roleUserRef = new RoleUserRef();
        roleUserRef.setCreateTime(new Date());
        roleUserRef.setRoleId(generalUserUpdateIn.getRoleId());
        roleUserRef.setUserId(user_db.getId());
        this.roleUserRefMapper.insert(roleUserRef);

    }


    @Override
    public void delete(Integer id) {
        //自己不能删除自己
        if(UserContext.getUserId().equals(id)){
            throw  new BizException(ERROR.NOT_DELETE_ONESELF);
        }
        this.deletes(new int[]{id});
    }

    @Override
    public void deletes(int[] ids) {

        //1.删除角色用户关联数据
        this.roleUserRefMapper.deleteByUserIds(ids);
        //2.删除用户数据
        this.userMapper.deleteByIds(ids);
    }

    @Override
    public void deletesStationUser(int[] ids) {

        //删除 桩站 用户关联
        stationUserRefMapper.deleteByIdsAndStation(ids, UserContext.getStationId());

        //1.删除桩站角色 用户关联数据
        RoleInfoQuery query = new RoleInfoQuery();
        query.setStationId(UserContext.getStationId());
        List<RoleInfo> roles = roleInfoMapper.getByQuery(query);
        if(Collections3.isNotEmpty(roles)) {
            List<Integer> roleIds = roles.stream().map(RoleInfo::getId).collect(Collectors.toList());
            roleUserRefMapper.deleteByUserIdsAndRoleIds(ids, roleIds.toArray(new Integer[roleIds.size()]));
        }

        //2.删除用户数据 判断是否关联其他桩站,有则不能删除
        for(int id:ids) {
            StationUserRefQuery stationUserRefQuery = new StationUserRefQuery();
            stationUserRefQuery.setUserId(id);
            List<StationUserRefOut> stationUserRefs = stationUserRefMapper.getByQuery(stationUserRefQuery);
            if(Collections3.isNotEmpty(stationUserRefs)) {

            } else {
                this.userMapper.delete(id);
            }
        }
    }

    @Override
    public User register(UserRegisterPasswordIn userRegisterPasswordIn) {
        //1.检查手机号是否已经注册
        User stationUser = this.getByMobileAndUserType(userRegisterPasswordIn.getMobile(), UserTypeEnum.STATION_MAIN.getValue());
        if (stationUser != null) {
            throw new BizException(ERROR.MOBILE_FOUND_USER);
        }

        Date now = new Date();
        //2.保存账号信息
        User user = new User();
        user.setAvatarUrl("");
        user.setBalance(BigDecimal.ZERO);
        user.setCreateTime(now);

        user.setCreateUser("StationAdmin");
        user.setEmail("");
        user.setGender(SexEnum.BOY.getValue());
        user.setIp("127.0.0.1");
        user.setLiftBanTime(now);
        user.setId(null);
        user.setUserName(userRegisterPasswordIn.getMobile());
        user.setLoginTime(now);
        user.setMobile(userRegisterPasswordIn.getMobile());
        user.setNickName("");

        Digests.Password password = Digests.encryptPwd(userRegisterPasswordIn.getPassword());
        user.setPassword(password.getEncryptPwd());
        user.setSalt(password.getSalt());

        user.setRealName(userRegisterPasswordIn.getMobile());
        user.setStatus(UserStatusEnum.ENABLED.getValue());
        user.setUserType(UserTypeEnum.STATION_MAIN.getValue());
        user.setVerifyEmail(YesNoEnum.NO.getValue());
        user.setVerifyMobile(YesNoEnum.NO.getValue());
        this.userMapper.insert(user);

        //保存角色账号关联信息
        RoleUserRef roleUserRef = new RoleUserRef();
        roleUserRef.setCreateTime(now);
        //桩站后台超级管理员
        roleUserRef.setRoleId(RoleInfoEnum.STATION_ADMIN.getValue());
        roleUserRef.setUserId(user.getId());
        this.roleUserRefMapper.insert(roleUserRef);

        return user;
    }

    @Override
    public UserEx loginStationByMobileCheck(String loginSessionId, String loginCaptchaCode, String userName, String password, Integer[] userTypes) {

        UserEx userEx = null;
        /**验证图形验证码*/
        if (StringUtils.isNotBlank(loginCaptchaCode) && StringUtils.isNotBlank(loginSessionId)) {
            String captchaCode = CaptchaImageUtils.getValidateCode(loginSessionId);
            if (StringUtils.isBlank(captchaCode)) {
                throw new BizException(ERROR.VERIFY_CODE_EXPIRED);
            }
            if (!StringUtils.equalsIgnoreCase(loginCaptchaCode, captchaCode)) {
                throw new BizException(ERROR.VERIFY_CODE_ERR);
            }
        }

        /**手机号登录*/
        if (StringUtils.isNotBlank(userName)) {
            //找出手机号 所有桩站用户 按主账号 子账号排序，有多个账号 默认选取第一个
            List<UserEx> userExes = userMapper.loginByMobileAndUserType(userName, userTypes);
            if(Collections3.isNotEmpty(userExes)) {
                userEx = userExes.get(0);

                //若是主账号 获取该账号关联的公司 审核状态
                if(userEx.getUserType().equals(UserTypeEnum.STATION_MAIN.getValue())) {

                    CompanyInfo companyInfo = companyInfoMapper.getByUserId(userEx.getId());
                    if(companyInfo != null) {
                        userEx.setAuditStatus(companyInfo.getAuditStatus());
                        userEx.setAuditMessage(companyInfo.getRemark());
                        userEx.setCompanyInfoId(companyInfo.getId());

                        //审核通过的公司 查找默认排序的第一个桩站
                        StationUserRefQuery query = new StationUserRefQuery();
                        query.setUserId(userEx.getId());
                        query.setCompanyInfoId(companyInfo.getId());
                        List<StationUserRefOut> stationUserRefs = stationUserRefMapper.getByUserIdAndCompanyId(query);
                        if(Collections3.isNotEmpty(stationUserRefs)) {
                            userEx.setStationId(stationUserRefs.get(0).getStationId());
                            //若出现多家桩站 则跳转切换桩站页面
                            if(stationUserRefs.size() > 1) {
                                userEx.setHasMoreStation(true);
                            }

                        } else {
                            //公司审核通过  但未创建站点
                            if(AuditStatusEnum.PASS.getValue() == companyInfo.getAuditStatus()) {
                                userEx.setAuditStatus(AuditStatusEnum.NO_CREATE_STATION.getValue());
                            }
                        }

                    } else {
                        //不存在公司说明 公司未提交资质
                        userEx.setAuditStatus(AuditStatusEnum.NO_JOIN.getValue());
                    }
                } else {
                    //子账号 均为审核通过
                    userEx.setAuditStatus(AuditStatusEnum.PASS.getValue());

                    //查找默认排序的第一个桩站
                    StationUserRefQuery query = new StationUserRefQuery();
                    query.setUserId(userEx.getId());
                    List<StationUserRefOut> stationUserRefs = stationUserRefMapper.getByUserIdAndCompanyId(query);
                    if(Collections3.isNotEmpty(stationUserRefs)) {
                        userEx.setStationId(stationUserRefs.get(0).getStationId());
                        userEx.setCompanyInfoId(stationUserRefs.get(0).getCompanyInfoId());
                        if(stationUserRefs.size()> 1 ) {
                            userEx.setHasMoreStation(true);
                        }
                    }
                }
            }
        }

        /**非null校验*/
        if (userEx == null) {
            throw new BizException(ERROR.NOT_FOUND_USER);
        }

        /**基于当前明文密码加密成新的密码*/
        if (StringUtils.isNotBlank(password)) {
            if (!Digests.validatePassword(password, userEx.getSalt(), userEx.getPassword())) {
                throw new AuthenticationException(ERROR.UN_AUTHORIZED.message());
            }
        }
        return userEx;
    }

    @Override
    public UserEx loginStationByMobileCodeCheck(String mobile, Integer[] userTypes) {

        UserEx userEx = null;

        /**手机号登录*/
        if (StringUtils.isNotBlank(mobile)) {
            //找出手机号 所有桩站用户 按主账号 子账号排序，有多个账号 默认选取第一个
            List<UserEx> userExes = userMapper.loginByMobileAndUserType(mobile, userTypes);
            if(Collections3.isNotEmpty(userExes)) {
                userEx = userExes.get(0);

                //若是主账号 获取该账号关联的公司 审核状态
                if(userEx.getUserType().equals(UserTypeEnum.STATION_MAIN.getValue())) {

                    CompanyInfo companyInfo = companyInfoMapper.getByUserId(userEx.getId());
                    if(companyInfo != null) {
                        userEx.setAuditStatus(companyInfo.getAuditStatus());
                        userEx.setAuditMessage(companyInfo.getRemark());
                        userEx.setCompanyInfoId(companyInfo.getId());

                        //审核通过的公司 查找默认排序的第一个桩站
                        StationUserRefQuery query = new StationUserRefQuery();
                        query.setUserId(userEx.getId());
                        query.setCompanyInfoId(companyInfo.getId());
                        List<StationUserRefOut> stationUserRefs = stationUserRefMapper.getByUserIdAndCompanyId(query);
                        if(Collections3.isNotEmpty(stationUserRefs)) {
                            userEx.setStationId(stationUserRefs.get(0).getStationId());
                            if(stationUserRefs.size()> 1 ) {
                                userEx.setHasMoreStation(true);
                            }
                        } else {
                            //公司审核通过  但未创建站点
                            if(AuditStatusEnum.PASS.getValue() == companyInfo.getAuditStatus()) {
                                userEx.setAuditStatus(AuditStatusEnum.NO_CREATE_STATION.getValue());
                            }
                        }

                    } else {
                        //不存在公司说明 公司未提交资质
                        userEx.setAuditStatus(AuditStatusEnum.NO_JOIN.getValue());
                    }
                } else {
                    //子账号 均为审核通过
                    userEx.setAuditStatus(AuditStatusEnum.PASS.getValue());

                    //查找默认排序的第一个桩站
                    StationUserRefQuery query = new StationUserRefQuery();
                    query.setUserId(userEx.getId());
                    List<StationUserRefOut> stationUserRefs = stationUserRefMapper.getByUserIdAndCompanyId(query);
                    if(Collections3.isNotEmpty(stationUserRefs)) {
                        userEx.setStationId(stationUserRefs.get(0).getStationId());
                        userEx.setCompanyInfoId(stationUserRefs.get(0).getCompanyInfoId());
                    }
                    if(stationUserRefs.size()> 1 ) {
                        userEx.setHasMoreStation(true);
                    }
                }
            } else {
                //不存在用户  先注册用
                UserRegisterPasswordIn userRegisterPasswordIn = new UserRegisterPasswordIn();
                userRegisterPasswordIn.setMobile(mobile);
                //默认给密码123456
                userRegisterPasswordIn.setPassword("123465");
                User user = this.register(userRegisterPasswordIn);
                userEx = dozer.map(user, UserEx.class);
                //不存在公司说明 公司未提交资质
                userEx.setAuditStatus(AuditStatusEnum.NO_JOIN.getValue());

            }
        }

//        /**非null校验*/
//        if (userEx == null) {
//            throw new BizException(ERROR.NOT_FOUND_USER);
//        }

        return userEx;
    }

    @Override
    public void modifyPassword(UserInPassDto userInPassDto) {
        //修改密码  该账号 下的 手机号 所关联的所有桩站账号 全部修改
        User user = this.userMapper.get(UserContext.getUserId());
        if(user != null) {
            if (!Digests.validatePassword(userInPassDto.getPassword(), user.getSalt(), user.getPassword())) {
                throw new BizException("旧密码输入有误");
            }
            //查找若有过个账号 密码全部修改
            List<User> users = userMapper.getByMobile(user.getMobile());
            modifyPass(userInPassDto.getNewPassword(), users);

        } else {
            throw new BizException("找不到用户");
        }
    }

    @Override
    public void resetPass(UserRegisterPasswordIn userRegisterPasswordIn) {

        //查找若有过个账号 密码全部修改
        List<User> users = userMapper.getByMobile(userRegisterPasswordIn.getMobile());
        modifyPass(userRegisterPasswordIn.getPassword(), users);
    }

    private void modifyPass(String password, List<User> users) {
        if(Collections3.isNotEmpty(users)) {
            Digests.Password newPassword = Digests.encryptPwd(password);
            //修改密码
            for(User u : users) {
                User userModifyPwd = new User();
                userModifyPwd.setId(u.getId());
                userModifyPwd.setPassword(newPassword.getEncryptPwd());
                userModifyPwd.setSalt(newPassword.getSalt());
                userMapper.update(userModifyPwd);
            }
        }
    }

    @Override
    public WeiChatLoginStatusOut carWeiChatLogin(StringIn strIn) {

        final String appId = "wx875dcc460cfe82ae";
        final String appSecret = "88d8aa84e75f5976daf009191eece704";

        String params = String.format("appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appId,appSecret,strIn.getStr());

        String result = HttpUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session",
                params);

        JSONObject jsonObject = JSON.parseObject(result);

        String sessionKey = jsonObject.getString("session_key");
        String openId = jsonObject.getString("openid");
        String errCode = jsonObject.getString("errcode");
        String errMsg = jsonObject.getString("errmsg");

        // 生成自定义登录态，返回前端
        WeiChatLoginStatusOut weiChatLoginStatus = new WeiChatLoginStatusOut();
        weiChatLoginStatus.setOpenid(openId);
        weiChatLoginStatus.setSessionKey(sessionKey);
        weiChatLoginStatus.setErrCode(errCode);
        weiChatLoginStatus.setErrMsg(errMsg);

        if(StringUtils.isBlank(openId) || StringUtils.isBlank(sessionKey)){
            throw new BizException(Integer.parseInt(errCode),errMsg);
        }

        return weiChatLoginStatus;
    }

    @Override
    public UserEx carWeiChatRegisterLogin(WeiChatLoginIn loginIn,UserTypeEnum userTypeEnum) {

        String decrypt = Encodes.decrypt(loginIn.getSessionId(), loginIn.getIv(), loginIn.getEncryptedData());

        JSONObject jsonObject = JSON.parseObject(decrypt);

        WeiChatData weiChatData = JSONObject.toJavaObject(jsonObject, WeiChatData.class);

        String mobile = weiChatData.getPurePhoneNumber();
        String code =  loginIn.getOpenId();

        // 检查手机号跟微信号是否匹配
        User carUser = this.getByMobileAndUserType(mobile, userTypeEnum.getValue());
        logger.info("===> 检查手机号跟微信号是否匹配"+loginIn.getOpenId()+" 手机号:"+mobile);
        if(carUser != null){
            // 查看该账户是否有绑定的微信号
            UserWeixin userWeixin = this.userWeixinMapper.getByUserId(carUser.getId());
            // 判断当前手机号绑定的微信和当前登录的微信是否同一个,如果不是，则抛出异常
            if(userWeixin != null && !userWeixin.getOpenId().equalsIgnoreCase(loginIn.getOpenId())){
                logger.info("===> "+userWeixin.getOpenId());
                throw new BizException(ERROR.USER_DIFFERENT);
            }
        }

        // 检测车主用户是否存在，不存在则添加一个车主用户,密码为短信验证码
        User user = CarUserloginByMobile(mobile, code, userTypeEnum);

        // 将用户信息添加到微信用户表中（由于用户账号和手机号存在一对多的关系故在此添加信息到微信用户表）
        if(user != null){
            // 查询微信用户表中是否存在
           UserWeixin weixin = this.userWeixinMapper.getByUserId(user.getId());
            if(weixin == null){
                UserWeixin userWeixin = new UserWeixin();
                userWeixin.setUserId(user.getId());
                userWeixin.setOpenId(loginIn.getOpenId());
                userWeixin.setAvatarUrl("");
                userWeixin.setCity("");
                userWeixin.setGender(0);
                userWeixin.setId(null);
                userWeixin.setNickName("");
                userWeixin.setProvince("");
                userWeixin.setUnionId("");
                userWeixin.setCreateTime(new Date());
                this.userWeixinMapper.insert(userWeixin);
            }

        }
        logger.info("===> mobile:"+mobile+" code:"+code+" userType:"+userTypeEnum);
        return this.loginCheck(null, null, mobile, code, userTypeEnum);
    }

    @Override
    public PageInfo<StationUserOut> stationUserPage(StationUserPageQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());

        //只查询桩站子账号
        query.setUserType(UserTypeEnum.STATION_SUB.getValue());
        query.setStationId(UserContext.getStationId());
        Page<StationUserOut> page = this.userMapper.stationUserPage(query);

        return new PageInfo<>(query.getPageNo(), query.getPageSize(), page.getTotal(), page.getResult());
    }

    @Override
    public void stationSave(StationUserIn userIn) {

        //1.检查手机号是否 已经注册为子账号
        List<UserEx> stationUser = this.userMapper.loginByMobileAndUserType(userIn.getUserName(), new Integer[]{UserTypeEnum.STATION_MAIN.getValue(),UserTypeEnum.STATION_SUB.getValue()});
        if(Collections3.isNotEmpty(stationUser)) {
            List<UserEx> users = stationUser.stream().filter(u -> UserTypeEnum.STATION_MAIN.getValue().equals(u.getUserType())).collect(Collectors.toList());
            if(Collections3.isNotEmpty(users)) {
                //注册为主账号的 不能成为子账号
                throw new BizException("该账号已存在");
            }

            //过滤出 ids
            List<Integer> userIds = stationUser.stream().map(UserEx::getId).collect(Collectors.toList());

            StationUserRefQuery query = new StationUserRefQuery();
            query.setStationId(UserContext.getStationId());
            query.setUserIds(userIds.toArray(new Integer[userIds.size()]));
            List<StationUserRefOut> stationUserRefs = stationUserRefMapper.getByUserIdAndCompanyId(query);
            if(Collections3.isNotEmpty(stationUserRefs)) {
                List<StationUserRefOut> stationUsers = stationUserRefs.stream().filter(p -> p.getStationId().equals(UserContext.getStationId())).collect(Collectors.toList());
                if(Collections3.isNotEmpty(stationUsers)) {
                    throw new BizException(ERROR.MOBILE_FOUND_USER_SUB);
                }
            }
        }


        User user = null;
        //若账号 已被其他桩站注册 则用户信息可复用
        if(Collections3.isNotEmpty(stationUser)) {

            user = stationUser.get(0);
        }


        Date now = new Date();
        if(user == null) {
            //2.保存账号信息
            User user1 = new User();
            user1.setAvatarUrl("");
            user1.setBalance(BigDecimal.ZERO);
            user1.setCreateTime(now);

            user1.setCreateUser("StationAdmin");
            user1.setEmail("");
            user1.setGender(SexEnum.BOY.getValue());
            user1.setIp("127.0.0.1");
            user1.setLiftBanTime(now);
            user1.setId(null);
            user1.setUserName(userIn.getUserName());
            user1.setLoginTime(now);
            user1.setMobile(userIn.getUserName());
            user1.setNickName("");

            //子管理员默认密码 123456
            Digests.Password password = Digests.encryptPwd("123456");
            user1.setPassword(password.getEncryptPwd());
            user1.setSalt(password.getSalt());

            user1.setRealName(userIn.getRealName());
            user1.setStatus(UserStatusEnum.ENABLED.getValue());
            user1.setUserType(UserTypeEnum.STATION_SUB.getValue());
            user1.setVerifyEmail(YesNoEnum.NO.getValue());
            user1.setVerifyMobile(YesNoEnum.NO.getValue());
            this.userMapper.insert(user1);

            user = user1;
        }

        //保存角色账号关联信息
        RoleUserRef roleUserRef = new RoleUserRef();
        roleUserRef.setCreateTime(now);
        //桩站后台普通管理员
        roleUserRef.setRoleId(userIn.getRoleId());
        roleUserRef.setUserId(user.getId());
        this.roleUserRefMapper.insert(roleUserRef);

        //保存桩站 用户关联表
        StationUserRef stationUserRef = new StationUserRef();
        stationUserRef.setStationId(UserContext.getStationId());
        stationUserRef.setStatus(1);
        stationUserRef.setUserId(user.getId());
        stationUserRef.setCreateTime(now);
        stationUserRefMapper.insert(stationUserRef);
    }

    @Override
    public void stationUpdate(StationUserUpdateIn userIn) {
        //修改子账号 用户名不做修改
//        //1.检查手机号是否 已经注册为子账号
//        List<UserEx> stationUser = this.userMapper.loginByMobileAndUserType(userIn.getUserName(), new Integer[]{UserTypeEnum.STATION_MAIN.getValue(),UserTypeEnum.STATION_SUB.getValue()});
//        if(Collections3.isNotEmpty(stationUser)) {
//            //过滤出 ids 修改排除自身
//            List<Integer> userIds = new ArrayList<>();
//            for(UserEx userEx:stationUser) {
//                if(!userEx.getId().equals(userIn.getUserId())) {
//                    userIds.add(userEx.getId());
//                }
//            }
//            if(Collections3.isNotEmpty(userIds)) {
//                StationUserRefQuery query = new StationUserRefQuery();
//                query.setStationId(UserContext.getStationId());
//                query.setUserIds(userIds.toArray(new Integer[userIds.size()]));
//                List<StationUserRefOut> stationUserRefs = stationUserRefMapper.getByUserIdAndCompanyId(query);
//                if(Collections3.isNotEmpty(stationUserRefs)) {
//                    throw new BizException(ERROR.MOBILE_FOUND_USER_SUB);
//                }
//            }
//        }
        Date now = new Date();
       //修改用户
        User user = userMapper.get(userIn.getUserId());
        user.setRealName(userIn.getRealName());
        userMapper.update(user);

        //1.删除桩站角色 用户关联数据
        RoleInfoQuery query = new RoleInfoQuery();
        query.setStationId(UserContext.getStationId());
        List<RoleInfo> roles = roleInfoMapper.getByQuery(query);
        if(Collections3.isNotEmpty(roles)) {
            List<Integer> roleIds = roles.stream().map(RoleInfo::getId).collect(Collectors.toList());
            roleUserRefMapper.deleteByUserIdsAndRoleIds(new int[]{user.getId()}, roleIds.toArray(new Integer[roleIds.size()]));
            //保存角色账号关联信息
            RoleUserRef roleUserRef = new RoleUserRef();
            roleUserRef.setCreateTime(now);
            //桩站后台普通管理员
            roleUserRef.setRoleId(userIn.getRoleId());
            roleUserRef.setUserId(user.getId());
            this.roleUserRefMapper.insert(roleUserRef);
        }

    }

    @Override
    public StationUserOut stationUserInfo(Integer userId, Integer stationId) {
        return this.userMapper.stationUserInfo(userId, stationId);
    }

}
