package com.vladimirkomlev.workoutdiaryandroid.activity.workouts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vladimirkomlev.workoutdiaryandroid.R;
import com.vladimirkomlev.workoutdiaryandroid.activity.editor.EditorActivity;
import com.vladimirkomlev.workoutdiaryandroid.activity.reset.ResetPasswordActivity;
import com.vladimirkomlev.workoutdiaryandroid.activity.signin.SignInActivity;
import com.vladimirkomlev.workoutdiaryandroid.model.WorkoutResponse;
import com.vladimirkomlev.workoutdiaryandroid.utils.DateConverter;

import java.util.List;

import static com.vladimirkomlev.workoutdiaryandroid.constant.Constants.PREFERENCES;

public class WorkoutsActivity extends AppCompatActivity implements WorkoutsView {
    private static final int INTENT_EDIT = 200;
    private static final int INTENT_ADD = 100;

    Toolbar toolbar;
    SwipeRefreshLayout swipeRefresh;
    TextView toolbarTextView;
    FloatingActionButton addButton;
    RecyclerView recyclerView;

    WorkoutsPresenter presenter;
    WorkoutsAdapter adapter;
    WorkoutsAdapter.RecyclerViewClickListener listener;
    List<WorkoutResponse> workouts;

    SharedPreferences preferences;

    String token;
    long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        toolbarTextView = findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText(R.string.workouts);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefresh = findViewById(R.id.swipe_refresh);

        addButton = findViewById(R.id.add_button);

        recyclerView = findViewById(R.id.workouts_recycler_viewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        token = preferences.getString("token", null);
        userId = preferences.getLong("userId", 0);

        presenter = new WorkoutsPresenter(this);
        presenter.getData(userId, token);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData(userId, token);
            }
        });

        listener = new WorkoutsAdapter.RecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(WorkoutsActivity.this, EditorActivity.class);
                intent.putExtra("id", workouts.get(position).getId());
                intent.putExtra("title", workouts.get(position).getTitle());
                intent.putExtra("date", DateConverter.toFirstFormat(workouts.get(position).getDate()));
                intent.putExtra("description", workouts.get(position).getDescription());
                startActivityForResult(intent, INTENT_EDIT);
            }
        };

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutsActivity.this, EditorActivity.class);
                startActivityForResult(intent, INTENT_ADD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_ADD && resultCode == RESULT_OK) {
            presenter.getData(userId, token);
        } else if (requestCode == INTENT_EDIT && resultCode == RESULT_OK) {
            presenter.getData(userId, token);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                preferences.edit().clear().apply();
                finish();
                Intent intent = new Intent(WorkoutsActivity.this, SignInActivity.class);
                startActivity(intent);
                break;

            case R.id.restorePassword:
                startActivity(new Intent(WorkoutsActivity.this, ResetPasswordActivity.class));
                break;
        }

        return true;
    }

    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<WorkoutResponse> workoutsList) {
        workouts = workoutsList;
        adapter = new WorkoutsAdapter(workouts, this, listener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
