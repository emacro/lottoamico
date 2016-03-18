package it.emacro.services;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationSettings implements ApplicationContextAware {
	
	public String applicationRoot;
	
	public boolean startDownloadExtractionsFile, startDbLoader, setSystemLookAndFeel;
	
//	private transient AutowireCapableBeanFactory beanFactory;
	
	@Autowired
	private ApplicationContext applicationContext = null;
	
	public ApplicationSettings() {
		// TODO Auto-generated constructor stub
	}

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          // Assign the ApplicationContext into a static variable
         this.applicationContext = applicationContext;
    }

}
