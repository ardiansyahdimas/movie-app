package com.movies.core.utils

import android.widget.ProgressBar
import androidx.core.view.isVisible

object Utils {
    fun updateUI(isLoading:Boolean, progressBar: ProgressBar){
        progressBar.isVisible = isLoading
    }
}