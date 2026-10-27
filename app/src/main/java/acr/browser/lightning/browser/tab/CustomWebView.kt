package acr.browser.lightning.browser.tab

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView

class CustomWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    private var lastMouseX = -1f
    private var lastMouseY = -1f

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (event != null && event.action == MotionEvent.ACTION_HOVER_MOVE) {
            lastMouseX = event.x
            lastMouseY = event.y
        }
        return super.onGenericMotionEvent(event)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus && lastMouseX != -1f && lastMouseY != -1f) {
            val hoverEvent = MotionEvent.obtain(
                SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),
                MotionEvent.ACTION_HOVER_MOVE,
                lastMouseX,
                lastMouseY,
                0
            )
            this.dispatchGenericMotionEvent(hoverEvent)
            hoverEvent.recycle()
        }
    }
}
