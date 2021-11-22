package com.vasantlab.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.vasantlab.data.tables.TagDataFeed;

/**
 *  IOS_NO represents ORDER ID as per excel sheet received, This is order id for all application tables
 * 
 * @author Shubha
 *
 */
@Repository
@Transactional
public interface TagDataFeedDao extends JpaRepository<TagDataFeed, Long> {

	@Query(value = "SELECT COUNT(*) FROM tb_tag_data_feed WHERE IOS_NO=? and barcode_details=?", nativeQuery = true)
	int findBarcode(@Param("IOS_NO") String orderId,@Param("barcode_details") String barcode_details);
	
	@Query(value = "SELECT * FROM tb_tag_data_feed WHERE IOS_NO=? and barcode_details=?", nativeQuery = true)
	TagDataFeed findTagData(@Param("IOS_NO") String orderId,@Param("barcode_details") String barcode_details);
	
	@Query(value = "SELECT columnVal FROM tb_tag_data_feed WHERE IOS_NO=? ", nativeQuery = true)
    String findByOrderId(@Param("OrderId") String OrderId);
	
	@Query(value = "SELECT file_path FROM tb_tag_data_feed WHERE IOS_NO=? and barcode_details=?" , nativeQuery = true)
    String findFilePath(@Param("OrderId") String OrderId,@Param("barcode_details") String barcode_details);

}
