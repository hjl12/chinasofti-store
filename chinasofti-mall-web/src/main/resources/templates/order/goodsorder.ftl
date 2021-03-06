<script type="text/javascript" src="js/goodsorder.js"></script>
<div class="easyui-layout" data-options="" >
	<!-- 主订单模块开始 -->
	<!-- 主订单工具栏 --> 
	<div id="mainorderToolbar">
		<!-- 主订单条件搜索 -->
		<form id="mainorderSearchForm" method="post">
			<div align="left">
				<table class="" style="width: 95%">
					<tr>
						<td style="width:10%;padding:0 10px 0 0;" align="right">主订单号</td>
						<td style="width:15%" align="left">
							<input class="easyui-textbox" id="mainorder-transactionid" name="transactionid" style="width:80%" />
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">大订单号</td>
						<td style="width:15%" align="left">
							<input class="easyui-textbox" id="mainorder-bigorderId" name="bigorderId" style="width:80%" />
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">订单开始时间</td>
						<td style="width:15%" align="left"> 
							<input class="easyui-datetimebox" id="mainorder-minPayTime" name="orderTime" data-options="editable:false" />
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">订单结束时间</td>
						<td style="width:15%" align="left">
							<input class="easyui-datetimebox" id="mainorder-maxPayTime" name="orderTime" data-options="editable:false" />
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 10px 0 0;" align="right">支付状态</td>
						<td style="width:15%" align="left">
							<select id="mainorder-payStatus" class="easyui-combobox" name="payStatus"
									data-options="width:135,panelMaxHeight:82">
								<option value="">请选择</option>
								<option value="0">未支付</option>
								<option value="1">已支付</option>
								<option value="2">已取消</option>
							</select>
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">支付渠道</td>
						<td style="width:15%" align="left">
							<select id="mainorder-payway" class="easyui-combobox" name="payway"
									data-options="width:135,panelMaxHeight:82">
								<option value="">请选择</option>
								<option value="1">微信</option>
								<option value="2">支付宝</option>
							</select>
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">订单状态</td>
						<td style="width:15%" align="left">
							<select id="mainorder-status" class="easyui-combobox" name="orderStatus"
									data-options="width:135,panelMaxHeight:82">
								<option value="">请选择</option>
								<option value="0">待付款</option>
								<option value="1">待发货</option>
								<option value="2">待收货</option>
								<option value="3">交易成功</option>
								<option value="4">交易关闭（已删除）</option>
								<option value="5">交易关闭（已取消）</option>
								<option value="6">交易关闭（退款成功）</option>
							</select>
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">联系人</td>
						<td style="width:15%" align="left">
							<input class="easyui-textbox"id="mainorder-contName" name="contName" />
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 10px 0 0;" align="right">清算日期</td>
						<td style="width:15%" align="left">
							<input class="easyui-datebox" id="mainorder-settleTimeFee" name="settleTimeFee" data-options="editable:false" />
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">商户编号</td>
						<td align="left" colspan="2">
							<input class="easyui-textbox" id="mainorder-vendorIds" name="vendorIds" readonly="true" />
							<a class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="venderChoose()">选择</a>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="3">
							<a class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="mainorderDoSearch()">查询</a>
							<a class="easyui-linkbutton" iconCls="icon-edit-clear" plain="false" onclick="mainorderClearAll()">清空</a>
							<a class="easyui-linkbutton" iconCls="icon-excel" onclick="mainOrderExport()" plain="false">导出Excel</a>
							<a class="easyui-linkbutton" iconCls="icon-excel" onclick="bigOrderExport()" plain="false">关联子订单导出Excel</a>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<!-- 主订单数据表格 -->
	<table id="mainorderDataGrid" class="easyui-datagrid" style="min-height:400px" singleSelect="true"
		data-options="url:'mainorder/selectall',fitColumns:true,pagination:true,sortName:'transactionid',
       				 sortOrder:'asc',toolbar:'#mainorderToolbar',title:'主订单列表',iconCls:'icon-man',striped:true,
       				 collapsible:true,checkOnSelect: false,selectOnCheck: true">
		<thead>
			<tr>
				<th field="transactionid" width="17%" align="center" data-options="sortable:true">主订单号</th>
				<th field="bigorderId" width="17%" align="center">大订单号</th>
				<th field="orderType" width="8%" align="center" data-options="formatter:orderTypeFormatter">订单类型</th>
				<th field="vendorSnm" width="10%" align="center">商户名称</th>
				<th field="orderTotalAmt" width="6%" align="center" data-options="">订单总额</th>
				<th field="payStatus" width="5%" align="center" data-options="formatter:payStatusFormatter">支付状态</th>
				<th field="status" width="5%" align="center" data-options="formatter:statusFormatter">订单状态</th>
				<th field="orderTime" width="13%" align="center">订单时间</th>
				<th field="settleStatues" width="8%" align="center" data-options="formatter:settleStatuesFormatter">清算状态</th>
				<!-- <th field="_ac" width="5%" align="center" data-options="">核销状态</th> -->
				<th field="ad" width="6%" align="center" 
					data-options="formatter:mainBtnFormatter">操作</th>
			</tr>
		</thead>
	</table>

	<!-- 主订单详细信息显示开始 -->
	<div id="mainorderEditDialog" class="easyui-dialog" data-options="closed:true,draggable:false,inline:true" style="width: 100%; height: 100%">
		<form id="mainorderEditForm" action="">
			<div id="" class="easyui-panel" title="基本信息" data-options="collapsible:true,iconCls:'icon-edit'" style="width: 100%">
				<table style="width:100%">
					<tr>
						<td style="width:10%;padding:0 15px 0 0;" align="right">主订单号</td>
						<td style="width:40%" align="left">
							<input name="transactionid" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
						</td>
						<td style="width:10%;padding:0 15px 0 0;" align="right">大订单号</td>
						<td style="width:40%" align="left">
							<input name="bigorderId" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 15px 0 0;" align="right">商户名称</td>
						<td style="width:40%" align="left">
							<input name="vendorSnm" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
						</td>
						<td style="width:10%;padding:0 15px 0 0;" align="right">订单类型</td>
						<td style="width:40%" align="left">
							<select data-options="panelMaxHeight:82" class="easyui-combobox" name="orderType" style="width:80%;height:30px;" readonly="true">
								<option value="1">普通订单</option>
								<option value="2">优惠券订单</option>
								<option value="3">实物众筹订单</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 15px 0 0;" align="right">运费</td>
						<td style="width:40%" align="left">
							<input name="freight" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
						</td>
						<td style="width:10%;padding:0 15px 0 0;" align="right">实付金额</td>
							<input name="orderAmt" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 15px 0 0;" align="right">订单总额</td>
						<td style="width:40%" align="left">
							<input name="orderTotalAmt" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
						</td>
						<td style="width:10%;padding:0 15px 0 0;" align="right">是否已申请退换货</td>
						<td style="width:40%" align="left">
							<input name="afterType" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 15px 0 0;" align="right">下单时间</td>
						<td style="width:40%" align="left">
							<input name="orderTime" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
						</td>
						<td style="width:10%;padding:0 15px 0 0;" align="right">配送方式</td>
						<td style="width:40%" align="left">
							<select data-options="panelMaxHeight:82" class="easyui-combobox" name="deliveryWay" style="width:80%;height:30px;" readonly="true">
								<option value="1">快递</option>
								<option value="2">自提</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 15px 0 0;" align="right">收货人</td>
						<td style="width:40%" align="left">
							<input name="contName" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
						</td>
						<td style="width:10%;padding:0 15px 0 0;" align="right">收货人手机号</td>
						<td style="width:40%" align="left">
							<input name="contPhone" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 15px 0 0;" align="right">收货人地址</td>
						<td style="width:40%" align="left">
							<input name="contAddress" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
						</td>
						<td style="width:10%;padding:0 15px 0 0;" align="right">客户订单备注</td>
						<td style="width:40%" align="left">
							<input name="buyersMessage" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 15px 0 0;" align="right">支付渠道</td>
						<td style="width:40%" align="left">
							<select data-options="panelMaxHeight:82" class="easyui-combobox" name="payway" style="width:80%;height:30px;" readonly="true">
								<option value="1">微信</option>
								<option value="2">支付宝</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 15px 0 0;" align="right">物流公司名称</td>
						<td style="width:40%" align="left">
							<input name="expressName" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
						</td>
						<td style="width:10%;padding:0 15px 0 0;" align="right">快递单号</td>
						<td style="width:40%" align="left">
							<input name="expressId" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td style="width:10%;padding:0 15px 0 0;" align="right">发货说明</td>
						<td style="width:40%" align="left">
							<input name="senddesc" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
						</td>
						<td style="width:10%;padding:0 15px 0 0;" align="right">发货时间</td>
						<td style="width:40%" align="left">
							<input name="sendouttime" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<!-- 主订单详细信息显示结束 -->
	<!-- 主订单模块结束 -->
	
	<!-- 子订单模块开始 -->
	<!-- 子订单模块工具栏 -->
	<div id="childorderToolbar">
		<!-- 子订单条件搜索 -->
		<form id="childorderSearchForm" method="post">
			<div align="left">
				<table class="" style="width: 95%">
					<tr>
						<td style="width:10%;padding:0 10px 0 0;" align="right">子订单号</td>
						<td style="width:15%" align="left">
							<input class="easyui-textbox" id="childorder-transactionid" name="transactionid" style="width:80%"/>
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">主订单号</td>
						<td style="width:15%" align="left">
							<input class="easyui-textbox" id="childorder-mainorderIds" name="mainorderIds" style="width:80%" />
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">商品类型</td>
						<td style="width:15%" align="left">
							<select id="childorder-type" data-options="panelMaxHeight:82" class="easyui-combobox" name="type">
								<option value="">请选择</option>
								<option value="0">普通商品</option>
								<option value="1">活动商品</option>
								<option value="2">实物众筹商品</option>
							</select>
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">订单类型</td>
						<td style="width:15%" align="left">
							<select id="childorder-orderType" data-options="panelMaxHeight:82" class="easyui-combobox" name="orderType">
								<option value="">请选择</option>
								<option value="1">普通订单</option>
								<option value="2">优惠券订单</option>
								<option value="3">实物众筹订单</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<a class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="childorderDoSearch()">查询</a>
							<a class="easyui-linkbutton" iconCls="icon-edit-clear" plain="false" onclick="childorderClearAll()">清空</a>
							<a class="easyui-linkbutton" iconCls="icon-excel" onclick="childOrderExport()" plain="false">子订单导出Excel</a>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>

	<!-- 子订单模块数据表 -->
	<table id="childorderDataGrid" class="easyui-datagrid" style="min-height:400px" singleSelect="true" 
		   data-options="url:'childorder/selectall',fitColumns:true,pagination:true,sortName:'TRANSACTIONID',sortOrder:'asc',
       				 toolbar:'#childorderToolbar',title:'子订单列表',iconCls:'icon-man',striped:true,collapsible:true">
		<thead>
			<tr>
				<th field="transactionid" width="17%" align="center" 
					data-options="sortable:true">子订单号</th>
				<th field="mainorderIds" width="17%" align="center">主订单号</th>
				<th field="orderType" width="8%" align="center"
					data-options="formatter:orderTypeFormatter">订单类型</th>
				<th field="goodsTitle" width="15%" align="center">商品名称</th>
				<th field="standard" width="8%" align="center">商品规格</th>
				<th field="goodsNum" width="5%" align="center">购买数量</th>
				<th field="orderAmt" width="5%" align="center">商品总价</th>
				<th field="afterType" width="8%" align="center" 
					data-options="formatter:afterTypeFormatter">退换货类型</th>
				<th field="approveStatus" width="8%" align="center" 
					data-options="formatter:approveStatusFormatter">售后状态</th>
				<th field="ad" width="8%" align="center" 
					data-options="formatter:btnFormatter">操作</th>
			</tr>
		</thead>
	</table>
	
	<!-- 子订单详细信息显示开始 -->
	<div id="childorderEditDialog" class="easyui-dialog" data-options="closed:true,draggable:false,inline:true"
		style="width: 100%; height: 100%">
		<form id="childorderEditForm" action="">
			<table style="width:100%">
				<tr>
					<td style="width:10%;padding:0 15px 0 0;" align="right">子订单号</td>
					<td style="width:40%" align="left">
						<input name="transactionid" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
					</td>
					<td style="width:10%;padding:0 15px 0 0;" align="right">主订单号</td>
					<td style="width:40%" align="left">
						<input name="mainorderIds" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td style="width:10%;padding:0 15px 0 0;" align="right">商品分类</td>
					<td style="width:40%" align="left">
						<input name="className" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
					</td>
					<td style="width:10%;padding:0 15px 0 0;" align="right">商品类型</td>
					<td style="width:40%" align="left">
						<select data-options="panelMaxHeight:82" class="easyui-combobox" name="goodsType" style="width:80%;height:30px;" readonly="true">
							<option value="0">普通商品</option>
							<option value="1">活动商品</option>
							<option value="2">实物众筹商品</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width:10%;padding:0 15px 0 0;" align="right">商品名称</td>
					<td style="width:40%" align="left">
						<input name="goodsTitle" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
					</td>
					<td style="width:10%;padding:0 15px 0 0;" align="right">商品编号</td>
					<td style="width:40%" align="left">
						<input name="goodsCode" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td style="width:10%;padding:0 15px 0 0;" align="right">商品规格</td>
					<td style="width:40%" align="left">
						<input name="goodsStandard" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
					</td>
					<td style="width:10%;padding:0 15px 0 0;" align="right">购买数量</td>
					<td style="width:40%" align="left">
						<input name="goodsNum" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td style="width:10%;padding:0 15px 0 0;" align="right">商品单价</td>
					<td style="width:40%" align="left">
						<input name="goodsPrice" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
					</td>
					<td style="width:10%;padding:0 15px 0 0;" align="right">商品总价</td>
					<td style="width:40%" align="left">
						<input name="orderAmt" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td style="width:10%;padding:0 15px 0 0;" align="right">支付金额(不含运费)</td>
					<td style="width:40%" align="left">
						<input name="orderRealAmt" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
					</td>
					<td style="width:10%;padding:0 15px 0 0;" align="right">运费</td>
					<td style="width:40%" align="left">
						<input name="freight" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td style="width:10%;padding:0 15px 0 0;" align="right">支付渠道</td>
					<td style="width:40%" align="left">
						<select data-options="panelMaxHeight:82" class="easyui-combobox" name="payWay" style="width:80%;height:30px;" readonly="true">
							<option value="1">微信</option>
							<option value="2">支付宝</option>
							<option value="3">银联</option>
						</select>
					</td>
					<td style="width:10%;padding:0 15px 0 0;" align="right">订单类型</td>
					<td style="width:40%" align="left">
						<select data-options="panelMaxHeight:82" class="easyui-combobox" name="orderType" style="width:80%;height:30px;" readonly="true">
							<option value="1">普通订单</option>
							<option value="2">优惠券订单</option>
							<option value="3">实物众筹订单</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width:10%;padding:0 15px 0 0;" align="right">商户备注</td>
					<td style="width:40%" align="left">
						<input name="venderRemark" class="easyui-textbox" style="width:80%;height:30px;" readonly="readonly" />
					</td>
					<td style="width:10%;padding:0 15px 0 0;" align="right">是否已申请退换货</td>
					<td style="width:40%" align="left">
						<select data-options="panelMaxHeight:82" class="easyui-combobox" name="afterType" style="width:80%;height:30px;" readonly="true">
							<option value="">否</option>
							<option value="1">是</option>
							<option value="2">是</option>
							<option value="3">是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width:10%;padding:0 15px 0 0;" align="right">退换货类型</td>
					<td style="width:40%" align="left">
						<select data-options="panelMaxHeight:82" class="easyui-combobox" name="afterType" style="width:80%;height:30px;" readonly="true">
							<option value="">未申请售后</option>
							<option value="1">退货退款</option>
							<option value="2">仅退款</option>
							<option value="3">换货</option>
						</select>
					</td>
					<td style="width:10%;padding:0 15px 0 0;" align="right">售后状态</td>
					<td style="width:40%" align="left">
						<select data-options="panelMaxHeight:82" class="easyui-combobox" name="approveStatus" style="width:80%;height:30px;" readonly="true">
							<option value="">未申请售后</option>
							<option value="0">待审核</option>
							<option value="1">审批通过</option>
							<option value="2">审批未通过</option>
							<option value="3">售后成功</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 子订单详细信息显示开始 -->
	<!-- 子订单模块结束 -->
