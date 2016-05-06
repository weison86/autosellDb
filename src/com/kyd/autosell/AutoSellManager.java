package com.kyd.autosell;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class AutoSellManager {
	
	
	private SQLiteDatabase db;


	public AutoSellManager(Context context) {
		// helper = new AutoSellHelper(context);
		
		// db = helper.getWritableDatabase();
		boolean sdExist = android.os.Environment.MEDIA_MOUNTED
				.equals(android.os.Environment.getExternalStorageState());
	
		if (!sdExist) {

			Log.e("SD卡管理:", "不存在");

		} else {
			String Dir = Environment.getExternalStorageDirectory().toString();
			Log.i("SD卡路径：", Dir);
			Dir += "/autosell/autosell.db";
			db = context.openOrCreateDatabase(Dir, Context.MODE_PRIVATE, null);
		}
	}
	
	public AutoSellManager(String Dir,Context context)
	{
		db = context.openOrCreateDatabase(Dir, Context.MODE_PRIVATE, null);
		
	}

	/*
	 * 1. 对数据库中buttoninfo 表的相关操作
	 * 2. addbuttons 添加多个按键对应信息(buttoninfo 结构) 到表buttoninfo 中;
	 * 3. insert 插入一条(buttoninfo 结构)信息到表buttoninfo 中;
	 * 4. update 更新,传入一个buttoninfo 结构，更应对应bnum(按键编号)的pid（产品列表主键）;
	 * 5. delete 删除，传入一个buttoninfo 结构，删除对应bnum（按键编号）的行;
	 * 6. get    传入一个buttoninfo，获取bnum对应的pid;
     */
	/*
	 * 添加多个buttoinfo结构到表buttoinfo 中主键自动增加，传入一个List<Buttoninfo>
	 */
//	public void addbuttons(List<Buttoninfo> buttoninfos) {
//		db.beginTransaction();
//		try {
//			for (Buttoninfo buttoninfo : buttoninfos) {
//				db.execSQL("INSERT INTO buttoninfo VALUES(null,?,?)",
//						new Object[] { buttoninfo.bnum, buttoninfo.pid });
//			}
//			db.setTransactionSuccessful();
//		} finally {
//
//			db.endTransaction();
//		}
//	}
    /*插入一行buttoninfo 信息*/
	public long insert(Buttoninfo buttoninfo) {
		ContentValues cv = new ContentValues();
		cv.put("bnum", buttoninfo.bnum);
		cv.put("pid", buttoninfo.pid);
		long RowId =db.insert("buttoninfo", null, cv);
		
		return RowId;
		// db.execSQL("INSERT INTO buttoninfo VALUES (NULL,?,?)", new Object[] {
		// buttoninfo.bnum, buttoninfo.pnum});
	}

	/* 更新按键对应的产器ID(productinfo 的主键值)，Buttoninfo对应的行*/
	public void update(Buttoninfo buttoninfo) {

		ContentValues cv = new ContentValues();
		cv.put("pid", buttoninfo.pid);
		db.update("buttoninfo", cv, "bnum= ?",new String[] { String.valueOf(buttoninfo.bnum) });
	}


	public void delete(Buttoninfo buttoninfo) {
		db.delete("buttoninfo", "bnum= ?",
				new String[] { String.valueOf(buttoninfo.bnum) });
	}
	
	

//	public List<Buttoninfo> getButtons(int pid) {
//		ArrayList<Buttoninfo> buttoninfos = new ArrayList<Buttoninfo>();
//		Cursor c = db.rawQuery("SELECT * FROM buttoninfo WHERE pid = ?",
//				new String[] { String.valueOf(pid) });
//		while (c.moveToNext()) {
//			Buttoninfo buttoninfo = new Buttoninfo();
//			buttoninfo.bnum = c.getInt(c.getColumnIndex("bnum"));
//			buttoninfo.pid = c.getInt(c.getColumnIndex("pid"));
//			buttoninfos.add(buttoninfo);
//
//		}

//		return buttoninfos;
//
//	}

	/* 通过bnum值 获取 pid 的值*/
	public void get(Buttoninfo buttoninfo) {
		if (buttoninfo !=null && buttoninfo.bnum >0){
		Cursor c = db.rawQuery("SELECT * FROM buttoninfo WHERE bnum = ?",
				new String[] { String.valueOf(buttoninfo.bnum) });
		while (c.moveToNext()) {

			buttoninfo.bnum = c.getInt(c.getColumnIndex("bnum"));
			buttoninfo.pid = c.getInt(c.getColumnIndex("pid"));

		}
 
	//	return buttoninfo;
		}
	//	return null;

	}

	/*查询所有的按键列表信息(Buttoninfo)*/
	public List<Buttoninfo> query(Buttoninfo buttoninfo) {
		ArrayList<Buttoninfo> buttoninfos = new ArrayList<Buttoninfo>();
		Cursor c = queryButtonInfoCursor();
		while (c.moveToNext()) {
			
			Buttoninfo mbuttoninfo = new Buttoninfo();
			mbuttoninfo.bnum = c.getInt(c.getColumnIndex("bnum"));
			mbuttoninfo.pid = c.getInt(c.getColumnIndex("pid"));
			buttoninfos.add(mbuttoninfo);
		}
		c.close();
		return buttoninfos;
	}

	/*查询接键信息数据列表*/
	public Cursor queryButtonInfoCursor() {
		Cursor c = db.rawQuery("SELECT * FROM buttoninfo", null);
		return c;

	}

	/* productinfo 数据表的相关操作
	 * 1.insert
	 * 2.update
	 * 3.query 返回当前表的所有项
	 * 
	 */

//	public void addProducts(List<Productinfo> productinfos) {
//		db.beginTransaction();
//		try {
//			for (Productinfo productinfo : productinfos) {
//				db.execSQL(
//						"INSERT INTO productinfo VALUES(null,?,?,?,?,?,?,?)",
//						new Object[] { productinfo.ccode, productinfo.bcode,
//								productinfo.dcode, productinfo.ptype,
//								productinfo.name, productinfo.price,
//								productinfo.img });
//			}
//			db.setTransactionSuccessful();
//		} finally {
//
//			db.endTransaction();
//		}
//	}
	/*插入一条产品信息*/
	public long insert(Productinfo productinfo) {
		ContentValues cv = new ContentValues();
		// cv.put("id", productinfo.id);
		cv.put("ccode", productinfo.ccode);
		cv.put("bcode", productinfo.bcode);
		cv.put("dcode", productinfo.dcode);
		cv.put("ptype", productinfo.ptype);
		cv.put("name", productinfo.name);
		cv.put("price", productinfo.price);
		cv.put("img", productinfo.img);
		long Rowid = db.insert("productinfo", null, cv);
		return Rowid;
		// db.execSQL("INSERT INTO buttoninfo VALUES (NULL,?,?)", new Object[] {
		// buttoninfo.bnum, buttoninfo.pnum});
		// return id;

		// db.execSQL("insert into productinfo(ccode,bcode,dcode,ptype,name,price,img) values(?,?,?,?,?,?,?) ",
		// new Object[]{productinfo.ccode,productinfo.bcode,productinfo.dcode,
		// productinfo.ptype,productinfo.price,productinfo.img});
	}
	
	/*获取对应id的产品信息*/
	public void get(Productinfo product)
	{
	
			Cursor c = db.rawQuery("SELECT * FROM productinfo WHERE id = ?",
					new String[] { String.valueOf(product.id)});
			while (c.moveToNext()) {

				 product.id = c.getInt(c.getColumnIndex("id"));
				 product.ccode = c.getString(c.getColumnIndex("ccode"));
				 product.bcode = c.getString(c.getColumnIndex("bcode"));
				 product.dcode = c.getString(c.getColumnIndex("dcode"));
				 product.ptype = c.getInt(c.getColumnIndex("ptype"));
				 product.name = c.getString(c.getColumnIndex("name"));
				 product.price = c.getInt(c.getColumnIndex("price"));
				 product.img = c.getString(c.getColumnIndex("img"));
		
	      }
	}

	/*更新对应id 的产品信息*/
	public void update(Productinfo productinfo) {

		ContentValues cv = new ContentValues();
		cv.put("ccode", productinfo.ccode);
		cv.put("bcode", productinfo.bcode);
		cv.put("dcode", productinfo.dcode);
		cv.put("ptype", productinfo.ptype);
		cv.put("name", productinfo.name);
		cv.put("price", productinfo.price);
		cv.put("img", productinfo.img);
		db.update("productinfo", cv, "id= ?",
				new String[] { String.valueOf(productinfo.id) });
	}

	
    /*删除对应id的产品信息*/
	public void delete(Productinfo productinfo) {
		db.delete("productinfo", "id= ?",
				new String[] { String.valueOf(productinfo.id) });
	}

	// public Productinfo get(Productinfo productinfo) {
	// Productinfo product = new Productinfo();
	// Cursor c = db.rawQuery("SELECT * FROM buttoninfo WHERE "
	// + "id = ? or ccode = ? or bcode = ? or dcode = ? ",
	// new String[] { String.valueOf(productinfo.id), productinfo.ccode,
	// productinfo.bcode, productinfo.dcode });
	// while (c.moveToNext()) {
	//
	// product.id = c.getInt(c.getColumnIndex("id"));
	// product.ccode = c.getString(c.getColumnIndex("ccode"));
	// product.bcode = c.getString(c.getColumnIndex("bcode"));
	// product.dcode = c.getString(c.getColumnIndex("dcode"));
	// product.ptype = c.getInt(c.getColumnIndex("ptype"));
	// product.name = c.getString(c.getColumnIndex("name"));
	// product.price = c.getInt(c.getColumnIndex("price"));
	// product.img = c.getString(c.getColumnIndex("img"));
	//
	// }
	//
	// return product;
	//
	// }

    /*查询productinfo 包含的所有产品信息*/
	public List<Productinfo> query(Productinfo productinfo){
		ArrayList<Productinfo> Productinfos = new ArrayList<Productinfo>();
		Cursor c = queryProductInfoCursor();
		while (c.moveToNext()) {
			Productinfo mproductinfo = new Productinfo();
			mproductinfo.id = c.getInt(c.getColumnIndex("id"));
			mproductinfo.ccode = c.getString(c.getColumnIndex("ccode"));
			mproductinfo.bcode = c.getString(c.getColumnIndex("bcode"));
			mproductinfo.dcode = c.getString(c.getColumnIndex("dcode"));
			mproductinfo.ptype = c.getInt(c.getColumnIndex("ptype"));
			mproductinfo.name = c.getString(c.getColumnIndex("name"));
			mproductinfo.price = c.getInt(c.getColumnIndex("price"));
			mproductinfo.img = c.getString(c.getColumnIndex("img"));
			productinfo = mproductinfo;
			Productinfos.add(productinfo);
		}
		c.close();
		return Productinfos;
	}

	public Cursor queryProductInfoCursor() {
		Cursor c = db.rawQuery("SELECT * FROM productinfo ", null);
		return c;

	}

	/*
	 * ==========================================================================
	 * =====================
	 */
	/* 库存信息和对应商品编码 */
	// public void addStoreinfo(List<Storeinfo> storeinfos) {
	// db.beginTransaction();
	// try {
	// for ( Storeinfo storeinfo: storeinfos) {
	// db.execSQL("INSERT INTO buttoninfo VALUES(?,?,?)",
	// new Object[] {storeinfo.id,storeinfo.count,storeinfo.pid });
	// }
	// db.setTransactionSuccessful();
	// } finally {
	//
	// db.endTransaction();
	// }
	// }

//	public void add(Storeinfo storeinfo) {
//
//		db.execSQL("INSERT INTO storeinfo VALUES(null,?,?)", new Object[] {
//				storeinfo.count, storeinfo.pid });
//
//	}

	public void insert(Storeinfo storeinfo) {
		ContentValues cv = new ContentValues();
		cv.put("id", storeinfo.id);
		cv.put("count", storeinfo.count);
		cv.put("pid", storeinfo.pid);
		db.insert("storeinfo", null, cv);
		// db.execSQL("INSERT INTO buttoninfo VALUES (NULL,?,?)", new Object[] {
		// buttoninfo.bnum, buttoninfo.pnum});
	}
	public void update(Storeinfo storeinfo) {

		ContentValues cv = new ContentValues();
		cv.put("count", storeinfo.count);
		cv.put("pid", storeinfo.pid);
		db.update("storeinfo", cv, "id= ?",
				new String[] { String.valueOf(storeinfo.id) });
	}

	public List<Storeinfo> get(Storeinfo storeinfo)/*通过pid 获取 storeinfo 相关的项*/
	{
		
		ArrayList<Storeinfo> storeinfos = new ArrayList<Storeinfo>();
		Cursor c = db.rawQuery("SELECT * FROM storeinfo WHERE pid = ?",
				new String[] { String.valueOf(storeinfo.pid) });
		while (c.moveToNext()) {
			Storeinfo mstoreinfo = new Storeinfo();
			mstoreinfo.id= c.getInt(c.getColumnIndex("id"));
			mstoreinfo.count= c.getInt(c.getColumnIndex("count"));
			mstoreinfo.pid  = c.getInt(c.getColumnIndex("pid"));
			storeinfo.id = mstoreinfo.id;
			storeinfo.count = mstoreinfo.count;
			storeinfo.pid = mstoreinfo.pid;
			storeinfos.add(mstoreinfo);
		}
		
		
		c.close();
		return storeinfos;
	    
		
	}
	public void delete(Storeinfo storeinfo) {
		db.delete("storeinfo", "id= ?", new String[] { String.valueOf(storeinfo.id) });
	}

	public List<Storeinfo> query(Storeinfo storeinfo) {
		ArrayList<Storeinfo> storeinfos = new ArrayList<Storeinfo>();
		Cursor c = queryStoreInfoCursor();
		while (c.moveToNext()) {
			Storeinfo mstoreinfo = new Storeinfo();
			mstoreinfo.id = c.getInt(c.getColumnIndex("id"));
			mstoreinfo.count = c.getInt(c.getColumnIndex("count"));
			mstoreinfo.pid = c.getInt(c.getColumnIndex("pid"));
			storeinfos.add(mstoreinfo);

		}
		c.close();
		return storeinfos;
	}

	public Cursor queryStoreInfoCursor() {
		Cursor c = db.rawQuery("SELECT * FROM storeinfo", null);
		return c;

	}

	public void closeDB() {
		db.close();
	}

}
