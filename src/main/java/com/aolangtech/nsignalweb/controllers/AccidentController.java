package com.aolangtech.nsignalweb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aolangtech.nsignalweb.exceptions.NSException;
import com.aolangtech.nsignalweb.models.AccidentModel;
import com.aolangtech.nsignalweb.models.DriverModel;
import com.aolangtech.nsignalweb.services.AccidentService;
import com.aolangtech.nsignalweb.services.DriverService;

@RestController
@RequestMapping(value = "/accident")
public class AccidentController {

	@Autowired
	private DriverService driverService;
	
	@Autowired
	private AccidentService accidentService;

	@RequestMapping(path = "/getAllAccidents", method = RequestMethod.GET)
	public List<AccidentModel> getAllAccidents() {
		// get all complaints
		List<AccidentModel> results = accidentService.getAll();
		
		DriverModel tmpDriver = null;
		for(AccidentModel accident : results) {
			try{
				// get correspond to driver
				tmpDriver = driverService.getDriverBaseById(accident.getDriverId());
				accident.setDriverIdNo(tmpDriver.getIdNo());
				accident.setDriverName(tmpDriver.getName());
				accident.setCarNumber(tmpDriver.getCarNumber());
			} catch (Exception e) {
			}
		}
		return results;
	}
	
	@RequestMapping(path = "/addAccident", method = RequestMethod.POST)
	public AccidentModel addAccident(@RequestBody AccidentModel record) throws NSException {
		if(record == null || record.getDriverId() == null) {
			throw new NSException("Accident is null");
		}
		// insert driver
		accidentService.addAccident(record);

		return record;
	}
	
	@RequestMapping(path = "/updateAccident", method = RequestMethod.POST)
	public Integer updateAccident(@RequestBody AccidentModel record) throws NSException {
		if(record == null || record.getDriverId() == null) {
			throw new NSException("Accident is null");
		}
		return accidentService.updateAccident(record);
	}
	
	@RequestMapping(path = "/removeAccident", method = RequestMethod.POST)
	public Integer removeAccident(@RequestBody Long id) throws NSException {
		if(id == null) {
			throw new NSException("Accident's id is null");
		}
		return accidentService.removeAccident(id);
	}
	
	@RequestMapping(path = "/removeAccidentList", method = RequestMethod.POST)
	public Integer updateAccident(@RequestBody List<Long> ids) throws NSException {
		if(ids == null) {
			throw new NSException("Accident's id is null");
		}
		int count = 0;
		for(Long id : ids) {
			count += accidentService.removeAccident(id);
		}
		return count;
	}		
}
