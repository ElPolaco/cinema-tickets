package com.example.biletykino.Repo;

import com.example.biletykino.Model.Seans;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeansRepository extends CrudRepository<Seans,Integer> {
    Seans findSeansById(Integer id);
    List<Seans> findAllByOrderByDataAsc();
}
