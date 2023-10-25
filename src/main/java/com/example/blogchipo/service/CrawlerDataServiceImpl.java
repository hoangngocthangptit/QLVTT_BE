package com.example.blogchipo.service;

import com.example.blogchipo.entity.Users;
import com.example.blogchipo.entityMX.NameData;
import com.example.blogchipo.repository.CrawlerDataRepository;
import com.example.blogchipo.entityMX.CrawlerData;
import com.example.blogchipo.entityMX.Info;
import com.example.blogchipo.repository.UsersRepository;
import com.example.blogchipo.until.JwtGenerator;
import com.example.blogchipo.until.UserException;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import jakarta.transaction.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class CrawlerDataServiceImpl implements CrawlerDataService{
    @Autowired
    CrawlerDataRepository courseRepository;
    @Autowired
    private JwtGenerator generate;
    @Autowired
    private UsersRepository usersRepository;

    public void createCourse(CrawlerData course) {
        courseRepository.save(course);
    }

    public List<CrawlerData> getCourse(String token) {
        if(token==null){
           return null;
        }
        Long id = generate.parseJWT(token);
        Users userInfo = usersRepository.findById(id).get();
        if (userInfo==null){
            return null;
        }
        return (List<CrawlerData>) courseRepository.findAll();
    }

    public Optional<CrawlerData> findById(long id) {
        return courseRepository.findById(id);

    }

    public CrawlerData update(CrawlerData course, long l) {
        return courseRepository.save(course);
    }

    public void deleteCourseById(long id) {
        courseRepository.deleteById(id);
    }

    public CrawlerData updatePartially(CrawlerData user, long id) {
        Optional<CrawlerData> usr = findById(id);
        CrawlerData newuser=usr.get();
        return courseRepository.save(newuser);
    }

    @Override
    public void crawlAndSaveData() {
        try {
            org.jsoup.nodes.Document document = Jsoup.connect("https://detail.1688.com/offer/660518309175.html")
                    .header("Cookie", "cna=wgE1HX2/uiECAQ6x78sfnQ6S; xlly_s=1; taklid=dbfef9401a2d4ab58bd0396c96b0a0d9; _bl_uid=mLlIvjInzsj4Lsc6Lqy6ta6cdw1I; _csrf_token=1689214767348; __cn_logon__=false; cookie2=118f5747cb87d4d20f41061cd886bf36; t=2842e52aa785fc3e9a0dd51775e1aa51; _tb_token_=b7356739d315; googtrans=/zh-CN/vi; googtrans=/zh-CN/vi; JSESSIONID=80A215E391BA6C60DD4F09AE4D8B4E30; _m_h5_tk=c8d7f03f7620874460e384bb22bd35de_1689236764321; _m_h5_tk_enc=7f8291a60a2b6b658378b756f166a27f; l=fBrC9eMRNk-0ppWDBO5CFurza77OoIRb4sPzaNbMiIEGa6tCqe_g-NC1xVHJndtjQT1bfetrip0J_dLHR3AiivG0J9P4VKpInxf1lJ2f.; tfstk=cnY5BFb0d82SiJg3rTG2cQ05a16PZRkfi06JNA3XC3_0mMd5iXUNCXLvx-bF9s1..; isg=BNTUqJ2kTCh92NhlDWYOXkjIpRJGLfgXRI2v8m614N_EWXSjlj53p4rTXUlBoTBv")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36").get();
            Element titleElement = document.select("title").first();

                // Lấy nội dung của thẻ <title>
            String title = titleElement.text();
            String data = document.toString();
            // Tìm vị trí bắt đầu và kết thúc của đoạn chuỗi
            int startIndex1 = data.indexOf("window.__GLOBAL_DADA=");
            int endIndex1 = data.indexOf("pkg-rax-proptypes/index-es6\",\"@ali/rax-pkg-universal-env/index-es6\",\"@ali/rax-pkg-rax-image/index-es6\"]}", startIndex1);
            String extractedSegment1 = data.substring(startIndex1, endIndex1);
            System.out.println(extractedSegment1);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public CrawlerData test(String path) {
        CrawlerData crawlerData = new CrawlerData();
        try {
            //            String path="https://detail.1688.com/offer/660518309175.html";
            org.jsoup.nodes.Document document = Jsoup.connect(path)
                    .header("Cookie", "cna=wgE1HX2/uiECAQ6x78sfnQ6S; xlly_s=1; taklid=dbfef9401a2d4ab58bd0396c96b0a0d9; _bl_uid=mLlIvjInzsj4Lsc6Lqy6ta6cdw1I; _csrf_token=1689214767348; __cn_logon__=false; cookie2=118f5747cb87d4d20f41061cd886bf36; t=2842e52aa785fc3e9a0dd51775e1aa51; _tb_token_=b7356739d315; googtrans=/zh-CN/vi; googtrans=/zh-CN/vi; JSESSIONID=80A215E391BA6C60DD4F09AE4D8B4E30; _m_h5_tk=c8d7f03f7620874460e384bb22bd35de_1689236764321; _m_h5_tk_enc=7f8291a60a2b6b658378b756f166a27f; l=fBrC9eMRNk-0ppWDBO5CFurza77OoIRb4sPzaNbMiIEGa6tCqe_g-NC1xVHJndtjQT1bfetrip0J_dLHR3AiivG0J9P4VKpInxf1lJ2f.; tfstk=cnY5BFb0d82SiJg3rTG2cQ05a16PZRkfi06JNA3XC3_0mMd5iXUNCXLvx-bF9s1..; isg=BNTUqJ2kTCh92NhlDWYOXkjIpRJGLfgXRI2v8m614N_EWXSjlj53p4rTXUlBoTBv")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36").get();
            Element titleElement = document.select("title").first();

            // Lấy nội dung của thẻ <title>
            String title = titleElement.text();
            String data = document.toString();
            //            System.out.println(data);
            // Tìm vị trí bắt đầu và kết thúc của đoạn chuỗi
            String start = "}; window.__INIT_DATA={\"";
            //  int startIndex1 = data.indexOf("{\"data\":{\"1081181309884\":{\"componentType\":\"@ali/tdmod-pc-od-dsc-");
            int startIndex1 = data.indexOf(start);
            int endIndex1 = data.indexOf("  </script>\n" +
                    "  <script>\n" +
                    "\tvar __supportsES6 = false;\n", startIndex1 + start.length());
            String extractedSegment1 = data.substring(startIndex1 + start.length() - 2, endIndex1);
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(extractedSegment1);
            com.google.gson.JsonObject jsonObject = jsonElement.getAsJsonObject();
            com.google.gson.JsonObject globalDataObject1 = jsonObject.getAsJsonObject("globalData");
            JsonArray imagesArray = globalDataObject1.getAsJsonArray("images");
            List<String> imagesList = new ArrayList<>();
            for (JsonElement jsonElementImg : imagesArray) {
                JsonObject imageObject = jsonElementImg.getAsJsonObject();
                String imageUrl = imageObject.get("fullPathImageURI").getAsString();
                imagesList.add(imageUrl);
            }

            /// name and linkimg detail

            com.google.gson.JsonObject globalDataObject4 = jsonObject.getAsJsonObject("globalData").getAsJsonObject("skuModel");
            JsonArray skuArraysPar = globalDataObject4.getAsJsonArray("skuProps");
            JsonObject firstObject = skuArraysPar.get(0).getAsJsonObject();
            JsonArray skuArraysSon = firstObject.getAsJsonArray("value");

            List<NameData> nameDatas = new ArrayList<>();
            for (JsonElement jsonElementImg : skuArraysSon) {
                NameData nameData = new NameData();
                JsonObject imageObject = jsonElementImg.getAsJsonObject();
//                String imageUrl = String.valueOf(imageObject.get("imageUrl"));
                String name="";
               if(imageObject.get("name")!=null)  {name= imageObject.get("name").getAsString();}
                nameData.setName(name);

                String imageUrl="";
                if(imageObject.get("imageUrl")!=null)  {imageUrl= imageObject.get("imageUrl").getAsString();}
                nameData.setImageUrl(imageUrl);
                nameDatas.add(nameData);
            }
            com.google.gson.JsonObject globalDataObject2 = jsonObject.getAsJsonObject("globalData").getAsJsonObject("orderParamModel").getAsJsonObject("orderParam").getAsJsonObject("skuParam");
            JsonArray priceArray = globalDataObject2.getAsJsonArray("skuRangePrices");
            List<String> priceList = new ArrayList<>();
            for (JsonElement jsonElementImg : priceArray) {
                JsonObject imageObject = jsonElementImg.getAsJsonObject();
                String imageUrl = imageObject.get("price").getAsString();
                priceList.add(imageUrl);
            }

            //info


            Gson gson = new Gson();
            com.google.gson.JsonObject globalDataObject3 = jsonObject.getAsJsonObject("globalData").getAsJsonObject("skuModel").getAsJsonObject("skuInfoMap");
            List<Info> skuInfoList = new ArrayList<>();
            //            for (Map.Entry<String, JsonElement> entry : globalDataObject3.entrySet()) {
            //                String key = entry.getKey();
            //                com.google.gson.JsonObject skuInfoObject = entry.getValue().getAsJsonObject();
            //                Info skuInfo = gson.fromJson(skuInfoObject, Info.class);
            //                skuInfoList.add(skuInfo);
            //            }
            for (Map.Entry<String, JsonElement> entry : globalDataObject3.entrySet()) {
                String key = entry.getKey();
                com.google.gson.JsonObject skuInfoObject = entry.getValue().getAsJsonObject();
                Info skuInfo = new Info();
                skuInfo.setSpecAttrs(String.valueOf(skuInfoObject.get("specAttrs")));
                skuInfo.setPrice(String.valueOf(skuInfoObject.get("price")));
                skuInfo.setCanBookCount(String.valueOf(skuInfoObject.get("canBookCount")));
                skuInfoList.add(skuInfo);
            }
            for (int i = 0; i < nameDatas.size(); i++) {
                List<Info> listInfo = new ArrayList<>();
                for (Info skuInfo : skuInfoList
                ) {

                    if (skuInfo.getSpecAttrs().contains(nameDatas.get(i).getName())) {
                        listInfo.add(skuInfo);
                    }

                }
                nameDatas.get(i).setListInfo(listInfo);
            }
            crawlerData.setPath(path);
            crawlerData.setTitle(title);
            crawlerData.setImages(imagesList);
            crawlerData.setPrice(priceList);
            crawlerData.setListValue(nameDatas);
            courseRepository.save(crawlerData);

        } catch (Exception e) {

            // Xử lý ngoại lệ (nếu cần)
            System.out.println(e);
        }
        return crawlerData;
    }

    @Override
    public CrawlerData findByPath(String path,String token) {
        return courseRepository.findByPath(path);
    }

}
