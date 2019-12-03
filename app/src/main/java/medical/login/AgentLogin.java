package medical.login;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class AgentLogin {
    private FirebaseAuth firebaseAuth;

    public AgentLogin(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        LocalDataBase.getInstance(context);
    }

    public boolean isSingIn() {
        return LocalDataBase.getInstance(null).getUser() != null;//Log.i("Error: ",(LocalDataBase.getInstance(null).getUser() == null)+"");
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        LocalDataBase.getInstance(null).deletedCredentials();
    }

    public void registrar(final String email, final String password, final DefaultCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //saveUIDLogin();
                            getUserData(callback);
                        } else
                            callback.onFinishProcess(false, null);
                    }
                });

            }
        }).start();

    }

    private void getUserData(final DefaultCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(5, TimeUnit.SECONDS)
                            .build();

                    RequestBody body = new FormBody.Builder()
                            .add("type", "1")
                            .add("id", firebaseAuth.getInstance().getCurrentUser().getUid() + "")
                            .build();


                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_PROFILE)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        User user = new User();
                        user.setCedula(object.getString("cedula"));
                        user.setEmail(object.getString("email"));
                        user.setEspecialidad(object.getString("especialidad"));
                        user.setId(object.getString("id"));
                        user.setNombre(object.getString("nombre"));
                        JSONArray paciente = new JSONArray(object.getString("pacientes"));
                        for (int i = 0; i < paciente.length(); i++) {
                            Log.i("..........", "" + paciente.getString(i));
                            // loop and add it to array or arraylist
                        }
                        user.setTelefono(object.getInt("telefono"));

                        LocalDataBase.getInstance(null).saveUser(user);
                        callback.onFinishProcess(true, null);
                    } else
                        callback.onFinishProcess(false, null);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

}