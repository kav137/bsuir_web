package by.bsuir.bsuirweb.core.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.io.File;
import java.util.List;

public interface PhotoPicker {
    void pickPhoto(@NonNull Activity activity);

    void pickPhoto(@NonNull Fragment fragment);

    void pickPhoto(@NonNull Fragment fragment, @Nullable Integer photosLimit, @Nullable Integer currentPhotosCount);

    boolean canHandleResult(int requestCode, int resultCode);

    List<File> getPhotoFilesFromResult(Context context, Intent result);
}