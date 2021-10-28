package com.example.covid19.model;

import com.google.gson.annotations.SerializedName;

public class CountryInfo{

	@SerializedName("flag")
	private String flag;

	@SerializedName("_id")
	private float id;

	@SerializedName("iso2")
	private String iso2;

	@SerializedName("lat")
	private float lat;

	@SerializedName("long")
	private float jsonMemberLong;

	@SerializedName("iso3")
	private String iso3;

	public void setFlag(String flag){
		this.flag = flag;
	}

	public String getFlag(){
		return flag;
	}

	public void setId(float id){
		this.id = id;
	}

	public float getId(){
		return id;
	}

	public void setIso2(String iso2){
		this.iso2 = iso2;
	}

	public String getIso2(){
		return iso2;
	}

	public void setLat(float lat){
		this.lat = lat;
	}

	public float getLat(){
		return lat;
	}

	public void setJsonMemberLong(float jsonMemberLong){
		this.jsonMemberLong = jsonMemberLong;
	}

	public float getJsonMemberLong(){
		return jsonMemberLong;
	}

	public void setIso3(String iso3){
		this.iso3 = iso3;
	}

	public String getIso3(){
		return iso3;
	}
}
