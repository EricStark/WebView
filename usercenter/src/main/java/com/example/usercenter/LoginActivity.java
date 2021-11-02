package com.example.usercenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.common.eventbus.LoginEvent;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String KEY_UID = "uid";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CREATED_AT = "created_at";

    private Button btnLogin;
    private Button btnLinkToRegister;
    private Button btnForgotPass;

    private TextInputLayout inputEmail;
    private TextInputLayout inputPassword;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.lTextEmail);
        inputPassword = findViewById(R.id.lTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLinkToRegister = findViewById(R.id.btnLinkToRegisterScreen);
        btnForgotPass = findViewById(R.id.btnForgotPassword);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Hide Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
    }

    private void init() {
        //通过EventBus把用户登录信息发送出去
        btnLogin.setOnClickListener(
                view -> {
                    EventBus.getDefault().post(new LoginEvent(inputEmail.getEditText().getText().toString()));
                    finish();
                });
    }
}