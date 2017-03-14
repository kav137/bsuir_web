package by.bsuir.bsuirweb.extensions

import android.app.Activity
import android.support.design.widget.Snackbar


fun Activity.showSnackbarMessage(message: String) {
    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
}