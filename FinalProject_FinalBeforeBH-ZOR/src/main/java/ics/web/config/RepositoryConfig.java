package ics.web.config;


import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ics.dao.OrderDAO;
import ics.dao.OrderHibernateDAOImpl;
import ics.dao.ProductDAO;
import ics.dao.ProductHibernateDAOImpl;
import ics.dao.VendorDAO;
import ics.dao.VendorHibernateDAOImpl;
import ics.model.Order;
import ics.model.Product;
import ics.model.Role;
import ics.model.User;
import ics.model.Vendor;

@Configuration
@EnableTransactionManagement
@ComponentScan("ics")
public class RepositoryConfig {
	  
	@Bean()    
	    public DataSource getDataSource()
	    {
	        DriverManagerDataSource ds = new DriverManagerDataSource();        
	        ds.setDriverClassName("com.mysql.jdbc.Driver");
	        ds.setUrl("jdbc:mysql://localhost:3306/inventorysys");
	        ds.setUsername("root");
	        ds.setPassword("0326");        
	        return ds;
	    }
	    
	    @Bean
	    @Autowired
	    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
	    {
	        HibernateTransactionManager htm = new HibernateTransactionManager();
	        htm.setSessionFactory(sessionFactory);
	        return htm;
	    }
	    
	    @Bean
	    @Autowired
	    public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory)
	    {
	        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
	        return hibernateTemplate;
	    }
	        
//	    @Bean
//	    public AnnotationSessionFactoryBean getSessionFactory()
//	    {
//	        AnnotationSessionFactoryBean asfb = new AnnotationSessionFactoryBean();
//	        asfb.setDataSource(getDataSource());
//	        asfb.setHibernateProperties(getHibernateProperties());        
//	        asfb.setPackagesToScan(new String[]{"com.sivalabs"});
//	        return asfb;
//	    }
	    
		@Bean
		public SessionFactory sessionFactory() {
			LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
			lsfb.setDataSource(getDataSource());
			lsfb.setPackagesToScan("com.concretepage.entity");
			lsfb.setHibernateProperties(getHibernateProperties());
			lsfb.setAnnotatedClasses(new Class[] {Product.class,Vendor.class,User.class,Role.class,Order.class});
			try {
				lsfb.afterPropertiesSet();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return lsfb.getObject();
		}

	    @Bean
	    public Properties getHibernateProperties()
	    {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	       // properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
	        properties.put("hibernate.show_sql", "true");
	        properties.put("hibernate.hbm2ddl.auto", "update");
	        
	        return properties;
	    }
	    
		@Autowired
		@Bean
		public ProductDAO getProductDao(SessionFactory sessionFactory) {
			return new ProductHibernateDAOImpl(sessionFactory);
		}
		@Autowired
		@Bean
		public OrderDAO orderHibernateDAOImpl(SessionFactory sessionFactory) {
			return new OrderHibernateDAOImpl(sessionFactory);
		}
		@Autowired
		@Bean
		public VendorDAO vendorHibernateDAOImpl(SessionFactory sessionFactory) {
			return new VendorHibernateDAOImpl(sessionFactory);
		}
    
}

