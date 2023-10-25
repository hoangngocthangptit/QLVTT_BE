package com.example.blogchipo.entity;

import com.google.common.base.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) throws Exception {
        String tee="https://chaoshi.detail.tmall.com/item.htm?id=638037681344&pvid=81247396-4f17-4267-87ae-aef4cfd7cfbb&scm=1007.12144.323676.73136_0_0&skuId=4579110041788&spm=875.7931836.0.0.66144265ESnNph&utparam={\"x_hestia_source\":\"73136\",\"x_object_type\":\"item\",\"x_hestia_subsource\":\"default\",\"x_mt\":5,\"x_src\":\"73136\",\"x_pos\":7,\"wh_pid\":-1,\"x_pvid\":\"81247396-4f17-4267-87ae-aef4cfd7cfbb\",\"scm\":\"1007.12144.323676.73136_0_0\",\"x_object_id\":638037681344,\"tpp_buckets\":\"2144";
      //  System.out.println(getLink("https://chaoshi.detail.tmall.com/item.htm?id=638037681344&pvid=81247396-4f17-4267-87ae-aef4cfd7cfbb&scm=1007.12144.323676.73136_0_0&skuId=4579110041788&spm=875.7931836.0.0.66144265ESnNph&utparam={\"x_hestia_source\":\"73136\",\"x_object_type\":\"item\",\"x_hestia_subsource\":\"default\",\"x_mt\":5,\"x_src\":\"73136\",\"x_pos\":7,\"wh_pid\":-1,\"x_pvid\":\"81247396-4f17-4267-87ae-aef4cfd7cfbb\",\"scm\":\"1007.12144.323676.73136_0_0\",\"x_object_id\":638037681344,\"tpp_buckets\":\"2144"));
        System.out.println(getLink(tee));
    }

    public static String getLink(String URL) throws Exception {
        URL = URL.replaceAll("%", "");
        URL = URL.replaceAll("&amp;", "&");
        String urlOutput = URL;
        try {

            urlOutput = URLDecoder.decode(URL, "UTF-8");
            Pattern pattern_url = Pattern.compile("(https|http):\\/\\/(www\\.)?[a-z0-9\\.:].*?(\\s+|$)");
            Matcher matcher_url = pattern_url.matcher(urlOutput);
            if (matcher_url.find()) {
                if (!Strings.isNullOrEmpty(matcher_url.group(0))) {
                    URL = matcher_url.group(0);
                }
            }
            if (!Strings.isNullOrEmpty(urlOutput)) {
                URL = URL.replaceAll("\\s", "");
            }
            if (URL.contains("#")) {
                URL = URL.replaceAll("#", "");
            }
//        if (URL.contains("item.taobao.com") || URL.contains("tmall.com")) {
//            return TextUtils.alibabaConventLinkByType(URL, false);
//        }
//        if (URL.contains("m.taobao.com") || URL.contains("m.intl.taobao.com")) {
//            return TextUtils.alibabaConventLinkByType(URL, true);
//        }
//        if (URL.contains("detail.1688.com")) {
//            return TextUtils.alibabaConventLinkByType(URL, true);
//        }
            if (URL.contains("m.1688.com")) {
                String regex = "(offer.?(Id|id)=)(\\d+)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(URL);
                if (matcher.find()) {
                    if (!Strings.isNullOrEmpty(matcher.group(3))) {
                        return "https://m.1688.com/offer/" + matcher.group(3) + ".html";
                    }
                } else {
                    return URL;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return URL;
    }

}
