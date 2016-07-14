package crud12.spring;

import crud10.Logger.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

public class Context {
    private static ServletContext servletContext;
    private static ApplicationContext context;

    private Context() {
    }

    public static void reloadContext() {
        context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext);
        Logger.log("SPRING:\tcontext (re)loaded.");
    }

    public static void setServletContext(ServletContext servletContext) {
        Context.servletContext = servletContext;
        reloadContext();
    }
    public static Object getBean(String beanId){
        Object obj=context.getBean(beanId);
        Logger.log("SPRING:\tloaded bean '"+beanId+"' ["+obj.toString()+"]\tOK.");
        return obj;
    }
}
