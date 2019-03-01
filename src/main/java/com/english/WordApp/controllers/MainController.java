package com.english.WordApp.controllers;

import com.english.WordApp.model.WordPojo;
import com.english.WordApp.services.WordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;

@Controller
public class MainController {


    private final WordService wordService;


    public MainController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping("/wordCreator")
    private String putWordToDb(WordPojo wordPojo) {
        wordPojo.setAddDate(System.currentTimeMillis());
        wordService.saveWord(wordPojo);
        return "redirect:/wordNew";
    }


    @GetMapping("/word")
    private String getWordFromDb(Model model) {
        model.addAttribute("word", new WordPojo());
        return "word";
    }


    @GetMapping("/wordNew")
    private String putWordToDb(Model model) {
        model.addAttribute("word", new WordPojo());
        return "wordNew";
    }

    @GetMapping("/word2")
    private String putWordFromFile() throws FileNotFoundException {
        wordService.addWordToDbFromFile();
        return "redirect:/index";
    }


    @GetMapping("/random")
    private String getRandomWord(Model model) {
        model.addAttribute("random", wordService.getRandomWord());
        return "random";
    }

    @GetMapping("/words")
    private String getAllWordFromDb(Model model) {
        model.addAttribute("words", wordService.getALLWord());
        return "words";
    }

    @GetMapping("/")
    private String mainPage() {

        return "index";
    }


    @PostMapping("/update")
    private String updateWordInDb(@RequestParam(value = "add") Long addDate) {

        wordService.updateWord(addDate);
        return "redirect:/random";
    }

    @PostMapping("/update2")
    private String updateWordInDb2(@RequestParam(value = "add") Long addDate) {

        wordService.updateWord(addDate);
        return "redirect:/words";
    }

    @PostMapping("/delete/{delete}")
    private String deleteWordFromDb(@PathVariable(value = "delete") Long addDate) {

        wordService.deleteWord(addDate);
        return "redirect:/words";
    }


}
