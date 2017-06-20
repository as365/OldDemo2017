package com.zfxc.util;

import java.util.ArrayList;
import java.util.List;

public class MyDbInfo {

	public static String account;
	public static String password;
	public static String enforType;
	public static String qx;
	public static List<String> PuResult = new ArrayList<>();

	private static String TableNames[] = { "TB_footprint", "TB_videocfg",
			"tbtempuser", "tbwebservicecfg", "tbmission", "tbzfxc", "tbzfsc",
			"tbenvironment", "tbzfgrPerson", "tbnyxczf", "tbjcxczf",
			"tbdbxczf", "tbncpzf", "tbpfkhzf", "tbzfperson" };// 表名

	private static String FieldNames[][] = {
			{ "ID", "TITLE", "TIME", "INFO", "TEMP1", "TEMP2" },
			{ "ID", "TITLE", "URL", "TEMP1", "TEMP2" },
			{ "ID", "username", "password", "stype", "time1", "time2","uqx" },
			{ "sinnerws", "soutterws", "time1", "time2" },
			{ "id", "sname", "scontent", "ddate", "sstatus", "scomment",
					"sresult", "sperson", "suser", "dexecutedate", "serverid",
					"isup", "senforcementtype", "contentFile", "county" },
			{ "id", "ddate", "scompanyname", "saddress", "scontactor",
					"stelephone", "sscjlresult", "sscjlproblem", "swzjlresult",
					"swzjlproblem", "strpresult", "strpproblem", "snysyresult",
					"snysyproblem", "sjgqresult", "sjgqproblem", "sbzbsresult",
					"sbzbsproblem", "sjcresult", "sjcproblem", "stjjresult",
					"stjjproblem", "sjdccresult", "sjdccproblem", "sclyj",
					"isup", "sshiji", "squji", "szhenji", "scunji", "sjcdw",
					"sgpsx", "sgpsy", "simages", "saudio", "svideo",
					"sadresset", "sisup" },
			{ "id", "sname", "ddate", "sfilepath", "sperson", "scomment" },
			{ "id", "ddate", "ip", "kqwd", "kqsd", "gz", "trwd", "trsd" },
			{ "Id", "MissionId", "Mobilephone", "Uname", "Status" },
			{ "Id", "nycompany", "nydate", "nyaddress", "nyperson",
					"nytelephone", "nyfullperson", "nypartperson", "nyzhong",
					"nychu", "nyyu", "nyrebumen", "nyrebmproblem", "nyrework",
					"nyreworkproblem", "nyrejilu", "nyrejlproblem",
					"nyredangan", "nyredaproblem", "nymasguifan",
					"nymasgfproblem", "nymascxml", "nymasmlproblem",
					"nymasbeian", "nymasbaproblem", "nymasguanli",
					"nymasglproblem", "nymaqiye", "nymaqyproblem", "maml",
					"mamlproblem", "mahuishou", "mahsproblem", "nysgpsx",
					"nysgpsy", "nysimages", "nysaudio", "nysvideo",
					"missionId", "missname", "missperson" },
			{ "Id", "jbjccompany", "jbjcphone", "jbzuzhang", "jbsuitong",
					"jbsjcompany", "jbsjphone", "jbfading", "jbproduct",
					"jbaddress", "wtproblem", "zgcontent", "zgzzsignature",
					"zgdate", "bjcon", "bjfzsignature", "bjdate", "fhproblem",
					"fhsjsignature", "fhdate", "jcsgpsx", "jcsgpsy",
					"jcsimages", "jcsaudio", "jcsvideo", "missionId",
					"missname", "missperson", "edate" },
			{ "Id", "sjcompany", "dbdate", "saddress", "jbdj", "jbxj",
					"hyzhong", "hychu", "hyyu", "wmlcount", "wproductcount",
					"wgzcount", "wpxcount", "wreg", "wdengji", "wdjproblem",
					"wziliao", "wzlproblem", "jgxize", "jgxzprobelm", "jgdiyu",
					"jgdyproblem", "jgbiaozhi", "jgbzproblem", "jgbeian",
					"jgbaproblem", "zcyusuan", "zcysproblem", "zcguihua",
					"zcghproblem", "zczhichi", "zczcproblem", "zckaohe",
					"zckhproblem", "zcjingyan", "yjproblem", "jcsgpsx",
					"jcsgpsy", "jcsimages", "jcsaudio", "jcsvideo",
					"missionId", "missname", "missperson" },
			{ "Id", "nccompany", "ncdate", "ncmembers", "ncaddress",
					"ncxnresult", "ncxnproblem", "ncdwresult", "ncdwproblem",
					"ncbcresult", "ncbcproblem", "nczyresult", "nczyproblem",
					"ncqjresult", "ncqjproblem", "ncwrresult", "ncwrproblem",
					"ncqqresult", "ncqqproblem", "ncglresult", "ncglproblem",
					"ncwzresult", "ncwzproblem", "nczqresult", "nczqproblem",
					"ncsxresult", "ncsxproblem", "ncdaresult", "ncdaproblem",
					"nczjresult", "nczjproblem", "nccdresult", "nccdproblem",
					"nccfresult", "nccfproblem", "ncssresult", "ncssproblem",
					"ncgfresult", "ncgfproblem", "nczfproblem", "ncsjyear",
					"ncsjmonth", "ncsjday", "ncxcyear", "ncxcmonth", "ncxcday",
					"ncgpsx", "ncsgpsy", "ncsimages", "ncsaudio", "ncsvideo",
					"missionId", "missname", "missperson" },
			{ "Id", "pfcompany", "pfdate", "pfexperts", "pftotalNum",
					"pftxtadress", "pfzhutiscore", "pfzhutiproblem",
					"pfleixingscore", "pfleixingproblem", "pfguimoscore",
					"pfguimoproblem", "pfmeiguanscore", "pfmeiguanproblem",
					"pffangbianscore", "pffangbianproblem", "pfnongcanscore",
					"pfnongcanproblem", "pffeiqiwuscore", "pffeiqiwuproblem",
					"pfchuliscore", "pfchuliproblem", "pftongzhiscore",
					"pftongzhiproblem", "pfjishuscore", "pfjishuproblem",
					"pfkongzhiscore", "pfkongzhiproblem", "pffushescore",
					"pffusheproblem", "pfzhengshuscore", "pfzhengshuproblem",
					"pfpeixunscore", "pfpeixunproblem", "pfzhiduscore",
					"pfzhiduproblem", "pfglshoucescore", "pfglshouceproblem",
					"pfzhanshiscore", "pfzhanshiproblem", "pfdaozescore",
					"pfdaozeproblem", "pfnongziscore", "pfnongziproblem",
					"pftianjianscore", "pftianjianproblem", "pfwangluoscore",
					"pfwangluoproblem", "pfshangchuanscore",
					"pfshangchuanproblem", "pfzhuisuscore", "pfzhuisuproblem",
					"pfbiaozhiscore", "pfbiaozhiproblem", "pfwendingscore",
					"pfwendingproblem", "pftongyiscore", "pftongyiproblem",
					"pfjiluscore", "pfjiluproblem", "pfshangbiaoscore",
					"pfshangbiaoproblem", "pfdymoshiscore", "pfdymoshiproblem",
					"pfbaodaoscore", "pfbaodaoproblem", "pfgpsx", "pfsgpsy",
					"pfsimages", "pfsaudio", "pfsvideo", "missionId",
					"missname", "missperson", "pftxtjiditotalscore",
					"pftxtjiditotalproblem", "pftxthuanjingtotalscore",
					"pftxthuanjingtotalproblem", "pftxtshengchantotalscore",
					"pftxtshengchantotalproblem", "pftxtguanlitotalscore",
					"pftxtguanlitotalproblem", "pftxtjianguantotalscore",
					"pftxtjianguantotalproblem", "pftxtjingyingtotalscore",
					"pftxtjingyingtotalproblem", "pftxtxiaoshoutotalscore",
					"pftxtxiaoshoutotalproblem" },
			{ "id", "pname", "ppunit", "pzfnum", "pdate", "pzfpic", "pcardpic","zpmobilephone" }

	};// 字段名

	private static String FieldTypes[][] = {
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT","TEXT" },
			{ "VARCHAR", "VARCHAR", "VARCHAR", "VARCHAR" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT" },

			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT",
					"TEXT", "TEXT", "TEXT","TEXT" } };// 字段类型

	public MyDbInfo() {
		// TODO Auto-generated constructor stub
	}

	public static String[] getTableNames() {
		return TableNames;
	}

	public static String[][] getFieldNames() {
		return FieldNames;
	}

	public static String[][] getFieldTypes() {
		return FieldTypes;
	}

}
