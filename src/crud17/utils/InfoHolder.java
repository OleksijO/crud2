package crud17.utils;

import crud17.view.beans.SessionBean;

import java.util.WeakHashMap;

public class InfoHolder {
    private static WeakHashMap sessionMap = new WeakHashMap();

    public static int getNumberOfSessions() {
        return sessionMap.size();
    }

    public static void addSession(SessionBean sessionBean) {
        if (!sessionMap.containsValue(sessionBean)) {
            sessionMap.put(sessionBean, 0);
        }
    }
}
