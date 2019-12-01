package medical.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;

import com.android.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import medical.login.LocalDataBase;

import medical.home.HomeActivity;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText txtCorreo;
    private EditText txtContraseña;
    private Button btnLogin;
    public LocalDataBase objldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();


        txtCorreo = findViewById(R.id.email);
        txtContraseña = findViewById(R.id.password);
        btnLogin = findViewById(R.id.actionButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loguearUsuario();

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));
        }
    }

    public void saveUIDLogin(String identified) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("luid.bin", Activity.MODE_PRIVATE));
            osw.write(identified.trim());
            osw.flush();
            osw.close();
            Toast.makeText(getApplicationContext(), "Almacenando: " + identified, Toast.LENGTH_LONG).show();
        } catch (IOException ex) {
            Toast.makeText(getApplicationContext(), "Oops falla: " + ex, Toast.LENGTH_LONG).show();
        }
    }

    public void loguearUsuario() {

        String email = txtCorreo.getText().toString().trim();
        String password = txtContraseña.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    try {
                        saveUIDLogin(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Falla: " + e, Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Correo o Contraseña Incorrecta", Toast.LENGTH_LONG).show();
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

