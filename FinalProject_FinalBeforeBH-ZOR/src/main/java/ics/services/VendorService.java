package ics.services;

import java.util.Collection;

import ics.model.Vendor;


public interface VendorService {
	public Collection<Vendor> listVendors();
	public void addOrUpdateVendor(Vendor vendor);
	public Vendor get(Long vendorID);
	public void delete(Long vendorID);
}
