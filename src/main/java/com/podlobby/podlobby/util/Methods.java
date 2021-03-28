package com.podlobby.podlobby.util;

import java.util.Date;
import java.sql.Timestamp;

public class Methods {

    public static String numberSuffix(int num){
        String s = String.valueOf(num);
        if(num >= 10 && num <= 20) return s+"th";
        char last = s.charAt(s.length() - 1);
        switch (last) {
            case '1':
                return s+"st";
            case '2':
                return s+"nd";
            case '3':
                return s+"rd";
            default:
                return s+"th";
        }
    }

    public String timeFormat(String timeStamp){
        String timeString = timeStamp.substring(0, 16);
        String time = timeString.substring(11, 13);
        int number = Integer.parseInt(time);
        if(number == 12) {
            return (timeString.substring(0, 11) + (number) + timeString.substring(13) + " pm");
        }
        if(number == 0) {
            return (timeString.substring(0, 11) + "12" + timeString.substring(13, 16) + " am");
        }
        if(number > 12) {
            return (timeString.substring(0, 11) + (number % 12) + timeString.substring(13, 16) + " pm");
        }
        return (timeString.substring(0, 11) + (number % 12) + timeString.substring(13, 16) + " am");
    }


    public static void main(String[] args) {
        Methods m = new Methods();
        System.out.println(m.timeFormat((new Timestamp(new Date().getTime())).toString()));
        System.out.println();
        System.out.println(m.timeFormat((new Timestamp(new Date().getTime() + 10000000)).toString()));
        System.out.println();
        System.out.println(m.timeFormat((new Timestamp(new Date().getTime() + 300040404)).toString()));
        System.out.println();
        System.out.println(m.timeFormat((new Timestamp(new Date().getTime() + 200000000)).toString()));



//        System.out.println(Methods.numberSuffix(22));
//        System.out.println(Methods.numberSuffix(61));
//        System.out.println(Methods.numberSuffix(7));
//        System.out.println(Methods.numberSuffix(9));
//        System.out.println(Methods.numberSuffix(55));
//        System.out.println(Methods.numberSuffix(1));
//        System.out.println(Methods.numberSuffix(3));
//        System.out.println(Methods.numberSuffix(2));
//        System.out.println(Methods.numberSuffix(4));
//        System.out.println(Methods.numberSuffix(77));
//        System.out.println(Methods.numberSuffix(24));
//        System.out.println(Methods.numberSuffix(14));
//        System.out.println(Methods.numberSuffix(13));

    }

}
