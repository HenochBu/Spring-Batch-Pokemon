package com.example.model.controller;

import com.example.model.Pokemon;
import com.example.model.dao.IPokemonDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pokedex")
@RequiredArgsConstructor
public class PokemonController{

    @Autowired
    private final IPokemonDao iPokemonDao;

    @GetMapping("/all")
    public List<Pokemon> getPokemons(){
        return iPokemonDao.findAll();
    }

    @GetMapping("/pokemon/numero/{id}")
    public Pokemon findByNumber(@PathVariable("id") String id) {

        Pokemon found = iPokemonDao.getByNumber(id).orElse(null);
        return found;
    }

    @GetMapping("/pokemon/nombre/{name}")
    public Pokemon findByName(@PathVariable("name") String name){

        Pokemon found = iPokemonDao.getByName(name).orElse(null);
        return found;
    }


}
