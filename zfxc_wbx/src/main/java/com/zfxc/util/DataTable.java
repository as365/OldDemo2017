package com.zfxc.util;

import java.util.ArrayList;
import java.util.List;

public class DataTable {
	List<DataRow> row; 
	public DataTable() { 
		row=new ArrayList<DataRow>();
	} 
	public void add(DataRow dr){
		row.add(dr);
		
		return ;
	}
	public DataTable(List<DataRow> r) { 
	row = r; 
	} 
	public List<DataRow> getRow() { 
	return row; 
	} 
	public void setRow(List<DataRow> row) { 
	this.row = row; 
	} 
	/** 
	* @�������� ˫������������ֶ����ӣ�Ҫ������������ֶΣ�����ÿһ����ݹ����ֶ���� ��dt1��Ӧcolname1 ,dt2 
	* ��ӦcolName2 
	* @���ܵĴ��� δ��� 
	* @���� ���� 
	* @�޸�˵�� 
	* @�޸��� 
	*/ 
	public static DataTable joinTable(DataTable dt1, DataTable dt2, String colName1, 
	String colName2) { 
	List<DataRow> newRows = new ArrayList<DataRow>(); 
	List<DataRow> rows1 = dt1.getRow(); 
	List<DataRow> rows2 = dt2.getRow(); 
	int i1 = rows1.size(); 
	int i2 = rows2.size(); 
	List<DataRow> temp = new ArrayList<DataRow>(); 
	String tempC = ""; 
	if (i1 > i2) { 
	temp = rows1; 
	rows1 = rows2; 
	rows2 = temp; 
	tempC = colName1; 
	colName1 = colName2; 
	colName2 = tempC; 
	} 
	for (DataRow r1 : rows1) { 
	String value1 = r1.eval(colName1); 
	for (DataRow r2 : rows2) { 
	String value2 = r2.eval(colName2); 
	if (value1.equals(value2)) { 
	List<DataColumn> cols = new ArrayList<DataColumn>(); 
	for (DataColumn c : r1.getCol()) { 
	cols.add(c); 
	} 
	for (DataColumn c : r2.getCol()) { 
	cols.add(c); 
	} 
	DataRow rr = new DataRow(cols); 
	newRows.add(rr); 
	} 
	} 
	} 
	DataTable dt = new DataTable(newRows); 
	return dt; 
	} 
	public static void outTable(DataTable dt) { 
	for (DataRow r : dt.getRow()) { 
	for (DataColumn c : r.getCol()) { 
	System.out.print(c.getKey() + ":" + c.getValue() + " "); 
	} 
	wl(""); 
	} 
	} 
	public static void wl(String s) { 
	System.out.println(s); }
}
