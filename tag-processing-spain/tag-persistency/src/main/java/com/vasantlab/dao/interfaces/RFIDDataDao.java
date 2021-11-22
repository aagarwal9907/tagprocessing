package com.vasantlab.dao.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vasantlab.data.tables.RFIDData;


public interface RFIDDataDao extends JpaRepository<RFIDData, Long>{

	
	@Query(value = "SELECT * FROM tb_rfid_data WHERE OrderId=? and BarCode=?", nativeQuery = true)
    List<RFIDData> findByOrderId(@Param("OrderId") String orderId,@Param("BarCode") String barCode);
}
