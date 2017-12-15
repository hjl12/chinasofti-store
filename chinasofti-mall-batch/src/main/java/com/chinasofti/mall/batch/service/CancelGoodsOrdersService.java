package com.chinasofti.mall.batch.service;

import com.chinasofti.mall.common.entity.order.PyBigGoodsorder;

public interface CancelGoodsOrdersService {
	/**
	 * 更新大订单的支付状态
	 * @param pyBigGodsOrder
	 * @throws Exception
	 */
	void updatePaywayStatus(PyBigGoodsorder pyBigGodsOrder) throws Exception ;
	
}
