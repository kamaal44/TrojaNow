package name.yyx.trojanow;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import name.yyx.trojanow.controller.Controller;
import name.yyx.trojanow.widget.ProgressCircle;


public class SettingActivity extends ActionBarActivity {

    private Button btnSignout;
    private ProgressCircle pCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        setTheme(R.style.SettingTheme);

        // load setting fragment
        // do not need to do it again for it has been linked in layout design
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getFragmentManager().beginTransaction()
//            .replace(R.id.setting_fragment, new SettingFragment())
//            .commit();

        pCircle = new ProgressCircle(SettingActivity.this);
        btnSignout = (Button)findViewById(R.id.btn_signout);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pCircle.show();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Controller controller = (Controller)getApplicationContext();
                        controller.signOut();
                        startActivity(new Intent(SettingActivity.this, SigninActivity.class));
                        finish();
                    }
                };
                new Thread(runnable).start();

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // avoid window leak
        if(pCircle != null) {
            pCircle.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static class SettingFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}