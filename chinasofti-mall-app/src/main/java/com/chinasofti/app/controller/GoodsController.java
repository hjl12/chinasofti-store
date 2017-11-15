package com.chinasofti.app.controller;



import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinasofti.app.feign.GoodsFeignClient;
import com.chinasofti.mall.common.utils.ResponseInfo;

@RestController
@RequestMapping("goodsInfo")
public class GoodsController {
	
	@Autowired
	private GoodsFeignClient goodsFeignClient;
	
	/**
	 * 根据Ids查询商品列表
	 * @param classId(ids)
	 * @return
	 */
	@RequestMapping("queryGoodList")
	public ResponseInfo queryGoodList(@RequestParam String id,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		return goodsFeignClient.queryGoodList(id);
	}
	@RequestMapping("queryGoodsInfo")
	public ResponseInfo queryGoodsInfo(@RequestParam String ids,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		return goodsFeignClient.queryGoodInfo(ids);
	}
}
