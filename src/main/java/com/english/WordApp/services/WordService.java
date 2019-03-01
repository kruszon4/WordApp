package com.english.WordApp.services;

import com.english.WordApp.domain.model.WordEntity;
import com.english.WordApp.domain.repositories.WordRepository;
import com.english.WordApp.model.WordPojo;
import com.sun.javaws.security.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.beans.beancontext.BeanContextSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
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

        if (wordPojo.getWord().length() == 0 || wordPojo.getTranslatedWord().length() == 0) {
            System.out.println("Puste");
        } else {
            wordRepository.save(map(wordPojo));

        }

    }


    public List<WordPojo> getALLWord() {

        return wordRepository.findAll(Sort.by(Sort.Direction.ASC, "addDate")).stream().map(this::map).collect(Collectors.toList());


    }


    public WordPojo getRandomWord() {

        WordPojo randomWordPojo = new WordPojo();

        try {

            randomWordPojo = map(wordRepository.findRandomWord());

        } catch (NullPointerException e) {
            randomWordPojo.setWord("null");
        }

        return randomWordPojo;
    }


    public void updateWord(Long id) {

        WordEntity wordEntity = wordRepository.getOne(id);
        wordEntity.setUnderstanding(1);
        wordRepository.save(wordEntity);
    }

//    public void addWordToDbFromFile(){
//
//        String data = "";
//        ClassPathResource cpr = new ClassPathResource("static/word.txt");
//        try {
//            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
//            data = new String(bdata, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            System.out.println("dfds");
//        }
//
//        String[] split = data.split("\r\n");
//
//        for (String str : split
//                ) {
//            String[] split1 = str.split(",");
//
//            WordPojo build = WordPojo.builder()
//                    .addDate(new Date().getTime())
//                    .word(split1[0].trim())
//                    .translatedWord(split1[1].trim())
//                    .understanding(0)
//                    .build();
//
//            saveWord(build);
//
//
//        }
//
//
//    }


    public WordEntity map(WordPojo source) {

        return WordEntity.builder()
                .word(source.getWord())
                .translatedWord(source.getTranslatedWord())
                .understanding(source.getUnderstanding())
                .addDate(source.getAddDate())
                .build();

    }

    public WordPojo map(WordEntity source) {

        return WordPojo.builder()
                .word(firstLetterUpperCase(source.getWord()))
                .translatedWord(firstLetterUpperCase(source.getTranslatedWord()))
                .understanding(source.getUnderstanding())
                .addDate(source.getAddDate())
                .build();

    }


    public void deleteWord(Long id) {

        WordEntity wordEntity = wordRepository.getOne(id);
        wordRepository.delete(wordEntity);
    }

    private String firstLetterUpperCase(String source) {


        return source.substring(0, 1).toUpperCase() + source.substring(1);


    }


}
