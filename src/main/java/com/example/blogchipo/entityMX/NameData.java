package com.example.blogchipo.entityMX;

import lombok.Data;

import java.util.List;

@Data
public class NameData {
    private String imageUrl;
    private String name;
    List<Info> listInfo;
}
