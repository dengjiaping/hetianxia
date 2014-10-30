package com.htx.search;

import java.util.List;
import java.util.Random;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.main.Update;
import com.htx.main.listActivity;
import com.htx.pub.PubActivity;

/**
 * 搜索页面
 * 
 * @author Ivan
 * 
 */
public class searchHomeActivity extends PubActivity implements OnClickListener {

	private EditText search_editText;
	private Button search_btn;
	private ImageView main_speak;
	private KeywordsFlow keywordsFlow;

	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {
			show();
			handler.postDelayed(this, 4000);
		}
	};

	public static final String[] keywords = { "T恤", "衬衫", "针织", "西装", "短外套",
			"连衣裙", "裤子", "牛仔", "雪纺", "风衣", "夹克", "西服", "牛仔裤", "卫衣", "皮衣", "风衣",
			"毛衣", "休闲裤", "文胸", "内裤", "袜子", "基础款", "童鞋", "套装", "单鞋", "帆布鞋",
			"短靴", "靴子", "休闲", "潮流", "板鞋", "皮鞋", "豆豆鞋", "围巾", "帽子", "皮带", "手套",
			"毛线", "女装", "男装", "内衣", "春款", "女鞋", "女包", "男包", "男鞋", "真皮", "大牌",
			"单肩", "钱包", "手提", "旅行箱包", "双肩", "旅行箱", "包", "登机", "运动", "运动鞋",
			"跑步", "板鞋", "Adidas", "NIKE", "卡西欧", "佳能", "帆布鞋", "短靴", "靴子", "篮球",
			"帆布", "健身", "泳衣", "跑步机", "舞蹈", "BB霜", "阿迪", "361度", "特步", "李宁",
			"安踏", "运动服", "套装", "POLO衫", "户外", "户外服", "钓鱼", "野餐", "欧莱雅", "玉兰油",
			"户外鞋袜", "背包", "单肩包", "军迷", "帐篷", "珠宝", "手表", "钻石", "黄金", "铂金",
			"施华洛", "时装表", "翡翠", "珍珠", "琥珀", "珊瑚", "宝石", "碧玺", "饰品", "项链", "手链",
			"发饰", "耳饰", "戒指", "果冻表", "水钻表", "复古表", "眼镜", "太阳镜", "眼睛框", "3D眼镜",
			"zippo", "数码", "手机", "iphone", "安卓", "三星", "蓝牙", "索尼", "HTC", "联想",
			"笔记本", "苹果", "戴尔", "HP", "华硕", "手机壳", "智能机", "中兴", "国产", "联想",
			"华为", "4核", "相机", "卡片机", "单反机", "尼康", "索尼", "平板", "ipadmini",
			"surface", "ipad4", "电脑", "键盘", "鼠标", "显示器", "CPU", "DIY", "路由器",
			"配件", "手机壳", "蓝牙", "移动电源", "苹果配件", "防尘塞", "创意", "办公", "高清投影",
			"打印机", "一体机", "家电", "小家电", "空气净化", "吸尘器", "挂烫", "预算器", "电视机", "空调",
			"冰箱", "洗衣机", "视听", "耳机", "家庭影院", "音响", "播放器", "饭煲", "电水壶", "榨汁机",
			"油烟机", "个人护理", "剃须刀", "电子秤", "按摩器", "净水", "面包机", "豆浆机", "酸奶机",
			"烤箱", "护肤", "洁面", "爽肤水", "面膜", "眼霜", "防晒", "彩妆", "香水", "BB霜", "美甲",
			"唇膏", "眼线", "美体", "美发", "身体乳", "丰胸", "洗发", "假发", "功效", "美白", "抗皱",
			"祛斑", "保温", "去黑头", "品牌", "兰寇", "美宝莲", "脱毛膏", "精油", "隔离", "卸妆",
			"男士", "母婴用品", "奶粉", "牛奶粉", "钙", "铁", "锌", "鱼油", "辅食", "用品", "纸尿裤",
			"奶瓶", "睡袋", "推车", "润肤", "玩乐", "积木", "童车", "书包", "爬行", "早教", "孕产",
			"孕裤", "裙装", "吸奶", "哺乳", "包", "新生儿", "礼盒", "婴儿床", "抱毯", "消毒器", "婴装",
			"爬服", "外出服", "鞋", "内衣", "家居建材", "家具", "床", "沙发", "衣柜", "餐桌", "电视柜",
			"床品", "四件套", "被子", "枕头", "抱枕", "拖鞋", "家饰", "摆件", "十字绣", "窗帘", "墙贴",
			"地毯", "建材", "吸顶灯", "灯饰", "墙纸", "瓷砖", "油漆", "卫浴", "浴室柜", "马桶", "龙头",
			"浴霸", "五金", "婚庆", "宜家", "挂画", "样板间", "家装日记", "零食", "牛肉", "红枣",
			"猪肉脯", "巧克力", "菜场", "樱桃", "寿司", "蛋糕", "烘焙", "方便面", "进口", "牛奶",
			"饼干", "保健", "上火", "燕窝", "山药", "阿胶", "品牌", "日用百货", "拖把", "压缩袋",
			"旋转拖", "清洁", "日用", "洗发", "纸巾", "洗衣液", "卫生巾", "易得康", "圣洁兰", "苏晓漫",
			"益生源", "皇冠金锅", "小风扇", "冰贴", "请贴", "生日报", "厨房", "茶具", "杯子", "锅",
			"保鲜盒", "居家", "晴雨伞", "竹炭", "结婚", "钟", "安全套", "情趣内衣", "汽车", "车品",
			"坐垫", "座套", "脚垫", "方向盘套", "配件", "导航", "轮胎", "轮殻", "机油", "记录仪",
			"摩托车", "汽车香水", "挂件", "车贴", "车衣", "文具", "铅笔", "钢笔", "圆珠笔", "水彩笔",
			"读书", "沙海", "童书", "1元购", "乐器", "吉他", "钢琴", "音像", "乐活", "宠物", "狗",
			"猫", "鱼缸", "狗窝", "服饰", "鲜花", "绿植", "网服", "洗照片", "软件", "翻译", "纸箱",
			"美食", "自助餐", "冰淇淋", "中餐", "外卖", "电影", "演唱会", "蛋糕", "元祖", "好喜利",
			"休闲", "健身", "美容", "春游", "减肥", "摄影", "婚庆", "贴膜", "加油卡", "房产", "优惠",
			"商场", "超市", "联华", "新世界" };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_home);

		// 初始化默认数据
		init();

		main_speak.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (net()) {
					PackageManager pm = getPackageManager();
					List<ResolveInfo> activities = pm
							.queryIntentActivities(new Intent(
									RecognizerIntent.ACTION_RECOGNIZE_SPEECH),
									0);
					if (activities.size() != 0) {
						startActivityForResult(new Intent(
								searchHomeActivity.this, listActivity.class),
								4321);
					} else {
						Update update = new Update(searchHomeActivity.this);
						update.check();
					}
				} else
					Toast("无网络", 1500);
			}
		});

		search_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				handler.removeCallbacks(runnable);

				String keyworld = search_editText.getText().toString();
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(searchHomeActivity.this
								.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				if (keyworld != null && !"".equals(keyworld)) {
					Intent it = new Intent(searchHomeActivity.this,
							SearchMainActivity.class);
					it.putExtra("sname", keyworld);
					startActivity(it);
				}
			}
		});
	}

	private void init() {
		search_editText = (EditText) findViewById(R.id.search_editText);
		search_btn = (Button) findViewById(R.id.search_btn);
		main_speak = (ImageView) findViewById(R.id.main_speak);

		keywordsFlow = (KeywordsFlow) findViewById(R.id.frameLayout1);
		keywordsFlow.setDuration(800l);
		keywordsFlow.setOnItemClickListener(this);
		
		// 添加
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);

		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable, 5000);

	}

	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		Random random = new Random();
		for (int i = 0; i < KeywordsFlow.MAX; i++) {
			int ran = random.nextInt(arr.length);
			String tmp = arr[ran];
			keywordsFlow.feedKeyword(tmp);
		}
	}

	public void onClick(View v) {

		if (v instanceof TextView) {
			String keyworld = ((TextView) v).getText().toString();
			
			handler.removeCallbacks(runnable);

			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(searchHomeActivity.this
							.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
			if (keyworld != null && !"".equals(keyworld)) {
				Intent it = new Intent(searchHomeActivity.this,
						SearchMainActivity.class);
				it.putExtra("sname", keyworld);
				startActivity(it);
			}
		}
	}

	private void show() {

		keywordsFlow.rubKeywords();
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		// keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
	};


	/**
	 * Handle the results from the recognition activity.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 4321 && resultCode == RESULT_OK) {
			search_editText.setText(data.getExtras().getString("result"));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 判断网络状态
	 * 
	 * @return netSataus
	 */
	public boolean net() {
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		boolean netSataus = false;
		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		Log.e("网络是否可用：", netSataus + "");
		return netSataus;
	}
}
