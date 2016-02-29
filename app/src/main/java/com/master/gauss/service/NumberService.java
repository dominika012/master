package com.master.gauss.service;

/**
 * Created by Dominika on 10. 1. 2016.
 */
public class NumberService {


    public int randomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

}
