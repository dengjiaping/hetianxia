package cn.anycall.map;

public class POIResultBean {
	
	private String name;//
	private POILocationBean location;
	private String address;//
	private String telephone;//
	private String uid;
	private POIDetailBean detail_info;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public POILocationBean getLocation() {
		return location;
	}
	public void setLocation(POILocationBean location) {
		this.location = location;
	}	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public POIDetailBean getDetail_info() {
		return detail_info;
	}
	public void setDetail_info(POIDetailBean detail_info) {
		this.detail_info = detail_info;
	}
}
