package com.afrovision.gaad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afrovision.gaad.LoadImage;
import com.afrovision.gaad.R;
import com.afrovision.gaad.model.Person;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Person> list;
    private Boolean isSkill;

    public ListAdapter(Context context, List<Person> list, Boolean isSkill) {
        this.context = context;
        this.list = list;
        this.isSkill = isSkill;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isSkill) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learner_item, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Person person = list.get(position);
        ((ViewHolder) holder).name.setText(person.getName());
        ((ViewHolder) holder).description.setText(person.getScore()+", "+person.getCountry());
        if(isSkill){
            new LoadImage(person.getImage()).loadSkillImage(context,((ViewHolder) holder).profile);
        }else{
            new LoadImage(person.getImage()).loadLearningImage(context,((ViewHolder) holder).profile);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, description;
        ImageView profile;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            View view = itemView;
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.desc);
            profile = view.findViewById(R.id.image);
        }


    }

}