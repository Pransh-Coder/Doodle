package com.proxima.elearning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterDiscussion_forum  extends RecyclerView.Adapter<RecyclerAdapterDiscussion_forum.ViewHolder> implements Filterable {

    RequestQueue queue;

    Context context;
    List<Questions> questionsList = new ArrayList<>();
    List<Questions> data = new ArrayList<>();
    private ArrayList<Questions> dataFull;

    public RecyclerAdapterDiscussion_forum(Context context, List<Questions> questions) {
        this.context = context;
        this.questionsList = questions;
        dataFull = new ArrayList<>(questionsList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.question.setText("Q."+questionsList.get(position).getQuestions()+".......");
        holder.quesTitle.setText(questionsList.get(position).getQuesTitle());
        holder.ques_Authors.setText("Name: "+questionsList.get(position).getAuth_of_ques());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q_id = questionsList.get(position).getQues_id();
                // Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences=context.getSharedPreferences("questions_id",context.MODE_PRIVATE);
                String a= sharedPreferences.getString("id","");   // we are receving this u_id from Login Activity this u_id is response
                System.out.println(a);

                Intent intent = new Intent(context.getApplicationContext(),QuestionDetails.class);
                intent.putExtra("id",q_id);
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView quesTitle,question,ques_Authors;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quesTitle = itemView.findViewById(R.id.ques_title);
            question = itemView.findViewById(R.id.question);
            ques_Authors = itemView.findViewById(R.id.auth_ques);
            constraintLayout = itemView.findViewById(R.id.constraintView2);
        }
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Questions> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length()==0)
            {
                filteredList.addAll(dataFull);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Questions item : dataFull)
                {
                    if (item.getQuesTitle().toLowerCase().startsWith(filterPattern))
                    {
                        filteredList.add(item);

                    }
                    else if (item.getQuestions().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                    else if (item.getAuth_of_ques().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                    else {

                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            questionsList.clear();
            questionsList.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };
}
