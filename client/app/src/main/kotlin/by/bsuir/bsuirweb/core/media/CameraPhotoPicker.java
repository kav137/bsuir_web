package by.bsuir.bsuirweb.core.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.io.File;
import java.util.Collections;
import java.util.List;

public final class CameraPhotoPicker implements PhotoPicker {
    private static final int REQUEST_CODE = 20;

    private static File PHOTO_FILE;

    @Override
    public void pickPhoto(@NonNull Activity activity) {
        activity.startActivityForResult(makeIntent(activity), CameraPhotoPicker.REQUEST_CODE);
    }

    @Override
    public void pickPhoto(@NonNull Fragment fragment) {
        fragment.startActivityForResult(makeIntent(fragment.getContext()), CameraPhotoPicker.REQUEST_CODE);
    }

    @Override public void pickPhoto(@NonNull Fragment fragment, @Nullable Integer photosLimit, @Nullable Integer currentPhotosCount) {
        //no-op
    }

    private Intent makeIntent(@NonNull Context context) {
        createPhotoFile(context);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(context));
        grantPhotoFilePermissions(context, intent);

        return intent;
    }

    private void createPhotoFile(Context context) {
        CameraPhotoPicker.PHOTO_FILE = Files.createPhotoFile(context);
    }

    private Uri getPhotoFileUri(Context context) {
        return Files.getUri(context, CameraPhotoPicker.PHOTO_FILE);
    }

    private void grantPhotoFilePermissions(Context context, Intent cameraIntent) {
        Uri photoFileUri = getPhotoFileUri(context);

        for (ResolveInfo camera : context.getPackageManager().queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY)) {
            String cameraPackage = camera.activityInfo.packageName;

            context.grantUriPermission(
                    cameraPackage, photoFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION
            );
        }
    }

    @Override
    public boolean canHandleResult(int requestCode, int resultCode) {
        return (requestCode == CameraPhotoPicker.REQUEST_CODE) && (resultCode == Activity.RESULT_OK);
    }

    @Override
    public List<File> getPhotoFilesFromResult(Context context, Intent result) {
        revokePhotoFilePermissions(context);

        return Collections.singletonList(PHOTO_FILE);
    }

    private void revokePhotoFilePermissions(Context context) {
        context.revokeUriPermission(
                getPhotoFileUri(context), Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION
        );
    }
}