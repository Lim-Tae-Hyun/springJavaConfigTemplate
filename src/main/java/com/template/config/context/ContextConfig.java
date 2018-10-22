package com.template.config.context;

import com.template.interceptor.CommonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;

/**
 * @description : context configuration class
 * @author : limth
 */
/* @Configuration : used on class which defines bean */
/* @EnableWebMvc : avoid webMvcAutoConfiguration which is configured by spring framework */
/* @ComponentScan : allow spring to know package location that has component */
/* @PropertySources : used to enroll properties sources */
/* @EnableAspectJAutoProxy : used to handle components marked with @Aspect annotation */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.template"})
@PropertySources({
        @PropertySource(name = "datasource", value = "classpath:config/properties/datasource.properties"),
        @PropertySource(name = "mybatis", value = "classpath:config/properties/mybatis.properties")
})
@EnableAspectJAutoProxy
public class ContextConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CommonInterceptor commonInterceptor;

    /* resolves within bean definition property values and @Value annotations against the current spring environment and its set of PropertySources */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /* for a couple of conventions for selecting the format required URL suffixes and A URL parameter */
    @Bean
    public ViewResolver contentNegotiatingViewResolver() {
        ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);
        contentNegotiationManager.addMediaType("xml", MediaType.APPLICATION_XML);
        contentNegotiationManager.addMediaType("html", MediaType.TEXT_HTML);

        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(contentNegotiationManager.getObject());

        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

        resolvers.add(internalResourceViewResolver());
        resolver.setViewResolvers(resolvers);

        return resolver;
    }

    /* allows to set properties such as prefix or suffix to the view name to generate the final view page */
    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setContentType("text/html; charset=UTF-8");
        return viewResolver;
    }

    /* allows to convert objects to json format */
    @Bean
    public MappingJackson2JsonView jsonViewResolver() {
        MappingJackson2JsonView viewResolver = new MappingJackson2JsonView();
        viewResolver.setPrefixJson(false);
        viewResolver.setContentType("application/json; charset=UTF-8");
        return viewResolver;
    }

    /* for JDK's standard message parsing provided by MessageFormat class */
    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("/config/message/msg");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /* for handling static resources (eg. css, images..) */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
    }

    /* use default servlet handler configuration */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /* used to add interceptors */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/**");
    }

}
