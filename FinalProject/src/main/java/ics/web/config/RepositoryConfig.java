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

import ics.dao.CartDAO;
import ics.dao.CartHibernateDAOImpl;
import ics.dao.OrderDAO;
import ics.dao.OrderHibernateDAOImpl;
import ics.dao.ProductDAO;
import ics.dao.ProductHibernateDAOImpl;
import ics.dao.ReceivedRpOrderDAO;
import ics.dao.ReceivedRpOrderHibernateDAOImpl;
import ics.dao.ReplenishmentOrderDAO;
import ics.dao.VendorDAO;
import ics.dao.VendorHibernateDAOImpl;
import ics.model.ReceivedRpOrder;
import ics.model.ReplenishmentOrder;

@Configuration
@EnableTransactionManagement
@ComponentScan("ics")
public class RepositoryConfig {
	  
		@Bean    
	    public DataSource getDataSource()
	    {
	        DriverManagerDataSource ds = new DriverManagerDataSource();        
	        ds.setDriverClassName("com.mysql.jdbc.Driver");
	        ds.setUrl("jdbc:mysql://localhost:3306/bhzorinventory");
	        ds.setUsername("root");
	        ds.setPassword("0326");    	        
//	        ds.setUrl("jdbc:mysql://bhzorinventory.crnhc996pmes.us-east-2.rds.amazonaws.com:3306/bhzorinventory");
//	        ds.setUsername("root");
//	        ds.setPassword("12345678");
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
			lsfb.setPackagesToScan(new String[] {"ics.model"});
			lsfb.setHibernateProperties(getHibernateProperties());
//			lsfb.setAnnotatedClasses(new Class[] {Product.class,Vendor.class,User.class,Role.class,Order.class});
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
		@Autowired
		@Bean
		public CartDAO cartHibernateDAOImpl(SessionFactory sessionFactory) {
			return new CartHibernateDAOImpl(sessionFactory);
		}
		@Autowired
		@Bean
		public ReplenishmentOrderDAO ReplenishmentOrderHibernateDAOImpl(SessionFactory sessionFactory) {
			return new ics.dao.ReplenishmentOrderHibernateDAOImpl(sessionFactory);
		}
		@Autowired
		@Bean
		public ReceivedRpOrderDAO ReceivedOrderHibernateDAOImpl(SessionFactory sessionFactory) {
			return new ReceivedRpOrderHibernateDAOImpl(sessionFactory);
		}
    
}

