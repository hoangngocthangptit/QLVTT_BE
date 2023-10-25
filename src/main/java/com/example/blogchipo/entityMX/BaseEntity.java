package com.example.blogchipo.entityMX;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@SuppressWarnings("serial")
@Document
public abstract class BaseEntity implements Serializable {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
