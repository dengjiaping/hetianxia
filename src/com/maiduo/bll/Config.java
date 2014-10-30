package com.maiduo.bll;
/**
 * 全局配置文件
 * @author Administrator
 *
 */
public class Config {
	//static String Domain = "http://116.255.203.184:8080";
	static String Domain = "http://ms.maiduo.com"; // 网上
	static String Domain72 = "http://116.255.204.72:8080"; // 网上
	//static String Domain = "http://192.168.0.150:803"; // 本地路径
	//static String Domain = "http://localhost:6617";
	//static String Domain = "http://116.255.204.72:8080"; // 72测试
	/**
	 * 请求服务器端的一些连接网址
	 * 正常的为json返回结果
	 * 变量后面加View的为html返回结果
	 */
	public static final String UrlHashKey2 = "{7d2264ca-c894-456-aa66-214fd1b94ae9}";
	public static final String UrlNewsDetailView = Domain +"/News/NewsDetail.aspx";
	public static final String UrlNewsList = Domain +"/News/NewsList.ashx";
	public static final String UrlCategoryList = Domain +"/Product/Category.ashx";
	public static final String UrlProductList = Domain +"/Product/ProductList.ashx";
	public static final String UrlSearchList = Domain +"/Product/ProductSearch.ashx";
	public static final String UrlProductSearch = Domain +"/Product/ProductSearch.ashx";
	public static final String UrlProductDetail = Domain +"/Product/ProductDetail.ashx";
	public static final String UrlProductDetailView = Domain +"/Product/ProductDetail.aspx";
	public static final String UrlSuggest = Domain +"/Suggest.ashx";// 提建议
	public static final String UrlProductTXM = Domain +"/Product/ProductSearchTXM.ashx";//条码搜索地址
	public static final String UrlDefaultInfo = Domain +"/DefaultInfo.ashx";//首页幻灯
	
	// 积分商家显示列表
	public static final String UrlJifenShopListWord = Domain +"/JifenShop/ShopListWord.ashx";// 商家文字列表
	public static final String UrlJifenShopListMap = Domain +"/JifenShop/ShopListMap.ashx";// 商家地图列表 （主要返回经纬度）
	public static final String UrlJifenShopListMapToList = Domain +"/JifenShop/ShopListMapToList.ashx";// 商家地图列表切换到文字列表 （主要返回距离）
	public static final String UrlJifenShopDetail = Domain +"/JifenShop/ShopDetail.ashx";// 商家终页显示
	public static final String UrlJifenShopAddComment = Domain +"/JifenShop/ShopAddComment.ashx";// 添加商家评论
	
	// 定时消息服务
	public static final String UrlMessageInfo = Domain72 +"/Message/GetInfo.ashx";// 获取消息数量
	public static final String UrlMessageList = Domain72 +"/Message/List.ashx";// 获取消息列表
	public static final String UrlMessageDetail = Domain72 +"/Message/MessageDetail.aspx";// 获取消息详情
	public static final String UrlMessageListHistory = Domain72 +"/Message/HistoryList.ashx";// 获取历史消息列表
	
	public static final String UrlMessageShowPushInfo = Domain +"/PushMessage/ShowInfo.aspx?id=";//推送
	
	
	
	// 积分商城
//	public static final String UrlJifenDefault = Domain +"/JifenProduct/Default.ashx";//积分商城首页幻灯
	public static final String UrlJifenCategory = Domain +"/JifenProduct/Category.ashx";//积分商品类别
	public static final String UrlJifenProductList = Domain +"/JifenProduct/List.ashx";//积分商品列表
	public static final String UrlJifenProductDetail = Domain +"/JifenProduct/ProductDetail.ashx";//积分商品终页
	public static final String UrlJifenProductDetailView = Domain +"/JifenProduct/ProductDetail.aspx";//图文版介绍
	
