package com.english.WordApp.domain.repositories;

import com.english.WordApp.domain.model.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {


    @Query(value = "SELECT * FROM word_entity WHERE understanding = 0 ORDER BY RANDOM() LIMIT 1",
            nativeQuery = true)
    public WordEntity findRandomWord();


}
