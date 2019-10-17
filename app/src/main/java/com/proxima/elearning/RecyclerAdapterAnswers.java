package com.proxima.elearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterAnswers extends RecyclerView.Adapter<RecyclerAdapterAnswers.ViewHolder> {

    Context context;
    List<Answer> answerList = new ArrayList<>();

    public RecyclerAdapterAnswers(Context context, List<Answer> answerList) {
        this.context = context;
        this.answerList = answerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answers_list,parent,false);
        return new RecyclerAdapterAnswers.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(answerList.get(position).getTitle());
        holder.ans.setText(answerList.get(position).getAnswer());
        holder.ans_by.setText(answerList.get(position).getAnswer_by());
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,ans,ans_by;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.ans_title);
            ans=itemView.findViewById(R.id.ans);
            ans_by=itemView.findViewById(R.id.answerBy);

        }
    }
}
