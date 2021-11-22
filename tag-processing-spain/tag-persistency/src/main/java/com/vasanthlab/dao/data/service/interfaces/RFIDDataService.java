package com.vasanthlab.dao.data.service.interfaces;

import java.util.List;


import com.vasantlab.data.tables.RFIDData;


public interface RFIDDataService {
	public void saveFileRecords( List<RFIDData> records);
	public void saveRFIDData(RFIDData rfidData);
	public List<RFIDData> finddata(String orderId,String barCode);
}
