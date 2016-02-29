package com.master.gauss.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dominika.gauss.MatrixExamples;
import com.example.dominika.gauss.R;
import com.master.gauss.core.Fraction;
import com.master.gauss.core.Matrix;
import com.master.gauss.service.NumberService;

public class Intro extends Activity {

    private Button button;
    private Button sample;

    private Matrix matrix;
    private MatrixExamples matrixExamples;

    private EditText rows;
    private EditText columns;

    Fraction fraction;

    NumberService numberService;

    int rowsCount;
    int columnsCount;

    final Context context = this;

    TextView tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        tw = (TextView)findViewById(R.id.tw);

        rows = (EditText)findViewById(R.id.rows_id);
        columns = (EditText)findViewById(R.id.columns_id);


        matrix = new Matrix();
        matrixExamples = new MatrixExamples();

        numberService = new NumberService();

        addListenerOnButtons();
    }


    public void addListenerOnButtons() {

        final Context context = this;

        button = (Button) findViewById(R.id.button_id);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                matrix = new Matrix();
                matrixExamples = new MatrixExamples();

                rowsCount = Integer.parseInt(rows.getText().toString());
                columnsCount = Integer.parseInt(columns.getText().toString());

                if ((rowsCount <= 10 && rowsCount>=2) && (columnsCount<=10 && columnsCount>=2)) {
                    matrix.setRows(rowsCount);
                    matrix.setColumns(columnsCount);

                    matrixExamples.setExampleId(0);

                    Intent intent = new Intent(context, InputValues.class);
                    intent.putExtra(Matrix.EXTRA, matrix);
                    intent.putExtra(MatrixExamples.MEXTRA, matrixExamples);
                    startActivity(intent);
                }
                else if ((rowsCount > 10 || rowsCount < 2) || (columnsCount > 10 || columnsCount < 2)) {
                    dialogMsg();
                }
            }

        });




        sample = (Button) findViewById(R.id.sample_id);

        sample.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                matrix = new Matrix();
                matrixExamples = new MatrixExamples();

                matrixExamples.setExampleId(numberService.randomNumber(1, 8));
                matrixExamples.sample();
                rowsCount = matrixExamples.getRows();
                columnsCount = matrixExamples.getColumns();

                matrix.setRows(rowsCount);
                matrix.setColumns(columnsCount);

                Intent intent = new Intent(context, InputValues.class);
                intent.putExtra(MatrixExamples.MEXTRA, matrixExamples);
                intent.putExtra(Matrix.EXTRA, matrix);
                startActivity(intent);


            }

        });

    }

    private void dialogMsg(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Zlý vstup");

        // set dialog message
        alertDialogBuilder
                .setMessage("Zadaj čísla v intervale od 2 do 10")
                .setCancelable(false)
                .setNegativeButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
