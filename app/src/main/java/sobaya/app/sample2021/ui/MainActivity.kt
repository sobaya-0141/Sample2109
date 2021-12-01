package sobaya.app.sample2021.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import sobaya.app.sample2021.ui.theme.Sample2021Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            Sample2021Theme {
                Surface(color = MaterialTheme.colors.background) {
                    DestinationsNavHost()
//                    NavHost(navController = navController, startDestination = "userList") {
//                        composable("userList") {  }
//                        composable(
//                            "userDetail/{userName}",
//                            arguments = listOf(
//                                navArgument("userName") { type = NavType.StringType }
//                            )
//                        ) { backStackEntry ->
//                            UserDetailView(
//                                requireNotNull(
//                                    backStackEntry.arguments?.getString("userName")
//                                ),
//                                hiltViewModel()
//                            )
//                        }
//                    }
                }
            }
        }
    }
}
