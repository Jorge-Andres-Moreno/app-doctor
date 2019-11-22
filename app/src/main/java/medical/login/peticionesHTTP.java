package medical.login;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class peticionesHTTP {

private OkHttpClient okHttpClient;
private GetPacientes getPacientes;

    public  peticionesHTTP(){
    }

    public void consultarPacientes(){
        getPacientes = new GetPacientes();
        getPacientes.execute();


    }




    private class GetPacientes extends AsyncTask<String,  Void, String>{

    public GetPacientes(){

    }

    @Override
    protected String doInBackground(String... strings) {

        System.out.println("peticion realizada");

        okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(ConstantesURLS.URLPRINCIPAL).get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String respuesta = response.body().string();
            System.out.println("peticion realizada");
            System.out.println(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}





}
