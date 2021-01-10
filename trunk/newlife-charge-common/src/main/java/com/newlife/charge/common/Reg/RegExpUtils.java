package com.newlife.charge.common.Reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证手机号
 *
 * Created by lincz on 2017/10/13 0013 10:12.
 */
public class RegExpUtils {

    public enum CHECK_TYPE {
        MOBILE("^[1][3,4,5,7,8,9][0-9]{9}$");
        private final String regexp;

        CHECK_TYPE(String regexp) {
            this.regexp = regexp;
        }

        public String regexp() {
            return this.regexp;
        }
    }

    public static boolean isMobile(String str) {
        return is(CHECK_TYPE.MOBILE, str);
    }

    public static boolean is(CHECK_TYPE checkType, String src) {
        Pattern pattern = Pattern.compile(checkType.regexp);
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

}
