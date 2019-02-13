package com.english.WordApp.services;

import com.english.WordApp.domain.model.WordEntity;
import com.english.WordApp.domain.repositories.WordRepository;
import com.english.WordApp.model.WordPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService {


    private final WordRepository wordRepository;


    @Lazy
    @Autowired
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }


    public void saveWord(WordPojo wordPojo) {

        wordRepository.save(map(wordPojo));


    }


    public List<WordPojo> getALLWord() {

        return wordRepository.findAll().stream().map(this::map).collect(Collectors.toList());


    }


    public WordEntity map(WordPojo source) {

        return WordEntity.builder()
                .word(source.getWord())
                .translatedWord(source.getTranslatedWord())
                .understanding(source.getUnderstanding())
                .build();

    }

    public WordPojo map(WordEntity source) {

        return WordPojo.builder()
                .word(source.getWord())
                .translatedWord(source.getTranslatedWord())
                .understanding(source.getUnderstanding())
                .addDate(source.getAddDate())
                .build();

    }


}
