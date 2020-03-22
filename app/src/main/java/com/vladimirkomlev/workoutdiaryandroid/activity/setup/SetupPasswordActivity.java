package com.vladimirkomlev.workoutdiaryandroid.activity.setup;

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
import com.vladimirkomlev.workoutdiaryandroid.activity.signin.SignInActivity;
import com.vladimirkomlev.workoutdiaryandroid.utils.Messages;

import static com.vladimirkomlev.workoutdiaryandroid.utils.Validation.validateNotEmptyField;
import static com.vladimirkomlev.workoutdiaryandroid.utils.Validation.validatePassword;

public class SetupPasswordActivity extends AppCompatActivity implements SetupPasswordView {
    Toolbar toolbar;
    TextView toolbarTextView;
    Button btnSubmit, btnCancel;
    MaterialEditText code, password;
    ProgressDialog progressDialog;

    SetupPasswordPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_password);

        presenter = new SetupPasswordPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        toolbarTextView = findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText(R.string.setup_password);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        password = findViewById(R.id.password_field);
        code = findViewById(R.id.confirmation_code);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            String email = (String) args.getSerializable("email");
            TextView setupPasswordDescription = findViewById(R.id.setupPasswordDescription);
            setupPasswordDescription.setText("We have sent a confirmation code on "
                    + email +
                    ". Please check your email," +
                    " enter the code and new password");
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateNotEmptyField(code) && validatePassword(password)) {
                    presenter.submit(
                            code.getText().toString().trim(),
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
    public void onSetupSuccess(String message) {
        Messages.showSuccess(this, message);

        Intent intent = new Intent(SetupPasswordActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSetupFailed(String message) {
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
