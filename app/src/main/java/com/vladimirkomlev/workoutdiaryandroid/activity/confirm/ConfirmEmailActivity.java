package com.vladimirkomlev.workoutdiaryandroid.activity.confirm;

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
import com.vladimirkomlev.workoutdiaryandroid.model.UserResponse;
import com.vladimirkomlev.workoutdiaryandroid.utils.Messages;

import static com.vladimirkomlev.workoutdiaryandroid.utils.Validation.validateNotEmptyField;

public class ConfirmEmailActivity extends AppCompatActivity implements ConfirmEmailView {
    Toolbar toolbar;
    TextView toolbarTextView;
    Button btnSubmit, btnCancel;
    MaterialEditText code;
    ProgressDialog progressDialog;

    ConfirmEmailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);

        presenter = new ConfirmEmailPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        toolbarTextView = findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText(R.string.confirm_email);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        code = findViewById(R.id.confirmation_code);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            UserResponse userResponse = (UserResponse) args.getSerializable(UserResponse.class.getSimpleName());
            TextView confirmEmailDescription = findViewById(R.id.confirmEmailDescription);
            confirmEmailDescription.setText(userResponse.getFirstName() +
                    ", we have sent a confirmation code on "
                    + userResponse.getEmail() +
                    ". Please check your email and" +
                    " enter the code to confirm your email.");
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
                if (validateNotEmptyField(code)) {
                    presenter.submit(code.getText().toString().trim());
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
    public void onConfirmSuccess(String message) {
        Messages.showSuccess(this, message);

        Intent intent = new Intent(ConfirmEmailActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConfirmFailed(String message) {
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
