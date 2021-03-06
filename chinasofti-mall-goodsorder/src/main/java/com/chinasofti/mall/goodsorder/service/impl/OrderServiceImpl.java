package com.chinasofti.mall.goodsorder.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinasofti.mall.common.entity.goods.ChnGoodsinfo;
import com.chinasofti.mall.common.entity.order.PyBigGoodsorder;
import com.chinasofti.mall.common.entity.order.PyChildGoodsorder;
import com.chinasofti.mall.common.entity.order.PyMainGoodsorder;
import com.chinasofti.mall.common.entity.order.PyOrderInfo;
import com.chinasofti.mall.common.entity.order.PyShoppingCartInfo;
import com.chinasofti.mall.common.entity.spuser.SpSendAddress;
import com.chinasofti.mall.common.utils.Constant;
import com.chinasofti.mall.common.utils.MsgEnum;
import com.chinasofti.mall.common.utils.ResponseInfo;
import com.chinasofti.mall.common.utils.StringDateUtil;
import com.chinasofti.mall.common.utils.UUIDUtils;
import com.chinasofti.mall.goodsorder.handler.GoodsinfoException;
import com.chinasofti.mall.goodsorder.handler.MyException;
import com.chinasofti.mall.goodsorder.service.BigGoodsorderService;
import com.chinasofti.mall.goodsorder.service.ChildGoodsorderService;
import com.chinasofti.mall.goodsorder.service.MainGoodsorderService;
import com.chinasofti.mall.goodsorder.service.OrderService;
import com.github.pagehelper.util.StringUtil;
/**
 * 调用多个订单服务处理用户订单信息
 * @ClassName: OrderServiceImpl.java
 * @Description: TODO
 * @author 黄佳喜
 * @Date: 2017年11月24日 下午2:30:27
 * @parma <T>
 */
