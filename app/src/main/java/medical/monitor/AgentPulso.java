package medical.monitor;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import medical.model.MonitorTake;
import medical.utils.DefaultCallback;
import medical.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentPulso {

    public static AgentPulso INSTANCE;

    public int type;
    private String idUser;
    public ArrayList<String> dates;
    public MonitorTake take;


    public AgentPulso(String idUser) {
        this.idUser = idUser;
        this.type = 0;
        dates = new ArrayList<String>();
        INSTANCE = this;
    }


    public void getMonitoringDates(final DefaultCallback notify) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();

                    Log.i("ID",idUser);
                    Log.i("type",type+"");

                    RequestBody body = new FormBody.Builder()
                            .add("id", idUser)
                            .add("type", type + "")
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_MONITOR_DATES)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();
                    Log.i("takes",response.code()+"");
                    if (response.code() == 200) {
                        Log.i("takes","epa2");
                        JSONObject object = new JSONObject(response.body().string());

                        JSONArray array = object.getJSONArray("tomas");

                        dates = new ArrayList<String>();
                        Log.i("takes","epa3");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject aux = new JSONObject(array.get(i).toString());
                            dates.add(aux.getString("fecha"));
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

    public void getDataMonitorDate(final String date, final DefaultCallback notify) {

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
                            .add("type", type + "")
                            .add("date", date)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_MONITOR_DATA)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();


                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        JSONObject monitor = object.getJSONObject(type == 0 ? "pulso" : "ecg");

                        take = new MonitorTake();
                        take.setDate(date);
                        take.setType(type);
                        take.setTime_start(monitor.getString("horaInicio"));
                        take.setTime_finish(monitor.getString("horaFin"));
                        take.setDuration(monitor.getString("duracion"));
                        take.setTime_pos_start(monitor.getString("horaInicio1"));
                        take.setTime_pos_finish(monitor.getString("horaFin1"));

                        JSONArray val_1 = monitor.getJSONArray("valoresPulso");

                        for (int i = 0; i < val_1.length(); i++)
                            take.getTakes_1().add(val_1.getInt(i));

                        JSONArray val_2 = monitor.getJSONArray("valoresPulso2");

                        for (int i = 0; i < val_2.length(); i++)
                            take.getTakes_2().add(val_2.getInt(i));

                        Log.i("takes_1", take.getTakes_1().size() + "");
                        Log.i("takes_2", take.getTakes_2().size() + "");

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
