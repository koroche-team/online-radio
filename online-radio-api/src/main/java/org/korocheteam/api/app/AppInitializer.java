package org.korocheteam.api.app;

import org.korocheteam.api.config.AppConfig;
import org.korocheteam.api.config.DatabaseConfig;
import org.korocheteam.api.config.SecurityConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.register(AppConfig.class);
		webApplicationContext.register(SecurityConfig.class);
		webApplicationContext.register(DatabaseConfig.class);

		ContextLoaderListener contextLoaderListener = new ContextLoaderListener(webApplicationContext);

		servletContext.addListener(contextLoaderListener);

		ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet(
				"dispatcher", new DispatcherServlet(webApplicationContext));
		dispatcherServlet.setLoadOnStartup(1);
		dispatcherServlet.addMapping("/");

	}
}
