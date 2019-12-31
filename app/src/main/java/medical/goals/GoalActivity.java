package medical.goals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.R;


import org.achartengine.GraphicalView;

import java.util.ArrayList;

import medical.utils.DefaultCallback;
import medical.utils.LineGraph;
import medical.utils.LineGraphBarPasos;
import medical.utils.LinearGraphBarKgCalorias;


public class GoalActivity extends AppCompatActivity {

    // Componentes actividad principal

    private TextView lblPasosAsignados;
    private TextView lblPasosLogrados;
    private TextView lblPasosFaltantes;

    private TextView lblKgCaloriasasignada;
    private TextView lblKgCaloriasLogradas;
    private TextView lblKgcaloriasFaltantes;

    private int intPasosAsignadas;
    private int intPasosLogrados;
    private int intPasosFaltantes;

    private int intKgcaloriasAsignadas;
    private double intKgcaloriasLogradas;

    private static GraphicalView viewPasos;
    private LineGraphBarPasos linePasos;
    private static GraphicalView viewKgCalorias;
    private LinearGraphBarKgCalorias lineKgCalorias;

    // componentes Resumen Actividad Fisica

    private static GraphicalView view;
    private static GraphicalView view1;
    private LineGraph line = new LineGraph();
    private LineGraph line1 = new LineGraph();

    private AgentGoal agent;

