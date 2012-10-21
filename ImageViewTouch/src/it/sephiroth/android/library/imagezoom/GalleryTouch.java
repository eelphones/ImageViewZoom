package it.sephiroth.android.library.imagezoom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

public class GalleryTouch extends Gallery {
	
	public GalleryTouch(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GalleryTouch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GalleryTouch(Context context) {
		super(context);
	}

	// The next 2 attibutes are here in order NOT to scale/handle another image at the same gallery gesture.
	private boolean inMove = false;
	private int movedItemIndex;

	public boolean onTouchEvent(MotionEvent event) {
		int selectedItemPosition = getSelectedItemPosition();
		Log.d("", "selectedItemPosition: " + selectedItemPosition);
		int action = event.getActionMasked();
//		Log.d("", "inMove: " + inMove);
//		Log.d("", "movedItemIndex: " + movedItemIndex);
//		Log.d("", "action: " + action);
//		Log.d("", "consecutiveMisses: " + consecutiveMisses);
		if (action == MotionEvent.ACTION_MOVE && !inMove) {
			inMove = true;
			movedItemIndex = getSelectedItemPosition();
		}
		if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
			inMove = false;
//			consecutiveMisses = 2;
//			Log.d("", "consecutiveMisses changed to: " + consecutiveMisses);
		}
//		if (gallery.getSelectedItemPosition() == movedItemIndex && !inMove) {
//		if (!inGallery && gallery.getSelectedItemPosition() == movedItemIndex) {
		if (getSelectedItemPosition() == movedItemIndex) {
			int imageViewTouchId = getImageViewTouchId();
			View selectedView = getSelectedView();
			ImageViewTouch imageViewTouch;
			if (imageViewTouchId == 0) {
				imageViewTouch = (ImageViewTouch) selectedView;
			}else {
				imageViewTouch = (ImageViewTouch) selectedView.findViewById(imageViewTouchId);
			}
			boolean handled = imageViewTouch.onTouchEvent(event);
			Log.d("", "handled: " + handled);
			if (handled) {
//				consecutiveMisses = 0;
//				Log.d("", "consecutiveMisses changed to: " + consecutiveMisses);
//				this.handled = true;

				// Cancel the the gallery action if the ImgeViewTouch is working
//				event.setAction(MotionEvent.ACTION_UP);
				super.onTouchEvent(event);
				return true;
			}
//			consecutiveMisses++;
//			Log.d("", "consecutiveMisses changed to: " + consecutiveMisses);
		}
//		if (consecutiveMisses >= 1) {
//			return super.onTouchEvent(event);
//		}
//    	return false;
		return super.onTouchEvent(event);
	}

	protected int getImageViewTouchId() {
		return 0;
	}
}
