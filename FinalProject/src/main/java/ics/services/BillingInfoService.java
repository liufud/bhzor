package ics.services;

import org.springframework.stereotype.Service;

import ics.model.BillingInfo;

@Service
public interface BillingInfoService {
	public void addOrUpdateBillingInfo(BillingInfo billingInfo);
	public BillingInfo get(Long billingInfoID);
	public void delete(Long billingInfoID);
}