    private TextView lblPulso1;
    private TextView lblPulso2;
    private TextView lblHoraInicio1;
    private TextView lblHoraInicio2;
    private TextView lblHoraFin1;
    private TextView lblHoraFin2;
    private TextView lblduracion1;
    private TextView lblduracion2;
    private TextView lblPulsoMinimo1;
    private TextView lblPulsoMinimo2;
    private TextView lblPulsoMaximo1;
    private TextView lblPulsoMaximo2;
    private TextView lblPasos;
    private TextView lblKgCalorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_goals);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));


        // inicializacion componentes interfaz locales

        lblPasosAsignados = findViewById(R.id.lblpasosAsignadas);
        lblPasosLogrados = findViewById(R.id.lblPasosLogradas);
        lblPasosFaltantes = findViewById(R.id.lblpasosFaltantes);

        lblKgCaloriasasignada = findViewById(R.id.lblKgcaloriasAsignada);
        lblKgCaloriasLogradas = findViewById(R.id.lblKgcaloriasLogradas);
        lblKgcaloriasFaltantes = findViewById(R.id.lblKgcaloriasFaltantes);

        lineKgCalorias = new LinearGraphBarKgCalorias();
        linePasos = new LineGraphBarPasos();

        LinearLayout linearLayout = findViewById(R.id.layoutKgcalorias);
        viewKgCalorias = lineKgCalorias.getView(this);
        linearLayout.addView(viewKgCalorias);


        LinearLayout linearLayout2 = findViewById(R.id.layoutPasos);
        viewPasos = linePasos.getView(this);
        linearLayout2.addView(viewPasos);

        // iniciacion componentes Resumen Actividad


        LinearLayout linearLayout3 = findViewById(R.id.TgraficaActividad);
        view = line.getView(this);
        linearLayout3.addView(view);


        LinearLayout linearLayout4 = findViewById(R.id.TgraficaActividad2);
        view1 = line1.getView(this);
        linearLayout4.addView(view1);

        lblPasos = findViewById(R.id.TlblPasos1);
        lblPulso1 = findViewById(R.id.TlblPromedioPulso1);
        lblPulso2 = findViewById(R.id.TlblPromedioPulso2);
        lblHoraInicio1 = findViewById(R.id.TlblHoraInicio1);
        lblHoraInicio2 = findViewById(R.id.TlblHoraInicio2);
        lblHoraFin1 = findViewById(R.id.TlblHoraFin1);
        lblHoraFin2 = findViewById(R.id.TlblHoraFin2);
        lblduracion1 = findViewById(R.id.TlblDuracion1);
        lblduracion2 = findViewById(R.id.TlblDuracion2);
        lblPulsoMinimo1 = findViewById(R.id.TlblPulsoMinimo1);
        lblPulsoMinimo2 = findViewById(R.id.TlblPulsoMinimo2);
        lblPulsoMaximo1 = findViewById(R.id.TlblPulsoMaximo1);
        lblPulsoMaximo2 = findViewById(R.id.TlblPulsoMaximo2);
        lblKgCalorias = findViewById(R.id.Tlblkgcalorias);

        agent = AgentGoal.getInstance();
        agent.getDataMonitorDateHome("0", new DefaultCallback() {
            @Override
            public void onFinishProcess(final boolean hasSucceeded, final Object result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (hasSucceeded) {
                            inicializarGrafica();
                            inicializarMetas(result);
                        }
                    }
                });
            }
        });

        findViewById(R.id.assign_goals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.assign_goals) {
                    GoalDialog dialog = new GoalDialog(v.getContext(), v, new DefaultCallback() {
                        @Override
                        public void onFinishProcess(final boolean hasSucceeded, Object result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(hasSucceeded) {
                                        finish();
                                    }else {
                                        Toast.makeText(GoalActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });
    }


    private void inicializarMetas(Object result) {


        String[] datosMetas = (String[]) result;

        intPasosAsignadas = Integer.parseInt(datosMetas[0]);
        intPasosLogrados = Integer.parseInt(datosMetas[1]);
        intKgcaloriasAsignadas = Integer.parseInt(datosMetas[2]);

        if (datosMetas[3].length() >= 0 && datosMetas[3].length() <= 6) {
            intKgcaloriasLogradas = Double.parseDouble(datosMetas[3]);
        } else {
            intKgcaloriasLogradas = Double.parseDouble(datosMetas[3].substring(0, 7));
        }
//        intKgcaloriasLogradas = Integer.parseInt(datosMetas[3]);
        intPasosFaltantes = intPasosAsignadas - intPasosLogrados;
//        intKgcaloriasFaltantes = intKgcaloriasAsignadas - intKgcaloriasLogradas;

        lblPasosAsignados.setText(datosMetas[0]);
        lblPasosLogrados.setText(datosMetas[1]);
        lblPasosFaltantes.setText("" + intPasosFaltantes);

        lblKgCaloriasasignada.setText(datosMetas[2]);
        lblKgCaloriasLogradas.setText(datosMetas[3]);
        lblKgcaloriasFaltantes.setText("" + intPasosFaltantes);

        linePasos.addCoordenada(1, intPasosAsignadas);
        linePasos.addCoordenada2(2, intPasosLogrados);

        lineKgCalorias.addCoordenada(1, intKgcaloriasAsignadas);
        lineKgCalorias.addCoordenada2(2, intKgcaloriasLogradas);

        lineKgCalorias.cambiarLimitesGrafica(3, intKgcaloriasAsignadas);
        linePasos.cambiarLimitesGrafica(3, intPasosAsignadas);

        viewPasos.repaint();
        viewKgCalorias.repaint();

    }


    public void inicializarGrafica() {

        lblduracion1.setText(agent.take.getDuration());
        lblduracion2.setText(agent.take.getDuration());
        lblHoraInicio1.setText(agent.take.getTime_start());
        lblHoraInicio2.setText(agent.take.getTime_pos_start());
        lblHoraFin1.setText(agent.take.getTime_finish());
        lblHoraFin2.setText(agent.take.getTime_pos_finish());
        lblPulso1.setText(agent.take.getPulsoPromedio());
        lblPulso2.setText(agent.take.getPulsoPromedio1());
        lblPulsoMaximo1.setText(agent.take.getPulsoMaximo());
        lblPulsoMaximo2.setText(agent.take.getPulsoMaximo1());
        lblPulsoMinimo1.setText(agent.take.getPulsoMinimo());
        lblPulsoMinimo2.setText(agent.take.getPulsoMinimo1());
        lblPasos.setText(agent.take.getPasos());
        lblKgCalorias.setText(agent.take.getKgCalorias());

        ArrayList<Integer> valores1 = agent.take.getTakes_1();
        ArrayList<Integer> valores2 = agent.take.getTakes_2();

        for (int i = 0; i < valores1.size(); i++)
            line.addCoordenada(i, valores1.get(i));

        line.cambiarLimitesGrafica(valores1.size(), Integer.parseInt(agent.take.getPulsoMaximo()));
        view.repaint();

        for (int i = 0; i < valores2.size(); i++)
            line1.addCoordenada(i, valores2.get(i));

        line1.cambiarLimitesGrafica(valores2.size(), Integer.parseInt(agent.take.getPulsoMaximo1()));
        view1.repaint();


    }
}
