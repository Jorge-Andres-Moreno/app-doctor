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
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        Log.i("Error: ",(LocalDataBase.getInstance(null).getUser() == null)+"");
        return LocalDataBase.getInstance(null).getUser() != null;
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

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_PROFILE)
                            .get()
                            .addHeader("id", firebaseAuth.getInstance().getCurrentUser().getUid())
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        Log.i("funciono", object + "");
                        User user = new User();

                        user.setCedula(object.getString("cedula"));
                        user.setEmail(object.getString("email"));
                        user.setEspecialidad(object.getString("especialidad"));
                        user.setId(object.getString("id"));
                        user.setNombre(object.getString("nombre"));
                        //JSONArray paciente = new JSONArray(object.getString("pacientes"));
                        //Log.i("Cuaja: ",paciente.getString(0));
                        user.setTelefono(object.getInt("telefono"));

                        LocalDataBase.getInstance(null).saveUser(user);
                        /*
                            "cedula": 11448189484,
    "email": "prueba@gmail.com",
    "especialidad": "corazon",
    "id": "VAggJkQTX0cZxoHbgKhnPI2ub0G2",
    "nombre": "prueba ejemplo",
    "pacientes": {
        "id": "0EaBsE2IitYHgz52mNOXmWp4Jey2"
    },
    "telefono": 7777777
                        */
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