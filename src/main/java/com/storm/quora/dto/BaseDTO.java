package com.storm.quora.dto;

import java.io.Serializable;

public class BaseDTO implements Serializable {
    protected  String keySet;

    public String getKeySet() {
        return keySet;
    }

    public void setKeySet(String keySet) {
        this.keySet = keySet;
    }
}
