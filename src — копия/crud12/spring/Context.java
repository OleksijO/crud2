package crud12.spring;

import crud10.Logger.Logger;
import crud10.dto.PageParameters;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        //Logger.log("SPRING:\tloaded bean '"+beanId+"' ["+obj.toString()+"]\tOK.");
        return obj;
    }
    public static PageParameters getPageParameters(HttpServletRequest request, HttpServletResponse response){
        PageParameters obj=(PageParameters) getBean("page-parameters");
        obj.init(request,response);
        return obj;
    }
}
