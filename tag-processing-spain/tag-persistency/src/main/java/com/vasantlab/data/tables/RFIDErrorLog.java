package com.vasantlab.data.tables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "tb_rfid_error_log")
public class RFIDErrorLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "order_id")
	private String order_id;
	@Column(name = "bar_code")
	private String bar_code;
	@Column(name = "msg")
	private String msg;
	@Column(name = "date")
	private String date;
	@Column(name = "time")
	private String time;
	
	@Column(name = "error_code")
	private String error_code;
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	
	@Override
	public String toString() {
		return "RRIDErrorLog [order_id=" + order_id + ", bar_code=" + bar_code + ", msg=" + msg
				+ ", date=" + date + ", time=" + time  + ", error_code=" + error_code
				+ ", getOrder_id()=" + getOrder_id() + ", getBar_code()=" + getBar_code() + ", getMsg()=" + getMsg()
				+ ", getDate()=" + getDate() + ", getTime()=" + getTime() + ", getError_code()=" + getError_code() + "]";
	}
}