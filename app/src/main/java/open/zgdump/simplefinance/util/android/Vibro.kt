package open.zgdump.simplefinance.util.android

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

fun vibrate(context: Context, milliseconds: Long) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val canVibrate = vibrator.hasVibrator()

    if (canVibrate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    milliseconds,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(milliseconds)
        }
    }
}