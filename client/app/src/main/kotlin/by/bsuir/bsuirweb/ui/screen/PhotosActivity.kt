package by.bsuir.bsuirweb.ui.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import by.bsuir.bsuirweb.R
import by.bsuir.bsuirweb.core.media.CameraPhotoPicker
import by.bsuir.bsuirweb.core.permissions.PermissionChecker
import by.bsuir.bsuirweb.core.permissions.Permissions
import kotlinx.android.synthetic.main.activity_photos.*

class PhotosActivity : AppCompatActivity() {

    lateinit var permission: Permissions

    var cameraPhotoPicker: CameraPhotoPicker = CameraPhotoPicker()

    companion object PhotosActivity {
        fun getIntent(context: Context): Intent {
            return Intent(context, by.bsuir.bsuirweb.ui.screen.PhotosActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        addPhotoView.setOnClickListener {
            takePhoto()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permission.onRequestPermissionResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(cameraPhotoPicker.canHandleResult(requestCode, resultCode)) {
             cameraPhotoPicker.getPhotoFilesFromResult(this, data)
        }
    }

    fun takePhoto() {
        if (PermissionChecker.of(this).isCameraAvailable) {
            cameraPhotoPicker.pickPhoto(this)
        } else {
            permission = Permissions.createForCamera(this, {
                cameraPhotoPicker.pickPhoto(this)
            }, "Для полного доступа к функционалу, нужен доступ к камере")

            permission.execute()
        }
    }
}