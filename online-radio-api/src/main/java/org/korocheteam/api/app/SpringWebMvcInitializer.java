package org.korocheteam.api.app;

import org.korocheteam.api.config.AppConfig;
import org.korocheteam.api.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringWebMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {



//	@Override
//	public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
//		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
//		webApplicationContext.register(AppConfig.class);
//		webApplicationContext.register(SecurityConfig.class);
//		webApplicationContext.register(DatabaseConfig.class);
//
//		ContextLoaderListener contextLoaderListener = new ContextLoaderListener(webApplicationContext);
//
//		servletContext.addListener(contextLoaderListener);
//
//		ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet(
//				"dispatcher", new DispatcherServlet(webApplicationContext));
//		dispatcherServlet.setLoadOnStartup(1);
//		dispatcherServlet.addMapping("/");
//
//	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebConfig.class};
	}
}
