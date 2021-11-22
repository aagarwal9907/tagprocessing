package com.vasantlab.tag.processingservice;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.vasantlab.data.tables.RFIDData;


@Service
public class MessageService {
    @Autowired
    public SimpMessagingTemplate messagingTemplate;

    public void sendMessage(RFIDData data) {
    	List<RFIDData> dataList=new LinkedList<>();
    	dataList.add(data);
        messagingTemplate.convertAndSend("/topic/feed", dataList);
    }
}
