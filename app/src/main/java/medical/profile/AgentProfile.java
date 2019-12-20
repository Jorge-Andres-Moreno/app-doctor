package medical.profile;

import android.util.Log;

import com.google.gson.Gson;

import medical.model.LocalDataBase;
import medical.model.Patient;

public class AgentProfile {

    public Patient patient;

    public LocalDataBase getDB() {
        return LocalDataBase.getInstance(null);
    }

    public void recoverPatient(String patient) {
        this.patient = (new Gson()).fromJson(patient, Patient.class);
    }
}