</div>

<!-- 子订单商户备注dialog -->
<div id="venderRemarkEditDialog" class="easyui-dialog" title="商户备注" style="width: 400px; height: 250px;padding:10px 10px 0 0;"
	 data-options="closed:true,iconCls:'icon-edit',resizable:true,modal:true,buttons:'#venderRemarkEditBtns',resizable:'false'">
	<form id="venderRemarkUpdateForm" action="" method="post">
    	<input type="hidden" name="ids">
    	<table style="">
    		<tr> 
    			<td style="width:80px;padding:0 15px 0 0;" align="right">商户备注</td>
				<td style="width:260px" align="left">
					<textarea name="venderRemark" style="width:250px;height:120px;"></textarea><br/>
					<span style="color:red">0-200字之间</span>
				</td>
    		</tr>
    	</table>
    </form>    
</div>
<!-- 商户备注dialog按钮 -->
<div id="venderRemarkEditBtns">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="updateVenderRemark()">保存</a>
</div> 

<!-- 主订单商户id查询dialog -->
<div id="venderChooseDialog" class="easyui-dialog" title="商户列表" style="width: 600px; height: 333px;padding:0 0 0 0;"
	 data-options="closed:true,resizable:true,modal:true,buttons:'#venderChooseBtns'">
	<div id="venderToolbar" style="width: 100%">
		<!-- 商户条件搜索 -->
		<form id="venderSearchForm" method="post">
			<div align="left">
				<table class="" style="width: 95%">
					<tr>
						<td style="width:10%;padding:0 10px 0 0;" align="right">商户编号</td>
						<td style="width:15%" align="left">
							<input class="easyui-textbox" id="vender-vendorId" name="vendorId" style="width:80%"/>
						</td>
						<td style="width:10%;padding:0 10px 0 0;" align="right">商户名称</td>
						<td style="width:15%" align="left">
							<input class="easyui-textbox" id="vender-vendorSnm" name="vendorSnm" style="width:80%" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<a class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="venderDoSearch()">查询</a>&nbsp;&nbsp;
							<a class="easyui-linkbutton" iconCls="icon-edit-clear" plain="false" onclick="venderClearAll()">清空</a>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<!-- 商户数据表 -->
	<table id="venderDataGrid" class="easyui-datagrid" singleSelect="true" style="width: 97%"
		data-options="url:'spUser/list',fitColumns:true,pagination:true,pageSize:5,pageList:[5,10,15,20],
       				 toolbar:'#venderToolbar',striped:true">
		<thead>
			<tr>
				<th field="_ddd" width="15%" data-options="checkbox:true">选择</th>
				<th field="vendorId" width="20%" align="center">商户编号</th>
				<th field="vendorFnm" width="50%" align="center">商户全称</th>
				<th field="vendorSnm" width="20%" align="center">商户简称</th>
			</tr>
		</thead>
	</table>    
</div>
<!-- 商户id查询dialog按钮 -->
<div id="venderChooseBtns">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="venderIdChoose()">选择</a>
</div>
<!-- End of easyui-dialog -->
