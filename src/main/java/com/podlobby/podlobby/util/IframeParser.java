package com.podlobby.podlobby.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IframeParser {

    public static String parseIframe(String embed){
        List <String> list = new ArrayList<>(Arrays.asList(embed.split(" ")));
        int idx = -1;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).startsWith("src")) idx = i;
        }
        if(idx == -1 || list.get(idx).length() < 7) {
            return "noSRC";
        }

        String source = list.get(idx);
        return source.substring(5, source.length() - 1);
    }

    public static void main(String[] args) {
        String anchor1 = "<iframe src=\"https://anchor.fm/matthew-baker3/embed/episodes/Creating-a-side-project-es8rqk\" height=\"102px\" width=\"400px\" frameborder=\"0\" scrolling=\"no\"></iframe>";
        String anchor2 = "<iframe src=\"https://anchor.fm/matthew-baker3/embed/episodes/first-test-for-show-cast-erqp0p\" height=\"102px\" width=\"400px\" frameborder=\"0\" scrolling=\"no\"></iframe>";
        String spotify1 = "<iframe src=\"https://open.spotify.com/embed-podcast/episode/2aB2swgyXqbFA06AxPlFmr\" width=\"100%\" height=\"232\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>";
        String spotify2 = "<iframe src=\"https://open.spotify.com/embed-podcast/episode/7B8zZcrYzcBBiDEyK3SY7P\" width=\"100%\" height=\"232\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>";

        System.out.println(IframeParser.parseIframe(anchor1));
        System.out.println(IframeParser.parseIframe(anchor2));
        System.out.println(IframeParser.parseIframe(spotify1));
        System.out.println(IframeParser.parseIframe(spotify2));

    }

}
