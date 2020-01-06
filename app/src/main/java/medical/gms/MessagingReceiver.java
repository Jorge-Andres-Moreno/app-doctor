package medical.gms;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.myapplication.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import medical.home.HomeActivity;
import medical.login.SplashScreenActivity;
import medical.model.LocalDataBase;
import medical.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.myapplication.R.color.colorAccent;


/**
 * Created by camilo on 2/16/18.
 */

public class MessagingReceiver extends FirebaseMessagingService {

    // This is the Notification Channel ID. More about this in the next section
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    //User visible Channel Name
    public static final String CHANNEL_NAME = "Notification Channel";
    // Importance applicable to all the notifications in this Channel
    int importance = NotificationManager.IMPORTANCE_DEFAULT;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if (LocalDataBase.getInstance(this).getUser() != null)
            try {

                OkHttpClient okhttp = new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .build();

                RequestBody body = new FormBody.Builder()
                        .add("type", "1")
                        .add("token", s)
                        .add("id", LocalDataBase.getInstance(this).getUser().getUID())
                        .build();


                Request request = new Request.Builder()
                        .url(NetworkConstants.URL + NetworkConstants.PATH_UPDATE_TOKEN)
                        .post(body)
                        .build();

                Response response = okhttp.newCall(request).execute();

                if (response.code() == 200) {
                    Log.i("token", s);
                    Log.i("success", "Update token");
                } else
                    Log.e("failed", "Failed update token");

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        createNotificationChannel();

        if (LocalDataBase.getInstance(this).getUser() != null)
            try {
//                JSONObject data = new JSONObject(remoteMessage.getData());
                Log.i("Notification_receiver", remoteMessage.getData() + "");
//                int type = Integer.parseInt(data.getString("nType"));

                String title = "Nueva sesión terminada";
                String content = "Un paciente ha terminado una sesión de entreno";
                String info = "no tiene contenido pero es mi primera notificacion";

                try {

                    JSONObject json = new JSONObject(remoteMessage.getData());
                    content = "El paciente " + json.getString("nombre") + " ha finalizado una nueva sesión de entreno. Revisa los detalles";

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                createNotification(title, info, content, intent);

            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = "descripcion del canal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(getResources().getColor(R.color.colorPrimary));
            channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            channel.enableVibration(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void createNotification(String title, String info, String content, Intent myintent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setSubText("Medical")
                .setContentText(info)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, builder.build());

    }
}
