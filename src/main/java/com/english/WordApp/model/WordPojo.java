package com.english.WordApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordPojo {


    private String word;
    private String translatedWord;
    private int understanding;
    private Date addDate;






}
