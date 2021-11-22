package com.vasantlab.data.tables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.core.style.ToStringCreator;
@Entity
@Table(name = "tb_tag_data_feed")
public class TagDataFeed implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7525794628505733164L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "IOS_NO")
	private String IOS_NO;
	@Column(name = "file_name")
	private String file_name;
	@Column(name = "file_path")
	private String file_path;
	@Column(name = "unique_code_name")
	private String unique_code_name;
	@Column(name = "barcode_details	")
	private String barcode_details	;
	@Column(name = "epc_code")
	private String epc_code;
	@Column(name = "kill_password")
	private String kill_password;
	@Column(name = "user_memory")
	private String user_memory;
	@Column(name = "access_password")
	private String access_password;
	@Column(name = "tag_kill_bit")
	private String tag_kill_bit;
	@Column(name ="column_val")
	private String columnVal;
	
	
	public String getColumnVal() {
		return columnVal;
	}

	public void setColumnVal(String columnVal) {
		this.columnVal = columnVal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIOS_NO() {
		return IOS_NO;
	}

	public void setIOS_NO(String iOS_NO) {
		IOS_NO = iOS_NO;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getUnique_code_name() {
		return unique_code_name;
	}

	public void setUnique_code_name(String unique_code_name) {
		this.unique_code_name = unique_code_name;
	}

	public String getBarcode_details() {
		return barcode_details;
	}

	public void setBarcode_details(String barcode_details) {
		this.barcode_details = barcode_details;
	}

	public String getEpc_code() {
		return epc_code;
	}

	public void setEpc_code(String epc_code) {
		this.epc_code = epc_code;
	}

	public String getKill_password() {
		return kill_password;
	}

	public void setKill_password(String kill_password) {
		this.kill_password = kill_password;
	}

	public String getUser_memory() {
		return user_memory;
	}

	public void setUser_memory(String user_memory) {
		this.user_memory = user_memory;
	}

	public String getAccess_password() {
		return access_password;
	}

	public void setAccess_password(String access_password) {
		this.access_password = access_password;
	}

	public String getTag_kill_bit() {
		return tag_kill_bit;
	}

	public void setTag_kill_bit(String tag_kill_bit) {
		this.tag_kill_bit = tag_kill_bit;
	}

	@Override
	public String toString() {
		 ToStringCreator creator=new ToStringCreator(this);
		 return creator.toString();
	}



}
