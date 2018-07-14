package pe.tp1.hdpeta.jalame.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.DataBase.DBHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                validateUserSession();
            }
        }, 2000);
    }

    private void validateUserSession(){
        DBHelper db = new DBHelper(this);
        PersonBean personBean = db.personBean();

        if (personBean == null) {
            presentLoginActivity();
        } else {
            presentMainActivity();
        }
    }

    private void presentMainActivity(){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void presentLoginActivity() {
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }
}
