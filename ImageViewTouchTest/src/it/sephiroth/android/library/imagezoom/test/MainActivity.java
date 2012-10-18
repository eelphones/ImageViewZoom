package it.sephiroth.android.library.imagezoom.test;

import it.sephiroth.android.library.imagezoom.GalleryTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

import java.io.File;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private ArrayAdapter<ImageView> arrayAdapter;
	private GalleryTouch gallery;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gallery = new GalleryTouch(this);

        setContentView(gallery, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        gallery.setSpacing(20);
        arrayAdapter = new ArrayAdapter<ImageView>(this, -1) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return getItem(position);
			}
        };
        int i = 0;
		Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "date_added DESC");
		while (cursor.moveToNext() && i<10) {
			i++;
			int dataColumn = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			String filePath = cursor.getString(dataColumn);
			File file = new File(filePath);
        	ImageViewTouch imageView = new ImageViewTouch(MainActivity.this);
    		imageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			Options options = new Options();
			options.inSampleSize = 2;
    		Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
    		imageView.setImageBitmap(bitmap);
            arrayAdapter.add(imageView);
		}
		cursor.close();

        gallery.setAdapter(arrayAdapter);
    }
    
    

//    @Override
//	public boolean onTouchEvent(MotionEvent event) {
//    	ImageViewTouch imageViewTouch = (ImageViewTouch) gallery.getSelectedItem();
//    	boolean handled = imageViewTouch.onTouchEvent(event);
//    	if (handled) {
//    		return true;
//    	}
//		return super.onTouchEvent(event);
//	}



//	@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
}
