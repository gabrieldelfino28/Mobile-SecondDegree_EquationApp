package br.edu.fateczl.mobilesquarerootapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etValA;
    private EditText etValB;
    private EditText etValC;
    private TextView tvDelta;
    private TextView tvOutputX1;
    private TextView tvOutputX2;
    private TextView tvAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etValA = findViewById(R.id.etValA);
        etValB = findViewById(R.id.etValB);
        etValC = findViewById(R.id.etValC);

        Button buttonCalc = findViewById(R.id.buttonCalc);

        tvAnswer = findViewById(R.id.tvAnswer);
        tvAnswer.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tvDelta = findViewById(R.id.tvDelta);
        tvDelta.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tvOutputX1 = findViewById(R.id.tvOutputX1);
        tvOutputX1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tvOutputX2 = findViewById(R.id.tvOutputX2);
        tvOutputX2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        buttonCalc.setOnClickListener(op -> computarRaizQuadrada());
    }

    /*
     *  Equação 2 grau: y = ax² + bx + c = 0
     *  Equação 1 grau: y = bx + c = 0 -> (-c / b) (Linear)
     */

    private void computarRaizQuadrada() {

        //Limpeza dos campos

        tvOutputX1.setText("");
        tvOutputX2.setText("");
        tvDelta.setText("");
        tvAnswer.setText("");

        double a = Double.parseDouble(etValA.getText().toString());
        double b = Double.parseDouble(etValB.getText().toString());
        double c = Double.parseDouble(etValC.getText().toString());

        if (a == 0) {
            if (b == 0) { //Não há solução.
                tvAnswer.setText(getString(R.string.noSolution));
            } else { //Equação Linear
                double x = calcEqLinear(b, c);
                tvAnswer.setText(getString(R.string.equacao_primeiroGrau));
                tvOutputX1.setText(String.valueOf(x));
            }
        } else {
            double x1;
            double x2;
            double delta = calcDelta(a, b, c);
            String ansDelta = getString(R.string.delta) + " " + String.valueOf(delta);
            tvDelta.setText(ansDelta);

            if (delta > 0) {

                tvAnswer.setText(getString(R.string.raiz_dois));
                x1 = calcPrimeiraRaiz(delta, a, b);
                x2 = calcSegundaRaiz(delta, a, b);
                tvOutputX1.setText(String.valueOf(x1));
                tvOutputX2.setText(String.valueOf(x2));

            } else if (delta == 0) {

                tvAnswer.setText(getString(R.string.raiz_um));
                x1 = calcPrimeiraRaiz(delta, a, b);
                tvOutputX1.setText(String.valueOf(x1));

            } else { //Raizes não reais
                tvAnswer.setText(getString(R.string.zero_raizes));
            }
        }
    }

    private double calcDelta(double a, double b, double c) {
        return (b * b) - (4 * a * c);
    }

    private double calcPrimeiraRaiz(double delta, double a, double b) {
        return (-b + Math.sqrt(delta)) / (2 * a);
    }

    private double calcSegundaRaiz(double delta, double a, double b) {
        return (-b - Math.sqrt(delta)) / (2 * a);
    }

    private double calcEqLinear(double b, double c) {
        return -c / b;
    }
}
