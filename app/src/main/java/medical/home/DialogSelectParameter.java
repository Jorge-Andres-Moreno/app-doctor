package medical.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.android.myapplication.R;

import medical.goals.AgentGoal;
import medical.goals.GoalActivity;
import medical.monitor.AgentPulso;
import medical.monitor.MonitorActivity;
import medical.profile.PatientProfileActivity;

public class DialogSelectParameter extends Dialog implements View.OnClickListener {

    private AgentHome agent;

    private CheckBox pulsoBox;
    private CheckBox ecgBox;
    private CheckBox info_check;

    private TextView textInmediate;
    private TextView infoText;
    private TextView textScheduled;


    public DialogSelectParameter(@NonNull Context context, AgentHome agentHome) {
        super(context);
        setContentView(R.layout.dialog_parameter);

        findViewById(R.id.actionButton).setOnClickListener(this);

        agent = agentHome;

        ecgBox = findViewById(R.id.ecgCheck);
        ecgBox.setOnClickListener(this);

        pulsoBox = findViewById(R.id.pulsoCheck);
        pulsoBox.setOnClickListener(this);

        textInmediate = findViewById(R.id.imediateText);
        textInmediate.setOnClickListener(this);

        textScheduled = findViewById(R.id.scheduleText);
        textScheduled.setOnClickListener(this);

        info_check = findViewById(R.id.info_check);
        info_check.setOnClickListener(this);

        infoText = findViewById(R.id.info_text);
        infoText.setOnClickListener(this);
    }


    private void change(int state) {
        if (state == 0) {
            pulsoBox.setChecked(true);
            textInmediate.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_strong));

            ecgBox.setChecked(false);
            textScheduled.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

            info_check.setChecked(false);
            infoText.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

        } else if (state == 1) {

            pulsoBox.setChecked(false);
            textInmediate.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

            ecgBox.setChecked(true);
            textScheduled.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_strong));

            info_check.setChecked(false);
            infoText.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

        } else if (state == 2) {

            pulsoBox.setChecked(false);
            textInmediate.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

            ecgBox.setChecked(false);
            textScheduled.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

            info_check.setChecked(true);
            infoText.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_strong));

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.pulsoCheck:
            case R.id.imediateText:
                change(0);
                break;
            case R.id.ecgCheck:
            case R.id.scheduleText:
                change(1);
                break;
            case R.id.info_check:
            case R.id.info_text:
                change(2);
                break;
            case R.id.closeButton:
                dismiss();
                break;

            case R.id.actionButton:
                if (pulsoBox.isChecked()) {
                    AgentPulso.INSTANCE.type = pulsoBox.isChecked() ? 0 : 1;
                    Intent in = new Intent(v.getContext(), MonitorActivity.class);
                    v.getContext().startActivity(in);
                    this.dismiss();
                } else if (ecgBox.isChecked()) {
                    Intent in = new Intent(v.getContext(), GoalActivity.class);
                    AgentGoal.getInstance().idUser = agent.select.getUID();
                    v.getContext().startActivity(in);
                    this.dismiss();
                } else if (info_check.isChecked()) {

                    Intent in = new Intent(v.getContext(), PatientProfileActivity.class);
                    in.putExtra("patient", agent.serializePatient());
                    v.getContext().startActivity(in);
                    this.dismiss();

                } else
                    Toast.makeText(v.getContext(), "Por favor seleccione una categoría", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
