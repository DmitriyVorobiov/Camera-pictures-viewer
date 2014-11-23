package com.example.camerapicturesviewer;

import java.io.File;
import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.jess.ui.TwoWayAdapterView;
import com.jess.ui.TwoWayGridView;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity implements TwoWayAdapterView.OnItemClickListener {

	private static final String DATA_TYPE = "image/*";
	private static final String JPG_ENDING = ".jpg";
	private static final String CAMERA_FOLDER = "/camera";
	private static final int PORTRAIT_ORIENTATION_ROW_COUNT = 4;
	private static final int LANDSCAPE_ORIENTATION_ROW_COUNT = 2;

	@ViewById(R.id.gridView)
	protected TwoWayGridView picturesGridView;

	@AfterViews
	protected void initComponents() {
		picturesGridView.setAdapter(new ImageAdapter(this, getFilelist()));
		picturesGridView.setNumRows(getNumRows());
		picturesGridView.setOnItemClickListener(this);
	}

	private ArrayList<File> getFilelist() {
		File[] fullFileList = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM.concat(CAMERA_FOLDER)).listFiles();
		ArrayList<File> goalFileList = new ArrayList<File>();
		for (File file : fullFileList) {
			if (file.getName().endsWith(JPG_ENDING)) {
				goalFileList.add(file);
			}
		}
		return goalFileList;
	}

	private void openFile(File file) {
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), DATA_TYPE);
		startActivity(intent);
	}

	private int getNumRows() {
		int orienation = this.getResources().getConfiguration().orientation;
		if (orienation == Configuration.ORIENTATION_LANDSCAPE) {
			return LANDSCAPE_ORIENTATION_ROW_COUNT;
		} else {
			return PORTRAIT_ORIENTATION_ROW_COUNT;
		}

	}

	@Override
	public void onItemClick(TwoWayAdapterView<?> parent, View view, int position, long id) {
		openFile(((File) picturesGridView.getAdapter().getItem(position)));
	}
}
