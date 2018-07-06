package com.storm.quora.config;

import com.storm.quora.common.ApplicationContextProvider;
import com.storm.quora.common.ConstantsService;
import com.storm.quora.service.UserService;
import org.springframework.context.ApplicationContext;

public class ServiceContainer {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ServiceContainer.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass)
    {
        return applicationContext.getBean(beanClass);
    }

    public static Object getBean(String beanName)
    {
        return applicationContext.getBean(beanName);
    }

    public static UserService userService;
    static {
        userService = (UserService) ApplicationContextProvider.getBean(ConstantsService.SERVICE_USER);

    }
}
