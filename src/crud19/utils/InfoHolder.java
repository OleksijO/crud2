package crud19.utils;

import crud19.logger.SessionListener;
import crud19.view.beans.SessionBean;

import java.util.HashMap;
import java.util.Map;

public class InfoHolder {
    private static HashMap<String, Integer> sessionMap = new HashMap<>();

    public static int getNumberOfSessions() {
        return SessionListener.getTotalActiveSession();
    }

    public static void addSession(String sessionId) {
       sessionMap.put(sessionId, -1);
    }
    public static void removeSession(String sessionId){
        sessionMap.remove(sessionId);
    }

    public static boolean isLogged(int id) {
        return sessionMap.containsValue(id);
    }

    public static void setLoggedIn(SessionBean sessionBean) {
        sessionMap.put(sessionBean.getSessionId(), sessionBean.getUserId());
    }

    public static void setLoggedOff(SessionBean sessionBean) {
        sessionBean.setUserId(-1);
        sessionMap.put(sessionBean.getSessionId(),-1);
    }

    public static int getTotalLoggedInUsers(){
        int count=0;
        for(Map.Entry<String, Integer> pair:sessionMap.entrySet()){
            if (pair.getValue()>-1) {count++;}
        }
        return count;
    }


}
