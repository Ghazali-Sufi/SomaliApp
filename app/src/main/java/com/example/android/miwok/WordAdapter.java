package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;
    public WordAdapter(Context context, ArrayList<Word> words, int colorResourceId){
        super(context,0, words);
        mColorResourceId = colorResourceId;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //convertView waa midka noo sahlaayo inaan view access gareeno
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }
        Word currentWord = getItem(position);

        View textContainer = listItemView.findViewById(R.id.text_container);
        //colorka soo qaad
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        //kadib background ka colorkaas kadhig
        textContainer.setBackgroundColor(color);

        TextView somaliTextview = listItemView.findViewById(R.id.somali);
        somaliTextview.setText(currentWord.getSomaliTranslation());

        TextView englishTranslation = listItemView.findViewById(R.id.english);
        englishTranslation.setText(currentWord.getDefaultTranslation());

        ImageView imageView = listItemView.findViewById(R.id.number_image);
        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getmImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }else {
            imageView.setVisibility(View.GONE);
        }


        return listItemView;
    }
}
