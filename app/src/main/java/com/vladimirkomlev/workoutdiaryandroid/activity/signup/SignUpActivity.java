package com.vladimirkomlev.workoutdiaryandroid.activity.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vladimirkomlev.workoutdiaryandroid.R;
import com.vladimirkomlev.workoutdiaryandroid.activity.confirm.ConfirmEmailActivity;
import com.vladimirkomlev.workoutdiaryandroid.model.UserResponse;
import com.vladimirkomlev.workoutdiaryandroid.utils.Messages;

import static com.vladimirkomlev.workoutdiaryandroid.utils.Validation.validateAge;
import static com.vladimirkomlev.workoutdiaryandroid.utils.Validation.validateEmail;
import static com.vladimirkomlev.workoutdiaryandroid.utils.Validation.validateNotEmptyField;
import static com.vladimirkomlev.workoutdiaryandroid.utils.Validation.validatePassword;

public class SignUpActivity extends AppCompatActivity implements SignUpView {
    Button btnSubmit, btnCancel;
    MaterialEditText firstname, lastname, age, email, password;
    TextView toolbarTextView;
    Toolbar toolbar;
    ProgressDialog progressDialog;

    SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        presenter = new SignUpPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        toolbarTextView = findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText(R.string.sign_up);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.signUpBtn));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        firstname = findViewById(R.id.firstname_field);
        lastname = findViewById(R.id.lastname_field);
        age = findViewById(R.id.age_field);
        email = findViewById(R.id.email_field);
        password = findViewById(R.id.password_field);

        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateNotEmptyField(firstname) &&
                        validateNotEmptyField(lastname) &&
                        validateAge(age) &&
                        validateEmail(email) &&
                        validatePassword(password)
                ) {
                    presenter.submit(
                            firstname.getText().toString().trim(),
                            lastname.getText().toString().trim(),
                            Integer.parseInt(age.getText().toString().trim()),
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
    public void onRegisterSuccess(String message, UserResponse userResponse) {
        Messages.showSuccess(this, message);

        Intent intent = new Intent(SignUpActivity.this, ConfirmEmailActivity.class);
        intent.putExtra(UserResponse.class.getSimpleName(), userResponse);
        startActivity(intent);
    }

    @Override
    public void onRegisterFailed(String message) {
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
