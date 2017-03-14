package by.bsuir.bsuirweb.core.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;


public final class Permissions implements View.OnClickListener {
    private final Activity activity;

    private final Fragment fragment;

    private final Runnable action;

    private final String permissionName;

    private final int permissionCode;

    private String permissionExplanation;

    private Permissions(@Nullable Activity activity,
                        @Nullable Fragment fragment,
                        @Nullable Runnable action,
                        @NonNull String permissionName,
                        int permissionCode,
                        @NonNull String permissionExplanation) {
        this.activity = activity;
        this.fragment = fragment;
        this.action = action;
        this.permissionExplanation = permissionExplanation;
        this.permissionName = permissionName;
        this.permissionCode = permissionCode;
    }

    public static Permissions createForExternalStorage(Activity activity, Runnable action, @NonNull String permissionExplanation) {
        return new Permissions(activity, null, action, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE.length(), permissionExplanation);
    }

    public static Permissions createForExternalStorage(Fragment fragment, Runnable action, @NonNull String permissionExplanation) {
        return new Permissions(null, fragment, action, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE.length(), permissionExplanation);
    }

    public static Permissions createForCamera(@NonNull Activity activity, @NonNull Runnable action, @NonNull String permissionExplanation) {
        return new Permissions(activity, null, action, Manifest.permission.CAMERA, Manifest.permission.CAMERA.length(), permissionExplanation);
    }

    public static boolean isPermissionAvailable(@NonNull Context context, @NonNull String permissionName) {
        return ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED;
    }

    public void execute() {
        if (isPermissionAvailable()) {
            executeAction();
            return;
        }

        if (isPermissionExplanationNeeded()) {
            showPermissionExplanation();
        } else {
            setUpPermission();
        }
    }

    private boolean isPermissionAvailable() {
        Context context = context();

        if (context == null) {
            return false;
        }

        return ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED;
    }

    private void executeAction() {
        action.run();
    }

    private boolean isPermissionExplanationNeeded() {
        Context context = context();

        return context != null && ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionName);

    }

    private void showPermissionExplanation() {
        View content;

        if (activity != null) {
            content = activity.findViewById(android.R.id.content);
        } else if (fragment != null && fragment.getActivity() != null) {
            content = fragment.getActivity().findViewById(android.R.id.content);
        } else {
            return;
        }

        Snackbar snackbar = Snackbar.make(content, permissionExplanation, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Для полного доступа к функционалу, нужен доступ к камере", this);
        snackbar.show();
    }

    @Override
    public void onClick(View snackbarAction) {
        setUpPermission();
    }

    private void setUpPermission() {
        if (activity != null) {
            ActivityCompat.requestPermissions(activity, new String[]{permissionName}, permissionCode);
        } else if (fragment != null) {
            fragment.requestPermissions(new String[]{permissionName}, permissionCode);
        }
    }

    public void onRequestPermissionResult(int permissionCode, int[] permissionGrants) {
        if (isPermissionAvailable(permissionCode, permissionGrants)) {
            executeAction();
        }
    }

    private boolean isPermissionAvailable(int permissionCode, int[] permissionGrants) {
        if (permissionCode != this.permissionCode) {
            return false;
        }

        for (int permissionGrant : permissionGrants) {
            if (permissionGrant == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    private Context context() {
        Context context = null;

        if (activity != null) {
            context = activity;
        } else if (fragment != null) {
            context = fragment.getContext();
        }

        return context;
    }
}