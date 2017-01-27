package kr.co.devinside.mycamera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by DevInside on 2016. 2. 16..
 */
@SuppressWarnings("ALL")
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private  SurfaceHolder mHolder;
    private Camera mCamera;

    private String TAG = "CameraPreview";

    public CameraPreview(Context context, Camera camera) {
        super(context);

        mHolder = getHolder();
        mHolder.addCallback(this);

        mCamera = camera;
        if(mCamera != null) {
            requestLayout();
            Camera.Parameters params = mCamera.getParameters();

            List<String> focusModes = params.getSupportedFocusModes();
            if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

                mCamera.setParameters(params);
            }
        }
    }

    @Override
    public  void surfaceCreated(SurfaceHolder holder) {
        try {
            if(mCamera != null) {
                mCamera.setPreviewDisplay(holder);
                //mCamera.startPreview();
            }
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mCamera != null) {
            mCamera.stopPreview();
        }
    }

    @Override
    public  void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            return;
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}