	// 我的买多
	public static final String UrlUserCheckLogin = "http://api.36936.com/ClientApi/ClientLogin.ashx";// 登录
	public static final String UrlUserHome = Domain +"/User/Default.ashx";// 我的买多首页
	public static final String UrlUserOrderList = Domain +"/User/OrderList.ashx";// 我的订单列表
	public static final String UrlUserOrderDetail = Domain +"/User/OrderDetail.ashx";// 我的订单终页
	public static final String UrlUserHelpReg = Domain +"/User/HelpReg.ashx";// 帮扶注册
	public static final String UrlUserRegister= Domain + "/user/register.ashx"; //自己注册
	public static final String UrlUserTransactionsRecord = Domain + "/user/TradeHistory.ashx"; //交易记录
	public static final String UrlUserJifenRecord = Domain + "/user/jifenlist.ashx"; //积分记录
	
	public static final String UrlUserCharge = Domain + "/OnlinePay/Charge.ashx"; //充值
	
	
	//public static final String UrlUserMyAddress = Domain +"/User/MyAddress.ashx";// 管理我的收货地址
	//public static final String UrlUserMyAddressAdd = Domain +"/User/MyAddressAdd.ashx";// 添加我的收货地址

	// 购物车
	public static final String UrlShoppingAddCart = Domain +"/Shopping/CartAdd.ashx";// 添加到购物车
	public static final String UrlShoppingShowCart = "http://api.36936.com/ClientApi/PointsShop/Cart/CartList.ashx";// 显示购物车
	public static final String UrlShoppingAddressList = Domain +"/Shopping/AddressList.ashx";// 显示收货地址
	public static final String UrlShoppingAddressAdd = Domain +"/Shopping/AddressAdd.ashx";// 添加收货地址
	public static final String UrlShoppingReceive = Domain +"/Shopping/Receive.ashx";// 收货信息 结算中心
	public static final String UrlShoppingPayType = Domain +"/Shopping/PayType.ashx";// 货到付款 是否显示货到付款
	public static final String UrlShoppingOtherShow = Domain +"/Shopping/OtherShow.ashx";// 货到付款 是否显示货到付款
	public static final String UrlShoppingSendType = Domain +"/Shopping/SendType.ashx";// 显示送货方式
	public static final String UrlShoppingCartEdit = Domain +"/Shopping/CartEdit.ashx";// 编辑购物车 删除商品 清空购物车
	public static final int ShoppingMaiduoCartType = 2;// 买多商城购物车CartType = 2 
	public static final int ShoppingJifenCartType = 15;// 积分商城购物车CartType = 15 
	public static final String UrlShoppingOther = Domain +"/Shopping/Other.ashx";// 附加信息
	public static final String UrlShoppingSubmit = Domain +"/Shopping/SubmitOrder.ashx";// 提交订单
	public static final String UrlShoppingPayOrderByBalance = Domain +"/Shopping/PayOrderByBalance.ashx";// 用余额支付
	public static final String UrlShoppingPayOrderByArrival = Domain +"/Shopping/PayOrderByArrival.ashx";// 货到付款的支付
	public static final String UrlShoppingActionSave = Domain +"/Shopping/ActionSave.ashx";// 保存临时收货信息的内容
	public static final String UrlShoppingPayBankCardBefore = Domain +"/Shopping/PayBankCardBefore.ashx";// 刷卡支付之前的操作
	public static final String UrlShoppingPayBankCardAfter = Domain +"/Shopping/PayBankCardAfter.ashx";// 刷卡支付之后的操作
	
	
	public static final int ShoppingSendTypeKuaidi = 3;// 发货方式 快递
	public static final int ShoppingSendTypeWuliu = 26;// 发货方式 物流
	
	public static final int ShoppingPayTypeBalance = 1;//余额支付
	public static final int ShoppingPayTypeNetBank = 2;//网银支付 
	public static final int ShoppingPayTypeArrival = 5;//货到付款
	public static final int ShoppingPayTypeBankCard = 6;//刷卡消费
	
