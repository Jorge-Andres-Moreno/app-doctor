package medical.monitoreo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.android.myapplication.R;
import medical.utils.DefaultCallback;

public class MonitoringActivity extends Activity implements DefaultCallback {

    TextView mostrado;
    AgentMonitoring objectam;

    String uidpatient = "0EaBsE2IitYHgz52mNOXmWp4Jey2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);

        //uidpatient = "" + getIntent().getStringExtra("idpatient");

        mostrado = findViewById(R.id.mostrado);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        objectam = new AgentMonitoring();
        objectam.getMonitoringData(uidpatient, "true", "false", this);
    }


    public void datospulsos() {

    }

    @Override
    public void onFinishProcess(boolean hasSucceeded, Object result) {

    }
}
