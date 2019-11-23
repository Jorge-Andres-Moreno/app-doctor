package medical.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;

import com.android.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import medical.home.HomeActivity;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText txtCorreo;
    private EditText txtContraseña;
    private Button btnLogin;

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
    }

    public void loguearUsuario() {

        String email = txtCorreo.getText().toString().trim();
        String password = txtContraseña.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Se debe de ingresar un email", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Se debe de ingresar una contraseña", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
/*
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);


                    } else {

                        Toast.makeText(getApplicationContext(), "Correo o Contraseña Incorrecta", Toast.LENGTH_LONG).show();
                    }

                }
            });*/
        }
    }

}
