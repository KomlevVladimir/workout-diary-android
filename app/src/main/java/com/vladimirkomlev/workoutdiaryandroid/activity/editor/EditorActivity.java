package com.vladimirkomlev.workoutdiaryandroid.activity.editor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.vladimirkomlev.workoutdiaryandroid.R;
import com.vladimirkomlev.workoutdiaryandroid.activity.workouts.WorkoutsActivity;
import com.vladimirkomlev.workoutdiaryandroid.utils.DateConverter;
import com.vladimirkomlev.workoutdiaryandroid.utils.Messages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.vladimirkomlev.workoutdiaryandroid.constant.Constants.PREFERENCES;
import static com.vladimirkomlev.workoutdiaryandroid.utils.Validation.validateNotEmptyField;

public class EditorActivity extends AppCompatActivity implements EditorView {
    private MaterialEditText etTitle, etDate, etDescription;
    private Calendar calendar = Calendar.getInstance();
    private String title, date, description;
    private long id;
    private Toolbar toolbar;
    private TextView toolbarTextView;
    private Menu menu;
    private ProgressDialog progressDialog;

    private String token;
    private long userId;

    EditorPresenter presenter;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        presenter = new EditorPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        toolbarTextView = findViewById(R.id.toolbar_text_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        etTitle = findViewById(R.id.title);
        etDate = findViewById(R.id.date);
        etDescription = findViewById(R.id.description);

        etDate.setFocusableInTouchMode(false);
        etDate.setFocusable(false);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditorActivity.this, onDateSetListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        userId = preferences.getLong("userId", 0);
        token = preferences.getString("token", null);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            id = (Long) args.getSerializable("id");
            title = (String) args.getSerializable("title");
            date = (String) args.getSerializable("date");
            description = (String) args.getSerializable("description");
        }

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();
            toolbarTextView.setText(title);

            etTitle.setText(title);
            etDate.setText(date);
            etDescription.setText(description);

        } else {
            toolbarTextView.setText("Add a workout");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        this.menu = menu;
        this.menu.findItem(R.id.menu_save).setVisible(false);

        if (id == 0) {
            this.menu.findItem(R.id.menu_edit).setVisible(false);
            this.menu.findItem(R.id.menu_delete).setVisible(false);
            this.menu.findItem(R.id.menu_save).setVisible(true);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.menu_edit:
                editMode();

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(etTitle, InputMethodManager.SHOW_IMPLICIT);

                this.menu.findItem(R.id.menu_edit).setVisible(false);
                this.menu.findItem(R.id.menu_delete).setVisible(false);
                this.menu.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:
                if (id == 0) {
                    if (validateNotEmptyField(etTitle) &&
                            validateNotEmptyField(etDescription) &&
                            validateNotEmptyField(etDate)
                    ) {
                        presenter.saveWorkout(
                                userId,
                                token,
                                etTitle.getText().toString().trim(),
                                DateConverter.toSecondFormat(etDate.getText().toString().trim()),
                                etDescription.getText().toString().trim()
                                );

                        this.menu.findItem(R.id.menu_edit).setVisible(true);
                        this.menu.findItem(R.id.menu_save).setVisible(false);
                        this.menu.findItem(R.id.menu_delete).setVisible(true);

                        readMode();
                    }
                } else {
                    if (validateNotEmptyField(etTitle) &&
                            validateNotEmptyField(etDescription) &&
                            validateNotEmptyField(etDate)
                    ) {
                        presenter.updateWorkout(
                                userId,
                                id,
                                token,
                                etTitle.getText().toString().trim(),
                                DateConverter.toSecondFormat(etDate.getText().toString().trim()),
                                etDescription.getText().toString().trim()
                        );

                        this.menu.findItem(R.id.menu_edit).setVisible(true);
                        this.menu.findItem(R.id.menu_save).setVisible(false);
                        this.menu.findItem(R.id.menu_delete).setVisible(true);

                        readMode();
                    }
                }

                return true;

            case R.id.menu_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorActivity.this);
                dialog.setMessage("Delete this workout?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        presenter.deleteWorkout(userId, id, token);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDate();
        }

    };

    public void setDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        etDate.setText(sdf.format(calendar.getTime()));
    }

    private void readMode() {
        etTitle.setFocusableInTouchMode(false);
        etTitle.setFocusable(false);
        etDate.setEnabled(false);
        etDescription.setFocusableInTouchMode(false);
        etDescription.setFocusable(false);
    }

    private void editMode() {
        etTitle.setFocusableInTouchMode(true);
        etTitle.setFocusable(true);
        etDate.setEnabled(true);
        etDescription.setFocusableInTouchMode(true);
        etDescription.setFocusable(true);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onRequestSuccess(String message) {
        Messages.showSuccess(this, message);

        Intent intent = new Intent(EditorActivity.this, WorkoutsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestFailed(String message) {
        Messages.showError(this, message);
    }
}
