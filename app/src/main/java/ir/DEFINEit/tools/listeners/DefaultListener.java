/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 3/26/22, 9:49 AM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.listeners;

import okhttp3.ResponseBody;
import retrofit2.Response;

public interface DefaultListener {
    default void onSuccess(String str) {
    }

    default void onSuccess() {
    }

    default void onFailure() {
    }

    default void onSuccess(Response<ResponseBody> response) {
    }

    default void onFailure(Throwable throwable) {
    }
}