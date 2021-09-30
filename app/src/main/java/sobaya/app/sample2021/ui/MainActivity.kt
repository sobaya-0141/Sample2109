package sobaya.app.sample2021.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import sobaya.app.list.view.UserListView
import sobaya.app.sample2021.ui.theme.Sample2021Theme
import sobaya.app.user_detail.view.UserDetailView

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Sample2021Theme {
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = "userList") {
                        composable("userList") { UserListView(navController, hiltViewModel()) }
                        composable(
                            "userDetail/{userName}",
                            arguments = listOf(
                                navArgument("userName") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            UserDetailView(
                                requireNotNull(
                                    backStackEntry.arguments?.getString("userName")
                                ),
                                hiltViewModel()
                            )
                        }
                    }
                }
            }
        }
    }
}
