package crud14.spring;

import crud14.Logger.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Context {
    private static WebApplicationContext context;

    private Context() {
    }

    static {
        context = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        Logger.log("SPRING:\tcontext (re)loaded.");
    }

    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static <T> T getBean(String beanId) {
        T obj = (T) context.getBean(beanId);
        Logger.log("SPRING:\tloaded bean '" + beanId + "' [" + obj.toString() + "]\tOK.");
        return obj;
    }
/*
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        T obj=(T)context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
        Logger.log("SPRING:\tloaded bean '"+beanName+"' ["+obj.toString()+"]\tOK.");
        return obj;
    }
    */
/*
    public static Object getBean(String beanId, Object... args){
        return context.getBean(beanId, args);
    }
    */
}
