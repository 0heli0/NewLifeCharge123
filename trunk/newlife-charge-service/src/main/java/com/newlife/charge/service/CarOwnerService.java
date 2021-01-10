package com.newlife.charge.service;


import com.newlife.charge.core.dto.in.CarOwnerUpdateIn;
import com.newlife.charge.core.dto.out.CarOwnerOut;

/**
 * 车主用户 Service 类
 * <p>
 */
public interface CarOwnerService {

    /**
     * 获取车主信息
     * @param userId
     * @param userType
     * @return
     */
    CarOwnerOut info(Integer userId, Integer userType);

    /**
     * 修改用户信息
     * @param updateIn
     * @return
     */
    int updateCarOwner(CarOwnerUpdateIn updateIn, Integer userId);

    /**
     * 修改性别
     * @param gender
     * @return
     */
    int updateGender(Integer gender, Integer userId);

    /**
     * 修改头像
     * @param avatarUrl
     * @return
     */
    int updateAvatarUrl(String avatarUrl, Integer userId);

    /**
     * 修改昵称
     * @param nickName
     * @return
     */
    int updateNickName(String nickName, Integer userId);

}

