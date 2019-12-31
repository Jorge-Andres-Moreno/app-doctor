package medical.monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.core.content.ContextCompat;

import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.myapplication.R;

import org.achartengine.GraphicalView;

import java.util.ArrayList;

import medical.utils.LineGraph;


public class SummaryActivity extends AppCompatActivity {


    private static GraphicalView view;
    private static GraphicalView view1;

    private LineGraph line = new LineGraph();
    private LineGraph line1 = new LineGraph();

    private AgentPulso agentPulso;

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
        setContentView(R.layout.activity_graphic);

        LinearLayout linearLayout = findViewById(R.id.graficaActividad);
        view = line.getView(this);
        linearLayout.addView(view);


        LinearLayout linearLayout2 = findViewById(R.id.graficaActividad2);
        view1 = line1.getView(this);
        linearLayout2.addView(view1);

        lblPasos = findViewById(R.id.lblPasos1);
        lblPulso1 = findViewById(R.id.lblPromedioPulso1);
        lblPulso2 = findViewById(R.id.lblPromedioPulso2);
        lblHoraInicio1 = findViewById(R.id.lblHoraInicio1);
        lblHoraInicio2 = findViewById(R.id.lblHoraInicio2);
        lblHoraFin1 = findViewById(R.id.lblHoraFin1);
        lblHoraFin2 = findViewById(R.id.lblHoraFin2);
        lblduracion1 = findViewById(R.id.lblDuracion1);
        lblduracion2 = findViewById(R.id.lblDuracion2);
        lblPulsoMinimo1 = findViewById(R.id.lblPulsoMinimo1);
        lblPulsoMinimo2 = findViewById(R.id.lblPulsoMinimo2);
        lblPulsoMaximo1 = findViewById(R.id.lblPulsoMaximo1);
        lblPulsoMaximo2 = findViewById(R.id.lblPulsoMaximo2);
        lblKgCalorias = findViewById(R.id.lblkgcalorias);

        agentPulso = AgentPulso.INSTANCE;
        initGraphic();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

    }

    public void initGraphic() {

        lblduracion1.setText(agentPulso.take.getDuration());
        lblduracion2.setText(agentPulso.take.getDuration());
        lblHoraInicio1.setText(agentPulso.take.getTime_start());
        lblHoraInicio2.setText(agentPulso.take.getTime_pos_start());
        lblHoraFin1.setText(agentPulso.take.getTime_finish());
        lblHoraFin2.setText(agentPulso.take.getTime_pos_finish());
        lblPulso1.setText(agentPulso.take.getPulsoPromedio());
        lblPulso2.setText(agentPulso.take.getPulsoPromedio1());
        lblPulsoMaximo1.setText(agentPulso.take.getPulsoMaximo());
        lblPulsoMaximo2.setText(agentPulso.take.getPulsoMaximo1());
        lblPulsoMinimo1.setText(agentPulso.take.getPulsoMinimo());
        lblPulsoMinimo2.setText(agentPulso.take.getPulsoMinimo1());
        lblPasos.setText(agentPulso.take.getPasos());
        lblKgCalorias.setText(agentPulso.take.getKgCalorias());

        ArrayList<Integer> values_1 = agentPulso.take.getTakes_1();
        ArrayList<Integer> values_2 = agentPulso.take.getTakes_2();

        for (int i = 0; i < values_1.size(); i++)
            line.addCoordenada(i, values_1.get(i));

        line.cambiarLimitesGrafica(values_1.size(), Integer.parseInt(agentPulso.take.getPulsoMaximo()));
        view.repaint();

        for (int i = 0; i < values_2.size(); i++)
            line1.addCoordenada(i, values_2.get(i));

        line1.cambiarLimitesGrafica(values_2.size(), Integer.parseInt(agentPulso.take.getPulsoMaximo1()));
        view1.repaint();


    }
}