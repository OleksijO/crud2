package crud19.logger;


import crud19.utils.InfoHolder;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    private static int totalActiveSessions;

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        totalActiveSessions++;
        InfoHolder.addSession(arg0.getSession().getId());
        System.out.println("sessionCreated - add one session into counter");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        totalActiveSessions--;
        InfoHolder.removeSession(arg0.getSession().getId());
        System.out.println("sessionDestroyed - deduct one session from counter");
    }
}

