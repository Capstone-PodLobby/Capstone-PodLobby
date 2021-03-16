package com.podlobby.podlobby.util;

public class Methods {

    public static String numberSuffix(int num){
        String s = String.valueOf(num);
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

    public static void main(String[] args) {
        System.out.println(Methods.numberSuffix(22));
        System.out.println(Methods.numberSuffix(61));
        System.out.println(Methods.numberSuffix(7));
        System.out.println(Methods.numberSuffix(9));
        System.out.println(Methods.numberSuffix(55));
        System.out.println(Methods.numberSuffix(1));
        System.out.println(Methods.numberSuffix(3));
        System.out.println(Methods.numberSuffix(2));
        System.out.println(Methods.numberSuffix(4));
        System.out.println(Methods.numberSuffix(77));
        System.out.println(Methods.numberSuffix(24));
        System.out.println(Methods.numberSuffix(14));
        System.out.println(Methods.numberSuffix(13));
    }

}
