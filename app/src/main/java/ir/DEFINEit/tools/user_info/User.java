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

//    public static void removeAccount() {
//        setUserSkip(true);
//        setUserId(0);
//        setUserStatus(0);
//        setUserNameFamily("");
//        setUserEmail("");
//        setUserNumber("");
//        setUserToken("");
//    }
//

    public static void setBuyTime(Long aLong) {
        SharedSingle.getSharedHelper().insert(StaticDatas.USER_BUY_TIME, aLong);
    }

    public static Long getBuyTime() {
        return SharedSingle.getSharedHelper().readLong(StaticDatas.USER_BUY_TIME);
    }

    public static void setDarkTheme(boolean isDark) {
        SharedSingle.getSharedHelper().insert(StaticDatas.USER_DARK_THEME, isDark);
    }

    public static boolean isDarkTheme() {
        return SharedSingle.getSharedHelper().readBoolean(StaticDatas.USER_DARK_THEME);
    }

//
//    public static boolean getUserSkip() {
//        return SharedSingle.getSharedHelper().readBoolean(StaticDatas.USER_SKIP);
//    }
//
//    public static void setUserSkip(boolean b) {
//        SharedSingle.getSharedHelper().insert(StaticDatas.USER_SKIP, b);
//    }
//
//    public static boolean userIsAvailable() {
//        return SharedSingle.getSharedHelper().readInt(StaticDatas.USER_ID) != 0;
//    }
//
//    public static String getUserPassword() {
//        return HashCreator.decoding(SharedSingle.getSharedHelper().readString(StaticDatas.USER_PASSWORD));
//    }
//
//    public static void setUserPassword(String new_password) {
//        SharedSingle.getSharedHelper().insert(StaticDatas.USER_PASSWORD, HashCreator.encoding(new_password));
//    }
//
//    public static void setUserId(int user_id) {
//        SharedSingle.getSharedHelper().insert(StaticDatas.USER_ID, user_id);
//    }
//
//    public static int getUserId() {
//        return SharedSingle.getSharedHelper().readInt(StaticDatas.USER_ID);
//    }
//
//    public static int getUserStatus() {
//        return SharedSingle.getSharedHelper().readInt(StaticDatas.USER_STATUS);
//    }
//
//    public static void setUserStatus(int status) {
//        SharedSingle.getSharedHelper().insert(StaticDatas.USER_STATUS, status);
//    }
//
//    public static String getUserNameFamily() {
//        return HashCreator.decoding(SharedSingle.getSharedHelper().readString(StaticDatas.USER_NAMEFAMILY));
//    }
//
//    public static void setUserNameFamily(String nameFamily) {
//        SharedSingle.getSharedHelper().insert(StaticDatas.USER_NAMEFAMILY, HashCreator.encoding(nameFamily));
//    }
//
//    public static String getUserEmail() {
//        return HashCreator.decoding(SharedSingle.getSharedHelper().readString(StaticDatas.USER_EMAIL));
//    }
//
//    public static void setUserEmail(String email) {
//        SharedSingle.getSharedHelper().insert(StaticDatas.USER_EMAIL, HashCreator.encoding(email));
//    }
//
//    public static String getUserNumber() {
//        return HashCreator.decoding(SharedSingle.getSharedHelper().readString(StaticDatas.USER_NUMBER));
//    }
//
//    public static void setUserNumber(String number) {
//        SharedSingle.getSharedHelper().insert(StaticDatas.USER_NUMBER, HashCreator.encoding(number));
//    }
//
//    public static String getUserToken() {
//        return HashCreator.decoding(SharedSingle.getSharedHelper().readString(StaticDatas.USER_TOKEN));
//    }
//
//    public static void setUserToken(String token) {
//        SharedSingle.getSharedHelper().insert(StaticDatas.USER_TOKEN, HashCreator.encoding(token));
//    }

}
