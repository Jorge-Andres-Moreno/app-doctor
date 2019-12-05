package medical.monitor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.myapplication.R;

import medical.graphicecg.LineGraph;
import medical.utils.DefaultCallback;
import medical.utils.ListDuo;

import org.achartengine.GraphicalView;

public class GrahicActivity extends Activity {


    private AgentMonitor agent;
    private static GraphicalView view;
    private LineGraph line = new LineGraph();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        agent = AgentMonitor.INSTANCE;

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));


        LinearLayout linear = findViewById(R.id.LayoutECG);
        view = line.getView(this);
        linear.addView(view);

        ListDuo listDuo = agent.takes;

        for (int i = 0; i < listDuo.size(); i++) {

            String valorX = listDuo.get(i).getValue1();

            valorX = valorX.replaceAll(":", "");

            if (agent.type==0&&valorX.charAt(0) == '0')
                valorX = valorX.substring(1, valorX.length() - 2);

            String valorY = listDuo.get(i).getValue2();

            double x = Integer.parseInt(valorX);
            double y = Integer.parseInt(valorY);

            line.addCoordenada(x, y);
        }

        view.repaint();

    }


}
