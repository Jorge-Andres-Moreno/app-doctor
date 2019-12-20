package medical.home;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import medical.model.LocalDataBase;
import medical.model.Patient;
import medical.utils.DefaultCallback;
import medical.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentHome {

    public ArrayList<Patient> pacientes;

    public Patient select;

    public AgentHome() {
        pacientes = new ArrayList<>();
    }

    public LocalDataBase getLocalDB() {
        return LocalDataBase.getInstance(null);
    }

    public String serializePatient(){
        return (new Gson()).toJson(select, Patient.class);
    }


    public void getPatientList(final DefaultCallback notify) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();

                    RequestBody body = new FormBody.Builder()
                            .add("id", LocalDataBase.getInstance(null).getUser().getUID())
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_PATIENT)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        JSONArray array = object.getJSONArray("pacientes");

                        pacientes = new ArrayList<Patient>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject aux = new JSONObject(array.get(i).toString());
                            JSONObject inf = aux.getJSONObject("informacion");

                            Patient patient = new Patient();
                            patient.setUID(inf.getString("id"));
                            patient.setName(inf.getString("nombre"));
                            patient.setId(inf.getString("cedula"));
                            patient.setBirth(inf.getString("fecha_nacimiento"));
                            patient.setAge(inf.getString("edad"));
                            patient.setRisk(inf.getString("riesgo"));
                            patient.setDiagnostic(inf.getString("diagnostico"));
                            patient.setEmail(inf.getString("email"));
                            patient.setMobile_number(inf.getString("celular"));

                            try {
                                patient.setTelephone(inf.getString("telefono"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            patient.setState(inf.getString("departamento"));
                            patient.setCity(inf.getString("ciudad"));
                            patient.setAddress(inf.getString("direccion"));
                            patient.setRef(inf.getString("ref"));

                            JSONObject inf_contact = inf.getJSONObject("contacto");
                            patient.setName_contact(inf_contact.getString("nombre"));
                            patient.setTelephone_contact(inf_contact.getString("telefono"));
                            patient.setRelation(inf_contact.getString("parentesco"));

                            pacientes.add(patient);
                        }

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
