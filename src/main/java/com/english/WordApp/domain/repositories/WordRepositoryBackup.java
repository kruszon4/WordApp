package com.english.WordApp.domain.repositories;

import com.english.WordApp.domain.model.WordEntity;
import com.english.WordApp.domain.model.WordEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepositoryBackup extends JpaRepository<WordEntityBackup, Long> {






}
