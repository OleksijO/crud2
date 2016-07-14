package crud12.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Context {
    private static Context ourInstance = new Context();
    private static ApplicationContext context=new ClassPathXmlApplicationContext("Beans.xml");

    public static Context getInstance() {
        return ourInstance;
    }

    private Context() {
    }
    public static ApplicationContext getContext(){
        return context;
    }
    public static void reloadContext(){
        context=new ClassPathXmlApplicationContext("Beans.xml");
    }
}
