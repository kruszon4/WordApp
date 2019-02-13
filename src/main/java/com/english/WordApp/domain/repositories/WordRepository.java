package com.english.WordApp.domain.repositories;

import com.english.WordApp.domain.model.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {



}
