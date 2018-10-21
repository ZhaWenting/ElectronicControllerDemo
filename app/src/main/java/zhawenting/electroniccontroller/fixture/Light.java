package zhawenting.electroniccontroller.fixture;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

import zhawenting.electroniccontroller.util.LogUtil;

/**
 * @author Zhawenting(Ada)
 * @description
 * @package zhawenting.electroniccontroller.fixture
 * @date 2018/10/20 21:30
 */
public class Light {

    private CameraManager manager;
    private Camera camera = null;
    private Context context;


    public Light(Context context) {
        this.context = context;
        manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            manager.getCameraIdList();
        } catch (CameraAccessException e) {
            LogUtil.e(e.getMessage());
        }
    }


    public void lightSwitch(final boolean lightStatus) {
        if (lightStatus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    manager.setTorchMode("0", false);
                } catch (Exception e) {
                   LogUtil.e(e.getMessage());
                }
            } else {
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    manager.setTorchMode("0", true);
                } catch (Exception e) {
                    LogUtil.e(e.getMessage());
                }
            } else {
                final PackageManager pm = context.getPackageManager();
                final FeatureInfo[] features = pm.getSystemAvailableFeatures();
                for (final FeatureInfo f : features) {
                    if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        if (null == camera) {
                            camera = Camera.open();
                        }
                        final Camera.Parameters parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                    }
                }
            }
        }
    }

}
