package com.vasantlab.dao.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vasanthlab.dao.data.service.interfaces.RFIDErrorLogDataService;
import com.vasantlab.dao.interfaces.RFIDErrorLogDao;
import com.vasantlab.data.tables.RFIDErrorLog;

@Service("rfidErrorLogDataService")
@Transactional(propagation = Propagation.REQUIRED)
public class RFIDErrorLogDataServiceImpl implements RFIDErrorLogDataService{

	@Autowired
	private RFIDErrorLogDao rFIDErrorLogDao;
	@Override
	public void saveRFIDErrorLogData(RFIDErrorLog rfidErrorLog) {
		rFIDErrorLogDao.saveAndFlush(rfidErrorLog);
		
	}

}
