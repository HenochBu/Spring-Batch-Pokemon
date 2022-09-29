package com.example.springbatchpokemon;

import com.example.listener.JobListener;
import com.example.model.Pokemon;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.example.processor.PokemonItemProcessor;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public FlatFileItemReader<Pokemon> reader(){

        return new FlatFileItemReaderBuilder<Pokemon>()
                .name("pokemonItemReader")
                .resource(new ClassPathResource("pokemon.csv"))
                .delimited()
                .names(new String[]{"number","name","type1","type2","total","hp","attack","defense","sp_atk","sp_def","speed","generation","legendary"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>(){{
                    setDistanceLimit(1);
                    setTargetType(Pokemon.class);
                }})
                .build();
    }

    @Bean
    public PokemonItemProcessor processor(){

        return new PokemonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Pokemon> writer(DataSource dataSource){

        return new JdbcBatchItemWriterBuilder<Pokemon>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO pokemon (number,name,type1,type2,total,hp,attack,defense,sp_atk,sp_def,speed,generation,legendary) VALUES (:number,:name,:type1,:type2,:total,:hp,:attack,:defense,:sp_atk,:sp_def,:speed,:generation,:legendary)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importPokemonJob(JobListener listener, Step step1){

        return jobBuilderFactory.get("importPokemonJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();

    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Pokemon> writer){

        return stepBuilderFactory.get("step1")
                .<Pokemon, Pokemon> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
