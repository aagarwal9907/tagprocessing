package com.vasantlab.tag.processingservice;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vasanthlab.dao.data.service.interfaces.RFIDDataService;
import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.model.DataProcessingStatus;
import com.vasantlab.model.ProcessingContext;
import com.vasantlab.model.TagProcessingInput;
import com.vasantlab.tag.processingservice.interfaces.TagProcessingService;
import com.vasantlab.tag.service.exception.TagError;
import com.vasantlab.tagdriver.RFIdDriverReference;
import com.vasantlab.tagdriver.RFIdInterfaceAdaptor;

@Component
public class TagProcessingServiceImpl implements TagProcessingService {

	@Autowired
	RFIDDataService rfidDataService;
	
	@Autowired
	private RFIdDriverReference rfIdDriverReference;
	
	@Autowired
	private TagOperationService tagOperationService;

	private RFIdInterfaceAdaptor rfIdAdaptor;
	
	@Override
	public void tagProcessing(TagProcessingInput tagProcessingInput) {

		String orderId = tagProcessingInput.getOrderId();
		String barcode = tagProcessingInput.getBarcode();
		
		
		// Find data in the database using OrderID and BarCode.
		List<RFIDData> dataRFDetailsList = new LinkedList<RFIDData>();
		List<RFIDData> rfidData = rfidDataService.finddata(orderId, barcode);
		if (rfidData.size() > 0) {
			
			for (RFIDData data : rfidData) {
				
					rfIdAdaptor = rfIdDriverReference.getDriverReference();
					ProcessingContext writeProcessingContext = rfIdAdaptor.tagWrite(data);

					if (writeProcessingContext.getTagError() == TagError.WRITEOK) {
						ProcessingContext readProcessingContext = rfIdAdaptor.tagRead();

						TagError validation = tagOperationService.epcValidation(readProcessingContext, data.getEpc());
						if (validation == TagError.VALIDATIONOK) {
							data.setStatus(DataProcessingStatus.DO.toString());
						} else if (validation == TagError.VALIDATIONFAIL) {
							data.setStatus(DataProcessingStatus.FA.toString());
						}
					} else if (writeProcessingContext.getTagError() == TagError.WRITEFAIL) {
						data.setStatus(DataProcessingStatus.FA.toString());
					}
					//need to add other values to data for database
					dataRFDetailsList.add(data);
				}
		} else {
			System.out.println("Data for"+orderId+"and"+barcode+"dose not exit;");
		}
	}
}
