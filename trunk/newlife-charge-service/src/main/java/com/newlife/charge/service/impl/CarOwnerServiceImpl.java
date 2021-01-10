/**
 * Author: zhengyou
 * Date:   2018/12/11 16:03
 * Descripition:
 */
package com.newlife.charge.service.impl;

import com.newlife.charge.core.domain.UserWeixin;
import com.newlife.charge.core.dto.in.CarOwnerUpdateIn;
import com.newlife.charge.core.dto.out.CarOwnerOut;
import com.newlife.charge.dao.NoticeMapper;
import com.newlife.charge.dao.UserMapper;
import com.newlife.charge.dao.UserWeixinMapper;
import com.newlife.charge.service.CarOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CarOwnerServiceImpl implements CarOwnerService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserWeixinMapper userWeixinMapper;

    @Override
    public CarOwnerOut info(Integer userId, Integer userType) {

        CarOwnerOut result = userMapper.carOwnerInfo(userId,userType);

        //查询未读站内信条数
        int count = this.noticeMapper.getNewCount(userId);

        if(result != null){
            result.setNoticeCount(count);
            UserWeixin weixinUser = userWeixinMapper.getByUserId(userId);
            if(weixinUser != null){
                result.setOpenid(weixinUser.getOpenId());
            }
        }

        return result;
    }

    @Override
    public int updateCarOwner(CarOwnerUpdateIn updateIn, Integer userId) {

        int result = userMapper.updateCarOwner(updateIn,userId);
        return result;
    }

    @Override
    public int updateGender(Integer gender, Integer userId) {
        int result = userMapper.updateGender(gender,userId);
        return result;
    }

    @Override
    public int updateAvatarUrl(String avatarUrl, Integer userId) {
        int result = userMapper.updateAvatarUrl(avatarUrl,userId);
        return result;
    }

    @Override
    public int updateNickName(String nickName, Integer userId) {
        int result = userMapper.updateNickName(nickName,userId);
        return result;
    }
}
