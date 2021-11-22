package com.vasanthlab.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.vasanthlab.dao.data.service.interfaces.RFIDDataService;
import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.exception.FileProcessingException;
import com.vasantlab.model.DeviceError;
import com.vasantlab.model.DeviceReadyEvent;
import com.vasantlab.model.ProcessControllerResponse;
import com.vasantlab.model.ReportGenerationStatus;
import com.vasantlab.model.ResumeResponse;
import com.vasantlab.model.TagProcessingInput;
import com.vasantlab.tag.processingservice.exception.TagProcessingException;
import com.vasantlab.tag.processingservice.interfaces.TagErrorProcessingService;
import com.vasantlab.tag.processingservice.interfaces.TagReportService;
import com.vasantlab.tag.processingservice.interfaces.TagStartService;

import reactor.core.publisher.Flux;

/**
 * Main controller for tagging activities
 * @author Shubha
 *
 */
@RestController
@RequestMapping("api/v1/tagging")
public class ProcessController {
	private static Logger log = LoggerFactory.getLogger(ProcessController.class);
	
	@Autowired
	private TagStartService startService;
	
	@Autowired
	private TagErrorProcessingService tagErrorProcessingService;
	
	@Autowired
	private RFIDDataService rfidDataService;
	
	@Autowired
	private TagReportService tagReportService;
	/**
	 * This service method allow operator to enter the OrderId and barcode as input parameters <code>TagProcessingInput</code>
	 * This service triggers operations of events related to data read from file for first time and storage in cache. 
	 * This would then send command to com port device to start the tagging activity</p>
	 * 
	 * @param TagProcessingInput tagProcessingInput
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/start", consumes = "application/json")
	public ResponseEntity<ProcessControllerResponse> startJob(@RequestBody TagProcessingInput tagProcessingInput) {
		ResponseEntity<ProcessControllerResponse> response = new ResponseEntity<>(
				new ProcessControllerResponse("Started"), HttpStatus.OK);
		log.info("received for processing {}", tagProcessingInput);
		try {
			startService.startProcessing(tagProcessingInput);
		} catch (TagProcessingException|FileProcessingException e) {
			response = new ResponseEntity<>(new ProcessControllerResponse(e.getMessage()), HttpStatus.OK);
		}
		return response;

	}
	
	/**
	 * <p>This service method uses <code>SSE</code> to inform the user interface that the COM device is ready and active to receive
	 * tagging command from the user. If the value return is true, the submit button on the interface would become enable and user can 
	 * provide orderid and barcode to start tagging the batch</p>
	 * @return SseEmitter
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/status", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter startJob() {
		SseEmitter emitter = new SseEmitter();
		ExecutorService nonBlockingService = Executors.newSingleThreadExecutor();
		nonBlockingService.execute(() -> {
			try {
				emitter.send(DeviceReadyEvent.isDeviceReady());
				// we could send more events
				emitter.complete();
			} catch (Exception ex) {
				emitter.completeWithError(ex);
			}
		});
		nonBlockingService.shutdown();
		return emitter;
	}
	
	/**
	 * This method sends all the error code back to the interface and that are show as popup on the user interface at the home screen
	 * @return DeviceError
	 * @see DeviceError
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/device/error", produces = "application/json")
	public DeviceError getDeviceErrors() {
		if (DeviceError.hasError()) {
			log.info("fetching message from error stack");
			DeviceError error = DeviceError.pop();
			if (error != null && log.isInfoEnabled()) {
				log.info(error.toString());
			}
			return error;
		}
		return null;
	}
	
	/**
	 * <p>Service method allow Resuming of tagging services after the operator act on the device error message. 
	 * since the message response handling is pre-coded. The user action of dismissing the message would automatically send the bite code back to
	 * com device. </p>
	 * @param  DeviceError error
	 * @return ResponseEntity<ResumeResponse>
	 */
	@RequestMapping(method=RequestMethod.POST, path="/device/error/resume",produces="application/json")
	public ResponseEntity<ResumeResponse> resumeFromError(@RequestBody DeviceError error){
		if(error!=null) {
			tagErrorProcessingService.propagateError(error.getState());
		}
		return new ResponseEntity<>(new ResumeResponse("Done"),HttpStatus.OK);
		
	}
	
	/**
	 * This service will handle generation of the report on input of orderid and barcode provided in <code>TagProcessingInput</code>.
	 * If the report generation fails due to file system or spreedsheet error then the ReportGenerationStatus would have <code>ErrorCode.error</code>
	 * as response along with any system error message
	 * @param tagProcessingInput
	 * @return ReportGenerationStatus
	 * @see ReportGenerationStatus
	 */
	@RequestMapping(method=RequestMethod.POST, path="/report/generate",produces="application/json")
	public  ReportGenerationStatus generateReport(@RequestBody TagProcessingInput tagProcessingInput) {
		
		return tagReportService.reportProcessing(tagProcessingInput);
	}
	
	public List<RFIDData> onlineReport(@RequestBody TagProcessingInput tagProcessingInput){
		return rfidDataService.finddata(tagProcessingInput.getOrderId(), tagProcessingInput.getBarcode());
	}
}
