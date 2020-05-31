package com.vladimirkomlev.workoutdiaryandroid.activity.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vladimirkomlev.workoutdiaryandroid.R;
import com.vladimirkomlev.workoutdiaryandroid.activity.reset.ResetPasswordActivity;
import com.vladimirkomlev.workoutdiaryandroid.activity.workouts.WorkoutsActivity;
import com.vladimirkomlev.workoutdiaryandroid.model.AuthResponse;
import com.vladimirkomlev.workoutdiaryandroid.utils.Messages;

import static com.vladimirkomlev.workoutdiaryandroid.constant.Constants.PREFERENCES;
import static com.vladimirkomlev.workoutdiaryandroid.utils.Validation.validateEmail;
import static com.vladimirkomlev.workoutdiaryandroid.utils.Validation.validatePassword;

public class SignInActivity extends AppCompatActivity implements SignInView {
    private Toolbar toolbar;
    private TextView toolbarTextView;
    private Button btnSubmit, btnCancel;
    private TextView forgotPassword;
    private MaterialEditText email, password;
    private ProgressDialog progressDialog;

    private SignInPresenter presenter;

    private String token;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        presenter = new SignInPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        toolbarTextView = findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText(R.string.sign_in);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnCancel = findViewById(R.id.btn_cancel);
        btnSubmit = findViewById(R.id.btn_submit);

        forgotPassword = findViewById(R.id.forgotPassword);

        email = findViewById(R.id.email_field);
        password = findViewById(R.id.password_field);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail(email) && validatePassword(password)) {
                    presenter.submit(
                            email.getText().toString().trim(),
                            password.getText().toString().trim()
                    );
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLogInSuccess(String message, AuthResponse authResponse) {
        Messages.showSuccess(this, message);

        token = authResponse.getToken();
        userId = authResponse.getUserId();

        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", authResponse.getToken());
        editor.putLong("userId", authResponse.getUserId());
        editor.apply();

        Intent intent = new Intent(SignInActivity.this, WorkoutsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onLogInFailed(String message) {
        Messages.showError(this, message);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }
}
