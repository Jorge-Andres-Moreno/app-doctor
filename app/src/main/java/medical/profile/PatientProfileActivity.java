package medical.profile;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.myapplication.R;

public class PatientProfileActivity extends AppCompatActivity {

    /**
     * Atributos del paciente
     */
    private TextView name;
    private TextView id;
    private TextView birth_date;
    private TextView age;
    private TextView risk;
    private TextView diagnostic;
    private TextView email;
    private TextView telephone;
    private TextView mobile_number;
    private TextView state;
    private TextView city;
    private TextView address;
    private TextView weight;
    private TextView height;

    private TextView password;
    private TextView password_confirm;

    /**
     * Atributos del contacto del paciente
     */

    private TextView name_contact;
    private TextView telephone_contact;
    private TextView relation;

    /**
     * Atributos de la clase
     */


    /**
     * Atributos de la clase
     */
    private AgentProfile agent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

        agent = new AgentProfile();
        agent.recoverPatient(getIntent().getExtras().getString("patient"));

        // init patient attributes
        name = findViewById(R.id.name);
        name.setText(agent.patient.getName());
        id = findViewById(R.id.id);
        id.setText(agent.patient.getId());
        birth_date = findViewById(R.id.birth_date);
        birth_date.setText(agent.patient.getBirth());
        age = findViewById(R.id.age);
        age.setText(agent.patient.getAge());
        risk = findViewById(R.id.risk);
        risk.setText(agent.patient.getRisk());
        diagnostic = findViewById(R.id.diagnostic);
        diagnostic.setText(agent.patient.getDiagnostic());
        email = findViewById(R.id.email);
        email.setText(agent.patient.getEmail());
        telephone = findViewById(R.id.telephone);
        telephone.setText(agent.patient.getTelephone());
        mobile_number = findViewById(R.id.mobile_number);
        mobile_number.setText(agent.patient.getMobile_number());
        state = findViewById(R.id.state);
        state.setText(agent.patient.getState());
        city = findViewById(R.id.city);
        city.setText(agent.patient.getCity());
        address = findViewById(R.id.address);
        address.setText(agent.patient.getAddress());

        weight = findViewById(R.id.weight);
        weight.setText(agent.patient.getWeight());

        height = findViewById(R.id.height);
        height.setText(agent.patient.getHeight());


        // init contact attributes
        name_contact = findViewById(R.id.name_contact);
        name_contact.setText(agent.patient.getName_contact());
        telephone_contact = findViewById(R.id.telephone_contact);
        telephone_contact.setText(agent.patient.getTelephone_contact());
        relation = findViewById(R.id.relation);
        relation.setText(agent.patient.getRelation());

    }
}
