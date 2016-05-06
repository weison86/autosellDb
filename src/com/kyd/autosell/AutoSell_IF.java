package com.kyd.autosell;

import java.util.List;

import android.content.Context;
import android.util.Log;

public class AutoSell_IF {
	private AutoSellManager mgr;  /* 数据库管理类*/

	AutoSell_IF(Context context) {
		mgr = new AutoSellManager(context);

	}

	/* 根据按键值，获取按键表的信息 */
	private Buttoninfo getButton(int key) {

		Buttoninfo button = new Buttoninfo();
		button.bnum = key;
		mgr.get(button);
//		Log.i("button", String.valueOf(button.bnum));
//		Log.i("button", String.valueOf(button.pid));
		return button;

	}

	/* 根据PID 值查询对应的货道值,返回与pid 关联的货道键表 */

	private List<Storeinfo> getStore(int pid) {

		List<Storeinfo> storeinfos;
		Storeinfo storeinfo = new Storeinfo();

		storeinfo.pid = pid;
		storeinfos = mgr.get(storeinfo);
		for (Storeinfo mstoreinfo : storeinfos) {
//			Log.i("Storeinfo id", String.valueOf(mstoreinfo.id));
//			Log.i("Storeinfo count", String.valueOf(mstoreinfo.count));
//			Log.i("Storeinfo pid", String.valueOf(mstoreinfo.pid));
		}

		return storeinfos;
	}

	private Productinfo getProduct(int pid) {
		Productinfo product = new Productinfo();
		product.id = pid;
		mgr.get(product);
//		Log.i("product id", String.valueOf(product.id));
//		Log.i("product ccode", String.valueOf(product.ccode));
//		Log.i("product price", String.valueOf(product.price));
		return product;

	}

	/*根据按键值(key),获取数据库的相关信息，得到的信息与AutoSellDb 相关联*/
	public AutoSellDb getmDb(int key) {
		AutoSellDb Db = new AutoSellDb();
		int pid;
		Db.button = getButton(key);    /* 查询与key相关的buttoninfo信息 */
		pid = Db.button.pid;          /*产品 PID*/
		Db.stores = getStore(pid);    /*根据 pid  所有对应的货道号*/
		Db.product = getProduct(pid); /*根据 pid 查询产品信息*/
		return Db;

	}

	/* 更新货道的数量和pid*/
	public void update(Storeinfo store) {
		mgr.update(store);
	}
    
	/*更新按键对应的pid*/
	public void update(Buttoninfo button) {
		mgr.update(button);
	}
    
	/* 更新 对应id 的 产品信息 */
	public void update(Productinfo product) {
		mgr.update(product);

	}

//	public AutoSellDb getmDb(int key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
