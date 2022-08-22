/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:55 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.DEFINEit.R;
import ir.DEFINEit.model.TextModel;

public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<TextModel> conversationList;

    public ConversationAdapter(Context context, List<TextModel> conversationList) {
        this.context = context;
        this.conversationList = conversationList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationViewHolder(LayoutInflater.from(context).inflate(R.layout.item_conversation_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ConversationViewHolder) holder).bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    private class ConversationViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView conversation_from, conversation_to;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @SuppressLint("NotifyDataSetChanged")
        public void bindView(View itemView, int position) {
            conversation_from = itemView.findViewById(R.id.conversation_from);
            conversation_to = itemView.findViewById(R.id.conversation_to);

            conversation_from.setText(conversationList.get(position).getText());
            conversation_to.setText(conversationList.get(position).getTranslation());

            itemView.setOnLongClickListener(v -> {

                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenu().add("حذف");
                popupMenu.setOnMenuItemClickListener(item -> {

                    if (item.getItemId() == 0) {
                        conversationList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                    }

                    return false;
                });
                popupMenu.show();
                return false;
            });

        }
    }

}
