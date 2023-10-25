package com.example.blogchipo.repository;

import com.example.blogchipo.entityMX.CrawlerData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrawlerDataRepository extends MongoRepository<CrawlerData, Long> {
    CrawlerData findByPath(String path);

}
