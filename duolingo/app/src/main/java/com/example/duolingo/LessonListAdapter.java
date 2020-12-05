package com.example.duolingo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LessonListAdapter extends ArrayAdapter<Lesson> {

    private final int resourceLayoutId;
    private final Context context;
    private final List<Lesson> data;

    public LessonListAdapter(Context context, int resourceLayoutId, List<Lesson> data) {
        super(context, resourceLayoutId, data);
        this.resourceLayoutId = resourceLayoutId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(resourceLayoutId, null);
        }

        Lesson lesson = data.get(position);

        TextView txt1 = (TextView)row.findViewById(R.id.lessonNameText);
        TextView txt2 = (TextView)row.findViewById(R.id.scoreText);
        ImageView imgLesson = (ImageView)row.findViewById(R.id.imgLesson);
        ImageView imgStar1 = (ImageView)row.findViewById(R.id.imgStar1);
        ImageView imgStar2 = (ImageView)row.findViewById(R.id.imgStar2);
        ImageView imgStar3 = (ImageView)row.findViewById(R.id.imgStar3);

        txt1.setText(lesson.name);
        txt2.setText(String.format("Score: %s", lesson.score));
        imgLesson.setImageResource(context.getResources().getIdentifier(lesson.levels.get(0).data.get(0), "drawable", context.getPackageName()));

        int lessonMaxScore = 0;
        for (int i = 0; i < lesson.levels.size(); i++) {
            lessonMaxScore += 10;
        }

        if(lesson.score > lessonMaxScore/3 && lesson.score <= lessonMaxScore * 2/3) {
            imgStar1.setImageResource(R.drawable.star_on);
            imgStar2.setImageResource(R.drawable.star_off);
            imgStar3.setImageResource(R.drawable.star_off);
        }
        else if(lesson.score > lessonMaxScore * 2/3 && lesson.score < lessonMaxScore) {
            imgStar1.setImageResource(R.drawable.star_on);
            imgStar2.setImageResource(R.drawable.star_on);
            imgStar3.setImageResource(R.drawable.star_off);
        }
        else if(lesson.score == lessonMaxScore) {
            imgStar1.setImageResource(R.drawable.star_on);
            imgStar2.setImageResource(R.drawable.star_on);
            imgStar3.setImageResource(R.drawable.star_on);
        } else {
            imgStar1.setImageResource(R.drawable.star_off);
            imgStar2.setImageResource(R.drawable.star_off);
            imgStar3.setImageResource(R.drawable.star_off);
        }

        return row;
    }
}
