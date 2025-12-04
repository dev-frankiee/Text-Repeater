package com.example.textrepeater;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText loginPass, loginEmail;
    MaterialButton matBut;
    TextView switchToSignup;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize
        switchToSignup = findViewById(R.id.signup);
        loginPass = findViewById(R.id.loginPass);
        loginEmail = findViewById(R.id.loginEmail);
        matBut = findViewById(R.id.matBut);
        mAuth = FirebaseAuth.getInstance();

        // Go to signup page
        switchToSignup.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
            finish();
        });

        // Login button
        matBut.setOnClickListener(v -> {
            String email = loginEmail.getText().toString().trim();
            String password = loginPass.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(Login.this, "Logging in...", Toast.LENGTH_SHORT).show();

            // ✅ Use signInWithEmailAndPassword for login
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login successful!", Toast.LENGTH_SHORT).show();

                            // Navigate to welcome screen
                            Intent intent = new Intent(Login.this, Repeater.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login.this,
                                    "Login failed: " + Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}