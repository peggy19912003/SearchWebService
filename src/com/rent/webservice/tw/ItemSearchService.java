package com.rent.webservice.tw;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.management.Query;


public class ItemSearchService {
	/** ���Ӱӫ~�W�٨ӷj�M
	 * 	id int					�ӫ~�y����
		name varchar(100)		���D
		describe varchar(2048)	�y�z
		account varchar			�X���H�b��
		date datetime			�W�Ǥ�
		days int				�X���Ѽ�
		money double			����
		pic varchar				�ۤ���m
		bclass varchar			�j����
		sclass varchar			�p����
	 * */
	interface DBConstant{
		String ID = "id";
		String NAME = "name";
		String DESCRIBE = "describe";
		String ACCOUNT = "account";
		String DATE = "date";
		String DAYS = "days";
		String MONEY = "money";
		String PIC = "pic";
		String BCLASS = "bclass";
		String SCLASS = "sclass";
	}
	
	public String[][] getAllItems(){
		Connection con = openDatabase();
		String QUERY = "SELECT usr_id, id, goods.name, goods.describe, account, date, days, money, pic, bclass, sclass,"
				+ "(SELECT COUNT(*) FROM goods) as Count FROM goods,usr_list WHERE usr_list.usr_account = goods.account;";
		
		String[][] itemSet = null;
		try {
			PreparedStatement prepStmt = con.prepareStatement(QUERY);
			ResultSet rs = prepStmt.executeQuery();
			
			int dataCount = 0;
			if(rs.first()){
				dataCount = Integer.valueOf(rs.getString("Count"));	
			}
			itemSet = new String[dataCount][11];
			for(int i = 0; i < dataCount; i++){
				itemSet[i][0] = rs.getString("name");
				itemSet[i][1] = rs.getString("describe");
				itemSet[i][2] = rs.getString("account");
				itemSet[i][3] = rs.getString("date");
				itemSet[i][4] = rs.getString("days");
				itemSet[i][5] = rs.getString("money");
				itemSet[i][6] = rs.getString("pic");
				itemSet[i][7] = rs.getString("bclass");
				itemSet[i][8] = rs.getString("sclass");
				itemSet[i][9] = rs.getString("id");
				itemSet[i][10] = rs.getString("usr_id");
				rs.next();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return itemSet;
	}
	
	private String[][] getItemByLIKEParams(String params, String columnName){
		Connection con = openDatabase();
		//�S�O�B�zparams���ťժ����p�A���ͤ���u��SQL�y�k
//		String QUERY = "SELECT  *,(SELECT COUNT(*) FROM goods WHERE "+columnName+" LIKE '%'||?||'%') as Count FROM goods WHERE "+columnName+" LIKE '%'||?||'%'";
//		String QUERY = "SELECT  *,(SELECT COUNT(*) FROM goods WHERE "+columnName+" = ?) as Count FROM goods WHERE "+columnName+" = ?;";
		//�h���ťաA���%���N
		params = params.replace(" ", "%");
		//String QUERY = "SELECT  *,(SELECT COUNT(*) FROM goods WHERE "+columnName+" LIKE '%"+params+"%') as Count FROM goods WHERE "+columnName+" LIKE '%"+params+"%';";
		String QUERY = "SELECT usr_id, id, goods.name, goods.describe, account, date, days, money, pic, bclass, sclass, "
				+ "(SELECT COUNT(*) FROM goods WHERE "+columnName+" LIKE '%"+params+"%') as Count FROM goods, usr_list "
				+ "WHERE "+columnName+" LIKE '%"+params+"%' AND usr_list.usr_account = goods.account;";
//		String QUERY = "SELECT *,(SELECT COUNT(*) FROM goods) as Count FROM goods;";
		//SELECT  *,(SELECT COUNT(*) FROM goods  WHERE account LIKE '%a001%') as Count FROM goods WHERE account LIKE '%a001%';
		
//		String multiParams[] = params.split(" ");
//		if(multiParams.length > 0){
//			QUERY = "SELECT  *,(SELECT COUNT(*) FROM goods WHERE ";
//			String likeString = "";
//			for(int i = 0; i < multiParams.length; i++){
//				likeString += columnName + " LIKE '%'||?||'%'";
//				if(i != multiParams.length-1)
//					likeString += " OR ";
//			}
//			QUERY += " " + likeString + " ) as Count FROM goods WHERE ";
//			QUERY += likeString;
//		}
		
		String[][] itemSet = null;
		try {
			PreparedStatement prepStmt = con.prepareStatement(QUERY);
//			if(multiParams.length > 0){
//				for(int i = 0; i < multiParams.length; i++){
//					prepStmt.setString(i+1, multiParams[i]);
//				}
//			}else{
//				prepStmt.setString(1, params);
//			}
//			prepStmt.setString(1, params);
//			prepStmt.setString(2, params);
			
			ResultSet rs = prepStmt.executeQuery();
			
			int dataCount = 0;
			if(rs.first()){
				dataCount = Integer.valueOf(rs.getString("Count"));	
			}
			itemSet = new String[dataCount][11];
			for(int i = 0; i < dataCount; i++){
				itemSet[i][0] = rs.getString("name");
				itemSet[i][1] = rs.getString("describe");
				itemSet[i][2] = rs.getString("account");
				itemSet[i][3] = rs.getString("date");
				itemSet[i][4] = rs.getString("days");
				itemSet[i][5] = rs.getString("money");
				itemSet[i][6] = rs.getString("pic");
				itemSet[i][7] = rs.getString("bclass");
				itemSet[i][8] = rs.getString("sclass");
				itemSet[i][9] = rs.getString("id");
				itemSet[i][10] = rs.getString("usr_id");
				rs.next();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return itemSet;
	}
	
	private String[][] getItemByParams(String params, String columnName){
		Connection con = openDatabase();
//		String QUERY = "SELECT  *,(SELECT COUNT(*) FROM goods WHERE "+columnName+" = '"+params+"') as Count FROM goods WHERE "+columnName+" = '"+params+"';";
		String QUERY = "SELECT usr_id, id, goods.name, goods.describe, account, date, days, money, pic, bclass, sclass, "
				+ "(SELECT COUNT(*) FROM goods WHERE "+columnName+" = '"+params+"') as Count FROM goods, usr_list "
				+ "WHERE "+columnName+" = '"+params+"' AND usr_list.usr_account = goods.account;";
		
		String[][] itemSet = null;
		try {
			PreparedStatement prepStmt = con.prepareStatement(QUERY);
//			prepStmt.setString(1, params);
//			prepStmt.setString(2, params);
			
			ResultSet rs = prepStmt.executeQuery();
			
			int dataCount = 0;
			if(rs.first()){
				dataCount = Integer.valueOf(rs.getString("Count"));	
			}
			itemSet = new String[dataCount][11];
			for(int i = 0; i < dataCount; i++){
				itemSet[i][0] = rs.getString("name");
				itemSet[i][1] = rs.getString("describe");
				itemSet[i][2] = rs.getString("account");
				itemSet[i][3] = rs.getString("date");
				itemSet[i][4] = rs.getString("days");
				itemSet[i][5] = rs.getString("money");
				itemSet[i][6] = rs.getString("pic");
				itemSet[i][7] = rs.getString("bclass");
				itemSet[i][8] = rs.getString("sclass");
				itemSet[i][9] = rs.getString("id");
				itemSet[i][10] = rs.getString("usr_id");
				rs.next();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return itemSet;
	}
	
	public String[][] getItemById(String id){
		return getItemByParams(id, DBConstant.ID);
	}
	
	public String[][] getItemByName(String itemName){
		return getItemByLIKEParams(itemName, DBConstant.NAME);
	}
	
	public String[][] getItemByDescribe(String describe){
		return getItemByLIKEParams(describe, DBConstant.DESCRIBE);
	}
	
	public String[][] getItemByAccount(String account){
		return getItemByParams(account, DBConstant.ACCOUNT);
	}
	
	public String[][] getItemByDate(String date){
		return getItemByLIKEParams(date, DBConstant.DATE);
	}
	
	public String[][] getItemByMoney(String money){
		return getItemByParams(money, DBConstant.MONEY);
	}
	
	public String[][] getItemByBClass(String bclass){
		return getItemByLIKEParams(bclass, DBConstant.BCLASS);
	}
	
	public String[][] getItemBySClass(String sclass){
		return getItemByLIKEParams(sclass, DBConstant.SCLASS);
	}
	
	public String getHasADeal(String goodsID){
		//SELECT EXISTS (SELECT * FROM webservice.records WHERE Deal_GoodsID = '1') as hasDeal;
		Connection con = openDatabase();
		String QUERY = "SELECT EXISTS (SELECT * FROM webservice.records WHERE Deal_GoodsID = '"+goodsID+"') as hasDeal;";
		
		String result = null;
		try {
			PreparedStatement prepStmt = con.prepareStatement(QUERY);
			
			ResultSet rs = prepStmt.executeQuery();
			
			int dataCount = 0;
			if(rs.first()){
				dataCount = Integer.valueOf(rs.getString("Count"));	
			}
			result = "0";
			for(int i = 0; i < dataCount; i++){
				result = rs.getString("hasDeal");
				rs.next();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private Connection openDatabase(){
		Properties props = new Properties();
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/webservice", "hsnl",
					"hsnl1130");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
		return con;
	}
}
