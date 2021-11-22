package com.vasantlab.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;


import com.vasantlab.data.tables.RFIDErrorLog;

public interface RFIDErrorLogDao extends JpaRepository<RFIDErrorLog, Long> {

}
