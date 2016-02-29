package com.master.gauss.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dominika.gauss.R;
import com.master.gauss.core.Fraction;
import com.master.gauss.core.Matrix;

public class TriangleForm extends Activity {

    private Button next;
    private Matrix matrix;
    private TableLayout output;
    private TextView cell;

    private int rows;
    private int columns;

    private Fraction fraction;
    private Fraction F[][];

    private TextView labelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle_form);

        output = (TableLayout) findViewById(R.id.output_id);

        matrix = (Matrix) getIntent().getSerializableExtra(Matrix.EXTRA);

        rows = matrix.getRows();
        columns = matrix.getColumns();

        F=new Fraction[rows][columns+1];
        F=matrix.getMatrix();

        labelView = new TextView(this);

        addListenerOnButtons();

        echelonForm();

        setRanks();
    }


    private void addListenerOnButtons() {

        final Context context = this;

        next = (Button) findViewById(R.id.rank_id);

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Rank.class);
                intent.putExtra(Matrix.EXTRA, matrix);
                startActivity(intent);

            }

        });

    }

    public void echelonForm(){
        //printMatrix(" Matica A");

        for (int k = 0; k < rows; k++) {
            /** find pivot row **/
            int max = k;
            if (k <= (columns)) {
                for (int i = k + 1; i < rows; i++)
                    if (Math.abs(F[i][k].getNumerator()) > Math.abs(F[max][k].getNumerator()))
                        max = i;

                /** swap row in A matrix **/

                int p = k + 1;

                Fraction[] tempf = F[k];
                F[k] = F[max];
                F[max] = tempf;


                if (F[k][k].getNumerator() != 0 && k != columns) {
                    stringRowForPivot(k);
                    printMatrix();
                }
                /** pivot within A and B **/
                String fac = "";
                String facf = "";
                String sign = "";


                for (int i = k + 1; i < rows; i++) {

                    if (F[i][k].getNumerator() != 0) {

                        Fraction fractionA = new Fraction();
                        fractionA = F[k][k].divide(F[i][k]);

                        for (int j = k; j < columns + 1; j++) {

                            Fraction fractionB = new Fraction();
                            Fraction fractionC = new Fraction();
                            fraction = new Fraction();

                            fractionB = F[i][j];
                            fractionC = F[k][j];

                            fraction = (fractionA.multiply(F[i][j])).subtract(F[k][j]);

                            F[i][j] = fraction;

                        }
                        int ii = i + 1;
                        int kk = k + 1;
                        stringRowForAdd(fractionA.toString(), ii, kk, F[k][k].multiply(F[i][k]));
                        printMatrix();
                    }

                }

            }
        }
        /** Print row echelon form **/

        //printMatrix(" Výsledný tvar \n");

        matrix.setMatrix(F);
    }


    private String sign(Fraction f){
        String string="";
        int sign=f.getNumerator();

        if(sign>0){
            string=" - ";
        }
        else if(sign<0){
            string=" + ";
        }
        return string;
    }

    private String printPositive(int d){
        String s="";
        if (d<0){
            d=d*(-1);
        }
        s=Integer.toString(d);
        return s;
    }

    private void setRanks(){
        setRank();
        setExpandedRank();
    }

    private void setRank(){

        Fraction fractions[][]=new Fraction[rows][columns+1];
        fractions=matrix.getMatrix();

        int rankLeft=0;

        int zerosInRow;

        for (int i=0;i<rows;i++) {
            zerosInRow=0;
            for (int j = 0; j < columns; j++) {
                if (fractions[i][j].getNumerator()==0) zerosInRow=zerosInRow+1;
            }
            if (zerosInRow==columns) rankLeft=rankLeft+1;
        }
        matrix.setRankLeft(rows - rankLeft);
    }

    private void setExpandedRank(){
        Fraction fractions[][]=new Fraction[rows][columns+1];
        fractions=matrix.getMatrix();
        int rank=0;
        int zerosInRow;

        for (int i=0;i<rows;i++) {
            zerosInRow=0;
            for (int j = 0; j < columns+1; j++) {
                if (fractions[i][j].getNumerator()==0) zerosInRow=zerosInRow+1;
            }
            if (zerosInRow==columns+1) rank=rank+1;
        }
        matrix.setRank(rows - rank);
    }

    private void stringRowForPivot(int value){
        TextView labelView = new TextView(this);
        labelView.setText(Html.fromHtml(
                " Riadok R"
                + "<small><sub>" + value + "</sub></small>"
                + " má pivota " + F[value][value] + ""));
        labelView.setTextSize(20);
        labelView.setHeight(75);
        output.addView(labelView);

    }

    private void stringRowForAdd(
            String stringFromFraction,
            int row1Index,
            int row2Index,
            Fraction fraction){

        TextView labelView = new TextView(this);
        labelView.setText(Html.fromHtml(
                stringFromFraction + " " + "R"
                + "<small><sub>" + row1Index + "</sub></small>"
                + sign(fraction) + "R"
                + "<small><sub>" + row2Index + "</sub></small>"));
        labelView.setTextSize(20);
        labelView.setHeight(75);
        output.addView(labelView);
    }

    private void printMatrix() {
        TextView line;
        for (int r = 0; r < rows; r++) {
            TableRow row = new TableRow(this);
            for (int c = 0; c < columns + 1; c++) {
                cell = new TextView(this);
                cell.setGravity(Gravity.CENTER);
                cell.setPadding(10, 10, 10, 10);
                cell.setWidth(160);

                cell.append("" + F[r][c]);

                cell.setTextSize(16);
                if (c == columns) {
                    line = new TextView(this);
                    line.setText("  |  ");
                    line.setTextSize(15);
                    row.addView(line);
                }
                row.addView(cell);
            }
            //row.setGravity(Gravity.CENTER);
            output.addView(row, new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT));
        }
    }
}
