package com.example.studyguru;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginUI{
    private Button loginButton;
    private Button registerButton;
    private EditText emailTextbox;
    private EditText passwordTextbox;

    public LoginUI(View rootView){
        this.loginButton = (Button) rootView.findViewById(R.id.login_button);
        this.registerButton = (Button) rootView.findViewById(R.id.goToRegister_button);
        this.emailTextbox = (EditText) rootView.findViewById(R.id.email_address_login);
        this.passwordTextbox = (EditText) rootView.findViewById(R.id.password_login);
    }

    public Button getLoginButton() {
        return loginButton;
    }
    public Button getRegisterButton() {
        return registerButton;
    }
    public EditText getEmailTextbox() {
        return emailTextbox;
    }
    public EditText getPasswordTextbox() {
        return passwordTextbox;
    }

}
