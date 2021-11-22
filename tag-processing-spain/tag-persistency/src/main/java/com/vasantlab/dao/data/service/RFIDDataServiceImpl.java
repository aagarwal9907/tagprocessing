package com.vasantlab.dao.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.vasanthlab.dao.data.service.interfaces.RFIDDataService;
import com.vasantlab.dao.interfaces.RFIDDataDao;
import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.model.DataProcessingStatus;

@Service("rfidDataService")
@Transactional(propagation = Propagation.REQUIRED)
public class RFIDDataServiceImpl implements RFIDDataService {

	@Autowired
	RFIDDataDao rfidDataDao;
	@Override
	public void saveRFIDData(RFIDData rfidData) {
		rfidDataDao.saveAndFlush(rfidData);
	}

	@Override
	public void saveFileRecords(List<RFIDData> records) {

		int size = records.size();
		int counter = 0;

		List<RFIDData> temp = new ArrayList<>();

		for (RFIDData rfidData : records) {

			RFIDData rfidDatafeed = new RFIDData();
			rfidDatafeed.setAccess_Password(rfidData.getAccess_Password());
			rfidDatafeed.setBarcode(rfidData.getBarcode());
			rfidDatafeed.setEpc(rfidData.getEpc());
			rfidDatafeed.setKill_Password(rfidData.getKill_Password());
			rfidDatafeed.setUser_Memory(rfidData.getUser_Memory());
			rfidDatafeed.setStatus(DataProcessingStatus.RE.toString());
			temp.add(rfidDatafeed);

			if ((counter + 1) % 100 == 0 || (counter + 1) == size) {
				rfidDataDao.saveAll(temp);
				rfidDataDao.flush();
				temp.clear();
			}
			counter++;
		}
	}

	@Override
	public List<RFIDData> finddata(String orderId, String barCode) {

		return rfidDataDao.findByOrderId(orderId, barCode);

	}

}
