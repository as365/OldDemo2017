/**
 * 
 */
package com.zfxc.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


import com.baidu.mapapi.search.core.PoiInfo.POITYPE;
import com.example.zfxc_wbx.R;
import com.zfxc.entity.Mp3Info;
import com.zfxc.entity.ZFPerson;
import com.zfxc.util.AppConstant;
import com.zfxc.util.DataTable;
import com.zfxc.util.FileUtils;
import com.zfxc.util.HttpDownloader;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author dell
 * 
 */
public class PersonFragment extends Fragment {

	private View view;
	private ImageView headImg;
	private ImageView cardImg;
	private TextView tvName;
	private TextView tvUnit;
	private TextView tvNum;
	private TextView tvDate;
	private TextView tvPhone;
	private MyDbHelper myDbHelper;
	private ZFPerson person;
	private FileUtils fileUtils;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				String getpath= Environment.getExternalStorageDirectory().getPath()+"/zfxc_wbx/pic/";
				 Bitmap headBmp=getLoacalBitmap(getpath+person.getPzfpic().substring(8));
				 Log.e("aaaa","================"+person.getPzfpic().substring(8));
					jianqie(headBmp);
					tvName.setText(person.getPname());
					tvUnit.setText(person.getPpunit());
					tvNum.setText(person.getPzfnum());
					tvDate.setText(person.getPdate());
					tvPhone.setText("执行人电话: "+person.getPphone());
					Bitmap cardBmp=getLoacalBitmap(getpath+person.getPcardpic().substring(8));
					cardImg.setImageBitmap(cardBmp);
				break;
			case 2:
				
