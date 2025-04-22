package com.example.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;


public class SignupActivity extends AppCompatActivity {
    private EditText fullNameInput, usernameInput, passwordInput, confirmPasswordInput;
    private Button createAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullNameInput = findViewById(R.id.full_name);
        usernameInput = findViewById(R.id.signup_username);
        passwordInput = findViewById(R.id.signup_password);
        confirmPasswordInput = findViewById(R.id.confirm_password);
        createAccountBtn = findViewById(R.id.btn_create_account);

        createAccountBtn.setOnClickListener(v -> {
            String name = fullNameInput.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();
            String pass = passwordInput.getText().toString().trim();
            String confirmPass = confirmPasswordInput.getText().toString().trim();

            if (name.isEmpty() || username.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirmPass)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", username);
                editor.putString("password", pass);
                editor.apply();

                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
