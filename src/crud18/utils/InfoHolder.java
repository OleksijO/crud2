package crud18.utils;

import crud18.view.beans.SessionBean;

import java.util.WeakHashMap;

public class InfoHolder {
    private static WeakHashMap sessionMap = new WeakHashMap();

    public static int getNumberOfSessions() {
        return sessionMap.size();
    }

    public static void addSession(SessionBean sessionBean) {
        if (!sessionMap.containsValue(sessionBean)) {
            sessionMap.put(sessionBean, sessionBean.getUserId());
        }
    }

    public static boolean isLogged(int id){
        return sessionMap.containsValue(id);
    }

    public static void setLoggedIn(SessionBean sessionBean){
        sessionMap.put(sessionBean, sessionBean.getUserId());
    }

    public static void setLoggedOff(SessionBean sessionBean) {
            sessionMap.put(sessionBean, -1);
    }
}
