/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:55 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.DEFINEit.R;
import ir.DEFINEit.model.LangModel;
import ir.DEFINEit.tools.assets_image_loader.AssetsImageLoader;
import ir.DEFINEit.tools.country.Country;
import ir.DEFINEit.tools.language_manager.LanguageManager;
import ir.DEFINEit.view.activity.ChangeLanguageActivity;

public class ChangeLanguageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<LangModel> langModels;

    public ChangeLanguageAdapter(Context context, List<LangModel> langModels) {
        this.langModels = langModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LanguageHolder(LayoutInflater.from(context).inflate(R.layout.change_language_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((LanguageHolder) holder).bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return langModels.size();
    }

    private class LanguageHolder extends RecyclerView.ViewHolder {

        AppCompatImageView translate_change_item_imageView;
        AppCompatTextView translate_change_item_textView;

        LanguageHolder(@NonNull View itemView) {
            super(itemView);
            translate_change_item_imageView = itemView.findViewById(R.id.translate_change_item_imageView);
            translate_change_item_textView = itemView.findViewById(R.id.translate_change_item_textView);
        }

        void bindView(View itemView, int position) {
            translate_change_item_textView.setText(langModels.get(position).getName());
            AssetsImageLoader.load(context, ("country/" + langModels.get(position).getImage()), translate_change_item_imageView);
            itemView.setOnClickListener(n -> {
                if (((ChangeLanguageActivity) context).isFrom) {
                    if (Country.generateLanguage().get(position).getName().equals(LanguageManager.getToLangauge())) {
                        LanguageManager.reverceLanguage();
                    } else {
                        LanguageManager.setFromLangauge(position);
                    }
                } else {
                    if (Country.generateLanguage().get(position).getName().equals(LanguageManager.getFromLangauge())) {
                        LanguageManager.reverceLanguage();
                    } else {
                        LanguageManager.setToLangauge(position);
                    }
                }
                ((Activity) context).setResult(1003);
                ((Activity) context).finish();
            });
        }
    }
}
