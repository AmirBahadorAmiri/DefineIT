/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:59 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.DEFINEit.R;
import ir.DEFINEit.model.WordSearchModel;
import ir.DEFINEit.view.activity.ShowWordActivity;

public class WordSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<WordSearchModel> wordModels = new ArrayList<>();
    private boolean isPersian;

    public void setPersian(boolean persian) {
        isPersian = persian;
    }

    public WordSearchAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordSearchHolder(LayoutInflater.from(context).inflate(R.layout.word_search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((WordSearchHolder) holder).bindHolder(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return wordModels.size();
    }

    public void loadData(List<WordSearchModel> newWordModels) {
        wordModels.clear();
        wordModels.addAll(newWordModels);
    }

    private class WordSearchHolder extends RecyclerView.ViewHolder {

        AppCompatTextView word_search_txt, word_search_txt2;

        private WordSearchHolder(@NonNull View itemView) {
            super(itemView);
            word_search_txt = itemView.findViewById(R.id.word_search_from);
            word_search_txt2 = itemView.findViewById(R.id.word_search_to);
        }

        private void bindHolder(View itemView, int position) {

            if (isPersian) {
                word_search_txt.setText(wordModels.get(position).getPersianWord());
                word_search_txt2.setText(wordModels.get(position).getEnglishWord());
            } else {
                word_search_txt2.setText(wordModels.get(position).getPersianWord());
                word_search_txt.setText(wordModels.get(position).getEnglishWord());
            }

            itemView.setOnClickListener(n -> {
                Intent intent = new Intent(context, ShowWordActivity.class);
                intent.putExtra("id", (wordModels.get(position).getId()));
                context.startActivity(intent);
            });

        }

    }

}
