package crud2.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Context {
    private static WebApplicationContext context;
    private static final Logger logger = Logger.getLogger(Context.class);

    private Context() {
    }

    static {
        context = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
    }

    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        T obj = null;
        try {
            obj = (T) context.getBean(beanId);
        } catch (BeansException e) {
            logger.error("SPRING ERROR:\t Can't load bean with id = '" + beanId + "'", e);
            throw new RuntimeException("SPRING:\tERROR\t Can't load bean with id = '" + beanId + "'");
        }
        return obj;
    }
/*
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        T obj=(T)context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
        logger.log("SPRING:\tloaded bean '"+beanName+"' ["+obj.toString()+"]\tOK.");
        return obj;
    }
    */
/*
    public static Object getBean(String beanId, Object... args){
        return context.getBean(beanId, args);
    }
    */
}
