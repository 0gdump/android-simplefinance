package open.zgdump.simplefinance.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import open.zgdump.simplefinance.App
import open.zgdump.simplefinance.R
import open.zgdump.simplefinance.Screens
import open.zgdump.simplefinance.global.CiceroneNavigator

class MainActivity : AppCompatActivity() {

    private val navigator = CiceroneNavigator(this, R.id.fragmentsContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.router.newRootChain(Screens.MainFlow)
    }

    override fun onResume() {
        super.onResume()
        App.navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.navigationHolder.removeNavigator()
    }

    override fun onBackPressed() {
        App.router.exit()
    }
}