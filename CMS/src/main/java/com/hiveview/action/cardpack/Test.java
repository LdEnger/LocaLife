package main.java.com.hiveview.action.cardpack;

import com.hiveview.util.DateUtil;

import java.util.Date;

/**
 * Created by wsw on 2017/7/6.
 */
public class Test {
    public static void main(String[] args){

        Date d =new Date();
        String ss= DateUtil.dateToMin(d,"yyyy-MM-dd HH:mm:ss");

        System.out.println(ss+" smg");


    }
}
