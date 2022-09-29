package com.example.model.dao;

import com.example.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPokemonDao extends JpaRepository<Pokemon,String> {

    Optional<Pokemon> getByName(String name);

    Optional<Pokemon> getByNumber(String code);
}
