package com.vladimirkomlev.workoutdiaryandroid.activity.workouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vladimirkomlev.workoutdiaryandroid.R;
import com.vladimirkomlev.workoutdiaryandroid.model.WorkoutResponse;
import com.vladimirkomlev.workoutdiaryandroid.utils.DateConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutViewHolder> {
    private List<WorkoutResponse> workouts;
    private RecyclerViewClickListener mListener;
    private Context context;

    public WorkoutsAdapter(List<WorkoutResponse> workouts, Context context, RecyclerViewClickListener listener) {
        this.workouts = workouts;
        this.mListener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        WorkoutResponse workout = workouts.get(position);
        holder.bind(workout);
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, description, date;
        private CardView cardItem;
        private RecyclerViewClickListener mListener;

        public WorkoutViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            cardItem = itemView.findViewById(R.id.card_item);

            mListener = listener;

            cardItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v, getAdapterPosition());
        }

        private void bind(WorkoutResponse workout) {
            title.setText(workout.getTitle());
            description.setText(workout.getDescription());
            date.setText(DateConverter.toFirstFormat(workout.getDate()));

            cardItem.setCardBackgroundColor(randomColor());
        }

        private Integer randomColor() {
            List<Integer> colors = new ArrayList<>();
            colors.add(context.getResources().getColor(R.color.purple));
            colors.add(context.getResources().getColor(R.color.colorAccent));
            colors.add(context.getResources().getColor(R.color.orange));
            colors.add(context.getResources().getColor(R.color.yellow));
            colors.add(context.getResources().getColor(R.color.white));
            colors.add(context.getResources().getColor(R.color.blue));
            colors.add(context.getResources().getColor(R.color.green));
            colors.add(context.getResources().getColor(R.color.textBottom));
            colors.add(context.getResources().getColor(R.color.red));
            colors.add(context.getResources().getColor(R.color.darkGreen));
            colors.add(context.getResources().getColor(R.color.pink));
            colors.add(context.getResources().getColor(R.color.darkWhite));
            colors.add(context.getResources().getColor(R.color.darkOrange));
            colors.add(context.getResources().getColor(R.color.teal));

            Random random = new Random();
            return colors.get(random.nextInt(colors.size()));
        }
    }

    public interface RecyclerViewClickListener {
        void onItemClick(View view, int position);
    }
}
