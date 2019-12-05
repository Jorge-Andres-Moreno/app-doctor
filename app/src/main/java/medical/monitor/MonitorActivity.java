package medical.monitor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myapplication.R;

import medical.utils.DefaultCallback;

public class MonitorActivity extends Activity implements DefaultCallback {


    private AgentMonitor agent;
    private RecyclerView recycler;
    private DateAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);

        String id = getIntent().getExtras().getString("id");

        agent = new AgentMonitor(id);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

        adapter = new DateAdapter(agent, this);
        recycler = findViewById(R.id.recycler_fechas);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        agent.getMonitoringDates(true, false, this);

    }


    @Override
    public void onFinishProcess(boolean hasSucceeded, Object result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
