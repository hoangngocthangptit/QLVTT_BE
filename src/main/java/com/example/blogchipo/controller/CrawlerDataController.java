package com.example.blogchipo.controller;

import com.example.blogchipo.entity.Users;
import com.example.blogchipo.repository.UsersRepository;
import com.example.blogchipo.response.Response;
import com.example.blogchipo.service.CrawlerDataService;
import com.example.blogchipo.service.UserService;
import com.example.blogchipo.entityMX.CrawlerData;
import com.example.blogchipo.until.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping(value = {
        "/datacrawler"
})
public class CrawlerDataController {
    @Autowired
    private CrawlerDataService courseService;
    @Autowired
    private JwtGenerator generate;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserService userServices;
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrawlerData> getcourseById(@PathVariable("id") long id) {
        System.out.println("Fetching course with id " + id);
        Optional<CrawlerData> optcourse = courseService.findById(id);
        CrawlerData course = optcourse.get();
        if (course == null) {
            return new ResponseEntity<CrawlerData> (HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CrawlerData> (course, HttpStatus.OK);
    }
    @PostMapping(value = "/create", headers = "Accept=application/json")
    public ResponseEntity<Void> createcourse(@RequestBody CrawlerData course, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating course " + course.getTitle());
        courseService.createCourse(course);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/course/{id}").buildAndExpand(course.getId()).toUri());
        return new ResponseEntity<Void> (headers, HttpStatus.CREATED);
    }
    @GetMapping(value = "", headers = "Accept=application/json")
    public ResponseEntity<Response> getAllcourse(@RequestHeader(required = false) String token) {
        List<CrawlerData> tasks = courseService.getCourse(token);
        if(tasks==null){
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Chưa đăng nhập",6996,tasks));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Response("ok",200,tasks));
    }
    @GetMapping(value = "/detail", headers = "Accept=application/json")
    public Response getAllcourseDetail(String path, @RequestHeader(required = false) String token) {
        if(token==null){
            return new Response("chưa đăng nhập" ,6996, new CrawlerData());
        }
        Long id = generate.parseJWT(token);
        Users userInfo = usersRepository.findById(id).get();
        if(userInfo==null){
            return new Response("chưa đăng nhập" ,6996, new CrawlerData());
        }
        String result = cutString(path);
        CrawlerData crawlerData=courseService.findByPath(result,token);
        return new Response("ok" ,200, crawlerData);
    }
    @DeleteMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<CrawlerData> deletecourse(@PathVariable("id") long id) {
        Optional<CrawlerData> optcourse = courseService.findById(id);
        CrawlerData course = optcourse.get();
        if (course == null) {
            return new ResponseEntity<CrawlerData> (HttpStatus.NOT_FOUND);
        }
        courseService.deleteCourseById(id);
        return new ResponseEntity<CrawlerData> (HttpStatus.NO_CONTENT);
    }
    @PatchMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<CrawlerData> updatecoursePartially(@PathVariable("id") long id, @RequestBody CrawlerData currentcourse) {
        Optional<CrawlerData> optcourse = courseService.findById(id);
        CrawlerData course = optcourse.get();
        if (course == null) {
            return new ResponseEntity<CrawlerData> (HttpStatus.NOT_FOUND);
        }
        CrawlerData usr = courseService.updatePartially(currentcourse, id);
        return new ResponseEntity<CrawlerData> (usr, HttpStatus.OK);
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
    @PostMapping("")
    public Response crawlingData(@RequestParam String path,@RequestHeader(required = false) String token) {
        String result = cutString(path);
        if(token==null){
            return new Response("chưa đăng nhập" ,6996, new CrawlerData());
        }
        Long id = generate.parseJWT(token);
        Users userInfo = usersRepository.findById(id).get();
        if(userInfo==null){
            return new Response("chưa đăng nhập" ,6996, new CrawlerData());
        }
       CrawlerData crawlerData=courseService.findByPath(result,token);
        if(crawlerData==null) {
            crawlerData=courseService.test(result);
        }
        return new Response("ok" ,200, crawlerData);
    }
}