@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private MainGoodsorderService mainGoodsorderService;

	@Autowired
	private ChildGoodsorderService childGoodsorderService;

	@Autowired
	private BigGoodsorderService bigGoodsorderService;

	@Override
	@Transactional(readOnly=true)
	public ResponseInfo queryOrderListByUserId(String userId) {
		ResponseInfo info = new ResponseInfo();
		Map<String, Object> data = new HashMap<String, Object>();
		//先查出用户的未付款订单（大订单展示）
		List<PyBigGoodsorder> pyBigGoodsorders = bigGoodsorderService.selectByUserIds(userId);
		//用户已付款订单（主订单展示）
		List<PyMainGoodsorder> pyMainGoodsorders = mainGoodsorderService.selectByUserIds(userId, 1, 3);
		if (pyBigGoodsorders.size() < 1 && pyMainGoodsorders.size() < 1) {
			info.setRetCode("暂无您的订单信息");
			return info;
		}
		data.put("unpaidOrder", pyBigGoodsorders);
		data.put("paymentOrder", pyMainGoodsorders);
		info.setData(data);
		info.setRetMsg(MsgEnum.SUCCESS.getMsg());
		info.setRetCode(MsgEnum.SUCCESS.getCode());
		return info;
	}

	
	@SuppressWarnings("all")
	@Override
	@Transactional(readOnly=false,rollbackFor={RuntimeException.class, Exception.class})//启动事务
	public ResponseInfo saveOrder(PyOrderInfo orderInfo) throws Exception{
		ResponseInfo res = new ResponseInfo();
		Map<String, Object> data = new HashMap<String, Object>();
		//验证输入参数
		res = checkBaseInfo(orderInfo);
		if(!"true".equals(res.getRetMsg())){
			return res;
		}
		String orderCreateTime = mathTime();//订单生成时间 yyyyMMddhhmmss
		PyBigGoodsorder pyBigGoodsorder = setBigOrder(orderInfo,orderCreateTime);//大订单对象
		
		
		List<PyChildGoodsorder> childList = new LinkedList<PyChildGoodsorder>();//子订单集合
		List<PyMainGoodsorder> mainList = new LinkedList<PyMainGoodsorder>() ;//主订单集合
		Map<String,Object> orderResult = installOrdergetOrderInfo(orderCreateTime,orderInfo);//主订单、子订单集合
		//添加订单的商品总数（黄佳喜添加的） 
		pyBigGoodsorder.setOrderTotalNum((Integer) orderResult.get("orderNum"));
		childList = (List<PyChildGoodsorder>) orderResult.get("childList");
		mainList = (List<PyMainGoodsorder>) orderResult.get("mainList");
		logger.info("*****childList="+childList);
		logger.info("*****mainList="+mainList);
		if(childList.size() !=0&&mainList.size()!=0){
			int child=childGoodsorderService.insertChildGoodsorderList(childList);//保存子订单
			logger.info("***子订单插入成功数***="+child);
			int main=mainGoodsorderService.insertMainGoodsorderList(mainList);//保存主订单
			logger.info("***主订单插入成功数***="+main);
			int big=bigGoodsorderService.save(pyBigGoodsorder);//保存大订单
			logger.info("***大订单插入成功数***="+big);
			
		}
		res.setRetCode(MsgEnum.SUCCESS.getCode());
		res.setRetMsg("订单提交成功");
		data.put("pyMainGoodsorder", mainList);
		data.put("pyChildGoodsorder", childList);
		data.put("pyBigGoodsorder", pyBigGoodsorder);
		res.setData(data);
		logger.info("*****订单提交成功*****");
		return res;
	}
	@Override
	@Transactional(readOnly=false,rollbackFor={RuntimeException.class, Exception.class})//启动事务
	public ResponseInfo cancelOrder(PyBigGoodsorder pyBigGoodsorder) {
		//减少库存
		ResponseInfo responseInfo = new ResponseInfo();
		List<PyChildGoodsorder> list = childGoodsorderService.selectByBigOrderIds(pyBigGoodsorder.getIds());
		int count = 0;
		if (list.size() > 0) {
			for (PyChildGoodsorder pyChildGoodsorder : list) {
				count += childGoodsorderService.updateCancelGoodsNum(pyChildGoodsorder);
			}
		}
		logger.info("取消的商品数为：" + count);
		// 0 未支付 1 已支付  2 已取消  3已删除
		pyBigGoodsorder.setPayStatus(Constant.PAY_STATUS_CANCLE);
		bigGoodsorderService.update(pyBigGoodsorder);
		//status : 订单状态: 0 待付款  1 待发货 2 待收货 3 交易成功  4 交易关闭（已删除） 5 交易关闭（已取消） 6 交易关闭（退款成功）
		mainGoodsorderService.updateByBigGoodsorder(pyBigGoodsorder.getTransactionid());
		if (count > 0) {
			responseInfo.setRetCode(MsgEnum.SUCCESS.getCode());
			responseInfo.setRetMsg(MsgEnum.SUCCESS.getMsg());
		} else {
			responseInfo.setRetCode(MsgEnum.ERROR.getCode());
			responseInfo.setRetMsg(MsgEnum.ERROR.getMsg());
			logger.error("提交取消订单失败");
		}
		return responseInfo;
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={RuntimeException.class, Exception.class})//启动事务
	public ResponseInfo deleteByBigOrderId(PyBigGoodsorder pyBigGoodsorder) {
		ResponseInfo responseInfo = new ResponseInfo();
		if (null == pyBigGoodsorder.getUserIds()) {
			responseInfo.setRetMsg("用户信息为空");
			return responseInfo;
		}
		//设置订单状态 无效状态
		pyBigGoodsorder.setStatus(Constant.STATUS_UNABLE);
		// 0 未支付 1 已支付  2 已取消 3已删除
		pyBigGoodsorder.setPayStatus(Constant.PAY_STATUS_DELETE);
		int count = bigGoodsorderService.update(pyBigGoodsorder);
		//更新订单状态
		mainGoodsorderService.updateByBigGoodsorder(pyBigGoodsorder.getTransactionid());
		if (count > 0) {
			responseInfo.setRetCode(MsgEnum.SUCCESS.getCode());
			responseInfo.setRetMsg(MsgEnum.SUCCESS.getMsg());
		} else {
			responseInfo.setRetCode(MsgEnum.ERROR.getCode());
			responseInfo.setRetMsg(MsgEnum.ERROR.getMsg());
			logger.error("提交删除失败");
		}
		return responseInfo;
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={RuntimeException.class, Exception.class})//启动事务
	public ResponseInfo payOrder(PyBigGoodsorder pyBigGoodsorder) {
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			//设置大订单支付信息和状态
			pyBigGoodsorder.setPayStatus(Constant.PAY_STATUS_OK);
			pyBigGoodsorder.setPayTime(StringDateUtil.convertDateToLongString(new Date()));
			pyBigGoodsorder.setPayway("信用卡支付");
			bigGoodsorderService.update(pyBigGoodsorder);
			//根据大订单的流水号更新主订单
			mainGoodsorderService.updateOrderByPay(pyBigGoodsorder.getTransactionid());
			responseInfo.setRetCode(MsgEnum.SUCCESS.getCode());
			responseInfo.setRetMsg(MsgEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			responseInfo.setRetCode(MsgEnum.ERROR.getCode());
			responseInfo.setRetMsg("订单支付失败");
			logger.error("支付订单失败");
		}

		return responseInfo;
	}

 	
	private Map<String,Object> installOrdergetOrderInfo(String orderCreateTime,PyOrderInfo orderInfo) throws GoodsinfoException, MyException{
		Map<String,Object> orderResult = new HashMap<String,Object>();//返回的对象
		List<PyChildGoodsorder> goodsInfoList = new LinkedList<PyChildGoodsorder>();//单个商户商品信息集合
		List<PyChildGoodsorder> childList = new LinkedList<PyChildGoodsorder>();//子订单集合
		List<PyMainGoodsorder> mainList = new LinkedList<PyMainGoodsorder>();//主订单集合
		List<PyShoppingCartInfo> shopCart = orderInfo.getShopCart();//购物车信息
		int orderNum = 0;//订单购买总数（黄佳喜添加的）
		for(int i=0;i<shopCart.size();i++){
			PyMainGoodsorder mainGoodsorder = setMainOrder(orderCreateTime,orderInfo);//主订单
			mainGoodsorder.setBigorderId(orderInfo.getOrderNo());//所属大订单
			mainGoodsorder.setVendorIds(shopCart.get(i).getVendorIds());//商户ID
			mainGoodsorder.setVendorSnm(shopCart.get(i).getVendorSnm());//商户名称
			mainGoodsorder.setUserIds(orderInfo.getUserId());//用户ID
			BigDecimal shoporderAmt = null;//商户订单总金额;
			int goodsNum = 0;//购买数量
			goodsInfoList = shopCart.get(i).getGoodsInfoList();//获取当前购买的商品信息
			for(int j=0;j<goodsInfoList.size();j++){
				PyChildGoodsorder childorder = setChildOrder(goodsInfoList,j,orderCreateTime);//子订单		
				goodsNum = goodsInfoList.get(j).getGoodsNum();//购买数量
				orderNum = goodsNum+orderNum;//（黄佳喜添加的）
				if(j==0){
					shoporderAmt = childorder.getOrderAmt(); 
				}else{
					shoporderAmt = shoporderAmt.add(childorder.getOrderRealAmt());
				}
				childorder.setMainorderIds(mainGoodsorder.getTransactionid());//所属主订单编号
				childorder.setCustIds(orderInfo.getUserId());//用户ID
				
				logger.info("************子订单"+j+"**************="+childorder.toString());
				childList.add(childorder);
				
			}    
			mainGoodsorder.setOrderTotalAmt(shoporderAmt);
			mainList.add(mainGoodsorder);
			logger.info("************主订单"+i+"*************="+mainGoodsorder.toString());
		}
		orderResult.put("childList", childList);
		orderResult.put("mainList", mainList);
		orderResult.put("orderNum", orderNum);//（黄佳喜添加的）
		
		return orderResult;
	}	
	//组装大订单
	private PyBigGoodsorder setBigOrder(PyOrderInfo orderInfo,String orderCreateTime) {
		PyBigGoodsorder pyBigGoodsorder = new PyBigGoodsorder();
		pyBigGoodsorder.setIds(UUIDUtils.getUuid());
		pyBigGoodsorder.setOrderDate(orderCreateTime);//订单生成时间
		pyBigGoodsorder.setTransactionid(orderInfo.getOrderNo());//大订单编号，流水号
		pyBigGoodsorder.setOrderTotalAmt(orderInfo.getOrderRealAmt());//商品总金额
		pyBigGoodsorder.setUserIds(orderInfo.getUserId());//用户ID
		//黄佳喜添加：大订单的状态
		pyBigGoodsorder.setStatus(Constant.STATUS_ABLE);
		pyBigGoodsorder.setPayStatus(Constant.PAY_STATUS_NOT);
		return pyBigGoodsorder;
	}
	//组装子订单
	private PyChildGoodsorder setChildOrder(List<PyChildGoodsorder> goodsInfoList, int j, String orderCreateTime) throws MyException, GoodsinfoException {
		PyChildGoodsorder childorder = new PyChildGoodsorder();
		String goodsId = goodsInfoList.get(j).getGoodsids();//商品ID
		String goodsName = goodsInfoList.get(j).getGoodsName();//商品名称
		int goodsNum = goodsInfoList.get(j).getGoodsNum();//购买数量
		BigDecimal goodsPrice = goodsInfoList.get(j).getGoodsPrice();//商品单价
		childorder.setGoodsids(goodsId);//商品ID
		childorder.setGoodsName(goodsName);
		childorder.setGoodsNum(goodsNum);//购买数量
		//验证商品并修改库存
		checkGoods(goodsId,new BigDecimal(goodsNum),goodsPrice,goodsName);
		childorder.setGoodsPrice(goodsPrice);
		BigDecimal goodsorderAmt = new BigDecimal(goodsNum).multiply(goodsPrice);//商品金额
		System.out.println(">>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<goodsorderAmt="+goodsorderAmt);
		childorder.setOrderAmt(goodsorderAmt);
		String childTransactionId = "G".concat(orderCreateTime.concat(getFixLenthString(4)));//子订单流水号
		childorder.setTransactionid(childTransactionId);
		childorder.setIds(UUIDUtils.getUuid());//IDS
		childorder.setIsevaluate(Constant.ISEAVLUATE);//是否已评价
		childorder.setOrderDate(orderCreateTime);
		return childorder;
	}
	//组装主订单
	private PyMainGoodsorder setMainOrder(String orderCreateTime,PyOrderInfo orderInfo) {
		//获取收件地址信息
		SpSendAddress address = childGoodsorderService.queryAddress(orderInfo.getAddressId());
		PyMainGoodsorder mainGoodsorder = new PyMainGoodsorder();
		mainGoodsorder.setIds(UUIDUtils.getUuid());//IDS
		String mainTransactionId = "M".concat(orderCreateTime.concat(getFixLenthString(4)));//主订单流水号
		mainGoodsorder.setTransactionid(mainTransactionId);
		mainGoodsorder.setOrderTime(orderCreateTime);//订单时间
		mainGoodsorder.setPayStatus(Constant.PAY_STATUS);//支付状态  0 未支付 1 已支付  2 取消
		mainGoodsorder.setStatus(Constant.STATUS_UNABLE);//订单状态   0 待付款  1 待发货 2 待收货 3 交易成功  4 交易关闭（已删除） 5 交易关闭（已取消） 6 交易关闭（退款成功）；
		mainGoodsorder.setContPostcode(address.getZipCode());//邮编
		mainGoodsorder.setContPhone(address.getMobile());//联系电话
		mainGoodsorder.setContProvince(address.getProvince());//省
		mainGoodsorder.setContCity(address.getCity());//市
		mainGoodsorder.setContDistrict(address.getDistrict());//区
		mainGoodsorder.setContStreet(address.getStreet());//街道
		mainGoodsorder.setContAddress(address.getAddress());//详细地址
		mainGoodsorder.setContName(address.getName());//收件人姓名	
		return mainGoodsorder;
	}
	//验证商品库存数量
	private boolean checkGoods(String goodsId,BigDecimal num,BigDecimal goodsPrice,String goodsName) throws GoodsinfoException {
		if(StringUtil.isEmpty(goodsId)){
			logger.info("商品ID不能为空");
			throw new GoodsinfoException("商品ID为空",goodsName);
		}
		ChnGoodsinfo goodsinfo = childGoodsorderService.selectGoodsInfo(goodsId);//商品详情
		if(goodsinfo == null){
			logger.info("商品异常或已下架");
			throw new GoodsinfoException("商品异常或已下架",goodsName);
		}
		BigDecimal price = goodsinfo.getPrice();
		logger.info("-----------price="+price);
		if(price.compareTo(goodsPrice)!=0){
			logger.info("商品价格发生变动");
			throw new GoodsinfoException("价格发生变动",goodsName);
		}
		BigDecimal storeNum = goodsinfo.getStoreNum();
		logger.info("-----------storeNum="+storeNum);
		if(storeNum == null){
			logger.info("获取商品数量为空或者发生异常");
			throw new GoodsinfoException("数量为空或者发生异常",goodsName);
		}
		int flag = num.compareTo(storeNum);//购买数量是否小于等于库存
		if(flag == 1){
			logger.info("购买数量大于库存，无法购买");
			throw new GoodsinfoException("数量超过库存",goodsName);
		}	
		//更新库存
		BigDecimal updateStoreNum = storeNum.subtract(num);
		
		int updateStore = updateStore(updateStoreNum,goodsId);
		logger.info("updateStore="+updateStore);
		if(updateStore !=1){
			logger.info("更新库存发生异常");
			throw new GoodsinfoException("更新库存发生异常",goodsName);
		}
		return true;
	}
	//更新库存
		private int updateStore(BigDecimal storeNum, String goodsId){
			//增加验证库存值
			
			ChnGoodsinfo chnGoodsinfo = new ChnGoodsinfo();
			chnGoodsinfo.setStoreNum(storeNum);
			chnGoodsinfo.setGoodsId(goodsId);
			chnGoodsinfo.setUpdateTime(mathTime());
			int updateStore=childGoodsorderService.updateStroe(chnGoodsinfo);
			return updateStore;
		}

	//生成时间戳
	private String mathTime(){
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowTime = sd.format(date);
		return nowTime;
	}
	//生成随机数
	private static String getFixLenthString(int strLength) {
        Random rm = new Random(); 
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return "0000".concat(fixLenthString.substring(1, strLength + 1));
    }
	
	private ResponseInfo checkBaseInfo(PyOrderInfo orderInfo) {
		ResponseInfo res = new ResponseInfo();
		String transactionid = orderInfo.getOrderNo();//为防止重复提交，前端传有一个流水号进来
		int count = bigGoodsorderService.countOrderNO(transactionid);
		if(count !=0){
			res.setRetCode(Constant.ORDER_EXCITE_CODE);
			res.setRetMsg(Constant.ORDER_EXCITE_MSG);
			return res;
		}
		//获取地址ID
		String addressId = orderInfo.getAddressId();
		if(addressId ==null){
			res.setRetCode(Constant.ORDER_ADDRESS_NULL);
			res.setRetMsg(Constant.ORDER_ADDRESS_MSG);
			return res;
		}
		//获取收件地址信息
		SpSendAddress address = childGoodsorderService.queryAddress(addressId);
		if(address ==null){
			res.setRetCode(Constant.ORDER_ADDRESS_ERROR);
			res.setRetMsg(Constant.ORDER_ADDRESS_ERROR_MSG);
			return res;
		}
		res.setRetMsg("true");//该校验通过
		return res;
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={RuntimeException.class, Exception.class})//启动事务
	public ResponseInfo deleteByMainOrderId(PyMainGoodsorder pyMainGoodsorder) {
		ResponseInfo responseInfo = new ResponseInfo();
		if (null == pyMainGoodsorder.getUserIds()) {
			responseInfo.setRetMsg("用户信息为空");
			return responseInfo;
		}
		//status : 订单状态: 0 待付款  1 待发货 2 待收货 3 交易成功  4 交易关闭（已删除） 5 交易关闭（已取消） 6 交易关闭（退款成功）
		pyMainGoodsorder.setStatus("4");
		int count = mainGoodsorderService.update(pyMainGoodsorder);
		bigGoodsorderService.updateByMainOrder(pyMainGoodsorder.getBigorderId());
		if (count > 0) {
			responseInfo.setRetCode(MsgEnum.SUCCESS.getCode());
			responseInfo.setRetMsg(MsgEnum.SUCCESS.getMsg());
		} else {
			responseInfo.setRetCode(MsgEnum.ERROR.getCode());
			responseInfo.setRetMsg(MsgEnum.ERROR.getMsg());
			logger.error("提交删除失败");
		}
		return responseInfo;
	}


	@Override
	@Transactional(readOnly=true)
	public ResponseInfo queryMainOrderList(PyMainGoodsorder pyMainGoodsorder) {
		ResponseInfo responseInfo = new ResponseInfo();
		//分页查询用户已完成交易的订单
		List<PyMainGoodsorder> list = mainGoodsorderService.selectByUserIds(pyMainGoodsorder.getUserIds(), pyMainGoodsorder.getPageNumber(), pyMainGoodsorder.getPageSize());
		System.out.println(list);
		if (list.size() < 1) {
			responseInfo.setRetMsg("没有更多的订单信息");
			return responseInfo;
		}
		responseInfo.setRetMsg("success");
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("mainList", list);
		responseInfo.setData(map);
		return responseInfo;
	}
}
