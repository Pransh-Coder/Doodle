package com.proxima.elearning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerAdapterAttendance extends RecyclerView.Adapter<RecyclerAdapterAttendance.ViewHolder> {

    Context context;
    List<AttendanceModelClass> attendanceList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String Course;

    public RecyclerAdapterAttendance(Context context, List<AttendanceModelClass> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_list,parent,false);
        return new RecyclerAdapterAttendance.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.name.setText(attendanceList.get(position).getName());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*sharedPreferences = context.getSharedPreferences("Login", MODE_PRIVATE);
                Course = sharedPreferences.getString("course", "");*/
                String stud_id= attendanceList.get(position).getId();

                Intent intent = new Intent(context.getApplicationContext(),GiveMarks.class);
                intent.putExtra("Student_id",stud_id);   // sending student_id to GiveMarks Activity
                Toast.makeText(context, ""+stud_id, Toast.LENGTH_SHORT).show();
                context.startActivity(intent);


            }
        });
    }


    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView name;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.Name);
            constraintLayout = itemView.findViewById(R.id.constraintView2);
        }
    }
}
