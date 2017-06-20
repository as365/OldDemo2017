package com.zfxc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

@SuppressLint("NewApi")
public class DatePickerFragment extends DialogFragment implements
DatePickerDialog.OnDateSetListener {
	private EditText etdate;
	private String dateinitial="";
public String getDateinitial() {
		return dateinitial;
	}
	public void setDateinitial(String dateinitial) {
		this.dateinitial = dateinitial;
	}
public EditText getEtdate() {
		return etdate;
	}
	public void setEtdate(EditText etdate) {
		this.etdate = etdate;
	}
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
final Calendar c = Calendar.getInstance();

int year ;
int month ;
int day ;
year = c.get(Calendar.YEAR);
month = c.get(Calendar.MONTH);
day = c.get(Calendar.DAY_OF_MONTH);
try{
	
	year = Integer.parseInt(dateinitial.split("-")[0]);
	 month = Integer.parseInt(dateinitial.split("-")[1])-1;
	 day =Integer.parseInt(dateinitial.split("-")[2]);
}catch(Exception e){
	
}


return new DatePickerDialog(getActivity(), this, year, month, day);
}
public void showResult(){
	
}
@Override
public void onDateSet(DatePicker view, int year, int month, int day) {
Log.d("OnDateSet", "select year:"+year+";month:"+month+";day:"+day);
SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
Date date=null;
try {
	 date=format.parse(year+"-"+(month+1)+"-"+day);
} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
etdate.setText(format.format(date));
}
}