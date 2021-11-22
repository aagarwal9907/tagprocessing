package com.vasantlab.dao.data.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.vasanthlab.dao.data.service.interfaces.TagDataFeedService;
import com.vasantlab.dao.interfaces.TagDataFeedDao;
import com.vasantlab.data.tables.TagDataFeed;


@Service("tagDataFeedService")
@Transactional(propagation = Propagation.REQUIRED)
public class TagDataFeedServiceImpl implements TagDataFeedService {
	@Autowired
	private TagDataFeedDao tagDataFeedDao;
	@Override
	public void saveFileRecords(TagDataFeed records) {
			tagDataFeedDao.saveAndFlush(records);
		
	}
	
	@Override
	public int findBarcode(String orderId, String barCode) {
		
		return tagDataFeedDao.findBarcode(orderId, barCode);
	}

	@Override
	public String findFilePath(String orderId, String barCode) {
		
		return tagDataFeedDao.findFilePath(orderId, barCode);
	}

	@Override
	public TagDataFeed findTagData(String orderId, String barCode) {
		
		return tagDataFeedDao.findTagData(orderId, barCode);
	}

}
