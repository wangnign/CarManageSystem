package edu.hzcc.webdemo.pojo;

import java.util.ArrayList;
import java.util.List;

public class CheXing {
	private int goumainian;
	private List<CheliangShumu> bjs;
	
	public void add(CheliangShumu clsm){
		if(bjs == null)
			bjs = new ArrayList<>();
		bjs.add(clsm);
	}

	public int getGoumainian() {
		return goumainian;
	}

	public void setGoumainian(int goumainian) {
		this.goumainian = goumainian;
	}

	public List<CheliangShumu> getBjs() {
		return bjs;
	}

	public void setBjs(List<CheliangShumu> bjs) {
		this.bjs = bjs;
	}

	@Override
	public String toString() {
		return "CheXing [goumainian=" + goumainian + ", bjs=" + bjs + ", getGoumainian()=" + getGoumainian()
				+ ", getBjs()=" + getBjs() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
}
