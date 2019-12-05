package medical.monitor;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import medical.model.LocalDataBase;
import medical.utils.DefaultCallback;
import medical.utils.ListDuo;
import medical.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentMonitor {

    public static AgentMonitor INSTANCE;

    private String idUser = "";
    public int type;
    public ArrayList<String> dates;
    public ListDuo takes;


    public AgentMonitor(String idUser) {
        this.idUser = idUser;
        this.type = 0;
        dates = new ArrayList<String>();
        takes = new ListDuo();
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

                    RequestBody body = new FormBody.Builder()
                            .add("id", idUser)
                            .add("type", type + "")
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_MONITOR_DATES)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        JSONArray array = object.getJSONArray("tomas");

                        dates = new ArrayList<String>();
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
                            .add("type", type+"")
                            .add("date", date)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_MONITORING)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();


                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        JSONArray array = object.getJSONArray("pulso");

                        takes = new ListDuo();

                        for (int i = 0; i < array.length(); i++) {

                            JSONArray auxArray =  array.getJSONArray(i);
                            String fecha =  auxArray.get(0).toString();
                            String valor =  auxArray.get(1).toString();

                            takes.add(fecha,valor);
                        }

                        Log.i("COUNT_TAKES",takes.size()+"");

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
