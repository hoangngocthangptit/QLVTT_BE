package com.example.blogchipo.repository;

import com.example.blogchipo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {
    Users findByEmail(String email);
    @Query(value = "select * from NhanVien u where ?1 is null or hoTen like %?1%", nativeQuery = true)
    Page<Users> GetAll(String name, Pageable pageable);
    List<Users> findByHoTen(String name);
}
