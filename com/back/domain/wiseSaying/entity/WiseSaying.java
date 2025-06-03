package com.back.domain.wiseSaying.entity;

public class WiseSaying {
    private int id;
    private String wiseSay;
    private String writer;

    public WiseSaying() {

    }

    public WiseSaying(int id, String wiseSay, String writer) {
        this.id = id;
        this.wiseSay = wiseSay;
        this.writer = writer;
    }

    public WiseSaying(String wiseSay, String writer) {
        this.wiseSay = wiseSay;
        this.writer = writer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWiseSay() {
        return wiseSay;
    }

    public String getWriter() {
        return writer;
    }
}
