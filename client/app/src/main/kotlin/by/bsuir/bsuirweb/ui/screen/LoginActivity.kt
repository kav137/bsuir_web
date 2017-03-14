package by.bsuir.bsuirweb.ui.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import by.bsuir.bsuirweb.R
import by.bsuir.bsuirweb.core.backend.Api
import by.bsuir.bsuirweb.core.backend.entity.Token
import by.bsuir.bsuirweb.extensions.applyOnStartAndFinish
import by.bsuir.bsuirweb.extensions.showSnackbarMessage
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authorizeView.setOnClickListener {
            Api.authorize(username.text.toString(), password.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .applyOnStartAndFinish(
                            onStart = {
                                authorizeView.isEnabled = false },
                            onFinish = {
                                authorizeView.isEnabled = true }
                    )
                    .subscribe(
                            {
                                token -> showSnackbarMessage(token.value) },
                            { error ->
                                Log.e("", "test", error)
                                showSnackbarMessage("Вы ввели неправильный логин или пароль") }
                    )
        }
    }


}