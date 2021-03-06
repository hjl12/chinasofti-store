package com.chinasofti.mall.goodsorder.service;

import com.chinasofti.mall.common.entity.order.PyBigGoodsorder;
import com.chinasofti.mall.common.entity.order.PyMainGoodsorder;
import com.chinasofti.mall.common.entity.order.PyOrderInfo;
import com.chinasofti.mall.common.utils.ResponseInfo;
import com.chinasofti.mall.goodsorder.handler.GoodsinfoException;

public interface OrderService {

	public ResponseInfo queryOrderListByUserId(String userId);
	
	public ResponseInfo saveOrder( PyOrderInfo orderInfo )throws Exception;

	public ResponseInfo cancelOrder(PyBigGoodsorder pyBigGoodsorder);
	
	public ResponseInfo deleteByBigOrderId(PyBigGoodsorder pyBigGoodsorder);

	public ResponseInfo payOrder(PyBigGoodsorder pyBigGoodsorder);

	public ResponseInfo deleteByMainOrderId(PyMainGoodsorder pyMainGoodsorder);

	public ResponseInfo queryMainOrderList(PyMainGoodsorder pyMainGoodsorder);
}
 