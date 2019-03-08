package com.english.WordApp.services;

import com.english.WordApp.domain.model.WordEntity;
import com.english.WordApp.domain.model.WordEntityBackup;
import com.english.WordApp.domain.repositories.WordRepository;
import com.english.WordApp.domain.repositories.WordRepositoryBackup;
import com.english.WordApp.model.WordPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService {
    private final WordRepository wordRepository;
    private final WordRepositoryBackup wordRepositoryBackup;


    @Lazy
    @Autowired
    public WordService(WordRepository wordRepository, WordRepositoryBackup wordRepositoryBackup) {
        this.wordRepository = wordRepository;
        this.wordRepositoryBackup = wordRepositoryBackup;
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


    public void deleteAllDatabase() {
        wordRepository.deleteAll();
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

    public void addWordToDbFromFile() {

        String data = "";
        ClassPathResource cpr = new ClassPathResource("static/word.txt");
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {

        }

        String[] split = data.split("\r\n");

        for (String str : split
                ) {
            String[] split1 = str.split(",");

            WordPojo build = WordPojo.builder()
                    .addDate(new Date().getTime())
                    .word(split1[0].trim())
                    .translatedWord(split1[1].trim())
                    .understanding(0)
                    .build();

            saveWord(build);


        }


    }


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


    public WordEntityBackup mapEntity(WordEntity source) {

        return WordEntityBackup.builder()
                .addDate(source.getAddDate())
                .translatedWord(source.getTranslatedWord())
                .understanding(0)
                .word(source.getWord())
                .build();

    }


    public WordEntity mapEntity(WordEntityBackup source) {

        return WordEntity.builder()
                .addDate(source.getAddDate())
                .translatedWord(source.getTranslatedWord())
                .understanding(source.getUnderstanding())
                .word(source.getWord())
                .build();

    }


    public void deleteWord(Long id) {

        WordEntity wordEntity = wordRepository.getOne(id);
        wordRepository.delete(wordEntity);
    }


    public void makeBackup() {

        List<WordEntityBackup> collect = wordRepository.findAll().stream().map(this::mapEntity).collect(Collectors.toList());

        List<WordEntityBackup> allBackup = wordRepositoryBackup.findAll();

        allBackup.addAll(collect);

        wordRepositoryBackup.saveAll(collect);
    }


    private String firstLetterUpperCase(String source) {


        return source.substring(0, 1).toUpperCase() + source.substring(1);


    }

    public int[] understanding() {
        int[] ints = new int[2];
        ints[0] = wordRepository.countByUnderstanding(1);
        ints[1] = wordRepository.countByUnderstanding(0);


        return ints;


    }

    public void restoreWord() {


        List<WordEntityBackup> allBackup = wordRepositoryBackup.findAll();

        wordRepository.saveAll(allBackup.stream().map(this::mapEntity).collect(Collectors.toList()));


    }


}
