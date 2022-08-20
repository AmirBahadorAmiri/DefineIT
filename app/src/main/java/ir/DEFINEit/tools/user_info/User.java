/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 3/14/22, 11:25 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.user_info;

import ir.DEFINEit.StaticDatas;
import ir.DEFINEit.tools.shared_helper.SharedSingle;

public class User {

    public static void setBuyTime(Long aLong) {
        SharedSingle.getSharedHelper().insert(StaticDatas.USER_BUY_TIME, aLong);
    }

    public static Long getBuyTime() {
        return SharedSingle.getSharedHelper().readLong(StaticDatas.USER_BUY_TIME);
    }

    public static boolean userCanUseApp() {
        return (System.currentTimeMillis() - User.getBuyTime()) <= (1800000);
    }

    public static void setDarkTheme(boolean isDark) {
        SharedSingle.getSharedHelper().insert(StaticDatas.USER_DARK_THEME, isDark);
    }

    public static boolean isDarkTheme() {
        return SharedSingle.getSharedHelper().readBoolean(StaticDatas.USER_DARK_THEME);
    }

}