	public static final String ShoppingPayTypeBankAppsign = "C8124F3F-14CA-4B82-9043-8EBD563B0C24";//我们的刷卡签名
	
	
	public static final String OnlinePay_URL_CREATE_ORDER = Domain +"/OnlinePay/umpay/CreateOrderToken.ashx";// 创建token
	public static final String OnlinePay_URL_PayNotify = Domain +"/OnlinePay/umpay/PayNotify.ashx";// 回调
	public static final String OnlinePay_URL_UmpayWap = "http://m.soopay.net/mpay/wml2/index.do?tradeNo=";// 手机网银提交支付地址
	
	
	
	// 手机充值
	public static final String UrlPhoneLocation="http://mpay.maiduo.com/Handler/CellphoneLocating.ashx";
	public static final String UrlPhoneCharge="http://mpay.maiduo.com/Handler/GetChargeProduct.ashx";
	public static final String UrlPhoneService="http://mpay.maiduo.com/Handler/mobileservice.ashx";
	
	// 机票订购 //192.168.0.150:8082
	public static final String UrlFlightSearchList= "http://jipiao.maiduo.com/hander/PhoneSearch.ashx";// 机票搜索
	public static final String UrlFlightCheckPayPassWord = Domain +"/User/CheckPayPassWord.ashx";// 验证支付密码
	public static final String UrlFlightBook = "http://jipiao.maiduo.com/hander/PhoneBooking.ashx";// 预订接口
	
	
	//同城兑换
	public static final String UrlCityforShow=Domain+"/jifencity/jifenCity.ashx";
	public static final String UrlCityforHome=Domain+"/jifencity/Default.ashx";
	public static final String UrlCityforList=Domain+"/jifencity/list.ashx";
	public static final String UrlCityforDetails=Domain+"/jifencity/detail.ashx";
	public static final String UrlCityforLike=Domain+"/jifencity/like.ashx";
	public static final String UrlCityforComment=Domain+"/jifencity/Comment.ashx";
	
	// 广告模块
	public static final String UrlAdAddCallLog	=Domain+"/Ad/AddCallLog.ashx";//看广告记录
	public static final String UrlAdMyCallLog	=Domain+"/Ad/GetCallLog.ashx";
	public static final String UrlAdGetMyAd		=Domain+"/Ad/GetAd.ashx";
	public static final String UrlAdGetUserLikeInfo=Domain+"/Ad/UserLikeInfo.ashx";//关注领域
	public static final String UrlAdPostasks	=Domain+"/ad/userQuestion.ashx";// 调查问卷
	public static final String UrlAdGetMoneyList=Domain+"/Ad/GetMoneyList.ashx";// 赚钱记录
	public static final String UrlAdChangeMoney	=Domain+"/Ad/ChangeMoney.ashx";// 转买多券
	public static final String UrlAdGetUserType	=Domain+"/Ad/GetAdUserType.ashx";// 获取用户类型，第一次进来会判断
	public static final String UrlAdCarryMoney	=Domain+"/Ad/CarryMoney.ashx";// 提现
	public static final String UrlAdCarryMoneyLog=Domain+"/Ad/CarryMoneyLog.ashx";// 提现记录
	
	
	
	//通天下下载地址
	public static final String UrlUserTongtx= "http://112.231.23.8:6789/tongtx.apk";
	
	
	// 异步请求地址
	public static final String UrlPostAreaPost = Domain +"/Post/area.ashx";// 省市县联动
	
	//网络传输时加密的密码key
	public static final String UrlHashKey = "maiduo0F51@#$758B075FD78B9FB";
	
	//检测版本
	public static final String UrlCheckVersion = Domain +"/Welcome.ashx";
	
	// 订购电话 
	public static final String PhoneNumber = "400-660-3690";
	
	//图片地址
	public static final String ProdImgPrefix = "http://image.369518.com/";
	
	//
	public static final String AdUserTypeNameFree = "免费会员";
	public static final String AdUserTypeNameVIP = "VIP会员";

	public static final String UrlSDFolderName= "/maiduo/";//sd卡中文件夹的名字 Const.UrlSDFolderName
	public static final String UrlSDFolderName1= "/maiduo";// 备用，有的地方不要后面的斜杠
	
}
