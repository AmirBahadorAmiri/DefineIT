/*
 * ~ Copyright (c) 2021
 * ~ Dev : Amir Bahador , Amiri
 * ~ City : Iran / Abadan
 * ~ time & date : 10/17/21 4:02 PM
 * ~ email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.permission_manager;

import java.util.List;

public interface PermisionListener {
    void onChecked(List<String> permissionGranted, List<String> permissionDenied);
}
