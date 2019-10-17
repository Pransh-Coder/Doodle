package com.proxima.elearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesAdapter extends BaseAdapter {
    ArrayList<GetNotes> getNotes;
    TextView topic,chapter,reply,note;
    LayoutInflater layoutInflater;
    Context context;
    public NotesAdapter(Context context, ArrayList<GetNotes> getNotes) {
        this.context = context;
        this.getNotes = getNotes;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return getNotes.size();
    }

    @Override
    public Object getItem(int i) {
        return getNotes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=layoutInflater.inflate(R.layout.notes_layout,null);
        TextView chapter,topic,note;
        chapter = view.findViewById(R.id.noteChapter);
        topic = view.findViewById(R.id.noteTopic);
        note = view.findViewById(R.id.note);
        chapter.setText(getNotes.get(i).getChapter());
        topic.setText(getNotes.get(i).getTopic());
        note.setText(getNotes.get(i).getNote());
        return view;
    }
}
