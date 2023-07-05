package io.drdroid.assignment2.dialogs

import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget
import com.google.android.material.card.MaterialCardView
import io.drdroid.assignment2.R
import io.drdroid.assignment2.databinding.DialogCustomBinding
import io.drdroid.assignment2.utils.DensityUtil

class CustomDialog(private val context: Context, cancelable: Boolean) {
    private var bind: DialogCustomBinding

    private val builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
//    private lateinit var card: MaterialCardView
//    private lateinit var title: TextView
//    private lateinit var scroll: ScrollView
//
//    private lateinit var message: TextView
//    private lateinit var positive: AppCompatButton
//    private lateinit var negative: AppCompatButton
//    private lateinit var neutral: AppCompatButton
//    private lateinit var frameLayout: FrameLayout

    init {
        bind = DialogCustomBinding.inflate(LayoutInflater.from(context))
        builder = AlertDialog.Builder(context).setCancelable(cancelable)
        setupDialog()
    }

    private fun setupDialog() {
        bind.apply {
//            val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null)
//            val root = view.findViewById<LinearLayout>(R.id.root)
            base.setBackgroundColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.navigation,
                    null
                )
            )
//            card = view.findViewById<MaterialCardView>(R.id.card)
//            title = view.findViewById<TextView>(R.id.title)
            title.visibility = View.GONE
//            title.setTextColor(theme.TextColor())
//            scroll = view.findViewById<ScrollView>(R.id.scroll)
//            message = view.findViewById<TextView>(R.id.message)
            message.visibility = View.GONE
//            message.setTextColor(theme.TextColor())
//            frameLayout = view.findViewById<FrameLayout>(R.id.framelayout)
//            positive = view.findViewById<AppCompatButton>(R.id.positive)
            positive.visibility = View.GONE
//            positive.setTextColor(theme.TextColor())
//            negative = view.findViewById<AppCompatButton>(R.id.negative)
            negative.visibility = View.GONE
//            negative.setTextColor(theme.TextColor())
//            neutral = view.findViewById<AppCompatButton>(R.id.neutral)
            neutral.visibility = View.GONE
//            neutral.setTextColor(theme.TextColor())
            builder.setView(root)
        }

    }

    fun setTitle(@StringRes title: Int): CustomDialog {
        setTitle(context.resources.getString(title))
        return this
    }

    fun setTitle(title: String): CustomDialog {
        if (title.isEmpty()) {
            return this
        }
        bind.title.text = title
        bind.title.visibility = View.VISIBLE
        return this
    }

    fun setMessage(@StringRes message: Int): CustomDialog {
        setMessage(context.resources.getString(message))
        return this
    }

    fun setMessage(message: String): CustomDialog {
        if (message.isEmpty()) {
            return this
        }
        bind.scroll.fullScroll(View.FOCUS_DOWN)
        bind.scroll.isSmoothScrollingEnabled = true
//        val wts = WordToSpan()
//        wts
//            .setColorTAG(Color.parseColor("#222324"))
//            .setColorURL(Color.MAGENTA)
//            .setColorPHONE(Color.RED)
//            .setColorMAIL(Color.YELLOW)
//            .setColorMENTION(Color.BLUE)
//            .setUnderlineURL(true)
//            .setLink(message)
//            .into(this.message)
//            .setClickListener { type, text ->
//                when (type) {
//                    "tag" -> openHashtag(text.substring(1))
//                    "mail" -> openEmail(text)
//                    "url" -> openUrl(text)
//                    "phone" -> openPhone(text)
//                    "mention" -> openMention(text.substring(1))
//                    "custom" -> {}
//                }
//            }
        bind.message.visibility = View.VISIBLE
        return this
    }

    fun includeView(@LayoutRes layout: Int): CustomDialog {
        includeView(LayoutInflater.from(context).inflate(layout, null))
        return this
    }

    fun includeView(view: View): CustomDialog {
        bind.framelayout.addView(view)
        return this
    }

    fun setPositive(@StringRes text: Int, clickListener: View.OnClickListener?): CustomDialog {
        setPositive(context.resources.getString(text), clickListener)
        return this
    }

    fun setPositive(text: String, clickListener: View.OnClickListener?): CustomDialog {
        val click = View.OnClickListener { v: View? ->
            clickListener?.onClick(v)
            dialog.dismiss()
        }
        bind.positive.text = text
        bind.positive.setOnClickListener(click)
        bind.positive.visibility = View.VISIBLE
        return this
    }

    fun setNegative(@StringRes text: Int, clickListener: View.OnClickListener?): CustomDialog {
        setNegative(context.resources.getString(text), clickListener)
        return this
    }

    fun setNegative(text: String, clickListener: View.OnClickListener?): CustomDialog {
        val click = View.OnClickListener { v: View? ->
            clickListener?.onClick(v)
            dialog.dismiss()
        }
        bind.negative.text = text
        bind.negative.setOnClickListener(click)
        bind.negative.visibility = View.VISIBLE
        return this
    }

    fun setNeutral(@StringRes text: Int, clickListener: View.OnClickListener?): CustomDialog {
        setNeutral(context.resources.getString(text), clickListener)
        return this
    }

    fun setNeutral(text: String, clickListener: View.OnClickListener?): CustomDialog {
        val click = View.OnClickListener { v: View? ->
            clickListener?.onClick(v)
            dialog.dismiss()
        }
        bind.neutral.text = text
        bind.neutral.setOnClickListener(click)
        bind.neutral.visibility = View.VISIBLE
        return this
    }

    fun setOnDismiss(onDismiss: DialogInterface.OnDismissListener?): CustomDialog {
        dialog.setOnDismissListener(onDismiss)
        return this
    }

    fun setOnShow(onShow: OnShowListener?): CustomDialog {
        dialog.setOnShowListener(onShow)
        return this
    }

    fun setOnCancel(onCancel: DialogInterface.OnCancelListener?): CustomDialog {
        dialog.setOnCancelListener(onCancel)
        return this
    }

    fun show() {
        if (!this::dialog.isInitialized || dialog == null) {
            dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        dialog.show()
    }

    val isShowing: Boolean
        get() = if (dialog != null) {
            dialog.isShowing
        } else false

    fun dismiss() {
        if (dialog != null) {
            dialog.dismiss()
        }
    }

    /*
    private fun openUrl(url: String) {
        if (isCustomTabSupported(context, Uri.parse(url))) {
            val builder = CustomTabsIntent.Builder()
            builder.setShowTitle(true)
            builder.setToolbarColor(theme.NavigationColor())
            val customTabsIntent = builder.build()
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://"))
            val resolveInfo = context.packageManager.resolveActivity(
                browserIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            if (resolveInfo != null && resolveInfo.activityInfo.packageName.length > 0) {
                customTabsIntent.intent.setPackage(resolveInfo.activityInfo.packageName)
            }
            customTabsIntent.launchUrl(context, Uri.parse(url))
        } else {
            Toast.makeText(
                context,
                "Couldn't find an app to open the web page.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openHashtag(hashtag: String) {
        val intent = Intent(context, HashTag::class.java)
        intent.putExtra(Common.EXTRA.STR_EXTRA_TAG, hashtag)
        context.startActivity(intent)
    }

    private fun openMention(mention: String) {
        TaskRunner().executeAsync(RestRequest2(context, getClient()).getUserInfo(mention)) { data ->
            if (data == null) {
                return@executeAsync
            }
            val i = Intent(context, Profile::class.java)
            i.putExtra(Common.EXTRA.STR_EXTRA_USER, Gson().toJson(data))
            i.putExtra(Common.EXTRA.STR_EXTRA_FRAGMENT, UserFragments.FEED)
            CircularReveal.presentActivity(
                Builder(
                    context as AppCompatActivity,
                    message,
                    i,
                    500
                )
            )
        }
    }

    private fun openEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    private fun openPhone(phone: String) {
        val number = Uri.parse("tel:$phone")
        val dial = Intent(Intent.ACTION_CALL, number)
        context.startActivity(dial)
    }
    */

    companion object {
        private val TAG = CustomDialog::class.java.simpleName
        fun loadingDialog(context: Context): CustomDialog {
            val image = ImageView(context)
            image.scaleType = ImageView.ScaleType.FIT_CENTER
            val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
                DensityUtil.px2dip(context, 1080F),
                DensityUtil.px2dip(context, 1080F)
            )
            image.layoutParams = params
            Glide.with(context)
                .asGif()
                .load(R.drawable.wait)
                .into(object : ImageViewTarget<GifDrawable?>(image) {
                    override fun setResource(resource: GifDrawable?) {
                        image.setImageDrawable(resource)
                    }
                })
            val customDialog = CustomDialog(context, false)
            val cardParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            cardParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
            customDialog.bind.card.layoutParams = cardParams
            customDialog.setTitle("Please wait...").includeView(image)
            return customDialog
        }
    }
}