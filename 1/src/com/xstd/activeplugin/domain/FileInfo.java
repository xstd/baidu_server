package com.xstd.activeplugin.domain;

public class FileInfo {
	private String software_name;
	private String package_name;
	private String everyday_count;
	private String apk_uri;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSoftware_name() {
		return software_name;
	}
	public void setSoftware_name(String software_name) {
		this.software_name = software_name;
	}
	public String getPackage_name() {
		return package_name;
	}
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}
	public String getEveryday_count() {
		return everyday_count;
	}
	public void setEveryday_count(String everyday_count) {
		this.everyday_count = everyday_count;
	}
	public String getApk_uri() {
		return apk_uri;
	}
	public void setApk_uri(String apk_uri) {
		this.apk_uri = apk_uri;
	}
	
}
