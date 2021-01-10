package com.newlife.charge.common;


import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发送请求指定url
 * <p>
 * Created by linzq on 2017/10/200013 9:27.
 */
public class ParseHtmlUtil {
    protected static final Logger logger = LoggerFactory.getLogger(ParseHtmlUtil.class);

    /**
     * 获取html代码
     * @param htmlurl
     * @return
     * @throws IOException
     */
    public static String getOneHtml(final String htmlurl)
    {
        URL url;
        String temp;
        final StringBuffer sb = new StringBuffer();
        try
        {
            url = new URL(htmlurl);
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));// 读取网页全部内容
            while ((temp = in.readLine()) != null)
            {
                sb.append(temp);
            }
            in.close();
        }
        catch (final MalformedURLException me)
        {
            System.out.println("你输入的URL格式有问题！请重新输入");
            me.getMessage();
            logger.error("错误信息:"+me.getMessage());
        }
        catch (final IOException e)
        {
            e.printStackTrace();
            logger.error("错误信息:"+e.getMessage());
        }
        return sb.toString();
    }

    /**
     *
     * @param s
     * @return 获得网页标题
     */
    public static String getTitle(final String s)
    {
        String regex;
        String title = "";
        final List<String> list = new ArrayList<String>();
        regex = "<title>.*?</title>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        for (int i = 0; i < list.size(); i++)
        {
            title = title + list.get(i);
        }
        return outTag(title);
    }

    /**
     *
     * @param s
     * @return 获得Header里的标题 目前只针对咪师自己的html页面
     */
    public static String getHeaderTitle(final String s) {
        String title;
        String regex = "<p>.*?</p>";
        title = MatcherByRegex(s,regex);
        if(StringUtil.isEmpty(title) || title=="") {
            regex = "<h1>.*?</h1>";
            title = MatcherByRegex(s,regex);
        }
        return outTag(title);
    }

    private static String MatcherByRegex(final String s,String regex) {
        final List<String> list = new ArrayList<String>();
        String title="";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        for (int i = 0; i < list.size(); i++)
        {
            title = title + list.get(i);
        }
        return title;
    }
    private static final String reg = "src\\s*=\\s*\"?(.*?)(\"|>|\\s+)";
    /**
     * 得到网页中图片的地址
     * @param htmlStr
     */
    public static List<String> getImgStr(String htmlStr) {
        List<String> pics = new ArrayList<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile(reg).matcher(img);
            while (m.find()) {
                if(!pics.contains(m.group(1))) {
                    pics.add(m.group(1));
                }
            }
        }
        return pics;
    }

    public static String getBody(String val) {
        String start = "<body>";
        String end = "</body>";
        int s = val.indexOf(start) + start.length();
        int e = val.indexOf(end);
        return val.substring(s, e);
    }

    public static String getHeader(String val) {
        String start = "<header>";
        String end = "</header>";
        int s = val.indexOf(start) + start.length();
        int e = val.indexOf(end);
        return val.substring(s, e);
    }

    /**
     *
     * @param s
     * @return 去掉标记
     */
    public static String outTag(final String s)
    {
        return s.replaceAll("<.*?>", "");
    }

    /**
     * 获取标题和头图
     * @param linkUrl
     * @return
     */
    public static Map<String, Object> getLinkTitleAndImg(String linkUrl) {
        String linkTitle;
        String linkImg="";
        Map<String, Object> map = Maps.newConcurrentMap();
        String htmlStr = getOneHtml(linkUrl);
        //从html页面header <p>,<h> 标签里获取标题
        String headerHtml = ParseHtmlUtil.getHeader(htmlStr);
        linkTitle = ParseHtmlUtil.getHeaderTitle(headerHtml);
        //获取不到直接拿title作为标题
        if(StringUtil.isEmpty(linkTitle)) {
            linkTitle = ParseHtmlUtil.getTitle(htmlStr);
        }
        List<String> titleImgs = ParseHtmlUtil.getImgStr(htmlStr);
        if(Collections3.isNotEmpty(titleImgs)) {
            linkImg = titleImgs.get(0);
        }
        map.put("linkTitle",linkTitle);
        map.put("linkImg",linkImg);
        map.put("htmlStr",htmlStr);
        return map;
    }
}
