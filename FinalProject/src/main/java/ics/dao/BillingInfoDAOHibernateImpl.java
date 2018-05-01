package ics.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ics.model.BillingInfo;

@Repository
public class BillingInfoDAOHibernateImpl implements BillingInfoDAO {
	@Autowired
	private SessionFactory sessionFactory;	
	
	public BillingInfoDAOHibernateImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public void addOrUpdateBillingInfo(BillingInfo billingInfo) {
		sessionFactory.getCurrentSession().saveOrUpdate(billingInfo);
	}
	
	@Transactional
	public BillingInfo get(Long billingInfoID) {
		return (BillingInfo) sessionFactory.getCurrentSession().get(BillingInfo.class, billingInfoID);
	}

	@Transactional
	public void delete(Long billingInfoID) {
		BillingInfo billingInfoToDelete = new BillingInfo();
		billingInfoToDelete.setBillID(billingInfoID);
		sessionFactory.getCurrentSession().delete(billingInfoID);
	}

}
