package ics.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ics.dao.VendorDAO;
import ics.model.Vendor;

@Service
public class VendorServiceImpl implements VendorService{
	@Autowired
	private VendorDAO VendorDAO;
	
	@Transactional
	public void addOrUpdateVendor(Vendor vendor) {
		VendorDAO.addOrUpdateVendor(vendor);
	}
	@Transactional
	public Collection<Vendor> listVendors() {
		return VendorDAO.listVendors();
	}
	@Transactional
	public Vendor get(Long vendorID) {
		return VendorDAO.get(vendorID);
	}
	@Transactional
	public void delete(Long vendorID) {
		VendorDAO.delete(vendorID);
	}

}
