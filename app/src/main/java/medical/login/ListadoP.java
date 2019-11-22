package medical.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.myapplication.R;

public class ListadoP extends AppCompatActivity {

    private Button btnPeticion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_p);

        btnPeticion = findViewById(R.id.btnHttp);

        btnPeticion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                peticionesHTTP peticionesHTTP = new peticionesHTTP();

                peticionesHTTP.consultarPacientes();






            }
        });
    }
}
