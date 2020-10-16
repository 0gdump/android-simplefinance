package open.zgdump.simplefinance.util.android

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

// From: https://stackoverflow.com/questions/36351141/
fun crossFade(viewIn: View, viewOut: View, animateViewOut: Boolean = true) {
    val crossFadeDuration = 100L

    // Set the content view to 0% opacity but visible, so that it is visible
    // (but fully transparent) during the animation.
    viewIn.alpha = 0f
    viewIn.visible(true)
    viewIn.bringToFront()

    // Animate the in view to 100% opacity, and clear any animation
    // listener set on the view.
    viewIn.animate()
        .alpha(1f)
        .setDuration(crossFadeDuration)
        .setListener(null)

    // Animate the out view to 0% opacity. After the animation ends,
    // set its visibility to GONE as an optimization step (it won't
    // participate in layout passes, etc.)
    viewOut.animate()
        .alpha(0f)
        .setDuration(if (animateViewOut) crossFadeDuration else 0)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                viewOut.visible(false)
            }
        })

}