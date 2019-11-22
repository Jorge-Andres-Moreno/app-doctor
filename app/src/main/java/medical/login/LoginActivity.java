package medical.login;

<<<<<<< HEAD
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
=======
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
>>>>>>> ac9222a3db2c6027962fd86a4e77fafc6184139f

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import medical.MainActivity;


<<<<<<< HEAD
import medical.home.HomeActivity;

public class LoginActivity extends Activity {
=======
public class LoginActivity extends AppCompatActivity  {

    private FirebaseAuth firebaseAuth;
    private EditText txtCorreo;
    private EditText txtContraseña;
    private Button btnLogin;
>>>>>>> ac9222a3db2c6027962fd86a4e77fafc6184139f

    public EditText email, con;

    public String correo = "qwert", contraseña ="qwert";

    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
<<<<<<< HEAD
        email = findViewById(R.id.email);
        con = findViewById(R.id.password);

        btn1 = findViewById(R.id.actionButton);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                if (con.getText().toString().equals(correo) && email.getText().toString().equals(contraseña)) {
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(), "incorrecto"+email.getText()+con.getText(), Toast.LENGTH_LONG).show();
                }
=======


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

    public void loguearUsuario(){

        String email = txtCorreo.getText().toString().trim();
        String password = txtContraseña.getText().toString().trim();

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Se debe de ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Se debe de ingresar una contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, ListadoP.class);
                    startActivity(intent);



                }else{

                    Toast.makeText(getApplicationContext(),"Correo o Contraseña Incorrecta",Toast.LENGTH_LONG).show();
                }

>>>>>>> ac9222a3db2c6027962fd86a4e77fafc6184139f
            }
        });
    }

<<<<<<< HEAD
=======

>>>>>>> ac9222a3db2c6027962fd86a4e77fafc6184139f
}
