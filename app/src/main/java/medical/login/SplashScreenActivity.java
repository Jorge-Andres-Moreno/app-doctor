package medical.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.myapplication.R;

import medical.MainActivity;

public class SplashScreenActivity extends Activity {

    private final int TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        final Activity me = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(TIME);
                    me.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(me, LoginActivity.class);
                            me.startActivity(i);
                            finish();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
