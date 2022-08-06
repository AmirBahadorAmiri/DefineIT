/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:58 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import ir.DEFINEit.R;
import ir.DEFINEit.view.activity.ConversationActivity;
import ir.DEFINEit.view.activity.MainActivity;

public class HomeFragment extends Fragment {

    private AppCompatImageButton drawerLogo;
    private AppCompatButton search_editText;
    private AppCompatImageButton send_speakLogo;
    private CardView main_fragment_cardview_translate, main_fragment_cardview_search, main_fragment_cardview_conversation;
//    private AppCompatImageView vipLogo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);
        start();

    }

    private void findViews(View view) {
        drawerLogo = view.findViewById(R.id.drawerLogo);
        search_editText = view.findViewById(R.id.search_editText);
        send_speakLogo = view.findViewById(R.id.send_speakLogo);
        main_fragment_cardview_translate = view.findViewById(R.id.main_fragment_cardview_translate);
        main_fragment_cardview_search = view.findViewById(R.id.main_fragment_cardview_search);
        main_fragment_cardview_conversation = view.findViewById(R.id.main_fragment_cardview_conversation);
//        vipLogo = view.findViewById(R.id.vipLogo);
    }

    private void start() {
        drawerLogo.setOnClickListener(n -> ((MainActivity) requireActivity()).openDrawer(true));

        /*vipLogo.setOnClickListener(view -> {

            Toast.makeText(requireContext(), "buy application from *", Toast.LENGTH_SHORT).show();

        });*/

        search_editText.setOnClickListener(v -> ((MainActivity) requireContext()).main_bottom_nav.setSelectedItemId(R.id.search_page));
        send_speakLogo.setOnClickListener(v -> ((MainActivity) requireContext()).main_bottom_nav.setSelectedItemId(R.id.search_page));
        main_fragment_cardview_conversation.setOnClickListener(view -> startActivity(new Intent(requireContext(), ConversationActivity.class)));
        main_fragment_cardview_translate.setOnClickListener(view -> ((MainActivity) requireContext()).main_bottom_nav.setSelectedItemId(R.id.translate_page));
        main_fragment_cardview_search.setOnClickListener(view -> ((MainActivity) requireContext()).main_bottom_nav.setSelectedItemId(R.id.search_page));
    }

}
