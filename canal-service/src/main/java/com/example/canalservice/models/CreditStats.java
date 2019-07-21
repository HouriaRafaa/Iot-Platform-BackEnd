package com.example.canalservice.models;

import java.util.Date;

public class CreditStats {

    private Date date;
    private Long count ;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public CreditStats(Date date, Long count) {
        this.date = date;
        this.count = count;
    }
}
