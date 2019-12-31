package medical.monitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.myapplication.R;

import medical.utils.LineGraph;
import medical.utils.ListDuo;

import org.achartengine.GraphicalView;

import java.util.ArrayList;

public class GrahicActivity extends Activity {


    private AgentPulso agent;


    private static GraphicalView view;
    private static GraphicalView view1;
    private LineGraph line = new LineGraph();
    private LineGraph line1 = new LineGraph();
    private static Thread thread;

    private ListDuo listDuo;
    private TextView lblPulso1;
    private TextView lblPulso2;
    private TextView lblHoraInicio1;
    private TextView lblHoraInicio2;
    private TextView lblHoraFin1;
    private TextView lblHoraFin2;
    private TextView lblduracion1;
    private TextView lblduracion2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        agent = AgentPulso.INSTANCE;

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));


        LinearLayout linearLayout = findViewById(R.id.graficaActividad);
        view = line.getView(this);
        linearLayout.addView(view);

        LinearLayout linearLayout2 = findViewById(R.id.graficaActividad2);
        view1 = line1.getView(this);
        linearLayout2.addView(view1);

//        lblPulso1 = findViewById(R.id.PulsoPromedio1);
//        lblPulso2 = findViewById(R.id.PulsoPromedio2);
//        lblHoraInicio1 = findViewById(R.id.HoraInicio1);
//        lblHoraInicio2 = findViewById(R.id.HoraInicio2);
//        lblHoraFin1 = findViewById(R.id.HoraFin1);
//        lblHoraFin2 = findViewById(R.id.HoraFin2);
//        lblduracion1 = findViewById(R.id.duracion1);
//        lblduracion2 = findViewById(R.id.duracion2);
inicializarGrafica();

//        ListDuo listDuo = agent.takes;
//
//        for (int i = 0; i < listDuo.size(); i++) {
//
//            String valorX = listDuo.get(i).getValue1();
//
//            valorX = valorX.replaceAll(":", "");
//
//            if (agent.type == 0 && valorX.charAt(0) == '0')
//                valorX = valorX.substring(1, valorX.length() - 2);
//
//            String valorY = listDuo.get(i).getValue2();
//
//            double x = Integer.parseInt(valorX);
//            double y = Integer.parseInt(valorY);
//
//            line.addCoordenada(x, y);
//        }

//        view.repaint();

    }

    public void inicializarGrafica() {

        medical.model.MonitorTake rutina = agent.take;

        lblduracion1.setText("1");
        lblduracion2.setText("1");
        lblHoraInicio1.setText(rutina.getTime_start());
        lblHoraInicio2.setText(rutina.getTime_pos_start());
        lblHoraFin1.setText(rutina.getTime_finish());
        lblHoraFin2.setText(rutina.getTime_pos_finish());
        lblPulso1.setText("90");
        lblPulso2.setText("70");

        ArrayList<Integer> valores1 = rutina.getTakes_1();
        ArrayList<Integer> valores2 = rutina.getTakes_2();

        for (int i = 0; i < valores1.size(); i++)
            line.addCoordenada(i, valores1.get(i));

        view.repaint();

        for (int i = 0; i < valores2.size(); i++)
            line1.addCoordenada(i, valores2.get(i));

        view1.repaint();


    }

}
