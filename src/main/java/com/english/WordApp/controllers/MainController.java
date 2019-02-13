package com.english.WordApp.controllers;

import com.english.WordApp.model.WordPojo;
import com.english.WordApp.services.WordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {



    private final WordService wordService;



    public MainController(WordService wordService) {
        this.wordService = wordService;
    }


    @PostMapping("/wordCreator")
    private String putWordToDb(WordPojo wordPojo) {

        wordService.saveWord(wordPojo);

        return "index";
    }

    @GetMapping("/word")
    private String getWordFromDb(WordPojo wordPojo) {


        List<WordPojo> allWord = wordService.getALLWord();


        return "index";
    }


}
