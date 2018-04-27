package com.aolangtech.nsignalweb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aolangtech.nsignalweb.exceptions.NSException;
import com.aolangtech.nsignalweb.models.ComplaintModel;
import com.aolangtech.nsignalweb.models.DriverModel;
import com.aolangtech.nsignalweb.services.ComplaintService;
import com.aolangtech.nsignalweb.services.DriverService;

@RestController
@RequestMapping(value = "/complaint")
public class ComplaintController {
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private DriverService driverService;

	@RequestMapping(path = "/getAllComplaints", method = RequestMethod.GET)
	public List<ComplaintModel> getAllComplaints() {
		// get all complaints
		List<ComplaintModel> results = complaintService.getAll();
		
		DriverModel tmpDriver = null;
		for(ComplaintModel complaint : results) {
			// get correspond to driver
			tmpDriver = driverService.getDriverBaseById(complaint.getDriverId());
			complaint.setDriverIdNo(tmpDriver.getIdNo());
			complaint.setDriverName(tmpDriver.getName());
			complaint.setCarNumber(tmpDriver.getCarNumber());
		}
		return results;
	}
	
	@RequestMapping(path = "/addComplaint", method = RequestMethod.POST)
	public ComplaintModel addComplaint(@RequestBody ComplaintModel record) throws NSException {
		if(record == null || record.getDriverId() == null) {
			throw new NSException("Complaint is null");
		}
		complaintService.addComplaint(record);
		return record;
	}
	
	@RequestMapping(path = "/updateComplaint", method = RequestMethod.POST)
	public Integer updateComplaint(@RequestBody ComplaintModel record) throws NSException {
		if(record == null || record.getDriverId() == null) {
			throw new NSException("Complaint is null");
		}
		return complaintService.updateComplaint(record);
	}
	
	@RequestMapping(path = "/removeComplaint", method = RequestMethod.POST)
	public Integer updateComplaint(@RequestBody Long id) throws NSException {
		if(id == null) {
			throw new NSException("Complaint's id is null");
		}
		return complaintService.removeComplaint(id);
	}
	
	@RequestMapping(path = "/removeComplaintList", method = RequestMethod.POST)
	public Integer updateComplaint(@RequestBody List<Long> ids) throws NSException {
		if(ids == null) {
			throw new NSException("Complaint's id is null");
		}
		int count = 0;
		for(Long id : ids) {
			count += complaintService.removeComplaint(id);
		}
		return count;
	}	
}
