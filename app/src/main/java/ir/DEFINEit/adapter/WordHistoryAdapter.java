/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:57 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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

public class WordHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WordSearchModel> wordModels;
    private boolean isPersian;

    public void setPersian(boolean persian) {
        isPersian = persian;
    }

    public WordHistoryAdapter(List<WordSearchModel> wordModels) {
        this.wordModels = wordModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordHistoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.word_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((WordHistoryHolder) holder).bindHolder(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return wordModels.size();
    }

    private class WordHistoryHolder extends RecyclerView.ViewHolder {

        AppCompatTextView word_history_txt, word_history_txt2;

        private WordHistoryHolder(@NonNull View itemView) {
            super(itemView);
            word_history_txt = itemView.findViewById(R.id.word_history_from);
            word_history_txt2 = itemView.findViewById(R.id.word_history_to);
        }

        private void bindHolder(View itemView, int position) {

            if (isPersian) {
                word_history_txt.setText(wordModels.get(position).getPersianWord());
                word_history_txt2.setText(wordModels.get(position).getEnglishWord());
            } else {
                word_history_txt2.setText(wordModels.get(position).getPersianWord());
                word_history_txt.setText(wordModels.get(position).getEnglishWord());
            }

            itemView.setOnClickListener(n -> {
                Intent intent = new Intent(itemView.getContext(), ShowWordActivity.class);
                intent.putExtra("id", (wordModels.get(position).getId()));
                ((Activity) itemView.getContext()).startActivityForResult(intent, 1001);
            });

            itemView.setOnLongClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenu().add("حذف");
                popupMenu.setOnMenuItemClickListener(item -> {

                    if (item.getItemId() == 0) {
                        DBM.getDB(v.getContext()).getWordDao().clearFromHistories(wordModels.get(position).getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                    }

                                    @SuppressLint("NotifyDataSetChanged")
                                    @Override
                                    public void onComplete() {
                                        Toast.makeText(v.getContext(), "از تاریخچه پاک شد", Toast.LENGTH_SHORT).show();
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
