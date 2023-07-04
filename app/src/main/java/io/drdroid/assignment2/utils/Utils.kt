package io.drdroid.assignment2.utils

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat


object Utils {

    fun View.hideKeyboard() {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun calculateBrightness(bitmap: Bitmap, skipPixel: Int): Int {
        var R = 0
        var G = 0
        var B = 0
        val height = bitmap.height
        val width = bitmap.width
        var n = 0
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        var i = 0
        while (i < pixels.size) {
            val color = pixels[i]
            R += Color.red(color)
            G += Color.green(color)
            B += Color.blue(color)
            n++
            i += skipPixel
        }
        return (R + B + G) / (n * 3)
    }

//    fun isColorDark(color: Int): Boolean {
//        val darkness =
//            1 - 0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color) / 255
//        println(darkness)
//        return darkness >= 0.5
//    }

    fun isDark(color: Int): Boolean {
        if (color == 0) {
            return false
        }
        return ColorUtils.calculateLuminance(color) < 0.5
    }

    fun setLightStatusBar(/*view: View, */activity: Activity) {
//        var flags = view.systemUiVisibility
        var flags = activity.window.decorView.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        view.systemUiVisibility = flags
        activity.window.decorView.systemUiVisibility = flags
    }

    fun clearLightStatusBar(activity: Activity) {
        var flags = activity.window.decorView.systemUiVisibility
        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        activity.window.decorView.systemUiVisibility = flags
    }

    fun View.colorTransition(endColor: Int, duration: Long = 250L) {
        var colorFrom = Color.TRANSPARENT
        if (background is ColorDrawable)
            colorFrom = (background as ColorDrawable).color

//        val colorTo = ContextCompat.getColor(context, endColor)
        val colorAnimation: ValueAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, endColor)
        colorAnimation.duration = duration

        colorAnimation.addUpdateListener {
            if (it.animatedValue is Int) {
                val color = it.animatedValue as Int
                setBackgroundColor(color)
            }
        }
        colorAnimation.start()
    }

    fun openUrl(context: Context, url: String?, color: Int = Color.BLACK) {
        if (getCustomTabsPackages(context, Uri.parse(url)).size > 0) {
            val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            builder.setNavigationBarColor(color)
            builder.setToolbarColor(color)
            builder.setShowTitle(true)
            val customTabsIntent: CustomTabsIntent = builder.build()
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://"))
            val resolveInfo = context.packageManager.resolveActivity(
                browserIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            if (resolveInfo != null && resolveInfo.activityInfo.packageName.isNotEmpty()) {
                customTabsIntent.intent.setPackage(resolveInfo.activityInfo.packageName)
            }
            url?.let {
                customTabsIntent.launchUrl(context, Uri.parse(url))
            }

        } else {
            Toast.makeText(
                context,
                "Couldn't find an app to open the web page.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getCustomTabsPackages(context: Context, url: Uri?): MutableList<ResolveInfo> {
        val pm = context.packageManager
        // Get default VIEW intent handler.
        val activityIntent = Intent(Intent.ACTION_VIEW, url)

        // Get all apps that can handle VIEW intents.
        val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)
        val packagesSupportingCustomTabs: MutableList<ResolveInfo> = mutableListOf()
        for (info in resolvedActivityList) {
            val serviceIntent = Intent()
            serviceIntent.action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
            serviceIntent.setPackage(info.activityInfo.packageName)
            // Check if this package also resolves the Custom Tabs service.
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info)
            }
        }
        return packagesSupportingCustomTabs
    }

    fun getThemedIcon(icon: Drawable, color: Int): Drawable {
        if (isDark(color)) {
            DrawableCompat.setTint(icon, Color.WHITE)
        } else {
            DrawableCompat.setTint(icon, Color.BLACK)
        }

        return icon
    }
}