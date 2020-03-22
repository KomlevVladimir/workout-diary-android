package com.vladimirkomlev.workoutdiaryandroid.activity.reset;

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
import com.vladimirkomlev.workoutdiaryandroid.activity.setup.SetupPasswordActivity;
import com.vladimirkomlev.workoutdiaryandroid.utils.Messages;
import com.vladimirkomlev.workoutdiaryandroid.utils.Validation;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordView {
    Toolbar toolbar;
    TextView toolbarTextView;
    Button btnSubmit, btnCancel;
    MaterialEditText email;
    ProgressDialog progressDialog;

    ResetPasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        presenter = new ResetPasswordPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        toolbarTextView = findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText(R.string.reset_password);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnCancel = findViewById(R.id.btn_cancel);
        btnSubmit = findViewById(R.id.btn_submit);

        email = findViewById(R.id.email_field);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.validateEmail(email)) {
                    presenter.submit(email.getText().toString().trim());
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
    public void onResetSuccess(String message) {
        Messages.showSuccess(this, message);

        Intent intent = new Intent(ResetPasswordActivity.this, SetupPasswordActivity.class);
        intent.putExtra("email", email.getText().toString().trim());
        startActivity(intent);
    }

    @Override
    public void onResetFailed(String message) {
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
