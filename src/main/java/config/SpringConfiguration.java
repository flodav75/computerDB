package config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "fr.excilys.controller", "fr.excilys.persistence", "fr.excilys.service",
		"fr.excilys.mappers" })
@EnableWebMvc
public class SpringConfiguration implements WebApplicationInitializer,WebMvcConfigurer {

	@Bean
	public DataSource getDataSource() {
		HikariConfig config = new HikariConfig(
				Thread.currentThread().getContextClassLoader().getResource("").getPath() + "hikari.properties");
		HikariDataSource dataSource = new HikariDataSource(config);

		return dataSource;
	}

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfiguration.class);
		// Manage the life cycle of the root application context
		container.addListener(new ContextLoaderListener(rootContext));
	}
	
	 @Bean
	   public ViewResolver viewResolver() {
	      InternalResourceViewResolver bean = new InternalResourceViewResolver();
	      bean.setViewClass(JstlView.class);
	      bean.setPrefix("/WEB-INF/views/");
	      bean.setSuffix(".jsp");
	 
	      return bean;
	   }
	 
	 
	 @Override 
	 public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		   registry.addResourceHandler("/ressources/static/**").addResourceLocations("/ressources/static/");	 
	 }
}
