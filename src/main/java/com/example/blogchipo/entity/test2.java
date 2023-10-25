package com.example.blogchipo.entity;

import com.example.blogchipo.entityMX.CrawlerData;
import com.example.blogchipo.entityMX.Info;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.bson.json.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test2 {
    public static void main(String[] args) {
        String text = "https://detail.1688.com/offer/578680908874.html?spm=a260k.dacugeneral.pc_index_cht.6.681635e4sdKK1g&&object_id=578680908874&udsPoolId=3247645";

        String result = cutString(text);
        System.out.println(result);
    }

    public static String cutString(String input) {
        int index = input.indexOf(".html");
        if (index != -1) {
            return input.substring(0, index + 5);
        } else {
            index = input.indexOf("object_id=");
            if (index != -1) {
                return input.substring(0, index - 1);
            }
        }
        return input;
    }
}


