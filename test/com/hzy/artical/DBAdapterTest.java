package com.hzy.artical;

import com.hzy.artical.persistence.DBAdapter;

import android.test.InstrumentationTestCase;

public class DBAdapterTest extends InstrumentationTestCase {
	
	
	DBAdapter adapter;

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		adapter = new DBAdapter(this.getInstrumentation().getContext());
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	
	
	
	
}
