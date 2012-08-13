package com.example.project7;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.hardware.Camera;
import android.hardware.Camera.OnZoomChangeListener;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project7.sricrop.sricrop;

public class camera1 extends Activity implements OnZoomChangeListener {

  private SurfaceView preview=null;
  private SurfaceHolder previewHolder=null;
  private Camera camera=null;
  private boolean inPreview=false;
  ImageView zin,zout;
  Bitmap bmp,itembmp;
  static Bitmap mutableBitmap;
  PointF start = new PointF();
  PointF mid = new PointF();
  float oldDist = 1f;
  File imageFileName = null;
  File imageFileFolder = null;
  private MediaScannerConnection msConn;
  Display d;
  int screenhgt,screenwdh;    
  ProgressDialog dialog;
  int zoomValue=0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFormat(PixelFormat.TRANSLUCENT);
    setContentView(R.layout.camera1);

    zin=(ImageView)findViewById(R.id.zoomin);
    zout=(ImageView)findViewById(R.id.zoomout);
    preview=(SurfaceView)findViewById(R.id.surface);

    previewHolder=preview.getHolder();
    previewHolder.addCallback(surfaceCallback);
    previewHolder.getSurface();
    previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    previewHolder.setFixedSize(getWindow().getWindowManager()
    .getDefaultDisplay().getWidth(), getWindow().getWindowManager()
    .getDefaultDisplay().getHeight());
    
    zin.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			setZoomIn();
		}
    	
    });
    zout.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			setZoomOut();
		}

		private void setZoomOut() {
			// TODO Auto-generated method stub
			try{
		        Camera.Parameters params = camera.getParameters();
		        if(params.isZoomSupported()) {
		        zoomValue -=5;
		        params.setZoom(zoomValue);
		        //System.out.println(params.getZoom());
		        //System.out.println(params.getMaxZoom());
		        camera.setParameters(params);
		        //Log.d(TAG, "Is support Zoom " + params.isZoomSupported());
		        }
		    }catch (Exception e) {
		        e.printStackTrace();
		    }
		}
    	
    });


  }


  @Override
  public void onResume() {
  super.onResume();
  camera=Camera.open();
  }

  @Override
  public void onPause() {
  if (inPreview) {
  camera.stopPreview();
  }

  camera.release();
  camera=null;
  inPreview=false;
  super.onPause();
  }

  private Camera.Size getBestPreviewSize(int width, int height,Camera.Parameters parameters){
  Camera.Size result=null;
  for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
  if (size.width<=width && size.height<=height) {
  if (result==null) {
  result=size;
  }
  else {
  int resultArea=result.width*result.height;
  int newArea=size.width*size.height;
  if (newArea>resultArea) {
  result=size;
  }
  }
  }
  }
  return(result);
  }

  SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback(){
    public void surfaceCreated(SurfaceHolder holder) {
      try {
        camera.setPreviewDisplay(previewHolder);
      }
      catch (Throwable t) {
        //Log.e("PreviewDemo-surfaceCallback",
              //"Exception in setPreviewDisplay()", t);
        Toast
          .makeText(camera1.this, t.getMessage(), Toast.LENGTH_LONG)
          .show();
      }
    }

    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width,
                               int height) {
      Camera.Parameters parameters=camera.getParameters();
      Camera.Size size=getBestPreviewSize(width, height,
                                          parameters);

      if (size!=null) {
      parameters.setPreviewSize(size.width, size.height);
      parameters.setPictureFormat(PixelFormat.JPEG);
      if(getWindowManager().getDefaultDisplay().getOrientation()==Configuration.ORIENTATION_PORTRAIT) {
    	  camera.setDisplayOrientation(90);
      }
      camera.setParameters(parameters);
      camera.startPreview();
      inPreview=true;
      }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
      // no-op
    }
  };


      Camera.PictureCallback photoCallback=new Camera.PictureCallback(){
      public void onPictureTaken(final byte[] data, final Camera camera){
      dialog=ProgressDialog.show(camera1.this,"","Saving Photo");
      new Thread(){
      public void run(){
      try{
          
      }
      catch(Exception ex){}
      onPictureTake(data,camera);     
      }
      }.start();      
      }
      };



      public void onPictureTake(byte[] data, Camera camera){

      bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
      //mutableBitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
      savePhoto(bmp);
      dialog.dismiss();
      }



      /*class SavePhotoTask extends AsyncTask<byte[], String, String> {
      @Override
      protected String doInBackground(byte[]... jpeg) {
      File photo=new File(Environment.getExternalStorageDirectory(),"photo.jpg");
      if (photo.exists()){
      photo.delete();
      }
      try {
      FileOutputStream fos=new FileOutputStream(photo.getPath());     
      fos.write(jpeg[0]);
      fos.close();
      }
      catch (java.io.IOException e) {
      //Log.e("PictureDemo", "Exception in photoCallback", e);
      }
      return(null);
      }
      }*/


public void savePhoto(Bitmap bmp)
{
imageFileFolder = new File(Environment.getExternalStorageDirectory(),"SriProject");
if(!imageFileFolder.exists()) {
imageFileFolder.mkdirs();
}
FileOutputStream out = null;
Calendar c = Calendar.getInstance();
String date = fromInt(c.get(Calendar.MONTH))
            + fromInt(c.get(Calendar.DAY_OF_MONTH))
            + fromInt(c.get(Calendar.YEAR))
            + fromInt(c.get(Calendar.HOUR_OF_DAY))
            + fromInt(c.get(Calendar.MINUTE))
            + fromInt(c.get(Calendar.SECOND));
String date1=date.toString();
imageFileName = new File(imageFileFolder, date1 + ".jpg");
try
{
 out = new FileOutputStream(imageFileName);
 bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
 out.flush();
 out.close();
 scanPhoto(imageFileName.toString());
 out = null;
} catch (Exception e)
{
e.printStackTrace();
}

}

public String fromInt(int val)
{
return String.valueOf(val);
}

public void scanPhoto(final String imageFileName)
{
msConn = new MediaScannerConnection(camera1.this,new MediaScannerConnectionClient()
{
public void onMediaScannerConnected()
{
msConn.scanFile(imageFileName, null);
//Log.i("msClient obj  in Photo Utility","connection established");
}
public void onScanCompleted(String path, Uri uri)
{
msConn.disconnect();
//Log.i("msClient obj in Photo Utility","scan completed");

}
});
msConn.connect();
Intent i=new Intent(camera1.this,sricrop.class);
i.putExtra("filename", imageFileName);
i.putExtra("back", "camera");
startActivity(i);
}

@Override
public boolean onKeyDown(int keyCode, KeyEvent event){
if ( keyCode == KeyEvent.KEYCODE_MENU&& event.getRepeatCount() == 0)
{
onBack();
}
return super.onKeyDown(keyCode, event);
}

public void onBack(){
//Log.e("onBack :","yes");
camera.takePicture(null,null,photoCallback);
inPreview=false;
}


@Override
public void onZoomChange(int arg0, boolean arg1, Camera arg2) {
	// TODO Auto-generated method stub
	
}

public void setZoomIn(){
    try{
    	
        Camera.Parameters params = camera.getParameters();
        
        if(params.isZoomSupported()) {
        	zoomValue +=5;
        params.setZoom(zoomValue);
        //System.out.println(params.getZoom());
        //System.out.println(params.getMaxZoom());
        camera.setParameters(params);
        }
        //Log.d(TAG, "Is support Zoom " + params.isZoomSupported());
    }catch (Exception e) {
        e.printStackTrace();
    }
}



}