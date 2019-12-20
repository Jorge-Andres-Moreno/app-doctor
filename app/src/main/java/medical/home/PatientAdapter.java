package medical.home;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myapplication.R;

import medical.monitor.AgentMonitor;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientHolder> {

    private AgentHome agent;
    private Activity activity;

    public PatientAdapter(AgentHome agent, Activity activity) {
        this.agent = agent;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_patient, parent, false);
        return new PatientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientHolder holder, int position) {
        holder.position = position;
        holder.name.setText(agent.pacientes.get(position).getName());
        holder.age.setText(agent.pacientes.get(position).getAge());
        holder.risk.setText(agent.pacientes.get(position).getRisk());
        holder.diagnostic.setText(agent.pacientes.get(position).getDiagnostic());
    }

    @Override
    public int getItemCount() {

        return agent.pacientes.size();
    }

    public class PatientHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int position;

        private TextView name;
        private TextView age;
        private TextView risk;
        private TextView diagnostic;


        public PatientHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            risk = itemView.findViewById(R.id.risk);
            diagnostic = itemView.findViewById(R.id.diagnostic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            agent.select = agent.pacientes.get(position);
            new AgentMonitor(agent.pacientes.get(position).getUID());
            DialogSelectParameter dialogSelectParameter = new DialogSelectParameter(activity, agent);
            dialogSelectParameter.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogSelectParameter.show();
        }
    }

}
