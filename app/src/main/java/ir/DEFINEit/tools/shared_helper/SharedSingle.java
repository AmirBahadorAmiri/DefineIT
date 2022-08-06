/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/10/22, 11:41 AM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.shared_helper;

import android.content.Context;

public class SharedSingle {

    private static SharedHelper sharedHelper;

    public static void initialize(Context applicatioContext, String name) {
        if (sharedHelper == null)
            sharedHelper = new SharedHelper(applicatioContext, name);
    }

    public static SharedHelper getSharedHelper() {
        return sharedHelper;
    }
}
