package com.podlobby.podlobby.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IframeParser {

    public IframeParser(){}

    public String parseIframe(String embed){
        String[] arr = embed.split(" ");
        List <String> list = new ArrayList<>(Arrays.asList(arr));
        int idx = 0;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).startsWith("src")) idx = i;
        }
        if(idx == 0){
            return "noSRC";
        }
        return list.get(idx).substring(5, list.get(idx).length() - 1);
    }

    public static void main(String[] args) {
        IframeParser parser = new IframeParser();
        String anchor1 = "<iframe src=\"https://anchor.fm/matthew-baker3/embed/episodes/Creating-a-side-project-es8rqk\" height=\"102px\" width=\"400px\" frameborder=\"0\" scrolling=\"no\"></iframe>";
        String anchor2 = "<iframe src=\"https://anchor.fm/matthew-baker3/embed/episodes/first-test-for-show-cast-erqp0p\" height=\"102px\" width=\"400px\" frameborder=\"0\" scrolling=\"no\"></iframe>";
        String spotify1 = "<iframe src=\"https://open.spotify.com/embed-podcast/episode/2aB2swgyXqbFA06AxPlFmr\" width=\"100%\" height=\"232\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>";
        String spotify2 = "<iframe src=\"https://open.spotify.com/embed-podcast/episode/7B8zZcrYzcBBiDEyK3SY7P\" width=\"100%\" height=\"232\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>";

        System.out.println(parser.parseIframe(anchor1));
        System.out.println(parser.parseIframe(anchor2));
        System.out.println(parser.parseIframe(spotify1));
        System.out.println(parser.parseIframe(spotify2));
    }

}
