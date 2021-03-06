package com.chinasofti.mall.goodsorder.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chinasofti.mall.common.entity.order.ChildorderCondition;
import com.chinasofti.mall.common.entity.order.PyChildGoodsorder;
import com.chinasofti.mall.goodsorder.service.ChildGoodsorderService;

import net.sf.json.JSONObject;


/**
* @ClassName: 	ChildGoodsorderController
* @Description: 子订单controller
* @author 		tanjl
* @Version 		V1.0
* @date 		2017年11月3日 下午2:16:51 
*/
@RestController
@RequestMapping("childorder")
public class ChildGoodsorderController {
	
	@Autowired
	private ChildGoodsorderService childGoodsorderService;
	
	

	@RequestMapping("select/{ids}")
	public PyChildGoodsorder findById(@PathVariable("ids") String id) {
		
		return childGoodsorderService.findById(id);
	}


	@RequestMapping("delete/{ids}")
	public String deleteById(@PathVariable("ids") String id) {
		childGoodsorderService.deleteById(id);
		return "delete";
	}


	@RequestMapping(value="update" , method = RequestMethod.POST)
	public String update(@RequestBody(required=false) PyChildGoodsorder childGoodsorder) {
		childGoodsorderService.update(childGoodsorder);
		return "update";
	}


	@RequestMapping(value="add" , method = RequestMethod.POST)
	public String add(@RequestBody(required=false) PyChildGoodsorder childGoodsorder) {
		childGoodsorderService.save(childGoodsorder);
		return "add";
	}
	
	/**
	* @Title: selectByExample
	* @Description: 条件查询
	* @param childGoodsorder
	* @return List<PyChildGoodsorder>
	* @throws
	*/
	@RequestMapping(value="list" , method = RequestMethod.POST)
	public JSONObject selectByExample(@RequestBody(required=false) PyChildGoodsorder childGoodsorder){
		
		return childGoodsorderService.selectByChildorderClass(childGoodsorder);
	}
	
	@RequestMapping(value="selectbycondition" , method = RequestMethod.POST)
	public JSONObject selectByChildorderCondition(@RequestBody(required=false)ChildorderCondition childorderCondition){
		return childGoodsorderService.selectByChildorderCondition(childorderCondition);
	}

}
