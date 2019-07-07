package com.yantur.newsapp.presenter

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun Fragment.checkAndRequestPermission(requestCode: Int): Boolean {
    var isGranted = false
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        contextNotNull {
            isGranted = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

            if (!isGranted) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
            }

        }
    } else {
        isGranted = true
    }
    return isGranted
}

fun <T> Fragment.contextNotNull(block: Context.() -> T) {
    context?.let {
        block(it)
    }
}

fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}

