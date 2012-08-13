package com.example.project7;

import java.io.File;
import com.example.project7.sricrop.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class gallery extends Activity {
    /** Called when the activity is first created. */
	Vector <ImageView> mySdcardimages=new Vector <ImageView>();
	private Integer[] mThumbsids={ };
	String myfile;
	ImageView imageview;
	GridView gridview=null;
	String[] filepath={};
	private static Drawable d;
	int width;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        Intent intent1=getIntent();
	    myfile=intent1.getStringExtra("folder");
	    File myfile1=new File(myfile);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().setBackgroundDrawableResource(R.drawable.browse);
    	gridview=(GridView) findViewById (R.id.gridview);
    	gridview.setAdapter(new ImageAdapter(getApplicationContext()));
    	
    	Display display=getWindowManager().getDefaultDisplay();
    	width=display.getWidth();
    	width=(width-30)/3;
    	
    	/* gridview.setOnItemClickListener(new OnItemClickListener() {
    		 public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
    			setContentView(R.layout.image);
    			imageview=(ImageView)findViewById(R.id.image1);
    			imageview.setMinimumHeight(540);
    			imageview.setMinimumWidth(480);
    			imageview.setImageDrawable(mySdcardimages.get(position).getDrawable()); 
    			
    		 }
   
    	});*/
    } 
    public class ImageAdapter extends BaseAdapter {
    	private Context mContext;
    	String filename=null;
    	public ImageAdapter(Context c) {
    		this.mContext=c;
    		int picIndex=12345;
    	   	List<Integer> drawablesId=new ArrayList<Integer>();
    	   	List<String> filenames=new ArrayList<String>();
 	    	File sdDir=new File(myfile);
 	    	File[] sdDirfiles=sdDir.listFiles();
 	    	for(File singleFile:sdDirfiles) {
 	    		if(singleFile.getAbsolutePath().toString().endsWith(".jpg")||(singleFile.getAbsolutePath().toString().endsWith(".png")||singleFile.getAbsolutePath().toString().endsWith(".gif")||singleFile.getAbsolutePath().toString().endsWith("bmp"))) {
 	    		filename=singleFile.getAbsolutePath().toString();
 	    		
 	    			ImageView myImageView=new ImageView(c);
 	    		myImageView.setImageDrawable(Drawable.createFromPath(filename));
 	    		myImageView.setId(picIndex);
 	    		picIndex++;
 	    		filenames.add(filename);
 	    		drawablesId.add(myImageView.getId());
 	    		mySdcardimages.add(myImageView);
 	    		}
 	    	}
 	    	mThumbsids=(Integer[])drawablesId.toArray(new Integer[0]);
 	    	filepath=(String[])filenames.toArray(new String[0]);
    	}
    	public int getCount() {
    		return mThumbsids.length;
    	}
    	public Object getItem(int position) {
    		return mThumbsids[position];
    	}
    	public long getItemId(int position) {
    		return position;
    	}
   
    	public View getView(final int position,View convertView,ViewGroup parent) {
    		ImageView i=new ImageView(mContext);
    		if(convertView==null) {
    			i.setLayoutParams(new GridView.LayoutParams(width,width));
    			i.setScaleType(ImageView.ScaleType.CENTER_CROP);
    			i.setPadding(8, 8, 8, 8);
    		}
    		else {
    			i=(ImageView) convertView;
    		}
    		d=mySdcardimages.get(position).getDrawable();
    		//Bitmap bmp=BitmapFactory.decodeFile(filepath[position]);
    		//i.setImageBitmap(bmp);
    		//if(d==null) {
    		i.setImageDrawable(d);
    		//}
    		//((ViewGroup) parent).removeAllViews();
    		//final int w=i.getWidth();
			//final int h=i.getHeight();
    		i.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					/*setContentView(R.layout.image);
					
	    			imageview=(ImageView)findViewById(R.id.image1);
	    			imageview.setMinimumHeight(h);
	    			imageview.setMinimumWidth(w);
	    			imageview.setImageDrawable(d);*/
					Intent i=new Intent(gallery.this,sricrop.class);
					i.putExtra("filename", filepath[position]);
					i.putExtra("myfile", myfile);
					startActivity(i);
				}
    			
    		});
    		return i;
    	}
    }
}