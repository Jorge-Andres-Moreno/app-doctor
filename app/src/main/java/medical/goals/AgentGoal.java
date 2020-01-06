package medical.goals;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import medical.model.MonitorTake;
import medical.utils.DefaultCallback;
import medical.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentGoal {

    private static AgentGoal INSTANCE;

    public String idUser;
    public MonitorTake take;

    public AgentGoal() {
        INSTANCE = this;
        this.idUser = "";
    }

    public static AgentGoal getInstance() {
        return INSTANCE == null ? (new AgentGoal()) : INSTANCE;
    }


    public void getMetas(final DefaultCallback notify) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();

                    RequestBody body = new FormBody.Builder()
                            .add("id", idUser)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_METAS)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    Log.i("ERROR CODE", "" + response.code());

                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        String[] datosMetas = new String[4];

                        datosMetas[0] = object.getString("PasosAsignados");

                        datosMetas[1] = object.getString("PasosLogrados");

                        datosMetas[2] = object.getString("KgCaloriasAsignadas");

                        datosMetas[3] = object.getString("kgCaloriasLogradas");

                        Log.i("METAS_2", Arrays.toString(datosMetas));
                        notify.onFinishProcess(true, datosMetas);

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    notify.onFinishProcess(false, "Error en el servidor");
                }
            }
        }).start();


    }

    public void setGoals(final int calories, final int steps, final DefaultCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();
                    Log.i("CODE", "" + idUser);


                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",idUser);
                    jsonObject.put("KgCaloriasAsignadas",calories);
                    jsonObject.put("PasosAsignados",steps);

                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, jsonObject.toString());

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_GOALS_UPDATE)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    Log.i("CODE", "" + response.code());

                    callback.onFinishProcess(response.code() == 200, null);

                } catch (Exception e) {
                    callback.onFinishProcess(false, null);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getDataMonitorDateHome(final String type, final DefaultCallback notify) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();

                    RequestBody body = new FormBody.Builder()
                            .add("id", idUser)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_LAST_TAKE)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    Log.i("ERROR CODE", "" + response.code());


                    if (response.code() == 200) {

                        System.out.println("Si entre al 200");

                        JSONObject object = new JSONObject(response.body().string());

                        JSONObject monitor = object;

                        take = new MonitorTake();
                        take.setType(Integer.parseInt(type));
                        take.setTime_start(monitor.getString("horaInicio"));
                        take.setTime_finish(monitor.getString("horaFin"));
                        take.setDuration(monitor.getString("duracion"));
                        take.setTime_pos_start(monitor.getString("horaInicio1"));
                        take.setTime_pos_finish(monitor.getString("horaFin1"));
                        take.setPasos(monitor.getString("pasos"));
                        take.setDuration(monitor.getString("duracion"));
                        take.setPulsoPromedio(monitor.getString("promedioPulso"));
                        take.setPulsoMinimo(monitor.getString("menorPulso"));
                        take.setPulsoMaximo(monitor.getString("mayorPulso"));
                        take.setPulsoPromedio1(monitor.getString("promedioPulso1"));
                        take.setPulsoMinimo1(monitor.getString("menorPulso1"));
                        take.setPulsoMaximo1(monitor.getString("mayorPulso1"));
                        take.setKgCalorias(monitor.getString("calorias"));

                        JSONArray val_1 = monitor.getJSONArray("valoresPulso");

                        for (int i = 0; i < val_1.length(); i++)
                            take.getTakes_1().add(val_1.getInt(i));

                        JSONArray val_2 = monitor.getJSONArray("valoresPulso2");

                        for (int i = 0; i < val_2.length(); i++)
                            take.getTakes_2().add(val_2.getInt(i));

                        Log.i("takes_1", take.getTakes_1().size() + "");
                        Log.i("takes_2", take.getTakes_2().size() + "");

                        notify.onFinishProcess(true, "Data");
                    } else {
                        notify.onFinishProcess(false, "No data");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    notify.onFinishProcess(false, "Error en el servidor");
                }
            }
        }).start();
    }


}
