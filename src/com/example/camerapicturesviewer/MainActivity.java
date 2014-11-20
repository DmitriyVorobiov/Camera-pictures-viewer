package com.example.camerapicturesviewer;

import java.io.File;
import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.GridView;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	private static final String ATTRIBUTE = "image/*";
	@ViewById(R.id.gridView)
	protected GridView picturesGridView;

	@ItemClick
	public void gridViewItemClicked(File selectedFile) {
		openFile(selectedFile);
	}

	@AfterViews
	protected void initViewComponents() {
		picturesGridView.setAdapter(new ImageAdapter(this, getFilelist()));
		picturesGridView.setNumColumns(getNumColumns());
	}

	private ArrayList<File> getFilelist() {
		File[] fullFileList = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).listFiles();
		ArrayList<File> goalFileList = new ArrayList<File>();
		for (File file : fullFileList) {
			if (file.getName().endsWith(".jpg")) {
				goalFileList.add(file);
			}
		}
		return goalFileList;
	}

	private void openFile(File file) {
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), ATTRIBUTE);
		startActivity(intent);
	}

	private int getNumColumns() {
		int count = 0;
		switch (this.getResources().getConfiguration().orientation) {
		case Configuration.ORIENTATION_LANDSCAPE: {
			Log.d("MainActivity", String.valueOf(getFilelist().size()));
			count = (getFilelist().size() / 2) + 1;

		}
			break;
		case Configuration.ORIENTATION_PORTRAIT: {
			count = (getFilelist().size() / 4) + 1;
		}
			break;
		}
		Log.d("MainActivity", String.valueOf(count));
		return count;
	}

}
