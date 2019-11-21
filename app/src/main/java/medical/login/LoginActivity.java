package medical.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.myapplication.R;

import medical.home.HomeActivity;

public class LoginActivity extends Activity {

    public EditText email, con;

    public String correo = "qwert", contraseña ="qwert";

    private Button btn1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
            }
        });
    }

}
