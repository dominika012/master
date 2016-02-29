package com.master.gauss.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dominika.gauss.R;
import com.master.gauss.core.Fraction;
import com.master.gauss.core.Matrix;
import com.master.gauss.service.InfiniteSolutionService;

public class Solution extends Activity {

    private Button next;
    private Matrix matrix;
    private TextView output;
    private TableLayout matrixView;
    private TextView cell;

    private int rows;
    private int columns;

    private Fraction F[][];

    private InfiniteSolutionService infSolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        output = (TextView) findViewById(R.id.output_id);
        matrixView = (TableLayout) findViewById(R.id.matrix_id);

        matrix = (Matrix) getIntent().getSerializableExtra(Matrix.EXTRA);

        rows = matrix.getRows();
        columns = matrix.getColumns();

        F =new Fraction[rows][columns+1];
        F =matrix.getMatrix();

        infSolution = new InfiniteSolutionService(matrix.getRows(), matrix.getColumns(), F);

        int frob=matrix.getRankSetter();

        printMatrix();
        switch (frob) {
            case -1:
                output.append(" Sústava nemá riešenie");
                break;
            case 1:
                output.append(infSolution.solve());
                break;
            case 0:
                print();
                break;
            default: output.append(" Nevalídny stav.");
                break;
        }
    }

    private Fraction[] solution(){

        /** back substitution **/
            Fraction[] solution = new Fraction[columns];

            Fraction sum=new Fraction();
            sum.setNumerator(0);
            sum.setDenominator(1);

            for (int i = columns - 1; i >= 0; i--)
            {
                for (int j = i + 1; j < columns; j++)
                    sum = sum.add(F[i][j].multiply(solution[j]));

                solution[i] = (F[i][columns].subtract(sum)).divide(F[i][i]);

            }
        return solution;
    }

    private void print() {

        for (int i = rows - 1; i >= 0; i--) {

            printEquation(i);

            printSolution(i);
            output.append("\n\n");
            }

    }

    private void printSolution(int i) {
        int indexi = i + 1;
        Fraction[] solution = solution();
        output.append(Html.fromHtml("x" + "<small><sub>" + indexi + "</sub></small>" + " = " + solution[i]));
        //output.append("x" + indexi + " = " + solution[i] + "\n\n");
    }

    private void printEquation(int i){
        int indexj=0;
        int indexi = i + 1;
        String sign = "";

        for (int j = 0; j < columns; j++) {
                indexj = j + 1;
                if (F[i][j].getNumerator() > 0) {
                    if (indexi != indexj) sign = "+";
                    else sign = "";
                    output.append(Html.fromHtml(sign + F[i][j] + " x" + "<small><sub>" + indexj + "</sub></small>" + "\t\t"));
                    //output.append(sign + F[i][j] + " x" + indexj + "\t\t");
                }
                else if (F[i][j].getNumerator() < 0){
                    output.append(Html.fromHtml(F[i][j] + " x" + "<small><sub>" + indexj + "</sub></small>"+ "\t\t"));
                    //output.append(F[i][j] + " x" + indexj + "\t\t");
                }

                else if (F[i][j].getNumerator() == 0)
                    output.append("");
            }
            output.append(" =  " + F[i][columns] + "\n");
    }

    private void printMatrix() {

        TextView line;

        for (int r = 0; r < rows; r++) {
            TableRow row = new TableRow(this);
            for (int c = 0; c < columns + 1; c++) {
                cell = new TextView(this);
                cell.setGravity(Gravity.CENTER);
                cell.setPadding(10, 10, 10, 10);
                cell.setWidth(140);
                cell.append("" + F[r][c]);
                cell.setTextSize(15);
                if (c == columns) {
                    line = new TextView(this);
                    line.setText("  |  ");
                    line.setTextSize(15);
                    row.addView(line);
                }
                row.addView(cell);
            }
            matrixView.addView(row, new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT));
        }
    }

    //pozri na googli: the most optimal swapping
}
