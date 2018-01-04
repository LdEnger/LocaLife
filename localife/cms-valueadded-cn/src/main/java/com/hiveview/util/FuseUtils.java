package com.hiveview.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Antony
 * Date: 16-9-27
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
public class FuseUtils {
    public static String key = "c837n71xak92d8casdhq2e0423dasda3";

    public static String getSign(Map<String, String> paramMap) {
        String params = buildQuery(paramMap);
        String data = params + "&key=" + key;
        String sign = DigestUtils.md5Hex(data);
        return sign;
    }
    public static String buildQuery(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        StringBuffer query = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key);
            if (value == null || value.length() == 0) {
                continue;
            }
            query.append("&").append(key);
            query.append("=").append(value);
        }
        return query.substring(1);
    }
}
