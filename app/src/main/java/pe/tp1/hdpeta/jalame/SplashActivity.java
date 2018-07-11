package pe.tp1.hdpeta.jalame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presentLoginActivity();
            }
        }, 3000);
    }

    private void presentLoginActivity() {
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }
}
