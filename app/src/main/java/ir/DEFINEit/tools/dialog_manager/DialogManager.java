/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 3/26/22, 9:30 AM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.dialog_manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Window;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Objects;

import ir.DEFINEit.R;
import ir.DEFINEit.tools.listeners.DefaultListener;

public class DialogManager {

    public static ProgressDialog getLoadingDialog(Context context, Boolean cancelable) {
        ProgressDialog loadingDialog = new ProgressDialog(context);
        loadingDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setCancelable(cancelable);
        loadingDialog.setMessage("کمی شکیبا باشید ...");
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return loadingDialog;
    }

    public static void showDialog(Context context, Boolean cancelable, String message, DefaultListener defaultListener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.create().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(cancelable);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                defaultListener.onFailure();
            }
        });
        alertDialog.setPositiveButton(context.getString(R.string.apply), (dialog, which) -> {
            defaultListener.onSuccess();
        });
        alertDialog.setNegativeButton(context.getString(R.string.dismiss), (dialog, which) -> {
            defaultListener.onFailure();
        });
        alertDialog.show();
    }

    public static void showMsgDialog(Context context, Boolean cancelable, String title, String hint, DefaultListener defaultListener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.setCancelable(cancelable);
        dialog.setContentView(R.layout.dialog_message);
        AppCompatTextView msg_dialog_title = dialog.findViewById(R.id.msg_dialog_title);
        AppCompatEditText msg_dialog_edit = dialog.findViewById(R.id.msg_dialog_edit);
        AppCompatButton apply = dialog.findViewById(R.id.apply);
        AppCompatButton dismiss = dialog.findViewById(R.id.dismiss);
        msg_dialog_title.setText(title);
        msg_dialog_edit.setHint(hint);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                defaultListener.onFailure();
            }
        });
        apply.setOnClickListener(view -> {
            defaultListener.onSuccess(Objects.requireNonNull(msg_dialog_edit.getText()).toString());
            dialog.dismiss();
        });
        dismiss.setOnClickListener(view -> {
            defaultListener.onFailure();
            dialog.dismiss();
        });
        dialog.show();
    }
}
