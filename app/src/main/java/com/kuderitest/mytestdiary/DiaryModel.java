package com.kuderitest.mytestdiary;

import java.io.Serializable;

/**
 * 다이어리 리스트의 아이템을 구성하는 모델
 * */
//Serializable - 직렬화
public class DiaryModel implements Serializable {
    int id; //계시물의 고유 아이디
    String title; //계시물의 제목
    String content; //계시물의 내용
    int weatherType; //날씨 타입 0: 맑음, 1: 흐림, 2: 비,....
    String userDate;
    String writeDate;

    //getter and settet
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(int weatherType) {
        this.weatherType = weatherType;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}
