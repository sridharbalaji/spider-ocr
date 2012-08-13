package com.example.project7;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

public class folder extends Activity {
	
	private static String FOLDER=Environment.getExternalStorageDirectory().toString();
	ListView listview;
	String[] folders={};
	String[] paths1={};
	ArrayList <String>strings=new ArrayList <String>();
	ArrayList <String>paths=new ArrayList<String>();
	ArrayList <Integer> nfiles=new ArrayList<Integer>();
	private GridView gridview;
	public void onCreate(Bundle sva) {
		super.onCreate(sva);
		//setContentView(R.layout.listview);
		folderscanner(FOLDER);
	}
	public void folderscanner(String folder) {
		File folderfile=new File(folder);
		File[] files=folderfile.listFiles();
		for(File file: files) {
			if(file.canRead()&&file.isDirectory()) {
				foldercheck(file.getAbsolutePath().toString());
			}
			if(file.isFile()) {
				String folder1=file.getParentFile().getAbsolutePath().toString();
				String folder4=file.getParentFile().getName().toString();
				nfiles.add(file.getParentFile().listFiles().length);
				//System.out.println("________________");
				//System.out.println(folder1);
				strings.add(folder4);
				paths.add(folder1);
				printfolder(files.length);
				break;
			}
		}
	}
	public void foldercheck(String file1) {
		File file=new File(file1);
		File[] folderfiles=file.listFiles();
		for(File folderfile: folderfiles) {
			if(folderfile.isDirectory()&&folderfile.canRead()) {
				folderscanner(folderfile.getAbsolutePath().toString());
			}
			if(folderfile.isFile()) {
				String folder2=folderfile.getParentFile().getAbsolutePath().toString();
				String folder3=folderfile.getParentFile().getName().toString();
				
				//System.out.println(folder2);
				strings.add(folder3);
				paths.add(folder2);
				nfiles.add(folderfile.getParentFile().listFiles().length);
				printfolder(folderfiles.length);
				break;
			}
		}
	}
	public void printfolder(int nofiles) {
		//System.out.println("+++++++++++++++++++");
		//System.out.println(folder.getName());
		//Log.v("Hello", "folder contains:"+nofiles+"files");
		
		/*String[] folders=(String[]) strings.toArray(new String[strings.size()]);
		final String[] paths1=(String[]) paths.toArray(new String[paths.size()]);
		listview=(ListView)findViewById(R.id.list);
		ArrayAdapter <String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,folders);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> lista, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				String folder1=paths1[position];
				Intent i=new Intent(newfile.this,Project9Activity.class);
				i.putExtra("folder", folder1);
				startActivity(i);
				System.out.println(folder1);
			}
			
		});*/
		setContentView(R.layout.listview);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		gridview=(GridView)findViewById(R.id.gridView1);
		gridview.setAdapter(new ImageAdapter1(getApplicationContext(),nofiles));
	}
	public class ImageAdapter1 extends BaseAdapter {
			private Context mContext;
			TextView fname;
			ImageView fimage;
			String[] folders=(String[]) strings.toArray(new String[strings.size()]);
			final String[] paths1=(String[]) paths.toArray(new String[paths.size()]);
			final Integer[] Nfiles=(Integer[]) nfiles.toArray(new Integer[0]);

		public ImageAdapter1(Context c,int nufiles1) {
			this.mContext=c;
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return folders.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row=convertView;
			if(row==null) {
			LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row=inflater.inflate(R.layout.folder_list, parent,false);
			}
			fname=(TextView)row.findViewById(R.id.foldername);
			fname.setText(folders[position]+"\n"+" ("+Nfiles[position]+" )".trim());
			fimage=(ImageView)row.findViewById(R.id.imageView1);
			fimage.setScaleType(ScaleType.CENTER_CROP);
			//fimage.setLayoutParams(gridview.LayoutParams(200,200));
		Bitmap bm=BitmapFactory.decodeResource(getResources(), R.drawable.folder5);
			//Bitmap bmp=Bitmap.createBitmap(bm, 0, 0, 150, 150);
			fimage.setImageBitmap(bm);
			//ImageAdapter adapter1=new ImageAdapter(getApplicationContext());
			/*GridView gridview2=null;
			for(int i=0;i<paths1.length;i++) {
			gridview2=(GridView)row.findViewById(R.id.gridView2);
			gridview2.setAdapter(new ImageAdapter(getApplicationContext(),paths1[i]));
			}*/
			row.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String folder1=paths1[position];
					final Intent i=new Intent(folder.this,gallery.class);
					i.putExtra("folder", folder1);
					final ProgressDialog dialog = ProgressDialog.show(folder.this,"Collecting...","Please Wait!!!");
				      new Thread(){
				      public void run(){
				      try{
				          
				      }
				      catch(Exception ex){}
						startActivity(i);
						dialog.dismiss();
				      }
				      }.start();
					//System.out.println(folder1);
				}
			});

				/*@Override
				public void onItemClick(AdapterView<?> lista, View arg1,
						int position1, long arg3) {
					// TODO Auto-generated method stub
					String folder1=paths1[position];
					Intent i=new Intent(newfile.this,Project9Activity.class);
					i.putExtra("folder", folder1);
					startActivity(i);
					System.out.println(folder1);
					
				}
				
			});*/
			return row;
		}
		
	}
	/*public class ImageAdapter extends BaseAdapter {
    	private Context mContext;
    	String filename=null;
    	Vector <ImageView> mySdcardimages=new Vector <ImageView>();
    	private Integer[] mThumbsids={ };
    	ImageView imageview;
    	GridView gridview=null;
    	String[] filepath={};
    	private Drawable d;
    	public ImageAdapter(Context c,String myfile) {
    		this.mContext=c;
    		int picIndex=12345;
    	   	List<Integer> drawablesId=new ArrayList<Integer>();
    	   	List<String> filenames=new ArrayList<String>();
 	    	File sdDir=new File(myfile);
 	    	File[] sdDirfiles=sdDir.listFiles();
 	    	for(File singleFile:sdDirfiles) {
 	    		if(singleFile.getAbsolutePath().toString().endsWith(".jpg")||(singleFile.getAbsolutePath().toString().endsWith(".png"))) {
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
    			i.setLayoutParams(new GridView.LayoutParams(85,85));
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
    		return i;
    	}
    }*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        Intent i=new Intent(this,imgtotext.class);
	        startActivity(i);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}