				break;
			case 3:
				break;
			case -1:
				break;
			}
		}

	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.person_fragment, null);
		headImg = (ImageView) view.findViewById(R.id.per_person_img);
		cardImg=(ImageView) view.findViewById(R.id.per_zf_img);
		tvName=(TextView) view.findViewById(R.id.per_name);
		tvUnit=(TextView) view.findViewById(R.id.per_unit);
		tvNum=(TextView) view.findViewById(R.id.per_cardnum);
		tvDate=(TextView) view.findViewById(R.id.per_carddate);
		tvPhone=(TextView) view.findViewById(R.id.per_phone);
		myDbHelper = MyDbHelper.getInstance(getActivity());
		
		
		
		DataHasRONo();
		// 鑾峰彇鎵�鏈夎褰�
		return view;
	}
	private void DataHasRONo(){
		String path= Environment.getExternalStorageDirectory().getPath()+"/zfxc_wbx/pic/";
		fileUtils = new FileUtils();
		myDbHelper.open();
		Cursor c=null;
		String sql="select * from tbzfPerson where zpmobilephone='"
				+ MyDbInfo.account + "'";
		c=myDbHelper.select(sql);
		c.moveToFirst();
		if(c.getCount()!=0){
			boolean flag=fileIsExists(path+c.getString(5).toString().substring(8))&&fileIsExists(path+c.getString(6).toString().substring(8));
			if(flag==true){
				Bitmap headBmp=getLoacalBitmap(path+c.getString(5).toString().substring(8));
				jianqie(headBmp);
				tvName.setText(c.getString(1).toString());
				tvUnit.setText(c.getString(2).toString());
				tvNum.setText(c.getString(3).toString());
				tvDate.setText(c.getString(4).toString());
				tvPhone.setText("执行人电话: "+c.getString(7).toString());
				Bitmap cardBmp=getLoacalBitmap(path+c.getString(6).toString().substring(8));
				cardImg.setImageBitmap(cardBmp);
			}else{
				getData();
				DownloadThread downloadThread = new DownloadThread(null);
				Thread thread = new Thread(downloadThread);
				thread.start();
			}
			
//			headImg.setImageBitmap(bmp);
		}else{
		
			getData();
			DownloadThread downloadThread = new DownloadThread(null);
			Thread thread = new Thread(downloadThread);
			thread.start();
			
		}
		if(c!=null){
			c.close();
			myDbHelper.close();
		}
		myDbHelper.close();
	}
	private void getData() {
		String path= Environment.getExternalStorageDirectory().getPath()+"/zfxc_wbx/pic/";
		String result=null;
		DataTable dt = null;
		HashMap<String, String> hm = new HashMap<String, String>();
		String susername = MyDbInfo.account;
		String spassword = MyDbInfo.password;
		WebServiceJC.susername = MyDbInfo.account;
		String sql1 = "select * from tbzfPerson where zpmobilephone='"
				+ susername + "'";
		hm.put("username", susername);
		hm.put("password", spassword);
		hm.put("sql", sql1);
		WebServiceJC.setUrl("inner", myDbHelper);
		if (hm != null) {

			result = WebServiceJC.WorkJC("downloadinfo", hm);

		}
		try {
			myDbHelper.open();
			dt = WebServiceJC.getChannelListzfxc(result);
			
				person=new ZFPerson();
				person.setPname(dt.getRow().get(0).getColumn("zpname").toString());
				person.setPpunit(dt.getRow().get(0).getColumn("zpunit").toString());
				person.setPzfnum(dt.getRow().get(0).getColumn("zpenforcementnumber").toString());
				person.setPdate(dt.getRow().get(0).getColumn("zpvalid").toString());
				person.setPzfpic(dt.getRow().get(0).getColumn("zpPic").toString());
				person.setPcardpic(dt.getRow().get(0).getColumn("zpCardPic").toString());
				person.setPphone(dt.getRow().get(0).getColumn("zpmobilephone").toString());
				Log.e("aaaa","======="+person.getPname()+"=="+person.getPpunit()+"=="+person.getPzfnum()+"=="+
						person.getPdate()+"=="+person.getPzfpic()+"=="+person.getPcardpic());
				String fields[] = { "pname", "ppunit", "pzfnum", "pdate", "pzfpic", "pcardpic","zpmobilephone" };
				String values[] = { "", "", "", "","","","" };
				values[0]=person.getPname();
				values[1]=person.getPpunit();
				values[2]=person.getPzfnum();
				values[3]=person.getPdate();
				values[4]=person.getPzfpic();
				values[5]=person.getPcardpic();
				values[6]=person.getPphone();
				myDbHelper.insert(MyDbInfo.getTableNames()[14], fields, values);
				myDbHelper.close();
				
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 /**
	    * 鍔犺浇鏈湴鍥剧墖
	    * @param url
	    * @return
	    */
	    public static Bitmap getLoacalBitmap(String url) {
	         try {
	              FileInputStream fis = new FileInputStream(url);
	              return BitmapFactory.decodeStream(fis);  ///鎶婃祦杞寲涓築itmap鍥剧墖        

	           } catch (FileNotFoundException e) {
	              e.printStackTrace();
	              return null;
	         }
	    }
	private void jianqie(Bitmap bmp) {
		// 瑁佸壀鍥剧墖
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
//		BitmapFactory.decodeResource(getResources(), R.drawable.duyanmei,
//				options);
		Log.d("aaa", "original outwidth: " + options.outWidth);
		// 姝ゅ搴︽槸鐩爣ImageView甯屾湜鐨勫ぇ灏忥紝浣犲彲浠ヨ嚜瀹氫箟ImageView锛岀劧鍚庤幏寰桰mageView鐨勫搴︺��
		int dstWidth = 135;
		// 鎴戜滑闇�瑕佸姞杞界殑鍥剧墖鍙兘寰堝ぇ锛屾垜浠厛瀵瑰師鏈夌殑鍥剧墖杩涜瑁佸壀
		int sampleSize = calculateInSampleSize(options, dstWidth, dstWidth);
		options.inSampleSize = sampleSize;
		options.inJustDecodeBounds = false;
		Log.d("aaa", "sample size: " + sampleSize);
//		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
//				R.drawable.duyanmei, options);

		// 缁樺埗鍥剧墖
		Bitmap resultBmp = Bitmap.createBitmap(dstWidth, dstWidth,
				Bitmap.Config.ARGB_8888);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		Canvas canvas = new Canvas(resultBmp);
		// 鐢诲渾
		canvas.drawCircle(dstWidth / 2, dstWidth / 2, dstWidth / 2, paint);
		// 閫夋嫨浜ら泦鍘讳笂灞傚浘鐗�
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bmp, new Rect(0, 0, bmp.getWidth(), bmp.getHeight()),
				new Rect(0, 0, dstWidth, dstWidth), paint);
		headImg.setImageBitmap(resultBmp);
		bmp.recycle();
	}

	private int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}
	class DownloadThread implements Runnable{
		private Mp3Info mp3Info = null;
		public DownloadThread(Mp3Info mp3Info){
			this.mp3Info = mp3Info;
		}
		@Override
		public void run() {
			
			try{
				
				myDbHelper.open();
				Cursor c=null;
				try {
////					Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
////							MyDbInfo.getFieldNames()[0], null, null, null, null, "TIME desc, id desc","0,9");
//					String sql="select * from tbzfPerson where zpmobilephone='"
//				+ MyDbInfo.account + "'";
//					c = myDbHelper.select(sql);				
					
//					while (c.moveToNext()) {
						String name=person.getPzfpic().substring(8);
						String name1=person.getPcardpic().substring(8);
				
						String mp3Url = "";
						
						mp3Url=AppConstant.URL.PIC_URL+name;
						 String path= Environment.getExternalStorageDirectory().getPath()+"/zfxc_wbx/pic/"+name;
						 File f=new File(path);
						 if(!f.exists()){
						HttpDownloader httpDownloader = new HttpDownloader();
					//	Toast.makeText(DownloadService.this, mp3Url, Toast.LENGTH_LONG).show();
						int mp3Result = httpDownloader.downFile(mp3Url, "zfxc_wbx/pic/",name);
						 }
						 mp3Url=AppConstant.URL.PIC_URL+name1;
						 String path1= Environment.getExternalStorageDirectory().getPath()+"/zfxc_wbx/pic/"+name1;
						 File f1=new File(path1);
						 if(!f1.exists()){
						HttpDownloader httpDownloader = new HttpDownloader();
					//	Toast.makeText(DownloadService.this, mp3Url, Toast.LENGTH_LONG).show();
						int mp3Result = httpDownloader.downFile(mp3Url, "zfxc_wbx/pic/",name1);
						 }
						
//						mp3Url=AppConstant.URL.BASE_URL+"zfsc20151027022147.doc";
//						HttpDownloader httpDownloader = new HttpDownloader();
//					
//						int mp3Result = httpDownloader.downFile(mp3Url, "zfxc/", "zfsc20151027022147.doc");
//					}
						 mHandler.sendEmptyMessage(1);
					c.close();
					myDbHelper.close();
					

				} catch (Exception e) {
					myDbHelper.close();
				}finally{
					if(c!=null){
						c.close();
					}
					
					myDbHelper.close();
					
				}
			//String mp3Url = AppConstant.URL.BASE_URL + mp3Info.getMp3Name();
			
	}catch(Exception e){
				
			}

		}
		
	}
	//鍒ゆ柇鏂囦欢鏄惁瀛樺湪  
    public boolean fileIsExists(String strFile)  
    {  
        try  
        {  
            File f=new File(strFile);  
            if(!f.exists())  
            {  
                    return false;  
            }  
  
        }  
        catch (Exception e)  
        {  
            return false;  
        }  
  
        return true;  
    }  
}
