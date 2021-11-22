package com.vasantlab;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.thingmagic.ReaderException;
import com.vasanthlab.serialport.comm.TwoWaySerialComm;
import com.vasantlab.config.SargasConfig;
import com.vasantlab.factory.RFIDTagAdaptorFactory;
import com.vasantlab.tag.event.TagDataEvent;
import com.vasantlab.tag.model.SargasDriver;
import com.vasantlab.tag.operation.RFIDDriverOperate;
import com.vasantlab.tag.service.exception.TagDriverErrorHandler;
import com.vasantlab.tag.util.SargasRFIDReaderWriter;
import com.vasantlab.tagdriver.RFIdInterfaceAdaptor;
import com.vasantlab.tagdriver.SargasRFIdAdaptor;

@SpringBootApplication
@Import({ WebSocketConfig.class })
@ComponentScan({ "com.vasanthlab", "com.vasantlab.file", "com.vasantlab.dao.data.service",
	"com.vasantlab.tag.operation", "com.vasantlab.tag.processingservice", "com.vasantlab.util",
	"com.vasantlab.tagdriver", "com.vasanthlab.serialport.comm", "com.vasantlab.tag.state",
	"com.vasantlab.tag.event", "com.vasantlab.factory", "com.vasantlab.file.processinterface",
	"com.vasantlab.file.service" })
@PropertySource(value = { "file:config/sargas.properties" })
@EnableConfigurationProperties(SargasConfig.class)
public class OrderTagProcessorApplication extends SpringBootServletInitializer implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(OrderTagProcessorApplication.class);

    @Autowired
    private SargasConfig config;

    @Value("${com.port}")
    private String comPort;

    @Value("${log4j.configfile}")
    private String log4jConfigFile;

    private TagDriverErrorHandler driverErrorHandler = new TagDriverErrorHandler();

    public static void main(String[] args) {
	SpringApplication.run(OrderTagProcessorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

	int[] antenna = config.getAntennaAsIntArray();
	Long start = System.currentTimeMillis();
	try {
	    SargasRFIDReaderWriter.initialize(config.getUrl(), antenna);
	} catch (Exception e) {

	    driverErrorHandler.getTagDriverError(20000, e.getStackTrace());
	}
	try {
	    SargasRFIDReaderWriter.connect();

	    SargasRFIDReaderWriter.setupRegionAndDetectAntenna();
	} catch (ReaderException re) {
	    if (config.getShutdownEnable()) {
		System.exit(-1);
	    }
	} catch (Exception e) {
	    driverErrorHandler.getTagDriverError(20005, e.getStackTrace());
	}
	Long end = System.currentTimeMillis();
	log.info("initialize time: {}", (end - start));
	log.info(config.getUrl());
	log.info(config.getAntenna());

    }

    @Bean
    public SargasDriver sargasDriver() {
	SargasDriver driver = new SargasDriver();
	driver.setEvent(new TagDataEvent());
	driver.setWriteAntennaNo(config.getWrite());
	driver.setReadAntennaNo(config.getRead());
	return driver;
    }

    @Bean
    public RFIDTagAdaptorFactory rfidTagFactory() {
	RFIDTagAdaptorFactory factory = new RFIDTagAdaptorFactory();
	RFIdInterfaceAdaptor sargasAdaptor = new SargasRFIdAdaptor(new RFIDDriverOperate(), sargasDriver());
	factory.register("mercury", sargasAdaptor);
	return factory;
    }

    @Bean
    public String rfidDriverName(@Value("${rfid.driver.name}") String name) {
	return name;
    }

    // @ConditionalOnProperty(value = "comsimulator.enabled", havingValue = "false",
    // matchIfMissing = false)
    @Bean
    public TwoWaySerialComm twoWaySerialComm() throws Exception {
	TwoWaySerialComm twoWaySerialComm = new TwoWaySerialComm();
	twoWaySerialComm.connect(comPort);
	return twoWaySerialComm;
    }

    @PreDestroy
    public void terminateDeviceConnections() {
	SargasRFIDReaderWriter.destroy();
    }

    @PostConstruct
    public void configLog4j() {
	DOMConfigurator.configureAndWatch(log4jConfigFile); // configuration of log4j from config file instead of taking
							    // it from classpath

    }

}
