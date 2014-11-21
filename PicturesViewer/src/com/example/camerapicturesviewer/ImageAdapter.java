package com.example.camerapicturesviewer;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {

	private ArrayList<File> fileList;
	private Context context;

	public ImageAdapter(Context c, ArrayList<File> list) {
		fileList = list;
		context = c;
	}

	@Override
	public int getCount() {
		return fileList.size();
	}

	@Override
	public Object getItem(int index) {
		return fileList.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView pictureView = (ImageView) convertView;
		if (pictureView == null) {
			pictureView = new ImageView(context);
		}
		int scale = getScale();
		Picasso.with(context).load(fileList.get(position)).noFade().resize(scale, scale).centerCrop().into(pictureView);
		return pictureView;
	}

	private int getScale() {
		int orienation = context.getResources().getConfiguration().orientation;
		if (orienation == Configuration.ORIENTATION_LANDSCAPE) {
			return getScreenWeight() / 4;
		} else {
			return getScreenHeight() / 4;
		}
	}

	private int getScreenWeight() {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return metrics.widthPixels;
	}

	private int getScreenHeight() {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return metrics.heightPixels;
	}

}
