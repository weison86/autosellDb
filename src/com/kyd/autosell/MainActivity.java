package com.kyd.autosell;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {

	private AutoSellManager autosellmgr;
	private AutoSell_IF  mIF;
	private ListView listView;
	private List<Buttoninfo> buttoninfos;
	private List<Productinfo> productinfos;
	private List<Storeinfo> storeinfos;
	private Buttoninfo buttoninfo;
	private Productinfo productinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// listView = (ListView) findViewById(R.id.listView);
		Log.i("Autosellmgr", "start");
		
		mIF =  new  AutoSell_IF(this);
		AutoSellDb db = new AutoSellDb();
		
		db = mIF.getmDb(2);
		Log.i("button bnum",String.valueOf(db.button.bnum) );
//		autosellmgr = new AutoSellManager(this);

//		buttoninfo = new Buttoninfo();
//
//		buttoninfos = autosellmgr.query(buttoninfo);
//		for (Buttoninfo mbuttoninfo : buttoninfos) {
//
//			Log.i("buttoninfo bnum", String.valueOf(mbuttoninfo.bnum));
//			Log.i("buttoninfo pid", String.valueOf(mbuttoninfo.pid));
//		}
		
//		Storeinfo mstoreinfo = new Storeinfo();
//		
//		mstoreinfo.pid = 30;
//		mstoreinfo.count= 40;
//		mstoreinfo.id= 2;
//		autosellmgr.update(mstoreinfo);
//		mstoreinfo.pid = 30;
//		mstoreinfo.count= 40;
//		mstoreinfo.id= 5;
//		autosellmgr.update(mstoreinfo);
//		storeinfos = autosellmgr.get(mstoreinfo);
//		Log.i("storeinfo id", String.valueOf(mstoreinfo.id));
//		Log.i("storeinfo count",String.valueOf(mstoreinfo.count));
//		Log.i("storeinfo pid", String.valueOf(mstoreinfo.pid));
////		storeinfos = autosellmgr.query(mstoreinfo);
//		for(Storeinfo storeinfo : storeinfos)
//		{
//			Log.i("storeinfo id", String.valueOf(storeinfo.id));
//			Log.i("storeinfo count",String.valueOf(storeinfo.count));
//			Log.i("storeinfo pid", String.valueOf(storeinfo.pid));
//			
//		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
