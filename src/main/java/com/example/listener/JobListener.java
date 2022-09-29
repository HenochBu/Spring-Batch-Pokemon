package com.example.listener;

import lombok.extern.slf4j.Slf4j;
import com.example.model.Pokemon;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JobListener extends JobExecutionListenerSupport {

    private JdbcTemplate jdbcTemplate;

    @Autowired
     public JobListener(JdbcTemplate jdbcTemplate){
         super();
         this.jdbcTemplate = jdbcTemplate;
     }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("Finalizo el Job!!Verifica los resultados: ");

            jdbcTemplate.query("SELECT number,name,type1,type2,total,hp,attack,defense,sp_atk,sp_def,speed,generation,legendary FROM pokemon",
                    (rs,row)->new Pokemon(rs.getString(1), rs.getNString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5),rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9),rs.getString(10),
                            rs.getString(11), rs.getString(12),rs.getString(13)))
                    .forEach(pokemon -> log.info("Registro < "+pokemon+" >"));
        }
    }
}
