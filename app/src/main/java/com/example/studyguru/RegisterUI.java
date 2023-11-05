package com.example.studyguru;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUI {
    private Button reg;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText con_pass;

    public RegisterUI(View rootview){
        this.reg = (Button) rootview.findViewById(R.id.registration_button);
        this.email = (EditText) rootview.findViewById(R.id.email_address_reg);
        this.username = (EditText) rootview.findViewById(R.id.username_reg);
        this.password = (EditText) rootview.findViewById(R.id.password_reg);
        this.con_pass = (EditText) rootview.findViewById(R.id.confirm_password_reg);
    }

    public Button getReg() {
        return reg;
    }

    public EditText getEmail() {
        return email;
    }

    public EditText getUsername() {
        return username;
    }

    public EditText getPassword() {
        return password;
    }

    public EditText getCon_pass() {
        return con_pass;
    }
}
