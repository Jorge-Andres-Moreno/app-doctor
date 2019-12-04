package medical.profile;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.android.myapplication.R;

public class ProfileActivity extends Activity {

    EditText nombre, correo, cedula, especialidad, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        nombre = findViewById(R.id.nombre);
        correo = findViewById(R.id.correo);
        cedula = findViewById(R.id.cedula);
        especialidad = findViewById(R.id.especialidad);
        telefono = findViewById(R.id.telefono);

    }

}
