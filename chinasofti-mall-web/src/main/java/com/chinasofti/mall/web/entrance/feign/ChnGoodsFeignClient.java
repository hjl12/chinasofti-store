package com.chinasofti.mall.web.entrance.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinasofti.mall.common.entity.goods.ChnGoodsClass;
import com.chinasofti.mall.common.entity.goods.ChnGoodsOnline;
import com.chinasofti.mall.common.entity.goods.ChnGoodsinfoCheck;
import com.chinasofti.mall.common.entity.goods.SpEvaluate;
import com.chinasofti.mall.web.entrance.hystrix.SpGoodsClassFeignClientHystrix;

import net.sf.json.JSONObject;

/**
 * Feign服务调用+负载均衡
 * @author Administrator
 *
 */
@FeignClient(name = "Goods-Service" , fallback = SpGoodsClassFeignClientHystrix.class)
public interface ChnGoodsFeignClient {
	
	/**
	 * 商品列表及条件查询
	 * @param goodscategory
	 * @return
	 */
	@RequestMapping(value = "/goodsClass/select" , method = RequestMethod.POST)
	public JSONObject selectByGoodsClass(@RequestBody(required = false) ChnGoodsClass chnGoodsClass);
	
	@RequestMapping(value = "/goodsClass/findGoodsClass" ,method = RequestMethod.POST)
	public List<ChnGoodsClass> findGoodsClass(@RequestBody(required = false) String ids);
	
	/**
	 * 商品分类根据ID删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/goodsClass/delete/{ids}" , method =RequestMethod.POST)
	public int deleteGoodsClassById(@PathVariable("ids") String ids);
	
	/**
	 * 商品分类根据ID查找
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/goodsClass/select/{ids}" , method = RequestMethod.POST)
	public ChnGoodsClass selectGoodsClassById(@PathVariable("ids") String ids);
	
	/**
	 * 商品分类根据ID修改
	 * @param goodscategory
	 * @return
	 */
	@RequestMapping(value = "/goodsClass/update" , method = RequestMethod.POST)
	public int updateGoodsClass(@RequestBody(required = false) ChnGoodsClass chnGoodsClass);
	
	/**
	 * 商品分类增加
	 * @param goodscategory
	 * @return
	 */
	@RequestMapping(value = "/goodsClass/save" , method = RequestMethod.POST)
	public int saveGoodsClass(@RequestBody(required = false) ChnGoodsClass chnGoodsClass);
	
	/**
	 * 商品审核列表及条件查询
	 * @param goodscategory
	 * @return
	 */
	@RequestMapping(value = "/goodsCheck/select" , method = RequestMethod.POST)
	public JSONObject selectByGoodsCheck(@RequestBody(required = false) ChnGoodsinfoCheck chnGoodsinfoCheck);
	
	/**
	 * 商品审核根据ID删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/goodsCheck/delete/{ids}" , method =RequestMethod.POST)
	public int deleteGoodsCheckById(@PathVariable("ids") String ids);
	
	/**
	 * 商品审核根据ID查找
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/goodsCheck/select/{ids}" , method = RequestMethod.POST)
	public ChnGoodsinfoCheck selectGoodsCheckById(@PathVariable("ids") String ids);
	
	/**
	 * 商品审核根据ID修改
	 * @param chnGoodsinfoCheck
	 * @return
	 */
	@RequestMapping(value = "/goodsCheck/update" , method = RequestMethod.POST)
	public int updateGoodsCheck(@RequestBody(required = false) ChnGoodsinfoCheck chnGoodsinfoCheck);
	 
	/**
	 * 商品审核增加
	 * @param chnGoodsinfoCheck
	 * @return
	 */
	@RequestMapping(value = "/goodsCheck/save" , method = RequestMethod.POST)
	public int saveGoodsCheck(@RequestBody(required = false) ChnGoodsinfoCheck chnGoodsinfoCheck);

	/**
	 * 商品在线管理查询
	 * @param chnGoodsOnline
	 * @return
	 */
	@RequestMapping(value = "/goodsOnline/list" , method = RequestMethod.POST)
	public JSONObject selectByGoodsOnline(ChnGoodsOnline chnGoodsOnline);
	  
	/**
	 * 更改审核状态
	 * @param chnGoodsinfoCheck
	 * @return
	 */
	@RequestMapping(value = "/goodsCheck/updateReviewStatus" ,method = RequestMethod.POST)
	public int updateGoodsCheckReviewStatus(@RequestBody(required = false) ChnGoodsinfoCheck chnGoodsinfoCheck);

	/**
	 * 商品上架
	 * @param chnGoodsOnline
	 * @return
	 */
	@RequestMapping(value = "/goodsOnline/updateGoodsStatus" ,method = RequestMethod.POST)
	public int updateGoodsStatus(@RequestBody(required = false) ChnGoodsOnline chnGoodsOnline);

	/**
	 * 修改商品库存
	 * @param chnGoodsOnline
	 * @return
	 */
	@RequestMapping(value = "/goodsOnline/update" ,method = RequestMethod.POST)
	public int updateStore(@RequestBody(required = false) ChnGoodsOnline chnGoodsOnline);

	/**
	* @Title: findByPage
	* @Description: 商品在线分页查询
	* @param paramMap
	* @return: String
	* @throws:
	 */
	@RequestMapping(value = "/goodsOnline/findByPage")
	public String findByPage(@RequestParam Map<String,Object> paramMap);

	/**
	 * 修改审核状态和在线状态(下架)
	 * @param chnGoodsOnline
	 * @return
	 */
	@RequestMapping(value = "/goodsOnline/updateGoodsOnlineReviewStatusAndStatus" ,method = RequestMethod.POST)
	public int updateGoodsOnlineReviewStatusAndStatus(@RequestBody(required = false) ChnGoodsOnline chnGoodsOnline);

	/**
	* @Title: findByPage
	* @Description: 商品评论分页查询
	* @param map
	* @return: String
	* @throws:
	 */
	@RequestMapping(value = "/comments/findByPage")
	public String findByCommentPage(@RequestParam Map<String, Object> map);

	/**
	 * @Title: deleteById
	 *  @Description: 通过主键删除商品评论
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/comments/delete/{ids}" , method =RequestMethod.POST)
	public int deleteById(@PathVariable("ids") String ids);

	/**
	 * @Title: selectByCommentsIds
	 * @Description: 通过主键查询商品评论
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/comments/reqCommentsImgPath/{ids}" , method = RequestMethod.POST)
	public SpEvaluate selectByCommentsIds(@PathVariable("ids") String ids);

	/**
	 * @Title: batchDeletes
	 * @Description: 批量删除商品评论
	 * @param delList
	 * @return
	 */
	@RequestMapping(value = "/comments/batchDeletes")
	public int batchDeletes(@RequestBody(required = false) List<String> delList);

	
}
