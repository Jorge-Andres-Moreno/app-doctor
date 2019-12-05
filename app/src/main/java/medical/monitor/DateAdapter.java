package medical.monitor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myapplication.R;

import medical.utils.DefaultCallback;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateHolder> {

    private AgentMonitor agent;
    private MonitorActivity activity;

    public DateAdapter(AgentMonitor agent, MonitorActivity activity) {
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
            agent.getDataMonitorDate(agent.dates.get(position), new DefaultCallback() {
                @Override
                public void onFinishProcess(boolean hasSucceeded, Object result) {
                    if (hasSucceeded) {
                        //VALLA PARA EL NUEVO ACTIVY
                    } else {
                        //MUESTRE EL ERROR
                    }
                }
            });
//            Intent in = new Intent(activity, MonitorActivity.class);
//            in.putExtra("id", agent.dates.get(position));
//            in.putExtra("fecha", agent.dates.get(position));
//            activity.startActivity(in);
        }
    }

}
