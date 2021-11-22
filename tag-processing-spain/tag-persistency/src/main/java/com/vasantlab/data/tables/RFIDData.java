package com.vasantlab.data.tables;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * @author Vibha
 *
 */
@Entity
@Table(name = "tb_rfid_data")
public class RFIDData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3133588360049442402L;
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "Barcode")
    private String barcode;
    @Column(name = "EPC", unique = true, nullable = false)
    private String epc;
    @Column(name = "User_Memory")
    private String user_Memory;
    @Column(name = "Access_Password")
    private String access_Password;
    @Column(name = "Kill_Password")
    private String kill_Password;
    @Column(name = "status")
    private String status;
    @Column(name = "RSSI")
    private String rssi;
    @Column(name = "Time")
    private String time;
    @Column(name = "Count")
    private String count;
    @Column(name = "orderid")
    private String orderId;
    @Column(name = "TidData")
    private String tidData;
    @Column(name = "Date")
    private String date;
    @Column(name = "memory_rw_error")
    private String memory_rw_error;

    public RFIDData() {
	FastDateFormat dateFormat = FastDateFormat.getInstance("dd-MM-YYYY");
	date = dateFormat.format(Calendar.getInstance());
    }

    public String getMemory_rw_error() {
	return memory_rw_error;
    }

    public void setMemory_rw_error(String memory_rw_error) {
	this.memory_rw_error = memory_rw_error;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public String getTidData() {
	return tidData;
    }

    public void setTidData(String tidData) {
	this.tidData = tidData;
    }

    public String getOrderId() {
	return orderId;
    }

    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    public String getRssi() {
	return rssi;
    }

    public void setRssi(String rssi) {
	this.rssi = rssi;
    }

    public String getTime() {
	return time;
    }

    public void setTime(String time) {
	this.time = time;
    }

    public String getCount() {
	return count;
    }

    public void setCount(String count) {
	this.count = count;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getBarcode() {
	return barcode;
    }

    public void setBarcode(String barcode) {
	this.barcode = barcode;
    }

    public String getEpc() {
	return epc;
    }

    public void setEpc(String epc) {
	this.epc = epc;
    }

    public String getUser_Memory() {
	return user_Memory;
    }

    public void setUser_Memory(String user_Memory) {
	this.user_Memory = user_Memory;
    }

    public String getAccess_Password() {
	return access_Password;
    }

    public void setAccess_Password(String access_Password) {
	this.access_Password = access_Password;
    }

    public String getKill_Password() {
	return kill_Password;
    }

    public void setKill_Password(String kill_Password) {
	this.kill_Password = kill_Password;
    }

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }
}
