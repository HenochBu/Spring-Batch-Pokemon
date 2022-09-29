package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Pokemon {
    @Id
    private String number;
    private String name;
    private String type1;
    private String type2;
    private String total;
    private String hp;
    private String attack;
    private String defense;
    private String sp_atk;
    private String sp_def;
    private String speed;
    private String generation;
    private String legendary;
}
