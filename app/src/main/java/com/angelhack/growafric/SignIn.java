package com.angelhack.growafric;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.angelhack.growafric.views.SignInView;
import com.balysv.materialripple.MaterialRippleLayout;
import com.google.firebase.auth.FirebaseUser;
import com.angelhack.growafric.presenters.SignInPresenter;

import org.jetbrains.annotations.NotNull;

public class SignIn extends AppCompatActivity implements SignInView{

    private View parent_view;
    private MaterialRippleLayout signIn;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private TextInputEditText email, password;
    private String _email, _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        parent_view = findViewById(android.R.id.content);
        email = findViewById(R.id.email);

        password = findViewById(R.id.password);
        ( findViewById(R.id.forgot_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent_view, "Forgot Password", Snackbar.LENGTH_SHORT).show();
            }
        });
        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _email = email.getText().toString();
                _password = password.getText().toString();
                new SignInPresenter(SignIn.this).signIn(_email, _password);
            }
        });
    }

    @Override
    public void onLoginSuccess(@NotNull FirebaseUser user) {
        Log.e("suc", user.getUid()+user.getDisplayName());
        String userid = user.getUid().toString();
        String displayname = user.getDisplayName().toString();
        String email = user.getEmail().toString();
        sharedPref = getBaseContext().getSharedPreferences("com.angelhack.growafric.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
            editor = sharedPref.edit();
            editor.putBoolean("loggedin", true);
            editor.putString("userid", userid);
            editor.putString("displayname", displayname);
            editor.putString("email", email);
            editor.commit();
         Intent intent = new Intent(SignIn.this, Home.class);
            startActivity(intent);
    }

    @Override
    public void onLoginFailed(@NotNull Exception error) {
        Log.e("suc", error.getMessage().toString());
    }
}
