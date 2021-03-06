package com.chinasofti.mall.common.entity.param;

import com.chinasofti.mall.common.utils.PageBean;

public class ParentParam extends PageBean{
    private Integer id;

    private String text;

    private String description;

    private String cearttime;

    private String status;
    
    private String state;
    
    private Integer _parentId;
    
    private String val;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCearttime() {
        return cearttime;
    }

    public void setCearttime(String cearttime) {
        this.cearttime = cearttime == null ? null : cearttime.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer get_parentId() {
		return _parentId;
	}

	public void set_parentId(Integer _parentId) {
		this._parentId = _parentId;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
	
}
