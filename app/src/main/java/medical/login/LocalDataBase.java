package medical.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;

public class LocalDataBase {

    private static LocalDataBase INSTANCE;
    private SharedPreferences preferences;
    private UserAtributtes me;

    public LocalDataBase(Context context) {
        preferences = context.getSharedPreferences("MedicalUIDDoctorUser", 0);
        //loadUser();
    }

    public static LocalDataBase getInstance(Context context) {
        return INSTANCE == null ? (INSTANCE = new LocalDataBase(context)) : INSTANCE;
    }

    public UserAtributtes getNombre() {
        //loadUser();
        return me;
    }

    private void loadUser() {
        Gson gson = new GsonBuilder().create();
        me = gson.fromJson(preferences.getString("email", null), UserAtributtes.class);
        me = gson.fromJson(preferences.getString("id", null), UserAtributtes.class);
        me = gson.fromJson(preferences.getString("nombre", null), UserAtributtes.class);
        me = gson.fromJson(preferences.getString("telefono", null), UserAtributtes.class);
        /*if (me != null)
            NetworkConstants.AWSOME = me.getAwsome();*/
    }



    public void saveUser(UserAtributtes user) {
        Gson gson = new GsonBuilder().create();
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("email", gson.toJson(user, UserAtributtes.class));
        edit.putString("id", gson.toJson(user, UserAtributtes.class));
        edit.putString("nombre", gson.toJson(user, UserAtributtes.class));
        edit.putString("telefono", gson.toJson(user, UserAtributtes.class));
        /*if (edit.commit()) {
            me = user;
            NetworkConstants.AWSOME = me.getAwsome();
        }*/
    }

/*   public void saveAdvisoryOnCourse(Advisory advisory) {
        Gson gson = new GsonBuilder().create();
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("advisory_onCourse", gson.toJson(advisory, Advisory.class));
        edit.commit();
    }
*/

/*    public Advisory getAdvisoryOnCourse() {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(preferences.getString("advisory_onCourse", null), Advisory.class);
    }*/

/*    public void deletedCredentials() {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("user", null);
        edit.putBoolean("optionDialogHomeAlert", true);
        edit.putBoolean("optionDialogOnlineAlert", true);
        edit.commit();
        me = null;
    }

    public boolean getOptionDialogHomeAlert() {
        return preferences.getBoolean("optionDialogHomeAlert", true);
    }

    public void saveOptionDialogHomeAlert(boolean available) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("optionDialogHomeAlert", available);
        edit.commit();
    }

    public boolean getOptionDialogOnlineAlert() {
        return preferences.getBoolean("optionDialogOnlineAlert", true);
    }

    public void saveOptionDialogOnlineAlert(boolean available) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("optionDialogOnlineAlert", available);
        edit.commit();
    }*/

}
