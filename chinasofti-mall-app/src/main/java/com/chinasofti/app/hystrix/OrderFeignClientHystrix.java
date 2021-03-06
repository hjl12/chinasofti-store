package com.chinasofti.app.hystrix;

import org.springframework.stereotype.Component;

import com.chinasofti.app.feign.OrderFeignClient;
import com.chinasofti.mall.common.entity.order.PyBigGoodsorder;
import com.chinasofti.mall.common.entity.order.PyMainGoodsorder;
import com.chinasofti.mall.common.entity.order.PyOrderInfo;
import com.chinasofti.mall.common.utils.ResponseInfo;

/**
 * 
* @ClassName: OrderrFeignClientHystrix
* @Description: 熔断类
* @author xiaozhiliang
* @date 2017年11月9日 
* @version
 */
@Component
public class OrderFeignClientHystrix implements OrderFeignClient{
	

	@Override
	public ResponseInfo queryOrderListByUserId(String userId) {
		return new ResponseInfo();
	}
	
 
	@Override
	public ResponseInfo saveOrder(PyOrderInfo orderInfo){
		return new ResponseInfo();
	}
	
	public ResponseInfo deleteOrderById(String orderId){
		return new ResponseInfo();
	}


	@Override
	public ResponseInfo deleteByBigOrderId(PyBigGoodsorder pyBigGoodsorder) {
		return new ResponseInfo();
	}


	@Override
	public ResponseInfo deleteByMainOrderId(PyMainGoodsorder pyMainGoodsorder) {
		return new ResponseInfo();
	}


	@Override
	public ResponseInfo payOrder(PyBigGoodsorder pyBigGoodsorder) {
		return new ResponseInfo();
	}


	@Override
	public ResponseInfo cancelOrder(PyBigGoodsorder pyBigGoodsorder) {
		return new ResponseInfo();
	}


	@Override
	public ResponseInfo queryMainOrderList(PyMainGoodsorder pyMainGoodsorder) {
		return new ResponseInfo();
	}


	

	
}
