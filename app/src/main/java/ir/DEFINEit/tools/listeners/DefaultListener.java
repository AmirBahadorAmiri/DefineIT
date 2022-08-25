/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 3/26/22, 9:49 AM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.listeners;

public interface DefaultListener {

    default void onSuccess(Object obj) {
    }

    default void onFailure(Object obj) {
    }

}