package medical.monitor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myapplication.R;

import medical.utils.DefaultCallback;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateHolder> {

    private AgentPulso agent;
    private MonitorActivity activity;

    public DateAdapter(AgentPulso agent, MonitorActivity activity) {
        this.agent = agent;
        this.activity = activity;
    }

    @NonNull
    @Override
    public DateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_date, parent, false);
        return new DateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateHolder holder, int position) {
        holder.position = position;
        holder.textView.setText(agent.dates.get(position) + "");
    }

    @Override
    public int getItemCount() {

        return agent.dates.size();
    }

    public class DateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int position;

        private TextView textView;


        public DateHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            agent.fecha = agent.dates.get(position);
            agent.getDataMonitorDate(agent.dates.get(position), new DefaultCallback() {
                @Override
                public void onFinishProcess(final boolean hasSucceeded, Object result) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (hasSucceeded) {

                                Intent in = new Intent(activity, SummaryActivity.class);
                                activity.startActivity(in);
                            } else
                                Toast.makeText(activity, "Get Data Fail", Toast.LENGTH_SHORT);
                        }
                    });

                }

            });
        }
    }

}
