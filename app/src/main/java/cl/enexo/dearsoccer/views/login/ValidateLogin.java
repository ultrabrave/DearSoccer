package cl.enexo.dearsoccer.views.login;

import com.google.firebase.auth.FirebaseAuth;

import cl.enexo.dearsoccer.views.login.LoginCallback;

/**
 * Created by Kevin on 03-01-2017.
 */

public class ValidateLogin {
    private LoginCallback loginCallback;

    public ValidateLogin(LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
    }

    public void login()
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            loginCallback.okLogin();
        } else {
            loginCallback.failLogin();
        }
    }
}
