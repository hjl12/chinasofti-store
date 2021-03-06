package com.chinasofti.mall.common.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinasofti.mall.common.entity.goods.ChnGoodsinfo;
import com.chinasofti.mall.common.entity.order.PyShoppingCart;
import com.chinasofti.mall.common.utils.Constant;
import com.chinasofti.mall.common.utils.MsgEnum;
import com.chinasofti.mall.common.utils.ResponseInfo;

public class RequestParamService {

	private static final Logger logger = LoggerFactory.getLogger(RequestParamService.class);
    //购物车删除的空参验证
	public static  ResponseInfo packageWithShoppingCartRequestParam(String userId,String id){
		ResponseInfo response = new ResponseInfo();
		logger.info("验证参数1====="+userId);
		if (StringUtils.isEmpty(userId)) {
			response.setRetCode(MsgEnum.ERROR.getCode());
			response.setRetMsg("userId不能为空！");
			return response;
		}
		logger.info("验证参数2====="+id);
		if (StringUtils.isEmpty(id)) {
			response.setRetCode(MsgEnum.ERROR.getCode());
			response.setRetMsg("Id不能为空！");
			return response;
		}
		logger.info("验证参数2====="+response);
		return response;
		
	}
	public static ResponseInfo checkRequestParam(PyShoppingCart goodsInfo,ResponseInfo response){
		
		logger.info("验证参数1====="+goodsInfo.getUserId());
		if (StringUtils.isEmpty(goodsInfo.getUserId())) {
			response.setRetCode(MsgEnum.ERROR.getCode());
			response.setRetMsg("userId不能为空！");
			return response;
		}
		logger.info("验证参数2====="+goodsInfo.getGoodsId());
		if (StringUtils.isEmpty(goodsInfo.getGoodsId())) {
			response.setRetCode(MsgEnum.ERROR.getCode());
			response.setRetMsg("goodsId不能为空！");
			return response;
		}
		logger.info("验证参数3====="+goodsInfo.getGoodsNum());
		if (StringUtils.isEmpty(String.valueOf(goodsInfo.getGoodsNum()))) {
			response.setRetCode(MsgEnum.ERROR.getCode());
			response.setRetMsg("goodsNum不能为空！");
			return response;
		}
		return response;
	}
	public static  ResponseInfo packageWithShoppingCartRequestParam(PyShoppingCart goodsInfo){
		ResponseInfo response = new ResponseInfo();
		 response= checkRequestParam(goodsInfo,response);
		logger.info("验证参数4====="+goodsInfo.getId());
		if (StringUtils.isEmpty(goodsInfo.getId())) {
			response.setRetCode(MsgEnum.ERROR.getCode());
			response.setRetMsg("Id不能为空！");
			return response;
		}
		logger.info("验证response====="+response);
		return response;
	}
	
	public static ResponseInfo packageWithAddShoppingCartParam(PyShoppingCart goodsInfo){
		ResponseInfo response = new ResponseInfo();
		response= checkRequestParam(goodsInfo,response);	
		logger.info("验证参数4====="+goodsInfo.getVendorId());
		if (StringUtils.isEmpty(goodsInfo.getVendorId())) {
			response.setRetCode(MsgEnum.ERROR.getCode());
			response.setRetMsg("vendorId不能为空！");
			return response;
		}
		
		return response;
	}

	/**
	 * 商品信息校验
	 * 
	 * @param shopCar
	 * @param storegoodsInfo
	 * @return
	 */
	public static ResponseInfo packageWithGoodsInfoRequest(PyShoppingCart shoppingCart, ChnGoodsinfo storegoodsInfo) {
		ResponseInfo response = new ResponseInfo();
		String goodsStatus = storegoodsInfo.getStatus();
		Map<String, Object> data = new HashMap<String, Object>();
		if (!goodsStatus.equals(Constant.GOODS_STATUS)) {
			response.setRetCode("500001");
			response.setRetMsg("该商品已下架或已删除");
			data.put("responseInfo", shoppingCart);
			response.setData(data);
			return response;
		}
		BigDecimal userBuyNum = shoppingCart.getGoodsNum();
		BigDecimal storeNum = storegoodsInfo.getStoreNum();
		if (userBuyNum.compareTo(storeNum) == 1) {
			response.setRetCode("600001");
			response.setRetMsg("库存不足");
			data.put("responseInfo", shoppingCart);
			response.setData(data);
			return response;
		}
		BigDecimal limitOrderNum = storegoodsInfo.getLimitOrderNum();
		//空值不限购
		if (limitOrderNum != null && userBuyNum.compareTo(limitOrderNum) == 1) {
			response.setRetCode("700001");
			response.setRetMsg("数量已超出单笔限制！");
			data.put("responseInfo", shoppingCart);
			response.setData(data);
			return response;
		}
		data.put("responseInfo", shoppingCart);
		response.setData(data);
		response.setRetCode(MsgEnum.SUCCESS.getCode());
	    response.setRetMsg(MsgEnum.SUCCESS.getMsg());
		return response;
	}

	
}
