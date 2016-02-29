package com.master.gauss.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dominika.gauss.R;
import com.master.gauss.core.Matrix;

public class Rank extends Activity {

    private Button next;
    private Matrix matrix;
    private TextView output;

    private int leftRank;
    private int rank;

    private int rows;
    private int columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        output = (TextView) findViewById(R.id.output_id);

        matrix = (Matrix) getIntent().getSerializableExtra(Matrix.EXTRA);

        leftRank=matrix.getRankLeft();
        rank=matrix.getRank();

        rows = matrix.getRows();
        columns = matrix.getColumns();

        addListenerOnButtons();

        printRanks();
        frobeny();
    }

    private void addListenerOnButtons() {

        final Context context = this;

        next = (Button) findViewById(R.id.solution_id);

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Solution.class);
                intent.putExtra(Matrix.EXTRA, matrix);
                startActivity(intent);

            }

        });

    }

    private void printRanks(){
        output.append("n = " + columns + "\n");
        output.append("h(A) = " + leftRank + "\n");
        output.append("h'(A) = " + rank + "\n");
    }

    private void frobeny(){

        int frob=2;

        if (leftRank!=rank) frob=-1;
        else if (leftRank==rank && rank<columns) frob=1;
        else if (leftRank==rank && rank==columns)frob=0;

        String eq = "";
        String statement = "";

        matrix.setRankSetter(frob);


        switch (frob) {
            case -1:
                eq= "h(A)" + "\u2260" + "h'(A)";
                statement = " Sústava nemá riešenie";
                break;
            case 1:
                eq= "h(A) = h'(A) < n \t\t" + rank + "<" + columns;
                statement = " Sústava má nekonečne veľa riešení.";
                break;
            case 0:
                eq= "h(A) = h'(A) = n = " + columns;
                statement = " Sústava má práve jedno riešenie";
                break;
            default: statement = " Nevalídny stav.";
                break;
        }

        output.append("\n" + eq + "\n");
        output.append("\n" + statement);

    }

}
