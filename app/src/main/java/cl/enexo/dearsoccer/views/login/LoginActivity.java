package cl.enexo.dearsoccer.views.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;

import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.views.config.ConfigActivity;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

public class LoginActivity extends Activity implements LoginCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new ValidateLogin(this).login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            okLoged();
        }
    }

    public void okLoged()
    {
        Intent i = new Intent(this,ConfigActivity.class);
        i.putExtra("WHERE","INIT");
        startActivity(i);
    }

    @Override
    public void okLogin() {
       okLoged();
    }

    @Override
    public void failLogin() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(
                                AuthUI.GOOGLE_PROVIDER,
                                AuthUI.FACEBOOK_PROVIDER)
                        .setIsSmartLockEnabled(false)
                        .setTheme(R.style.FullscreenTheme)
                        .build(),
                RC_SIGN_IN);
    }
}
