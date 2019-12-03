package medical.help;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import medical.utils.DefaultCallback;
import com.android.myapplication.R;

public class HelpActivity extends Activity implements DefaultCallback{

    Spinner spin;
    Button btnSendHelp;
    EditText messagehelp;
    String reasonselected ="";
    ArrayAdapter adapter;
    String[] objects = { "Seleccionar", "Sugerencia", "Queja", "Reclamo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        btnSendHelp = findViewById(R.id.button2);
        messagehelp = findViewById(R.id.editText5);
        spin = findViewById(R.id.spinner);
        adapter= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1 ,objects);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reasonselected = (String) parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSendHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendhelpme(reasonselected,messagehelp.getText().toString());
            }
        });
    }

    public void sendhelpme(String reasonselected, String messagehelp){
        new AgentHelp().sendMessageSupport(reasonselected,messagehelp,this);
    }

    @Override
    public void onFinishProcess(boolean hasSucceeded, Object result) {
    }
}
