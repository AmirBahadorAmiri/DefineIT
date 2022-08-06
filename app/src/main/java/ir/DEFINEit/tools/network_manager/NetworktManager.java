/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 3/19/22, 5:41 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.network_manager;

public class NetworktManager {

  /*  public static boolean isVpnConnected() {
        try {
            StringBuilder sb = new StringBuilder();
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if ( networkInterface.isUp() ) {
                    sb.append(networkInterface.getName()).append(" ");
                }
            }
            if ( sb.toString().contains(StaticDatas.TUN) | sb.toString().contains(StaticDatas.PPP) | sb.toString().contains(StaticDatas.PPTP) ) {
             return true;
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
        if ( info != null ) {
            for ( NetworkInfo info1 : info ) {
                if ( info1 != null && info1.isAvailable() && info1.isConnected() ) isConnected = true;
            }
        }
        return isConnected;
    }*/

}
