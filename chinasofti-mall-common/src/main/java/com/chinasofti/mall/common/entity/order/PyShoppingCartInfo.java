package com.chinasofti.mall.common.entity.order;

import java.util.List;
import com.chinasofti.mall.common.utils.PageBean;

public class PyShoppingCartInfo extends PageBean{
	
    /**
    * @Fields vendorIds : 商户号
    */
    private String vendorIds;
    
    /**
     * @Fields vendorIds : 商户名称
     */
     private String vendorSnm;
    
    /**
	* 商品信息
    */
	private List<PyChildGoodsorder> goodsInfoList;

	public String getVendorIds() {
		return vendorIds;
	}

	public void setVendorIds(String vendorIds) {
		this.vendorIds = vendorIds;
	}

	public List<PyChildGoodsorder> getGoodsInfoList() {
		return goodsInfoList;
	}

	public void setGoodsInfoList(List<PyChildGoodsorder> goodsInfoList) {
		this.goodsInfoList = goodsInfoList;
	}

	public String getVendorSnm() {
		return vendorSnm;
	}

	public void setVendorSnm(String vendorSnm) {
		this.vendorSnm = vendorSnm;
	}
	
	

    
}