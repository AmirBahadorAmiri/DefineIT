/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:56 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.adapter;

import android.annotation.SuppressLint;
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
import ir.DEFINEit.model.SentenceModel;
import ir.DEFINEit.tools.database.DBM;

public class TranslateHistoryAdapter extends RecyclerView.Adapter<TranslateHistoryAdapter.TranslatedTextHolder> {

    private List<SentenceModel> sentenceModels;

    public TranslateHistoryAdapter(List<SentenceModel> sentenceModels) {
        this.sentenceModels = sentenceModels;
    }

    @NonNull
    @Override
    public TranslatedTextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TranslatedTextHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.translated_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TranslatedTextHolder holder, int position) {
        ((TranslatedTextHolder) holder).bindHolder(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return sentenceModels.size();
    }

    public class TranslatedTextHolder extends RecyclerView.ViewHolder {

        AppCompatTextView translated_history_from, translated_history_to;

        public TranslatedTextHolder(@NonNull View itemView) {
            super(itemView);
            translated_history_from = itemView.findViewById(R.id.translated_history_from);
            translated_history_to = itemView.findViewById(R.id.translated_history_to);
        }

        private void bindHolder(View itemView, int position) {
            translated_history_from.setText(sentenceModels.get(position).getSegment());
            translated_history_to.setText(sentenceModels.get(position).getTranslation());

            itemView.setOnClickListener(n -> {

            });

            itemView.setOnLongClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenu().add("حذف");
                popupMenu.setOnMenuItemClickListener(item -> {

                    if (item.getItemId() == 0) {
                        DBM.getDB(v.getContext()).getSentenceDao().delete(sentenceModels.get(position))
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
                                        sentenceModels.remove(position);
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
