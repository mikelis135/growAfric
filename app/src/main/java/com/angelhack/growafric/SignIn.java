package com.angelhack.growafric;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        parent_view = findViewById(android.R.id.content);

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
                new SignInPresenter(SignIn.this).signIn("testtest@test.com", "testtest");
            }
        });
    }

    @Override
    public void onLoginSuccess(@NotNull FirebaseUser user) {
        Log.e("suc", user.getUid()+user.getDisplayName());
        String userid = user.getUid().toString();
        if (!userid.equalsIgnoreCase("")) {
            editor = sharedPref.edit();
            editor.putBoolean("loggedin", true);
            editor.commit();
        }
    }

    @Override
    public void onLoginFailed(@NotNull Exception error) {
        Log.e("suc", error.getMessage().toString());
    }
}
