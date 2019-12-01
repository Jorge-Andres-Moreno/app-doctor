package medical.login;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;


import com.android.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import medical.home.HomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        firebaseAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if(firebaseUser ==null) {

                    /*si (existe archivo de shared preferences){
                    * capturar los datos e intentar loguear}
                    * si no{*/
                        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                    /*}*/
                }else{

                    Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(intent);

                }
                finish();
            }
        }, 3000);
    }

    public void readuidlogin(){
        String archivos[] = fileList();
        String filename = "luid.bin";

        if (fileexist(archivos, filename) ){
            try{
                InputStreamReader isr = new InputStreamReader(openFileInput(filename));
                BufferedReader br = new BufferedReader(isr);
                String saveuid = br.readLine();
                br.close();
                isr.close();
                Toast.makeText(getApplicationContext(), "Recuperado: " + saveuid, Toast.LENGTH_LONG).show();
            }catch (IOException ex){

            }

        }
    }
    private boolean fileexist(String[] files, String filename) {
        for (int i = 0; i < files.length ; i++)
            if (filename.equals(files[i]))
                return true;
        return false;
    }
}

