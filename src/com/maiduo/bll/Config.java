package com.maiduo.bll;
/**
 * ȫ�������ļ�
 * @author Administrator
 *
 */
public class Config {
	//static String Domain = "http://116.255.203.184:8080";
	static String Domain = "http://ms.maiduo.com"; // ����
	static String Domain72 = "http://116.255.204.72:8080"; // ����
	//static String Domain = "http://192.168.0.150:803"; // ����·��
	//static String Domain = "http://localhost:6617";
	//static String Domain = "http://116.255.204.72:8080"; // 72����
	/**
	 * ����������˵�һЩ������ַ
	 * ������Ϊjson���ؽ��
	 * ���������View��Ϊhtml���ؽ��
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
	public static final String UrlSuggest = Domain +"/Suggest.ashx";// �Ὠ��
	public static final String UrlProductTXM = Domain +"/Product/ProductSearchTXM.ashx";//����������ַ
	public static final String UrlDefaultInfo = Domain +"/DefaultInfo.ashx";//��ҳ�õ�
	
	// �����̼���ʾ�б�
	public static final String UrlJifenShopListWord = Domain +"/JifenShop/ShopListWord.ashx";// �̼������б�
	public static final String UrlJifenShopListMap = Domain +"/JifenShop/ShopListMap.ashx";// �̼ҵ�ͼ�б� ����Ҫ���ؾ�γ�ȣ�
	public static final String UrlJifenShopListMapToList = Domain +"/JifenShop/ShopListMapToList.ashx";// �̼ҵ�ͼ�б��л��������б� ����Ҫ���ؾ��룩
	public static final String UrlJifenShopDetail = Domain +"/JifenShop/ShopDetail.ashx";// �̼���ҳ��ʾ
	public static final String UrlJifenShopAddComment = Domain +"/JifenShop/ShopAddComment.ashx";// ����̼�����
	
	// ��ʱ��Ϣ����
	public static final String UrlMessageInfo = Domain72 +"/Message/GetInfo.ashx";// ��ȡ��Ϣ����
	public static final String UrlMessageList = Domain72 +"/Message/List.ashx";// ��ȡ��Ϣ�б�
	public static final String UrlMessageDetail = Domain72 +"/Message/MessageDetail.aspx";// ��ȡ��Ϣ����
	public static final String UrlMessageListHistory = Domain72 +"/Message/HistoryList.ashx";// ��ȡ��ʷ��Ϣ�б�
	
	public static final String UrlMessageShowPushInfo = Domain +"/PushMessage/ShowInfo.aspx?id=";//����
	
	
	
	// �����̳�
//	public static final String UrlJifenDefault = Domain +"/JifenProduct/Default.ashx";//�����̳���ҳ�õ�
	public static final String UrlJifenCategory = Domain +"/JifenProduct/Category.ashx";//������Ʒ���
	public static final String UrlJifenProductList = Domain +"/JifenProduct/List.ashx";//������Ʒ�б�
	public static final String UrlJifenProductDetail = Domain +"/JifenProduct/ProductDetail.ashx";//������Ʒ��ҳ
	public static final String UrlJifenProductDetailView = Domain +"/JifenProduct/ProductDetail.aspx";//ͼ�İ����
	
	// �ҵ����
	public static final String UrlUserCheckLogin = "http://api.36936.com/ClientApi/ClientLogin.ashx";// ��¼
	public static final String UrlUserHome = Domain +"/User/Default.ashx";// �ҵ������ҳ
	public static final String UrlUserOrderList = Domain +"/User/OrderList.ashx";// �ҵĶ����б�
	public static final String UrlUserOrderDetail = Domain +"/User/OrderDetail.ashx";// �ҵĶ�����ҳ
	public static final String UrlUserHelpReg = Domain +"/User/HelpReg.ashx";// ���ע��
	public static final String UrlUserRegister= Domain + "/user/register.ashx"; //�Լ�ע��
	public static final String UrlUserTransactionsRecord = Domain + "/user/TradeHistory.ashx"; //���׼�¼
	public static final String UrlUserJifenRecord = Domain + "/user/jifenlist.ashx"; //���ּ�¼
	
	public static final String UrlUserCharge = Domain + "/OnlinePay/Charge.ashx"; //��ֵ
	
	
	//public static final String UrlUserMyAddress = Domain +"/User/MyAddress.ashx";// �����ҵ��ջ���ַ
	//public static final String UrlUserMyAddressAdd = Domain +"/User/MyAddressAdd.ashx";// ����ҵ��ջ���ַ

	// ���ﳵ
	public static final String UrlShoppingAddCart = Domain +"/Shopping/CartAdd.ashx";// ��ӵ����ﳵ
	public static final String UrlShoppingShowCart = "http://api.36936.com/ClientApi/PointsShop/Cart/CartList.ashx";// ��ʾ���ﳵ
	public static final String UrlShoppingAddressList = Domain +"/Shopping/AddressList.ashx";// ��ʾ�ջ���ַ
	public static final String UrlShoppingAddressAdd = Domain +"/Shopping/AddressAdd.ashx";// ����ջ���ַ
	public static final String UrlShoppingReceive = Domain +"/Shopping/Receive.ashx";// �ջ���Ϣ ��������
	public static final String UrlShoppingPayType = Domain +"/Shopping/PayType.ashx";// �������� �Ƿ���ʾ��������
	public static final String UrlShoppingOtherShow = Domain +"/Shopping/OtherShow.ashx";// �������� �Ƿ���ʾ��������
	public static final String UrlShoppingSendType = Domain +"/Shopping/SendType.ashx";// ��ʾ�ͻ���ʽ
	public static final String UrlShoppingCartEdit = Domain +"/Shopping/CartEdit.ashx";// �༭���ﳵ ɾ����Ʒ ��չ��ﳵ
	public static final int ShoppingMaiduoCartType = 2;// ����̳ǹ��ﳵCartType = 2 
	public static final int ShoppingJifenCartType = 15;// �����̳ǹ��ﳵCartType = 15 
	public static final String UrlShoppingOther = Domain +"/Shopping/Other.ashx";// ������Ϣ
	public static final String UrlShoppingSubmit = Domain +"/Shopping/SubmitOrder.ashx";// �ύ����
	public static final String UrlShoppingPayOrderByBalance = Domain +"/Shopping/PayOrderByBalance.ashx";// �����֧��
	public static final String UrlShoppingPayOrderByArrival = Domain +"/Shopping/PayOrderByArrival.ashx";// ���������֧��
	public static final String UrlShoppingActionSave = Domain +"/Shopping/ActionSave.ashx";// ������ʱ�ջ���Ϣ������
	public static final String UrlShoppingPayBankCardBefore = Domain +"/Shopping/PayBankCardBefore.ashx";// ˢ��֧��֮ǰ�Ĳ���
	public static final String UrlShoppingPayBankCardAfter = Domain +"/Shopping/PayBankCardAfter.ashx";// ˢ��֧��֮��Ĳ���
	
	
	public static final int ShoppingSendTypeKuaidi = 3;// ������ʽ ���
	public static final int ShoppingSendTypeWuliu = 26;// ������ʽ ����
	
	public static final int ShoppingPayTypeBalance = 1;//���֧��
	public static final int ShoppingPayTypeNetBank = 2;//����֧�� 
	public static final int ShoppingPayTypeArrival = 5;//��������
	public static final int ShoppingPayTypeBankCard = 6;//ˢ������
	
	public static final String ShoppingPayTypeBankAppsign = "C8124F3F-14CA-4B82-9043-8EBD563B0C24";//���ǵ�ˢ��ǩ��
	
	
	public static final String OnlinePay_URL_CREATE_ORDER = Domain +"/OnlinePay/umpay/CreateOrderToken.ashx";// ����token
	public static final String OnlinePay_URL_PayNotify = Domain +"/OnlinePay/umpay/PayNotify.ashx";// �ص�
	public static final String OnlinePay_URL_UmpayWap = "http://m.soopay.net/mpay/wml2/index.do?tradeNo=";// �ֻ������ύ֧����ַ
	
	
	
	// �ֻ���ֵ
	public static final String UrlPhoneLocation="http://mpay.maiduo.com/Handler/CellphoneLocating.ashx";
	public static final String UrlPhoneCharge="http://mpay.maiduo.com/Handler/GetChargeProduct.ashx";
	public static final String UrlPhoneService="http://mpay.maiduo.com/Handler/mobileservice.ashx";
	
	// ��Ʊ���� //192.168.0.150:8082
	public static final String UrlFlightSearchList= "http://jipiao.maiduo.com/hander/PhoneSearch.ashx";// ��Ʊ����
	public static final String UrlFlightCheckPayPassWord = Domain +"/User/CheckPayPassWord.ashx";// ��֤֧������
	public static final String UrlFlightBook = "http://jipiao.maiduo.com/hander/PhoneBooking.ashx";// Ԥ���ӿ�
	
	
	//ͬ�Ƕһ�
	public static final String UrlCityforShow=Domain+"/jifencity/jifenCity.ashx";
	public static final String UrlCityforHome=Domain+"/jifencity/Default.ashx";
	public static final String UrlCityforList=Domain+"/jifencity/list.ashx";
	public static final String UrlCityforDetails=Domain+"/jifencity/detail.ashx";
	public static final String UrlCityforLike=Domain+"/jifencity/like.ashx";
	public static final String UrlCityforComment=Domain+"/jifencity/Comment.ashx";
	
	// ���ģ��
	public static final String UrlAdAddCallLog	=Domain+"/Ad/AddCallLog.ashx";//������¼
	public static final String UrlAdMyCallLog	=Domain+"/Ad/GetCallLog.ashx";
	public static final String UrlAdGetMyAd		=Domain+"/Ad/GetAd.ashx";
	public static final String UrlAdGetUserLikeInfo=Domain+"/Ad/UserLikeInfo.ashx";//��ע����
	public static final String UrlAdPostasks	=Domain+"/ad/userQuestion.ashx";// �����ʾ�
	public static final String UrlAdGetMoneyList=Domain+"/Ad/GetMoneyList.ashx";// ׬Ǯ��¼
	public static final String UrlAdChangeMoney	=Domain+"/Ad/ChangeMoney.ashx";// ת���ȯ
	public static final String UrlAdGetUserType	=Domain+"/Ad/GetAdUserType.ashx";// ��ȡ�û����ͣ���һ�ν������ж�
	public static final String UrlAdCarryMoney	=Domain+"/Ad/CarryMoney.ashx";// ����
	public static final String UrlAdCarryMoneyLog=Domain+"/Ad/CarryMoneyLog.ashx";// ���ּ�¼
	
	
	
	//ͨ�������ص�ַ
	public static final String UrlUserTongtx= "http://112.231.23.8:6789/tongtx.apk";
	
	
	// �첽�����ַ
	public static final String UrlPostAreaPost = Domain +"/Post/area.ashx";// ʡ��������
	
	//���紫��ʱ���ܵ�����key
	public static final String UrlHashKey = "maiduo0F51@#$758B075FD78B9FB";
	
	//���汾
	public static final String UrlCheckVersion = Domain +"/Welcome.ashx";
	
	// �����绰 
	public static final String PhoneNumber = "400-660-3690";
	
	//ͼƬ��ַ
	public static final String ProdImgPrefix = "http://image.369518.com/";
	
	//
	public static final String AdUserTypeNameFree = "��ѻ�Ա";
	public static final String AdUserTypeNameVIP = "VIP��Ա";

	public static final String UrlSDFolderName= "/maiduo/";//sd�����ļ��е����� Const.UrlSDFolderName
	public static final String UrlSDFolderName1= "/maiduo";// ���ã��еĵط���Ҫ�����б��
	
}
