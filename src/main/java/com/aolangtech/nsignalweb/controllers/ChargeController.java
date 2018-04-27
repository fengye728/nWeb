package com.aolangtech.nsignalweb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aolangtech.nsignalweb.exceptions.NSException;
import com.aolangtech.nsignalweb.models.ChargeModel;
import com.aolangtech.nsignalweb.models.DriverModel;
import com.aolangtech.nsignalweb.services.ChargeService;
import com.aolangtech.nsignalweb.services.DriverService;

@RestController
@RequestMapping(value = "/charge")
public class ChargeController {

	@Autowired
	private DriverService driverService;
	
	@Autowired
	private ChargeService chargeService;

	@RequestMapping(path = "/getAllCharges", method = RequestMethod.GET)
	public List<ChargeModel> getAllCharges() {
		// get all complaints
		List<ChargeModel> results = chargeService.getAll();
			
		DriverModel tmpDriver = null;
		for(ChargeModel charge : results) {
			// get correspond to driver
			tmpDriver = driverService.getDriverBaseById(charge.getDriverId());
			charge.setDriverIdNo(tmpDriver.getIdNo());
			charge.setDriverName(tmpDriver.getName());
			charge.setCarNumber(tmpDriver.getCarNumber());
		}
		return results;
	}
	
	@RequestMapping(path = "/addCharge", method = RequestMethod.POST)
	public ChargeModel addCharge(@RequestBody ChargeModel record) throws NSException {
		if(record == null || record.getDriverId() == null) {
			throw new NSException("Charge is null");
		}
		chargeService.addCharge(record);
		return record;
	}
	
	@RequestMapping(path = "/updateCharge", method = RequestMethod.POST)
	public Integer updateCharge(@RequestBody ChargeModel record) throws NSException {
		if(record == null || record.getDriverId() == null) {
			throw new NSException("Charge is null");
		}
		return chargeService.updateCharge(record);
	}
	
	@RequestMapping(path = "/removeCharge", method = RequestMethod.POST)
	public Integer updateCharge(@RequestBody Long id) throws NSException {
		if(id == null) {
			throw new NSException("Charge's id is null");
		}
		return chargeService.removeCharge(id);
	}
	
	@RequestMapping(path = "/removeChargeList", method = RequestMethod.POST)
	public Integer updateCharge(@RequestBody List<Long> ids) throws NSException {
		if(ids == null) {
			throw new NSException("Charge's id is null");
		}
		int count = 0;
		for(Long id : ids) {
			count += chargeService.removeCharge(id);
		}
		return count;
	}		
}
