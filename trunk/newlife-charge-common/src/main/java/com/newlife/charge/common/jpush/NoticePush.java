package com.newlife.charge.common.jpush;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 消息透传实体类
 * Created by linzq on 2017/11/21 0029.
 */
public class NoticePush {

    private String title;

    private String content;

    private List<String> userIds = Lists.newArrayList();

    private String responseUrl;

    private Map<String, String> extraMap = Maps.newConcurrentMap();

    public Map<String, String> getExtraMap() {
        return extraMap;
    }

    public void setExtraMap(Map<String, String> extraMap) {
        this.extraMap = extraMap;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
