package medical.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.myapplication.R;

import medical.home.HomeActivity;
import medical.utils.DefaultCallback;


public class LoginActivity extends AppCompatActivity implements DefaultCallback {

    private AgentLogin agentLogin;
    private EditText txtCorreo;
    private EditText txtContraseña;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        agentLogin = new AgentLogin(this);

        txtCorreo = findViewById(R.id.email);
        txtContraseña = findViewById(R.id.password);
        btnLogin = findViewById(R.id.actionButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loguearUsuario();
            }
        });

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));
    }

    public void loguearUsuario() {

        String email = txtCorreo.getText().toString().trim();
        String password = txtContraseña.getText().toString().trim();

        agentLogin.registrar(email, password, this);

    }

    @Override
    public void onFinishProcess(final boolean hasSucceeded, Object result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (hasSucceeded) {
                    Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "fail login", Toast.LENGTH_SHORT);
                }
            }
        });

    }
}


/* Basuras si las moscas
    /*
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Se debe de ingresar un email", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Se debe de ingresar una contraseña", Toast.LENGTH_LONG).show();

        } else {
            //Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();*/

