package medical.help;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

import medical.utils.DefaultCallback;
import medical.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import medical.model.LocalDataBase;
import medical.model.User;
public class AgentHelp {

    private FirebaseAuth firebaseAuth;


    public void sendMessageSupport(final String reason, final String message, final DefaultCallback notify) {

        firebaseAuth = FirebaseAuth.getInstance();
        final String uid = firebaseAuth.getUid();
        Log.i("Llorar: ", uid);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();


                    RequestBody body = new FormBody.Builder()
                            .add("userId", uid)
                            .add("reason", reason)
                            .add("name", message)
                            .add("phone", message)
                            .add("type", message)
                            .add("email", message)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_HELP)
                            .put(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.isSuccessful()) {
                        notify.onFinishProcess(true, "success");
                    } else {
                        notify.onFinishProcess(false, "Error intente nuevamente");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    notify.onFinishProcess(false, "Error en el servidor");
                }
            }
        }).start();
    }


}
