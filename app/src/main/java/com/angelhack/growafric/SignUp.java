package com.angelhack.growafric;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.angelhack.growafric.views.RegisterView;
import com.balysv.materialripple.MaterialRippleLayout;
import com.google.firebase.auth.FirebaseUser;;
import com.angelhack.growafric.presenters.RegisterPresenter;

import org.jetbrains.annotations.NotNull;

public class SignUp extends AppCompatActivity implements RegisterView{

    private MaterialRippleLayout register;
    private TextInputEditText fullname, email, password;
    private String _fullname, _email, _password;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       // initToolbar();
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        sharedPref = getBaseContext().getSharedPreferences("com.angelhack.growafric.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);

        register.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _fullname = fullname.getText().toString();
                _email = email.getText().toString();
                _password = password.getText().toString();
                Log.e("pas", _email+ _password+ _fullname+"");
                new RegisterPresenter(SignUp.this).signUp( _email, _password, _fullname);

            }
        });
            //How you would register a user

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProcessing() {

    }

    @Override
    public void onRegisterSuccess(@NotNull FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        Log.e("userid", userId+""+firebaseUser.getDisplayName()+firebaseUser.getEmail());
        Intent intent = new Intent(SignUp.this, SignIn.class);
        if (!sharedPref.getString("userid", "").equalsIgnoreCase("")) {
            editor = sharedPref.edit();
            editor.putString("userid", userId.toString());
            editor.putString("displayname", firebaseUser.getDisplayName().toString());
            editor.putString("email", firebaseUser.getEmail().toString());
            editor.commit();
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterFailed(@NotNull Exception error) {

    }
}
