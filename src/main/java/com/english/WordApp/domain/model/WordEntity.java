package com.english.WordApp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordEntity {


    public WordEntity(String word, String translatedWord, int understanding) {
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
