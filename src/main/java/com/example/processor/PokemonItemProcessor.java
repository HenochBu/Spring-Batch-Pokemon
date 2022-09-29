package com.example.processor;


import lombok.extern.slf4j.Slf4j;
import com.example.model.Pokemon;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PokemonItemProcessor implements ItemProcessor<Pokemon,Pokemon> {

    @Override
    public Pokemon process(Pokemon item) throws Exception {


        Pokemon pokemon = Pokemon.builder()
                                .number(item.getNumber())
                                .name(item.getName().toUpperCase())
                                .type1(item.getType1().toUpperCase())
                                .type2(item.getType2().toUpperCase())
                                .total(item.getTotal().toUpperCase())
                                .hp(item.getHp().toUpperCase())
                                .attack(item.getAttack().toUpperCase())
                                .defense(item.getDefense().toUpperCase())
                                .sp_atk(item.getSp_atk().toUpperCase())
                                .sp_def(item.getSp_def().toUpperCase())
                                .speed(item.getSpeed().toUpperCase())
                                .generation(item.getGeneration().toUpperCase())
                                .legendary(item.getLegendary().toUpperCase())
                                .build();
        log.info("Convirtiendo ("+item+") a ("+pokemon+")");

        return pokemon;
    }
}
