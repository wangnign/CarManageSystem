package edu.hzcc.webdemo.util;

import java.util.List;

import net.sf.json.JSONObject;

public class JsonResult {
	private boolean succ;
	private String stmt;
	private long total;
	private List<?> rows;
	
	//计算多少页码的
	private int page;
	private int pageSize;
	private int totalPage;

	public JsonResult() {
		super();
	}

	public JsonResult(boolean succ) {
		super();
		this.succ = succ;
	}

	public JsonResult(boolean succ, String stmt) {
		this.succ = succ;
		this.stmt = stmt;
	}

	public JsonResult(long total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public String getStmt() {
		return stmt;
	}

	public void setStmt(String stmt) {
		this.stmt = stmt;
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		//需要计算totalpage了
		if(total>0 && pageSize>0){
			this.totalPage = (int)(this.total / this.pageSize);
			if(this.total % this.pageSize != 0)
				this.totalPage++;
		}
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String toJson(){
		JSONObject jsonObject = JSONObject.fromObject(this);
		return jsonObject.toString();
	}
}

