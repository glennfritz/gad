package com.afrovision.gaad;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class LoadImage {
    String url;

    public LoadImage(String url) {
        this.url = url;
    }

    public void loadSkillImage(Context context,ImageView v){
        if (!url.equalsIgnoreCase(""))
            Picasso.with(context).load(url).placeholder(R.drawable.skill)
                    .into(v);
    }

    public void loadLearningImage(Context context,ImageView v){
        if (!url.equalsIgnoreCase(""))
            Picasso.with(context).load(url).placeholder(R.drawable.learner)
                    .into(v);
    }


}
