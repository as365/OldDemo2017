package com.zfxc.fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zfxc_wbx.R;
import com.zfxc.adapter.MyAdapter;
import com.zfxc.entity.NewsInfo;
import com.zfxc.entity.ZfscDB;
import com.zfxc.entity.Zfscentity;
import com.zfxc.ui.LoginActivity;
import com.zfxc.ui.ZFSCDetailActivity;
import com.zfxc.util.JDTextView;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;
import com.zfxc.util.XRTextView;

public class ZFSCFragment extends Fragment {
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);

	}

	private Button btSeach;
	private EditText edContext;
	private ListView lvPunishment;
	private ListView lvYiju;
	private String enforcementtype;
	private TextView tvContext;
	private List<ZfscDB> dbList;
	private XRTextView xrtextview;
	private String s = "论文摘要 权利作为法理学的一个重要且复杂的研究对象，不同时期的不同法学家各自对权利做出了不同的定义。拉兹作为当代西方最具影响力的法学家之一，他的权利利益论推进了世界权利理论的发展。...论文摘要 权利作为法理学的一个重要且复杂的研究对象，不同时期的不同法学家各自对权利做出了不同的定义。拉兹作为当代西方最具影响力的法学家之一，他的权利利益论推进了世界权利理论的发展。在国内，张文显所提出的以要素综合说为基础的“权利本位论”成为通说。因此，本文通过比较分析的研究方法对拉兹与张文显的权利概念进行比较，探析两者之间的异同，以期进一步完善学界对权利的定义。论文关键词 核心期刊论文发表,权利,拉兹,张文显“权利”一词最早出现于古罗马的“私法”中，并一直沿用至今，就目前学界所具有资料来看，单从国外引进的并为大家耳熟能详的权利概念或者定义就多达十余种。其中，影响力较大的就是当代重要的权利理论家之一的约瑟夫·拉兹（Joseph Raz）所提出的权利利益论。近数十年来，中国法学界对权利也做出了各种定义。当然，在中国法学界成为通说的便是张文显所提出来的以要素综合说为基础的“权利本位论”。在此，本文就这两位当代重要法学家所提出来的权利概念进行比较，以求更全面地把握权利的概念。一、拉兹的权利概念探析——以利益为基础的权利本位观作为当代重要的法学家之一，约瑟夫·拉兹对分析法学做出了巨大的贡献，因此,他也为中国法学界所熟知。在西方的权利分析领域，拉兹的利益论成为一种主导性理论。但是，拉兹突破了霍菲尔德、哈特等人将权利仅限于法律权利的分析范式，提出了对权利的一般性解释，即不仅仅适用于法律权利，而且适用于道德权利。故本文主要从拉兹的一般性权利概念出发对其进行分析。（一）拉兹关于权利的定义拉兹认为从权利的定义开始讨论权利是存在危险的，因为人们或许会以一个定义结束讨论，而根据该定义，有些所谓“不重要”的东西便不是权利；与此相反的危险则是，把任何有价值的东西都视为权利，从而便无法把握权利所具有的特殊重要性。这两种危险表明，权利的定义必须涵盖法律和道德领域中所有权利的共同本质，并有助于解释权利在实践中特殊作用。为此，拉兹把权利定义为： “X 拥有一项权利，当且仅当X能够拥有各种权利，并且其他事情相等，X的福祉（他的利益） 的某一方面是使其他某个（些）人承担某项义务的充分理由。”　　　　（二）拉兹权利概念的要素　　　　在拉兹的权利概念中，我们至少可以发现有这样三个要素： 第一，拥有权利能力的权利主体；第二，权利的基础——利益；第三，权利和义务的关系——权利是义务的基础。首先，关于权利主体，拉兹认为主体拥有权利，取决于“拥有权利能力的条件”。拉兹将“拥有权利的能力”定义为： 一个个体（ An individual）或者一个拟制的人（如法人）当且仅当或者他的利益具有终极价值（ultimate value）时，只要可以行动，需要承担一定的义务，就说明它们能够拥有权利，成为权利主体。所谓终极价值，即指一种内在的而非派生的独立于其工具价值的固有的价值。拉兹认为只有“同一道德共同体”的人才能拥有权利，此即所谓的互惠论，但他指出，不应把同一道德共同体仅看作是相互作用的个体的共同体，而应把同一道德共同体的概念扩展到所有的道德行为人，把任何遵守义务的人都看成是有能力拥有权利的主体。需要指出的是，这里所谈的义务（duty）和职责（obligation）拉兹认为是没有区别的。拉兹还表明事物及动物不会拥有权利，原因在于事物或动物的存在和繁荣不具有终极价值，而只是一种工具价值。　　　　其次，根据拉兹的权利概念，权利是建立在利益的基础之上的，利益构成了权利存在的必要条件，无论道德权利还是法律权利皆是以利益为出发点的。即如果某人的利益足以使另一个人承担某项义务，那么他就拥有某项权利。具体为法律权利时便是指，如果他的利益被法律承认，并且法律使他的利益成为令其他人承担某项义务的充分依据。　　　　最后，拉兹关于权利与义务的关系，笔者将在下文论述。　　　　（三）拉兹的权利与义务关系　　　　在权利分析领域，霍菲尔德通过对法律权利概念的分析，得出这样一种相依性的结论：每一项权利都与一项义务相对应，反过来也是如此。拉兹对此表示异议，他认为权利和义务之间不是一一对应关系，并且两者之间存在先后顺序关系。　　　　第一，权利是义务的基础。根据其权利定义，如果某个人拥有某项权利，那么他某一方面的利益就成为使他人负有某项义务的充分条件。即一项权利的存在通常会导致其他人负有某种义务，而这种义务并不取决于权利所有人是否渴望。权利是判断一个个体负有某项义务的理由，证明了主体所负有的义务具有正当性，而不是相反。因此，拉兹认为把权利和义务简化为一种相依性关系是不正确的。　　　　第二，权利与义务之间并不是一一对应的关系。一个权利可能会对应着很多的义务，例如人身安全的权利并不要求保护一个人免受所有的意外和伤害，但却对应着不得非法拘禁他人、不得伤害他人、不得杀人等多项义务。同时，拉兹提出权利是动态的，他认为随着社会环境的变化会在旧的权利基础上创设出新的义务。　　　　第三，权力相对于义务具有优先性。首先，一个人或许知道某项权利的存在及其存在的理由，但不知道谁由于该项权利而受到义务的约束，或不知道这些义务具体是什么。其次，由于权利的动态特征，人们在社会环境变化之前不可能事先确定某项权利的含义以及以该权利为基础的义务。从而，进一步表明，权利是义务的基础，而且优先于义务。　　　　二、张文显的权利概念分析——以要素综合说为基础的权利本位观　　　　自改革开放以来，经过数十年对权利理论的分析研究，“权利本位论”逐渐成为中国法学界的一种通说，得到了许多知名理论法学家的赞同。其中最具代表性的人物张文显先生在他的著作《法哲学范畴研究》一书中系统地论证了“权利本位论”。他的结论是，权利或者“权利本位”是现代法哲学的基石。　　　　（一）张文显关于权利的定义　　　　对于权利的研究，张文显更强调的是一种法律权利，而非道德权利，他在为权利下定义时，专门加了“法律”两个字，他指出，法律权利是指规定或隐含在法律规范中，实现于法律关系中的，主体以相对自由的作为或不作为的方式获得利益的一种手段。　　　　（二）张文显权利概念的要素　　　　权利作为一个内涵丰富且复杂的概念，不可简单地一语道之，要素分析法在国内外学术界被广泛运用，张文显在权利问题的研究上也运用了这种方法。　　　　张文显提出，为了避免出现对权利的残缺不全的认识，需要在对其的各个要素（主张、利益、资格、权能、自由、规范、合理预期、选择等）进行讨论的基础上进行综合的分析。根据张文显在他的著作中所对法律权利下的定义，可以看出，他认为法律权利至少包含四个要素：（1）法律规范，即指权利是由规范性法律文件规定的。（2）主体，指能够享有权利的符合法律规定的自然人或法人。（3）自由，指主体在意志自由的基础上免受一切非法干扰地行使权利的自由。（4）利益，指主体通过权利得以享有或者维护的特定对象。不难看出，实际上张文显是认同了资格说、主张说、自由说和利益说这四种有关权利的定义。故笔者将其界定为是以要素综合说为基础的权利概念。　　　　（三）张文显的权利与义务关系　　　　从法理学视角出发，张文显把权利和义务的关系概括为以下四个方面：（1）结构上的相关关系，即权利和义务对立统一、相互依存、相互贯通、一定条件下可以相互转化，两者从两个对立面出发共同构成了法这一特殊事物。（2）数量上的等值关系，即社会中的权利总量和义务总量是均衡，在某一具体法律关系中，权利和义务是对等的。（3）功能上的互补关系，指权利提供不确定的指引直接体现法律的价值目标，义务提供确定的指引以保障价值目标和权利的实现。（4）价值意义上的主次关系，权利是第一性的，义务是第二性的， 权利是义务存在的依据和意义。　　　　三、拉兹与张文显的权利概念之异同　　　　（一）相同之处：均坚持权利本位观　　　　通过上文对拉兹和张文显的权利概念的分析，可以发现两者之间的共通之处表现在以下几个方面：（1）所有社会成员皆为权利主体， 没有人因为性别、种族、肤色、语言、信仰等的不同而被剥夺权利主体的资格， 或在基本权利的分配上受到歧视对待。同时，两者都认为法人可以成为权利的主体。（2）在权利和义务的关系上，两者都认为权利是目的，义务是手段，法律设定义务的目的在于保障权利的实现，权利是义务存在的依据和意义。　　　　（二）相异之处：权利本位观的基础不同　　　　首先，表现在拉兹的权利概念是以利益为基础的，如果某人的利益足以使另一个人承担某项义务，那么他就拥有某项权利。利益是其权利概念的核心，其权利的本质可以被称之为利益说的典型代表，但针对利益的正当性拉兹并未进行关注。而张文显的权利概念则是建立在包含资格、主张、自由、利益等要素的综合说的基础之上，虽然这样得出的权利概念貌似涵盖了权利的各方各面，但没有说明权利概念的核心是什么，不利于把握权利的深层次本质。　　　　其次，权利主体在行使其权利的过程中，张文显认为其应受法律规范的限制，而确定这种限制的目的在于保证对其他主体的权利给以应有的同样的承认、尊重和保护，以创造一个尽可能使所有主体的权利都得以实现的自由而公平的法律秩序。而拉兹的观点则是权利主体是否拥有权利以及在权利行使过程中，不仅仅有法律的规定，还有道德的约束。拉兹对权利的一般性定义，不仅适用于法律权利，也适用于道德权利。　　　　最后，在权利与义务的关系方面，张文显认为权利与义务在数量上等同，权利与义务相互依存，没有无权利的义务也没有无义务的权利，每一项权利都与一项义务相对应，这点类似于霍菲尔德的权利和义务的相依性定理。然而，拉兹持另一种观点，他认为权利与义务之间并不是一一对应的关系，权利是义务的基础，但某项权利并不见得仅是某项固定义务的基础；权利是动态的，并不存在与权利相对应的义务的封闭清单，拉兹提出，由于某些普遍的社会事实，某项权利的存在通常导致另一个人负有某项义务，但是环境的改变也会导致在原有权利基础之上，产生一些新义务。　　　　通过对拉兹的以利益为基础的权利本位观和张文显的以要素综合说为基础的权利本位观的比较，可以发现资格、主体、自由、利益、主张、权能、规范、选择等确实是与权利密切相关的各种属性或要素，但每一种属性或要素仅仅代表着权利的某一个层面或领域，都不足以反映权利的内在本质。在此，笔者认为，我们在定义权利概念时应该考虑利益的正当性因素。因为就利益本身来说，它是盲目、片面、无止境的，它具有不法性的本能，我们应该保证正当的利益成为权利。";
	private View view;// 缓存页面
	private String content = "";
	private String title = "";
	MyDbHelper myDbHelper;
	private MyAdapter adapter;

	ArrayList<Zfscentity> zfscs = new ArrayList<Zfscentity>();
	private Object[] activities = { "执法手册1", LoginActivity.class, "执法手册2",
			LoginActivity.class, };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("slide", "JobFragment--onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.zfsc_fragment, container, false);
		Log.i("slide", "JobFragment-rootView=null111");
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生IllegalStateException。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
			Log.i("slide", "JobFragment-removeView");
		}
		btSeach = (Button) view.findViewById(R.id.zfsc_seach);
		edContext = (EditText) view.findViewById(R.id.zfsc_seach_content);
		dbList = new ArrayList<>();
