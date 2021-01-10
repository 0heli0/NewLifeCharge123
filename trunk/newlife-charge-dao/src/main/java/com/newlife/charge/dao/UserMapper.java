/* -------------------------------------------
 * UserMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-23 Created
 * ------------------------------------------- */


package com.newlife.charge.dao;

import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.User;
import com.newlife.charge.core.domain.exModel.UserEx;
import com.newlife.charge.core.dto.in.CarOwnerUpdateIn;
import com.newlife.charge.core.dto.in.StationUserPageQuery;
import com.newlife.charge.core.dto.out.CarOwnerOut;
import com.newlife.charge.core.dto.out.GeneralCompanyUserPageOut;
import com.newlife.charge.core.dto.out.GeneralUserOut;
import com.newlife.charge.core.dto.out.StationUserOut;
import com.newlife.charge.dao.common.CrudRepository;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends CrudRepository<User> {



    /**
     * 查询桩站主账号
     *
     * @param stationId 桩站ID
     * @return
     */
    User getStationMainUser(@Param("stationId") Integer stationId);


    /**
     * 用户分页 或 列表查询
     *
     * @param userType 用户类型
     * @param mobile   手机号码
     * @param nickName 用户昵称
     * @return
     */
    Page<UserEx> page(@Param("userType") Integer userType, @Param("mobile") String mobile, @Param("nickName") String nickName);


    /**
     * 总后台账号 分页 或 列表查询
     *
     * @return
     */
    Page<GeneralUserOut> generalPage(@Param("userType") Integer userType, @Param("nickName") String nickName);


    /**
     * 总后台 桩站注册账号分页查询
     *
     * @param companyName 公司名称
     * @param mobile      账号（手机号）
     * @param managerName 管理员姓名
     * @param auditStatus 审核状态(0审核中 1审核通过 2审核不通过，空表示全部)
     * @return
     */
    Page<GeneralCompanyUserPageOut> generalCompanyUserPageList(
            @Param("companyName") String companyName, @Param("mobile") String mobile,
            @Param("managerName") String managerName, @Param("auditStatus") Integer auditStatus);


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
    List<UserEx> loginByMobile(@Param("mobile") String mobile);


    /**
     * 通过手机号查找
     *
     * @param mobile
     * @return
     */
    List<User> getByMobile(@Param("mobile") String mobile);


    /**
     * 通过手机号和类型 查找
     *
     * @param mobile
     * @param userType
     * @return
     */
    User getByMobileAndUserType(@Param("mobile") String mobile, @Param("userType") Integer userType);

    /**
     * @Description: 通过手机号和类型 查找
     * @Author: Linzq
     * @CreateDate:  2019/5/5 0005 15:29
     */
    List<UserEx> loginByMobileAndUserType(@Param("mobile") String mobile, @Param("userTypes") Integer[] userTypes);
    /**
     * 通过主键查找用户信息
     * @param id
     * @return
     */
    CarOwnerOut carOwnerInfo(@Param("id")Integer id, @Param("userType")Integer userType);

    /**
     * 更新用户信息
     * @param updateIn
     * @param id
     * @return
     */
    int updateCarOwner(@Param("updateIn")CarOwnerUpdateIn updateIn, @Param("id")Integer id);

    /**
     * 性别更新
     * @param gender
     * @param id
     * @return
     */
    int updateGender(@Param("gender")Integer gender, @Param("id")Integer id);

    /**
     * 头像更新
     * @param avatarUrl
     * @param id
     * @return
     */
    int updateAvatarUrl(@Param("avatarUrl")String avatarUrl, @Param("id")Integer id);

    /**
     * 昵称更新
     * @param nickName
     * @param id
     * @return
     */
    int updateNickName(@Param("nickName")String nickName, @Param("id")Integer id);

    /**
     * 桩站后台 账号管理分页查询
     *
     * @return
     */
    Page<StationUserOut> stationUserPage(StationUserPageQuery query);

    /**
     * 桩站后台 条件查询子账号
     *
     * @return
     */
    List<UserEx> getStationUserByQuery(StationUserPageQuery query);

    /**
     * @Description: 桩站后台 账号管理-账号详情
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 15:41
     */
    StationUserOut stationUserInfo(@Param("userId")Integer userId, @Param("stationId")Integer stationId);

    /**
     * @Description: 删除单条
     * @Author: Linzq
     * @CreateDate:  2019/5/30 0030 11:50
     */
    void delete(@Param("id")Integer id);
}