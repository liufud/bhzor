package ics.web.config;



import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewInterceptor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ics.dao.ProductDAO;
import ics.dao.ProductInMemoryDAOImpl;
import ics.dao.ReceivedRpOrderDAO;
import ics.dao.ReceivedRpOrderHibernateDAOImpl;
import ics.dao.RoleDAO;
import ics.dao.RoleDAOImpl;
import ics.dao.UserDAO;
import ics.dao.UserDAOImpl;
import ics.model.Product;
import ics.model.Role;
import ics.model.Vendor;
import ics.services.CartService;
import ics.services.CartServiceImpl;
import ics.services.ProductService;
import ics.services.ProductServiceImpl;
import ics.services.ReceivedRpOrderService;
import ics.services.ReceivedRpOrderServieImpl;
import ics.services.ReplenishmentOrderService;
import ics.services.ReplenishmentOrderServiceImpl;
import ics.services.RoleService;
import ics.services.RoleServiceImpl;
import ics.services.SecurityService;
import ics.services.SecurityServiceImpl;
import ics.services.UserDetailsServiceImpl;
import ics.services.UserService;
import ics.services.UserServiceImpl;



@Configuration
@EnableWebMvc
@ComponentScan("ics")
public class ICSConfig extends WebMvcConfigurerAdapter {
	@Autowired
	SessionFactory sessionFactory;
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("WEB-INF/view/");
		vr.setSuffix(".jsp");
		
		return vr;
	}
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/img/**").addResourceLocations("/img/");
	    registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		System.out.println("view controller in ICSConfig is called");
		//registry.addViewController("/login.html").setViewName("login");
//		registry.addViewController("/").setViewName("vendor");
	}
	
	@Bean
	public Product product() {
		return new Product();
	}
	
	@Bean
	public Vendor vendor() {
		return new Vendor();
	}
	
	@Bean
	public ProductDAO productDAO() {
		return new ProductInMemoryDAOImpl();
	}
	
	@Bean
	public ProductService productService() {
		return new ProductServiceImpl();
	}
	
	@Bean
	public RoleDAO RoleDAO() {
		return new RoleDAOImpl();
	}
	
	@Bean
	public RoleService roleService() {
		return new RoleServiceImpl();
	}

	@Bean
	public UserDAO userDAO() {
		return new UserDAOImpl();
	}
	
	@Bean
	public UserService UserService() {
		return new UserServiceImpl();
	}
	@Bean
	public SecurityService SecurityService() {
		return new SecurityServiceImpl();
	}

	@Bean
	public CartService cartService() {
		return new CartServiceImpl();
	}
	@Bean
	public ReplenishmentOrderService rpOS() {
		return new ReplenishmentOrderServiceImpl();
	}
	@Bean
	public ReceivedRpOrderService receivedRpOrderService() {
		return new ReceivedRpOrderServieImpl();
	}
	@Bean
	public Role role() {
		return new Role();
	}

	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		OpenSessionInViewInterceptor osiv = new OpenSessionInViewInterceptor();
		osiv.setSessionFactory(sessionFactory);
		registry.addWebRequestInterceptor(osiv);
	}	
	
}
