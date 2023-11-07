package com.example.blogchipo.repository;

import com.example.blogchipo.entity.KhoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface KhoRepository  extends CrudRepository<KhoEntity, String> {
    Optional<KhoEntity> findByMaCN(String maCN);
}
