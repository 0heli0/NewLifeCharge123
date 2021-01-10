package com.newlife.charge.service;


import com.newlife.charge.core.domain.User;
import com.newlife.charge.core.domain.exModel.UserEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.UserStatusEnum;
import com.newlife.charge.core.enums.UserTypeEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 Service 类
 * <p>
 */
public interface UserService {


    /**
     * 用户分页查询
     *
     * @return
     */
    PageInfo<UserEx> page(int pageNo, int pageSize, Integer userType, String mobile, String nickName);


    /**
     * 总后台账号查询
     *
     * @param id
     */
    User get(Integer id);

    /**
     * 总后台账号删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 总后台账号批量删除
     *
     * @param ids
     */
    void deletes(int[] ids);

    /**
     * 桩站后台账号批量删除
     *
     * @param ids
     */
    void deletesStationUser(int[] ids);

    /**
     * 总后台 账号管理分页查询
     *
     * @return
     */
    PageInfo<GeneralUserOut> generalPage(int pageNo, int pageSize, Integer userType, String nickName);


    /**
     * 总后台 用户管理-分页查询
     *
     * @return
     */
    PageInfo<GeneralCarUserPageOut> generalCarUserPage(int pageNo, int pageSize, Integer userType, String mobile);


    /**
     * 总后台首页新注册车主用户列表数据(5条)
     *
     * @param pageNo   页码
     * @param pageSize 页容量
     * @return
     */
    List<GeneralIndexCarUserListOut> generalIndexCarUserList(int pageNo, int pageSize);


    /**
     * 总后台 桩站注册账号分页查询
     *
     * @return
     */
    PageInfo<GeneralCompanyUserPageOut> generalCompanyUserPageList(int pageNo, int pageSize, GeneralStationUserPageIn pageIn);



    /**
     * 通过主键id查询总后台账号信息
     *
     * @param id
     * @return
     */
    GeneralUserOut generalInfo(Integer id);


    /**
     * 手机号登录
     *
     * @param mobile
     * @return
     */
    UserEx loginByMobile(String mobile);

    /**
     * 手机号登录
     *
     * @param mobile 手机号
     * @param userType  用户类型
     * @return
     */
    UserEx loginByMobileAndUserType(String mobile,Integer userType);


    /**
     * 通过手机号查找
     *
     * @param mobile
     * @return
     */
    List<User> getByMobile(String mobile);


    /**
     * 查询桩站主账号
     *
     * @param stationId 桩站ID
     * @return
     */
    User getStationMainUser(Integer stationId);


    /**
     * 通过手机号及类型查找
     *
     * @param mobile
     * @param userType
     * @return
     */
    User getByMobileAndUserType(String mobile, Integer userType);


    /**
     * @Description: 通过手机号和类型 查找
     */
    List<UserEx> getByMobileAndUserType(String mobile, Integer[] userTypes);


    /**
     * 统一登录检查
     * 1.手机号登录检查（图形验证码、密码）
     * TODO:2.桩站子账号账户切换
     *
     * @param loginSessionId
     * @param loginCaptchaCode
     * @param userName
     * @param password
     * @return
     */
    UserEx loginCheck(String loginSessionId, String loginCaptchaCode, String userName, String password, UserTypeEnum userTypeEnum);


    /**
     * 手机号登录检查（图形验证码、密码）
     *
     * @param loginSessionId
     * @param loginCaptchaCode
     * @param userName
     * @param password
     * @param userTypeEnum
     * @return
     */
    UserEx loginByMobileCheck(String loginSessionId, String loginCaptchaCode, String userName, String password, UserTypeEnum userTypeEnum);


    /**
     * 车主用户短信验证码登录
     *
     * @param mobile       手机号
     * @param code         短信验证码
     * @param userTypeEnum 用户类型，应该是车主用户类型
     * @return
     */
    UserEx carUserLoginByMobileCheck(String mobile, String code, UserTypeEnum userTypeEnum);


    /**
     * 更新登录 时间
     *
     * @param user
     */
    void updateLoginTime(User user);


    /**
     * 更新
     *
     * @param user
     */
    void update(User user);


    /**
     * 通过用户id查找用户的所有权限
     *
     * @param userId
     * @return
     */
    List<String> getUserPermissions(Integer userId);


    /**
     * 总后台 新增账号
     *
     * @param generalUserSaveIn
     */
    void generalSave(GeneralUserSaveIn generalUserSaveIn);

    /**
     * 总后台 更新账号
     *
     * @param generalUserUpdateIn
     */
    void generalUpdate(GeneralUserUpdateIn generalUserUpdateIn);


    /**
     * 总后台 更新用户状态（启用、禁用）
     *
     * @param userId         用户ID
     * @param userStatusEnum 状态
     */
    void generalUpdateStatus(Integer userId, UserStatusEnum userStatusEnum);

    /**
     * @Description: 桩站管理后台 注册账号
     * @Author: Linzq
     * @CreateDate:  2019/5/5 0005 10:43
     */
    User register(UserRegisterPasswordIn userRegisterPasswordIn);

    /**
     * @Description: 桩站后台账号和子账号 手机登入
     * @Author: Linzq
     * @CreateDate:  2019/5/5 0005 15:17
     */
    UserEx loginStationByMobileCheck(String loginSessionId, String loginCaptchaCode, String userName, String password, Integer[] userTypes);

    /**
     * @Description: 桩站小程序 账号和子账号 手机验证码登入
     * @Author: Linzq
     * @CreateDate:  2019/5/5 0005 15:17
     */
    UserEx loginStationByMobileCodeCheck(String mobile,Integer[] userTypes);

    /**
     * @Description: 桩站后台账号更改密码
     * @Author: Linzq
     * @CreateDate:  2019/5/6 0006 14:50
     */
    void modifyPassword(UserInPassDto userInPassDto);

    /**
     * @Description: 桩站后台 用户找回密码
     * @Author: Linzq
     * @CreateDate:  2019/5/6 0006 14:50
     */
    void resetPass(UserRegisterPasswordIn userRegisterPasswordIn);

    /**
     *
     * @param strIn
     * @return
     */
    WeiChatLoginStatusOut carWeiChatLogin(StringIn strIn);

    /**
     * 车主用户微信新用户注册并同时登录
     * @param loginIn
     * @return
     */
    UserEx carWeiChatRegisterLogin(WeiChatLoginIn loginIn,UserTypeEnum userTypeEnum);

    /**
     * 桩站后台 账号管理分页查询
     *
     * @return
     */
    PageInfo<StationUserOut> stationUserPage(StationUserPageQuery query);

    /**
     * @Description: 桩站后台添加子账号
     * @Author: Linzq
     * @CreateDate:  2019/5/14 0014 11:56
     */
    void stationSave(StationUserIn userIn);

    /**
     * @Description: 桩站后台 修改子账号
     * @Author: Linzq
     * @CreateDate:  2019/5/14 0014 11:56
     */
    void stationUpdate(StationUserUpdateIn userIn);

    /**
     * @Description: 桩站后台 账号管理-账号详情
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 15:41
     */
    StationUserOut stationUserInfo(@Param("userId")Integer userId, @Param("id")Integer stationId);
}

