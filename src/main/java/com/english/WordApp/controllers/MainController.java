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
        wordService.makeBackup();
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
    private String putWordFromFile() {
        wordService.deleteAllDatabase();
        wordService.addWordToDbFromFile();
        return "redirect:/words";
    }


    @GetMapping("/backup")
    private String backup() {
        wordService.makeBackup();
        return "redirect:/words";
    }


    @GetMapping("/random")
    private String getRandomWord(Model model) {
        model.addAttribute("understanding", wordService.understanding());
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


    @GetMapping("/deleteAll")
    private String deleteAllWord() {
        wordService.deleteAllDatabase();
        return "redirect:/words";
    }


    @GetMapping("/restore")
    private String restoreWordFromBackup() {
        wordService.restoreWord();
        return "redirect:/words";
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
