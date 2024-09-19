package com.example.es2cosmobile.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.es2cosmobile.ui.screens.home.HomeScreen
import com.example.es2cosmobile.ui.screens.login.LoginScreen
import com.example.es2cosmobile.ui.screens.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.dsl.koinApplication

sealed class Es2Route(
    val route: String,
    val title: String
) {
    data object Login : Es2Route("login", "Login")

    data object Home : Es2Route("home", "Home")


}

@Composable
fun Es2NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Es2Route.Login.route,
        modifier = modifier
    ) {
        with(Es2Route.Login) {
            composable(route) {
                val loginVm = koinViewModel<LoginViewModel>()
                val state by loginVm.state.collectAsStateWithLifecycle()
                val configuration = LocalConfiguration.current
                val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
                LoginScreen(
                    state = state,
                    actions = loginVm.actions,
                    onSubmit = {
                        loginVm.login { success ->
                            if (success) {
                                navController.navigate(Es2Route.Home.route)
                            }
                        }
                    },
                    isPortrait = isPortrait
                )
            }
        }
        with(Es2Route.Home) {
            composable(route) {
                HomeScreen()
            }
        }

    }
}