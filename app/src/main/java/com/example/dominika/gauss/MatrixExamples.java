package com.example.dominika.gauss;

import android.widget.EditText;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dominika on 17. 11. 2015.
 */
public class MatrixExamples implements Serializable {
    public static final String MEXTRA = "com.example.dominika.gauss.mextras";

    private int exampleId;

    private int rows;
    private int columns;

    int[] integers;

    private int[]  example1 =
            { 3, 5, 6, 1,
              4, 3, 2, 5,
              3, 5, 1, 1
    };
    private int[]  example2 =
            { 1, 3, 2, 2, 3,
              2, 4, 1, 0, 12,
              1, 3, 2, 1, 4,
              3, 2, 4, 6, -1
    };
    private int[] example3 =
            { 2, -1, 1, -1, 3,
              4, -2, -2, 3, 2,
              2, -1, 5, -6, 1,
              2, -1, -3, 4, 5
    };
    private int[]  example4 =
            { 2, 7, 3, 1, 5,
              1, 3, 5, -2, 3,
              1, 5, -9, 8, 1,
              5, 18, 4, 5, 12
    };
    private int[]  example5 =
            { 5, 12, 5, 3, 10,
              11, 11, 4, 8, 8,
              2, 7, 3, 1, 6,
              7, -3, -2, 6, -4
    };
    private int[]  example6 =
            { 1, 2, 3, 1, 1,
              1, 1, 2, 3, 2,
              2, 3, 1, -1, 1,
              3, 4, 3, 2, 3
    };
    private int[]  example7 =
            { -2, 2, 0, 0, -6,
              1, 8, 2, 1, -3,
              3, 10, 2, -1, -2,
              2, 0, 0, -1, 1,
              4, 3, 1, 1, 5
    };
    private int[]  example8 =
            { 5, 5, 4, 7, 5,
              11, 4, 6, 0, 4,
              4, 5, 5, 9, 8,
              2, 3, 2, 5, 3,
              9, 4, 8, 4, 10
    };

    public void setExampleId(int exampleId) {
        this.exampleId = exampleId;
    }

    public int getExampleId() {
        return exampleId;
    }

    public int[] getIntegers() {
        return integers;
    }

    public void setIntegers(int[] integers) {
        this.integers = integers;
    }

    public int[] getExample1() {
        return example1;
    }

    public void setExample1(int[] example1) {
        this.example1 = example1;
    }

    public int[] getExample2() {
        return example2;
    }

    public void setExample2(int[] example2) {
        this.example2 = example2;
    }

    public int[] getExample3() {
        return example3;
    }

    public void setExample3(int[] example3) {
        this.example3 = example3;
    }

    public int[] getExample4() {
        return example4;
    }

    public void setExample4(int[] example4) {
        this.example4 = example4;
    }

    public int[] getExample5() {
        return example5;
    }

    public void setExample5(int[] example5) {
        this.example5 = example5;
    }

    public int[] getExample6() {
        return example6;
    }

    public void setExample6(int[] example6) {
        this.example6 = example6;
    }

    public int[] getExample7() {
        return example7;
    }

    public void setExample7(int[] example7) {
        this.example7 = example7;
    }

    public int[] getExample8() {
        return example8;
    }

    public void setExample8(int[] example8) {
        this.example8 = example8;
    }

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

    public void sample() {
        switch (exampleId) {
            case 1:
                integers = example1;
                setRows(3);
                setColumns(3);
                break;
            case 2:
                integers = example2;
                setRows(4);
                setColumns(4);
                break;
            case 3:
                integers = example3;
                setRows(4);
                setColumns(4);
                break;
            case 4:
                integers = example4;
                setRows(4);
                setColumns(4);
                break;
            case 5:
                integers = example5;
                setRows(4);
                setColumns(4);
                break;
            case 6:
                integers = example6;
                setRows(4);
                setColumns(4);
                break;
            case 7:
                integers = example7;
                setRows(5);
                setColumns(4);
                break;
            case 8:
                integers = example8;
                setRows(5);
                setColumns(4);
                break;
        }
    }

}
