package ics.dao;

import java.util.Collection;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ics.model.Vendor;


@Repository
public class VendorHibernateDAOImpl implements VendorDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public VendorHibernateDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public Collection<Vendor> listVendors() {
		@SuppressWarnings("unchecked")
		List<Vendor> listVendor = sessionFactory.getCurrentSession()
				.createQuery("from Vendor").list();
		return listVendor;
	}
	
	@Transactional
	public void addOrUpdateVendor(Vendor vendor) {
		sessionFactory.getCurrentSession().saveOrUpdate(vendor);
	}
	
	@Transactional
	public Vendor get(Long vendorID) {
		String hql = "from Vendor where id=" + vendorID;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		@SuppressWarnings("unchecked")
		List<Vendor> listVendor = (List<Vendor>) query.list();
		if(listVendor != null && !listVendor.isEmpty()) {
			return listVendor.get(0);
		}
		return null;
	}
	
	@Transactional
	public void delete(Long vendorID) {
		Vendor VendorToDelete = new Vendor();
		VendorToDelete.setVendorID(vendorID);
		sessionFactory.getCurrentSession().delete(VendorToDelete);
		
	}

}
