package ics.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import ics.dao.BillingInfoDAO;
import ics.model.BillingInfo;

public class BillingInfoServiceImpl implements BillingInfoService {
	@Autowired
	private BillingInfoDAO billingInfoDAO;
	
	public void addOrUpdateBillingInfo(BillingInfo billingInfo) {
		billingInfoDAO.addOrUpdateBillingInfo(billingInfo);
	}

	public BillingInfo get(Long billingInfoID) {
		return billingInfoDAO.get(billingInfoID);
	}

	public void delete(Long billingInfoID) {
		billingInfoDAO.delete(billingInfoID);
	}

	public Collection<BillingInfo> listBillingInfo() {
		return billingInfoDAO.listBillingInfo();
	}

}
