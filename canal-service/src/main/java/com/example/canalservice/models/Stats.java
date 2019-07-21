package com.example.canalservice.models;

public class Stats {

    private String date;
    private Long count ;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Stats(String date, Long count) {
        this.date = date;
        this.count = count;
    }

}
