package com.example.biletykino.Repo;

import com.example.biletykino.Model.Rezerwacja;
import org.springframework.data.repository.CrudRepository;

public interface RezerwacjaRepository extends CrudRepository<Rezerwacja,Integer> {
    Rezerwacja findRezerwacjaById(Integer id);
}
