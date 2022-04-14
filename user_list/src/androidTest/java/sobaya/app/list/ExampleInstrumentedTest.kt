package sobaya.app.list

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sobaya.app.composables.lv3_organisms.ListCard

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_3A)

    @Test
    fun useAppContext() {
        paparazzi.snapshot {
            ListCard(onClick = {}, login = "sobaya")
        }
    }
}
