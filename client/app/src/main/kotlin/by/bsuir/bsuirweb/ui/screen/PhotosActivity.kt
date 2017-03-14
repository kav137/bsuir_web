package by.bsuir.bsuirweb.ui.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import by.bsuir.bsuirweb.R
import by.bsuir.bsuirweb.core.backend.Api
import by.bsuir.bsuirweb.core.backend.uploadPhoto
import by.bsuir.bsuirweb.core.media.CameraPhotoPicker
import by.bsuir.bsuirweb.core.permissions.PermissionChecker
import by.bsuir.bsuirweb.core.permissions.Permissions
import by.bsuir.bsuirweb.core.settings.Settings
import by.bsuir.bsuirweb.extensions.applyOnStartAndFinish
import by.bsuir.bsuirweb.extensions.showSnackbarMessage
import by.bsuir.bsuirweb.ui.adapter.PhotosAdapter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_photos.*
import retrofit2.http.HTTP
import java.io.File

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
        photos.layoutManager = LinearLayoutManager(this)
        refreshPhotos()
        addPhotoView.setOnClickListener {
            takePhoto()

        }

        addPhotoView.setOnLongClickListener {
            refreshPhotos()
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permission.onRequestPermissionResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (cameraPhotoPicker.canHandleResult(requestCode, resultCode)) {
            uploadPhoto(cameraPhotoPicker.getPhotoFilesFromResult(this, data).get(0))
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

    fun showContent(isShowText: Boolean) {
        if(isShowText) {
            text.visibility = View.VISIBLE
        } else {
            text.visibility = View.GONE
        }

        animator.displayedChild = animator.indexOfChild(content)
    }

    fun showProgress() {
        animator.displayedChild = animator.indexOfChild(progress)
    }

    fun uploadPhoto(file: File) {
        Api.uploadPhoto(this, file, Settings.token(this)!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .applyOnStartAndFinish(
                        onStart = this::showProgress,
                        onFinish = {}
                )
                .subscribe(
                        {
                            result ->
                            refreshPhotos()
                        },
                        {
                            error ->
                            showSnackbarMessage((error as HttpException).message())
                        })

    }

    fun refreshPhotos() {
        Api.photos(Settings.token(this)!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .applyOnStartAndFinish(
                        onStart = this::showProgress,
                        onFinish = {}
                )
                .subscribe(
                        {
                            photosResult ->
                            if(photosResult.photos.isNotEmpty()) {
                                showContent(false)
                                photos.swapAdapter(PhotosAdapter(photosResult.photos), true)
                            } else {
                                showContent(true)
                            }
                            Log.d("photos", photosResult.toString())
                        },
                        {
                            error ->
                            showContent(true)
                            showSnackbarMessage((error as HttpException).message())
                        })

    }
}