package medical.goals;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.myapplication.R;

import medical.utils.DefaultCallback;

public class GoalDialog extends Dialog {


    public GoalDialog(@NonNull Context context, View v, final DefaultCallback callback) {
        super(context);
        setContentView(R.layout.dialog_goals);
        findViewById(R.id.actionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.actionButton) {
                    try {
                        int calorias = Integer.parseInt(((EditText) findViewById(R.id.calories)).getText() + "");
                        int pasos = Integer.parseInt(((EditText) findViewById(R.id.steps)).getText() + "");
                        AgentGoal.getInstance().setGoals(calorias, pasos, callback);
                        dismiss();

                    } catch (Exception e) {
                        Toast.makeText(v.getContext(), "Por favor ingrese valores correctos", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}
