package com.example.biletykino.Repo;

import com.example.biletykino.Model.SalaKinowa;
import org.springframework.data.repository.CrudRepository;

public interface SalaKinowaRepository extends CrudRepository<SalaKinowa,Integer> {
    SalaKinowa findSalaKinowaById(Integer id);
}
