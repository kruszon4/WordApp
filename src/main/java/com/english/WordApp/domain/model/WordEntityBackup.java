package com.english.WordApp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordEntityBackup {


    public WordEntityBackup(String word, String translatedWord, int understanding) {
        this.word = word;
        this.translatedWord = translatedWord;
        this.understanding = understanding;
    }

    @Id
    private Long addDate;

    private String word;
    private String translatedWord;
    private int understanding;
}
