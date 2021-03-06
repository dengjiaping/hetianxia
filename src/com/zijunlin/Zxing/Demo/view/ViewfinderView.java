package com.zijunlin.Zxing.Demo.view;

import com.google.zxing.ResultPoint;
import com.hetianxia.activity.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author (Daniel Switkin)
 */
public final class ViewfinderView extends View {

  private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
  private static final long ANIMATION_DELAY = 100L;
  private static final int OPAQUE = 0xFF;

  private final Paint paint;
	private final Paint paintCorner;
  private Bitmap resultBitmap;
  private final int maskColor;
  private final int resultColor;
  private final int frameColor;
  private final int laserColor;
  private final int resultPointColor;
  private int scannerAlpha;
  private List<ResultPoint> possibleResultPoints;
  private List<ResultPoint> lastPossibleResultPoints;

	private Rect framingRect;
	private static final int MIN_FRAME_WIDTH = 240;
	private static final int MIN_FRAME_HEIGHT = 240;
	private static final int MAX_FRAME_WIDTH = 600;
	private static final int MAX_FRAME_HEIGHT = 400;
	int loopTop,loopBottom;
	private Context context;
	private static final int CURRENT_POINT_OPACITY = 0xA0;
	private static final int POINT_SIZE = 6;

  // This constructor is used when the class is built from an XML resource.
  public ViewfinderView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    // Initialize these once for performance rather than calling them every time in onDraw().
    paint = new Paint();
	paintCorner = new Paint(Paint.DITHER_FLAG);
    Resources resources = getResources();
    maskColor = resources.getColor(R.color.viewfinder_mask);
    resultColor = resources.getColor(R.color.result_view);
    frameColor = resources.getColor(R.color.viewfinder_frame);
    laserColor = resources.getColor(R.color.viewfinder_laser);
    resultPointColor = resources.getColor(R.color.possible_result_points);
    scannerAlpha = 0;
    possibleResultPoints = new ArrayList<ResultPoint>(5);
  }

  
  public void onDraw(Canvas canvas) {
    Rect frame = getFramingRect();
    if (frame == null) {
      return;
    }	

    int width = canvas.getWidth();
    int height = canvas.getHeight();

    // Draw the exterior (i.e. outside the framing rect) darkened
    paint.setColor(resultBitmap != null ? resultColor : maskColor);
    canvas.drawRect(0, 0, width, frame.top, paint);
    canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
    canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
    canvas.drawRect(0, frame.bottom + 1, width, height, paint);

	//这里画取景框四个角落的绿色夹角
	paintCorner.setColor(Color.GREEN);
	paintCorner.setAntiAlias(true);
	paintCorner.setStrokeWidth(5);
	canvas.drawLine(frame.left-2.5f, frame.top, frame.left+16, frame.top, paintCorner);
	canvas.drawLine(frame.left, frame.top, frame.left, frame.top+16, paintCorner);
	canvas.drawLine(frame.right-16, frame.top, frame.right+2.5f, frame.top, paintCorner);
	canvas.drawLine(frame.right, frame.top, frame.right, frame.top+16, paintCorner);
	canvas.drawLine(frame.left-2.5f, frame.bottom, frame.left+16, frame.bottom, paintCorner);
	canvas.drawLine(frame.left, frame.bottom-16, frame.left, frame.bottom, paintCorner);
	canvas.drawLine(frame.right-16, frame.bottom, frame.right+2.5f, frame.bottom, paintCorner);
	canvas.drawLine(frame.right, frame.bottom-16, frame.right, frame.bottom, paintCorner);

	if (resultBitmap != null) {
		// Draw the opaque result bitmap over the scanning rectangle
		paint.setAlpha(CURRENT_POINT_OPACITY);
		canvas.drawBitmap(resultBitmap, null, frame, paint);
	} else {

		// Draw a red "laser scanner" line through the middle to show
		// decoding is active
		paint.setColor(laserColor);
		paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
		scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
		int middle = frame.height() / 2 + frame.top;
		//绘制中间的红线
		loopTop++;
		Log.d("steven", "loopTop:"+loopTop);
		Log.d("steven", "loopBottom:"+loopBottom);
		if(loopTop>=frame.bottom){
			loopTop = frame.top;
		}
//		canvas.drawRect(frame.left + 2, loopTop - 1, frame.right - 1,loopTop + 2, paint);

		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int widthz = display.getWidth();
		int heightz = display.getHeight();
//		Rect previewFrame = cameraManager.getFramingRectInPreview();
		float scaleX = frame.width() / (float) widthz;
		float scaleY = frame.height() / (float) heightz;

		List<ResultPoint> currentPossible = possibleResultPoints;
		List<ResultPoint> currentLast = lastPossibleResultPoints;
		int frameLeft = frame.left;
		int frameTop = frame.top;
		if (currentPossible.isEmpty()) {
			lastPossibleResultPoints = null;
		} else {
			possibleResultPoints = new ArrayList<ResultPoint>(5);
			lastPossibleResultPoints = currentPossible;
			paint.setAlpha(CURRENT_POINT_OPACITY);
			paint.setColor(resultPointColor);
			synchronized (currentPossible) {
				for (ResultPoint point : currentPossible) {
					canvas.drawCircle(frameLeft+ (int) (point.getX() * scaleX), frameTop+ (int) (point.getY() * scaleY), POINT_SIZE,paint);
				}
			}
		}
		//绘制一闪一闪的黄点
		if (currentLast != null) {
			paint.setAlpha(CURRENT_POINT_OPACITY / 2);
			paint.setColor(resultPointColor);
			synchronized (currentLast) {
				float radius = POINT_SIZE / 2.0f;
				for (ResultPoint point : currentLast) {
					canvas.drawCircle(frameLeft+ (int) (point.getX() * scaleX), frameTop+ (int) (point.getY() * scaleY), radius, paint);
				}
			}
		}

		// Request another update at the animation interval, but only
		// repaint the laser line,not the entire viewfinder mask.
		//仅仅刷新中间的红线，让其一闪一闪的动画,不是整个矩形刷新
		
		loopTop++;
		//if(isRun){
			postInvalidateDelayed(ANIMATION_DELAY, frame.left - POINT_SIZE,
					frame.top - POINT_SIZE, frame.right + POINT_SIZE,frame.bottom + POINT_SIZE);
		//}
	}
	
//    if (resultBitmap != null) {
//      // Draw the opaque result bitmap over the scanning rectangle
//      paint.setAlpha(OPAQUE);
//      canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
//    } else {
//
//      // Draw a two pixel solid black border inside the framing rect
//      paint.setColor(frameColor);
//      canvas.drawRect(frame.left, frame.top, frame.right + 1, frame.top + 2, paint);
//      canvas.drawRect(frame.left, frame.top + 2, frame.left + 2, frame.bottom - 1, paint);
//      canvas.drawRect(frame.right - 1, frame.top, frame.right + 1, frame.bottom - 1, paint);
//      canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1, frame.bottom + 1, paint);
//
//      // Draw a red "laser scanner" line through the middle to show decoding is active
//      paint.setColor(laserColor);
//      paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
//      scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
//      int middle = frame.height() / 2 + frame.top;
//      canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
//
//      Collection<ResultPoint> currentPossible = possibleResultPoints;
//      Collection<ResultPoint> currentLast = lastPossibleResultPoints;
//      if (currentPossible.isEmpty()) {
//        lastPossibleResultPoints = null;
//      } else {
//        possibleResultPoints = new HashSet<ResultPoint>(5);
//        lastPossibleResultPoints = currentPossible;
//        paint.setAlpha(OPAQUE);
//        paint.setColor(resultPointColor);
//        for (ResultPoint point : currentPossible) {
//          canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 6.0f, paint);
//        }
//      }
//      if (currentLast != null) {
//        paint.setAlpha(OPAQUE / 2);
//        paint.setColor(resultPointColor);
//        for (ResultPoint point : currentLast) {
//          canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 3.0f, paint);
//        }
//      }
//
//      // Request another update at the animation interval, but only repaint the laser line,
//      // not the entire viewfinder mask.
//      postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
//    }
  }

  public void drawViewfinder() {
    resultBitmap = null;
    invalidate();
  }

  /**
   * Draw a bitmap with the result points highlighted instead of the live scanning display.
   *
   * @param barcode An image of the decoded barcode.
   */
  public void drawResultBitmap(Bitmap barcode) {
    resultBitmap = barcode;
    invalidate();
  }

  public void addPossibleResultPoint(ResultPoint point) {
    possibleResultPoints.add(point);
  }
	public synchronized Rect getFramingRect() {
		if (framingRect == null) {
			WindowManager manager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = manager.getDefaultDisplay();
			int widthz = display.getWidth();
			int heightz = display.getHeight();
			int width = widthz * 3 / 4;
			if (width < MIN_FRAME_WIDTH) {
				width = MIN_FRAME_WIDTH;
			} else if (width > MAX_FRAME_WIDTH) {
				width = MAX_FRAME_WIDTH;
			}
			// int height = screenResolution.y * 3 / 4;
			int height = width;
			if (height < MIN_FRAME_HEIGHT) {
				height = MIN_FRAME_HEIGHT;
			} else if (height > MAX_FRAME_HEIGHT) {
				height = MAX_FRAME_HEIGHT;
			}
			int leftOffset = (widthz - width) / 2;
			// int topOffset = (screenResolution.y - height) / 2;
			int topOffset = (heightz - width) / 2;
			framingRect = new Rect(leftOffset, topOffset, leftOffset + width,
					topOffset + height);
		}
		return framingRect;
	}

}
