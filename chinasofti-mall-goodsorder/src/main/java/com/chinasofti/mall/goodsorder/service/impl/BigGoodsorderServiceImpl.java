package com.chinasofti.mall.goodsorder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasofti.mall.common.entity.order.PyBigGoodsorder;
import com.chinasofti.mall.common.entity.order.PyBigGoodsorderExample;
import com.chinasofti.mall.goodsorder.mapper.PyBigGoodsorderMapper;
import com.chinasofti.mall.goodsorder.service.BigGoodsorderService;
import com.github.pagehelper.PageHelper;


/**
* @ClassName: 	BigGoodsorderServiceImpl
* @Description: 大订单service实现类
* @author 		tanjl
* @Version 		V1.0
* @date 		2017年11月3日 上午11:45:01 
*/
@Service
public class BigGoodsorderServiceImpl implements BigGoodsorderService {
	
	@Autowired
	private PyBigGoodsorderMapper bigGoodsorderMapper;

	@Override
	public int save(PyBigGoodsorder bigGoodsorder) {
		
		return bigGoodsorderMapper.insertSelective(bigGoodsorder);
	}

	@Override
	public List<PyBigGoodsorder> findAll() {
		PageHelper.startPage(1, 20);
		return bigGoodsorderMapper.selectByExample(null);
	}

	@Override
	public PyBigGoodsorder findById(String id) {
		
		return bigGoodsorderMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteById(String id) {
		
		return bigGoodsorderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int update(PyBigGoodsorder bigGoodsorder) {
		
		return bigGoodsorderMapper.updateByPrimaryKeySelective(bigGoodsorder);
	}
	
	@Override
	public List<PyBigGoodsorder> selectByExample(PyBigGoodsorderExample example){
		
		return bigGoodsorderMapper.selectByExample(example);
	}

	@Override
	public PyBigGoodsorder selectByIds(String ids) {
		return bigGoodsorderMapper.selectByPrimaryKey(ids);
	}
	
	@Override
	public List<PyBigGoodsorder> selectByUserIds(String userId) {
		return bigGoodsorderMapper.selectByUserIds(userId);
	}

	@Override
	public int updateByMainOrder(String bigorderId) {
		return bigGoodsorderMapper.updateByMainOrder( bigorderId);
	}
	public int countOrderNO(String orderNo){
		return bigGoodsorderMapper.countOrderNo(orderNo);
	}
}
