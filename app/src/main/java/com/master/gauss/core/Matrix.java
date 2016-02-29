package com.master.gauss.core;

import com.master.gauss.core.Fraction;

import java.io.Serializable;

/**
 * Created by Dominika on 21. 9. 2015.
 */
public class Matrix implements Serializable {
    public static final String EXTRA = "com.example.dominika.gauss.extras";

    private int rows;

    private int columns;

    private Fraction[][] matrix;

    private int rankLeft;

    private int rank;

    private int rankSetter;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }


    public void setMatrix(Fraction[][] matrix) {

        this.matrix = matrix;
    }

    public Fraction[][] getMatrix() {
        return matrix;
    }

    public void setRankLeft(int rankLeft) {

        this.rankLeft = rankLeft;
    }

    public int getRankLeft() {

        return rankLeft;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {

        return rank;
    }

    public int getRankSetter() {
        return rankSetter;
    }

    public void setRankSetter(int rankSetter) {
        this.rankSetter = rankSetter;
    }
}
