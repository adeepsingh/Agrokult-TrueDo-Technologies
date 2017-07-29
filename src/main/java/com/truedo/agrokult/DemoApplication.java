package com.truedo.agrokult;


import java.util.Locale;
import java.util.Properties;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	

	@Bean(name = "dataSource")
	   public DriverManagerDataSource dataSource() {
	        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	        driverManagerDataSource.setUrl(ConfigConstants.DatabaseName);
	        driverManagerDataSource.setUsername(ConfigConstants.setUsername);
	        driverManagerDataSource.setPassword(ConfigConstants.setPassword);
	        return driverManagerDataSource;
	    } 
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    mailSender.setUsername("vkumar111223@gmail.com");
	    mailSender.setPassword("mypasswordisnotweak");
	     
	    Properties props = new Properties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    mailSender.setJavaMailProperties(props);
        
	    return mailSender;
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoApplication.class);
	}
	
	@Bean
	public ServletRegistrationBean jerseyServlet() {
	    ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
	    registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
	    registration.addInitParameter(ServletProperties.FILTER_FORWARD_ON_404, "true");
	    return registration;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
	 SessionLocaleResolver slr = new SessionLocaleResolver();
	 slr.setDefaultLocale(Locale.US); // Set default Locale as US
	 return slr;
	}
	 
	@Bean
	public ResourceBundleMessageSource messageSource() {
	 ResourceBundleMessageSource source = new ResourceBundleMessageSource();
	 source.setBasenames("i18n/messages");  // name of the resource bundle 
	 source.setUseCodeAsDefaultMessage(true);
	 return source;
	}
	
}