//		xrtextview = (XRTextView) view.findViewById(R.id.mytextview_tv);
		lvYiju=(ListView) view.findViewById(R.id.zfsc_lv);
		getContent();
		
		init(view);
		btSeach.setOnClickListener(Lisclick);
		return view;
	}

	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	private OnClickListener Lisclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			title = "";
			content = "";
			getContent();
		}
	};

	private void init(View view) {
		// TODO Auto-generated method stub
		myDbHelper = MyDbHelper.getInstance(getActivity());
		Cursor c = null;

		try {
			myDbHelper.open();
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");
			String sql = "select * from " + MyDbInfo.getTableNames()[6]
					+ " order by sname desc, id desc ";
			c = myDbHelper.select(sql);

			zfscs.clear();
			while (c.moveToNext()) {

				Zfscentity zfscentity = new Zfscentity();
				zfscentity.setId(c.getString(0));
				zfscentity.setFileName(c.getString(1));
				zfscentity.setFilePath(c.getString(3));
				zfscs.add(zfscentity);

			}

			c.close();
			myDbHelper.close();

		} catch (Exception e) {
			myDbHelper.close();
		} finally {
			if (c != null) {
				c.close();
			}

			myDbHelper.close();

		}

		CharSequence[] list = new CharSequence[zfscs.size()];
		for (int i = 0; i < list.length; i++) {
			list[i] = zfscs.get(i).getFileName();
		}

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				getActivity(), android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView) view.findViewById(R.id.ListView01);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(getActivity(),
						ZFSCDetailActivity.class);
				intent.putExtra("id", zfscs.get(position).getId());

				getActivity().startActivity(intent);
				// showfiles(position);
				// switch (position) {
				// case 0:
				// // Toast.makeText(getActivity(), "sdsdsd"+position,
				// Toast.LENGTH_SHORT).show();
				// String path=
				// Environment.getExternalStorageDirectory().getPath()+"/zfxc/zfxc.doc";
				// File f=new File(path);
				// if(f.exists()){
				// Intent intent=getWordFileIntent(path);
				// try{
				// startActivity(intent);
				// }catch(Exception e){
				//
				// }
				// }else{
				// Toast.makeText(getActivity(), "正在下载请稍后。。。",
				// Toast.LENGTH_LONG).show();
				//
				// }
				//
				//
				// break;
				// case 1:
				//
				// break;
				// default:
				// break;
				// }
			}

		});

		listView.setOnLongClickListener((new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "确定删除吗", Toast.LENGTH_LONG)
						.show();
				return false;
			}

		}));

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init(view);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			try {
				init(view);
			} catch (Exception e) {

			}

		}
	}

	private void showfiles(int position) {
		// TODO Auto-generated method stub

		String path = Environment.getExternalStorageDirectory().getPath()
				+ "/zfxc/" + zfscs.get(position).getFilePath();
		File f = new File(path);
		if (f.exists()) {
			Intent intent = getWordFileIntent(path);
			try {
				startActivity(intent);
			} catch (Exception e) {

			}
		} else {
			Toast.makeText(getActivity(), "正在下载请稍后。。。", Toast.LENGTH_LONG)
					.show();

		}

	}

	/**
	 * 获取内置SD卡路径
	 * 
	 * @return
	 */
	public String getInnerSDCardPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	/**
	 * 获取外置SD卡路径
	 * 
	 * @return 应该就一条记录或空
	 */
	public List<String> getExtSDCardPath() {
		List<String> lResult = new ArrayList<String>();
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("extSdCard")) {
					String[] arr = line.split(" ");
					String path = arr[1];
					File file = new File(path);
					if (file.isDirectory()) {
						lResult.add(path);
					}
				}
			}
			isr.close();
		} catch (Exception e) {
		}
		return lResult;
	}

	public static Intent getWordFileIntent(String param)

	{

		Intent intent = new Intent("android.intent.action.VIEW");

		intent.addCategory("android.intent.category.DEFAULT");

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "application/msword");

		return intent;

	}

	private void getContent() {
		if(dbList!=null){
			dbList.clear();
		}
		String result = null;
		HashMap<String, String> hm = new HashMap<String, String>();
		String susername = MyDbInfo.account;
		Log.e("aaaaa", "==================" + susername);
		String spassword = MyDbInfo.password;
		WebServiceJC.susername = MyDbInfo.account;
		Log.e("aaaaa", "==================" + WebServiceJC.susername);
		// String
		// sql="select zpenforcementtype from tbzfPerson where zpmobilephone='"+WebServiceJC.susername+"'";
		// hm.put("username",susername);
		hm.put("zpmobilephone", susername);
		// hm.put("sql",sql);
		WebServiceJC.setUrl("inner", myDbHelper);
		if (hm != null) {

			enforcementtype = WebServiceJC.WorkJC("getUserType", hm);
		}
		String sql1 = "select * from tbzfscdb where sjcontext like '%"
				+ edContext.getText().toString() + "%'";
		// + "where senforcementtype='"
		// + enforcementtype + "'";

		hm.put("username", susername);
		hm.put("password", spassword);
		hm.put("sql", sql1);
		WebServiceJC.setUrl("inner", myDbHelper);
		if (hm != null) {

			result = WebServiceJC.WorkJC("Down_rwgg_json", hm);

		}
		try {
			JSONObject json = new JSONObject(result);
			JSONArray jsonArray = json.getJSONArray("root");
			for (int i = 0; i < jsonArray.length(); i++) {
				ZfscDB zfDB = new ZfscDB();
				JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
				zfDB.setId(jsonObjectSon.getInt("id"));
				zfDB.setTitle(jsonObjectSon.getString("sjtitle"));
				zfDB.setContext(jsonObjectSon.getString("sjcontext"));

				Log.e("aaaaaa", "=========" + zfDB.getId() + "==========="
						+ zfDB.getTitle() + "==========" + zfDB.getContext());
//				xrtextview.setText("");
//				if (zfDB != null) {
//					if (title != null) {
//						title = "";
//					}
//					title = "                                                                                   "
//							+ "" + title + zfDB.getTitle() + "" + "\n\n";
//					content = content + "\n" + title + zfDB.getContext() + "\n";
//				}
//
//				String a = ToDBC(content);
//				xrtextview.setText(a);
				dbList.add(zfDB);
			}
			adapter=new MyAdapter(dbList, getActivity());
			lvYiju.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		title = "";
		content = "";
		Log.e("slide", "JobFragment--onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.e("slide", "JobFragment--onStop");
	}
}