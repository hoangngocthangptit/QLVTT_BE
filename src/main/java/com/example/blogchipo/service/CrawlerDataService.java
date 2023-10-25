package com.example.blogchipo.service;

import com.example.blogchipo.entityMX.CrawlerData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CrawlerDataService {
    public void createCourse(CrawlerData course);
    public List<CrawlerData> getCourse(String token);
    public Optional<CrawlerData> findById(long id);
    public CrawlerData update(CrawlerData course, long l);
    public void deleteCourseById(long id);
    public CrawlerData updatePartially(CrawlerData course, long id);
    void crawlAndSaveData();
    CrawlerData test(String path);
    CrawlerData findByPath(String path,String token);
}
