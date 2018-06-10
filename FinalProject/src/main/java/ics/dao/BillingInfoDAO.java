package ics.dao;

import java.util.Collection;

import ics.model.BillingInfo;

public interface BillingInfoDAO {
	public void addOrUpdateBillingInfo(BillingInfo billingInfo);
	public BillingInfo get(Long billingInfoID);
	public void delete(Long billingInfoID);
	public Collection<BillingInfo> listBillingInfo();
}
