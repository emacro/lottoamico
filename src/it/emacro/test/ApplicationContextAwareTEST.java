package it.emacro.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextAwareTEST implements ApplicationContextAware {
	
//	public ApplicationContext applicationContext;
	
	private transient AutowireCapableBeanFactory beanFactory;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		this.beanFactory = applicationContext.getAutowireCapableBeanFactory();

	}

}
