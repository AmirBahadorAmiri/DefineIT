/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:57 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.DEFINEit.R;
import ir.DEFINEit.model.WordSearchModel;
import ir.DEFINEit.tools.database.DBM;
import ir.DEFINEit.view.activity.ShowWordActivity;

public class WordTaggedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<WordSearchModel> wordModels;
    private boolean isPersian;

    public void setPersian(boolean persian) {
        isPersian = persian;
    }

    public WordTaggedAdapter(Context context, List<WordSearchModel> wordModels) {
        this.context = context;
        this.wordModels = wordModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordTaggedHolder(LayoutInflater.from(context).inflate(R.layout.word_tagged_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((WordTaggedHolder) holder).bindHolder(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return wordModels.size();
    }

    private class WordTaggedHolder extends RecyclerView.ViewHolder {

        AppCompatTextView word_search_txt, word_search_txt2;

        private WordTaggedHolder(@NonNull View itemView) {
            super(itemView);
            ;
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
                ((Activity) context).startActivityForResult(intent, 1001);
            });

            itemView.setOnLongClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenu().add("حذف");
                popupMenu.setOnMenuItemClickListener(item -> {

                    if (item.getItemId() == 0) {
                        DBM.getDB(v.getContext()).getWordDao().clearFromFavorites(wordModels.get(position).getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                    }

                                    @SuppressLint("NotifyDataSetChanged")
                                    @Override
                                    public void onComplete() {
                                        Toast.makeText(v.getContext(), "از کلمات ستاره دار پاک شد", Toast.LENGTH_SHORT).show();
                                        wordModels.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, getItemCount());
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                    }
                                });
                    }

                    return false;
                });
                popupMenu.show();
                return false;
            });

        }

    }

}
