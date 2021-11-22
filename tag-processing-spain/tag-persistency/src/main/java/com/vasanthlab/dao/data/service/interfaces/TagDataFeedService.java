package com.vasanthlab.dao.data.service.interfaces;


import com.vasantlab.data.tables.TagDataFeed;

public interface TagDataFeedService {
	public void saveFileRecords( TagDataFeed records);
	
	public int findBarcode(String orderId,String barCode);
	
	public String findFilePath(String orderId,String barCode);
	
	public TagDataFeed findTagData(String orderId,String barCode);
	
}
