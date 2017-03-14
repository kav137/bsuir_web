package by.bsuir.bsuirweb.core.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

public class PermissionChecker {

    private Context context;

    private PermissionChecker(@NonNull Context context) {
        this.context = context;
    }

    public static PermissionChecker of(@NonNull Context context) {
        return new PermissionChecker(context);
    }

    public boolean isCameraAvailable() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public  boolean isReadExternalStorageAvailable() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
}
