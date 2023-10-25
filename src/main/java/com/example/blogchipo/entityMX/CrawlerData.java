package com.example.blogchipo.entityMX;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "crawl")
public class CrawlerData {
    @Id
    private String id;
    private String title;
    private List<String> price;
    private String path;
    private List<String> images;
    private List<NameData>listValue;
}
