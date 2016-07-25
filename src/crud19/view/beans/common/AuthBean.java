package crud19.view.beans.common;

import crud19.entities.secure.User;
import crud19.service.dao.UsersDao;
import crud19.utils.InfoHolder;
import crud19.view.beans.SessionBean;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;

@ManagedBean
@RequestScoped
public class AuthBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AuthBean.class);
    @ManagedProperty("#{daoUser}")
    private UsersDao daoService;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{userEntity}")
    private User user;
    private String login = "";
    private String password = "";


    public void logIn() {
        try {
            user.setLogin(login);
            user = daoService.retrieve(user);
            if (user == null) {
                throw new RuntimeException("User with login '" + login + "' not found.");
            }
            String plaintext = password + "1029384756";
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            StringBuilder passwordMD5 = new StringBuilder(bigInt.toString(16));
            while (passwordMD5.length() < 32) {
                passwordMD5.insert(0, "0");
            }
            if (user.getLogin().equals(login) && user.getPassword().equals(passwordMD5.toString())) {
                if (InfoHolder.isLogged(user.getId())) {
                    throw new RuntimeException("User with login '" + login + "' already logged in.");
                }
                sessionBean.setLoggedUserName(user.getName());
                sessionBean.setUserId(user.getId());
                InfoHolder.setLoggedIn(sessionBean);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "You have been login as " + sessionBean.getLoggedUserName(), null));
                logger.info("User id=" + user.getId() + " login=" + login + " logged in.");
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Combination of login and password not found.", null));
                logger.warn("User id=" + user.getId() + " login=" + login + " not logged in - wrong password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cant't login." + e.getMessage(), null));
            logger.info("Authentification failed: " + e.getMessage());
        }
        refreshNeededComponents();
    }

    public void logOut() {
        logger.info("User " + sessionBean.getLoggedUserName() + "logged out");
        sessionBean.setLoggedUserName("Guest");
        InfoHolder.setLoggedOff(sessionBean);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "You have been logout... Hello, " + sessionBean.getLoggedUserName() + "!", null));
        refreshNeededComponents();
    }

    private void refreshNeededComponents() {
        try {
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("body");
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("add-buttons");
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("db-management");
            logger.debug("Updated views: body, add-buttons, db-management");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public UsersDao getDaoService() {
        return daoService;
    }

    public void setDaoService(UsersDao daoService) {
        this.daoService = daoService;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public AuthBean getInstance() {
        return this;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
