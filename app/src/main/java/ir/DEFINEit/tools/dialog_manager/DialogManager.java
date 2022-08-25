/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 3/26/22, 9:30 AM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.dialog_manager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Objects;

import ir.DEFINEit.R;
import ir.DEFINEit.tools.listeners.DefaultListener;

public class DialogManager {

    public static void showItemChooserDialog(Context context, boolean cancelable, String title, String[] items, short isSelcted, DefaultListener defaultListener) {
        AlertDialog.Builder chooseGender = new androidx.appcompat.app.AlertDialog.Builder(context);
        if (!title.isEmpty()) {
            chooseGender.setTitle(title);
        }
        chooseGender.setCancelable(cancelable);
        chooseGender.setOnCancelListener(dialog -> defaultListener.onFailure(null));
        chooseGender.setSingleChoiceItems(items, isSelcted, (dialog, which) -> {
            defaultListener.onSuccess(which);
            dialog.dismiss();
        });
        chooseGender.setNegativeButton("لغو", (dialog, which) -> {
            defaultListener.onFailure(null);
        });
        chooseGender.show();
    }

    public static ProgressDialog showLoadingDialog(Context context, Boolean cancelable) {
        ProgressDialog loadingDialog = new ProgressDialog(context);
        loadingDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setCancelable(cancelable);
        loadingDialog.setMessage("کمی شکیبا باشید ...");
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return loadingDialog;
    }

    public static void showScorableQuizDialog(Context context, Boolean cancelable, String message, DefaultListener defaultListener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.create().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(cancelable);
        alertDialog.setOnCancelListener(dialogInterface -> defaultListener.onFailure(null));
        alertDialog.setPositiveButton(context.getString(R.string.apply), (dialog, which) -> defaultListener.onSuccess(null));
        alertDialog.setNegativeButton(context.getString(R.string.dismiss), (dialog, which) -> defaultListener.onFailure(null));
        alertDialog.show();
    }

    public static void showAcceptableQuizDialog(Context context, Boolean cancelable, String message, DefaultListener defaultListener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.setCancelable(cancelable);
        dialog.setContentView(R.layout.dialog_acceptable_quiz);
        AppCompatTextView msg_dialog_textView = dialog.findViewById(R.id.msg_dialog_textView);
        AppCompatButton apply = dialog.findViewById(R.id.apply);
        AppCompatButton dismiss = dialog.findViewById(R.id.dismiss);
        msg_dialog_textView.setText(message);
        dialog.setOnCancelListener(dialogInterface -> defaultListener.onFailure(null));
        apply.setOnClickListener(view -> {
            defaultListener.onSuccess(null);
            dialog.dismiss();
        });
        dismiss.setOnClickListener(view -> {
            defaultListener.onFailure(null);
            dialog.dismiss();
        });
        dialog.show();
    }

    public static void showDescribableQuizDialog(Context context, Boolean cancelable, String title, String hint, DefaultListener defaultListener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.setCancelable(cancelable);
        dialog.setContentView(R.layout.dialog_describable_quiz);
        AppCompatTextView msg_dialog_title = dialog.findViewById(R.id.msg_dialog_title);
        AppCompatEditText msg_dialog_edit = dialog.findViewById(R.id.msg_dialog_edit);
        AppCompatButton apply = dialog.findViewById(R.id.apply);
        AppCompatButton dismiss = dialog.findViewById(R.id.dismiss);
        msg_dialog_title.setText(title);
        msg_dialog_edit.setHint(hint);
        dialog.setOnCancelListener(dialogInterface -> defaultListener.onFailure(null));
        apply.setOnClickListener(view -> {
            defaultListener.onSuccess(Objects.requireNonNull(msg_dialog_edit.getText()).toString());
            dialog.dismiss();
        });
        dismiss.setOnClickListener(view -> {
            defaultListener.onFailure(null);
            dialog.dismiss();
        });
        dialog.show();
    }
}
