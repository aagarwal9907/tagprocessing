package com.vasantlab.tag.processingservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.vasantlab.tag.processingservice.interfaces.CommPortReadCommandService;
import com.vasantlab.util.CommPortReadConfigurationCommand;
import com.vasantlab.util.DataConverter;


@Service("commPortReadConfigService")
public class CommPortReadCommandServiceImpl implements CommPortReadCommandService{
	private static Logger log = LoggerFactory.getLogger(CommPortReadCommandServiceImpl.class);
	
	private int decimalData=-1;
	@Override
	public void readCommand(char[] recevieData) {
		//Fetching ReadCommand
		StringBuilder commandCode = new StringBuilder();
		commandCode.append(recevieData[8]).append(recevieData[9]);
		CommPortReadConfigurationCommand command =CommPortReadConfigurationCommand.fromString(commandCode.toString());
		String configCommand = command.getReadConfigurationCommand();
		//Fetching Data
		decimalData = -1;
		StringBuilder dataCode = new StringBuilder();
		//Fetching data byte.
		StringBuilder byteCode = new StringBuilder();
		byteCode.append(recevieData[6]).append(recevieData[7]);
		if(byteCode.toString().equals("02")){
			decimalData = DataConverter.hexadecimalToDecimal("0");
		}else if(byteCode.toString().equals("03")){
			dataCode.append(recevieData[10]).append(recevieData[11]);
			decimalData = DataConverter.hexadecimalToDecimal(dataCode.toString());
		}else if(byteCode.toString().equals("04")){
			dataCode.append(recevieData[10]).append(recevieData[11]).append(recevieData[12]).append(recevieData[13]);
			decimalData = DataConverter.hexadecimalToDecimal(dataCode.toString());
		}
		
		log.info("Data received for config command {}",decimalData);
			//Using GUI display Data
	}

	public int getData() {
		
		return decimalData;
	}


}
