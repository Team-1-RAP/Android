package com.team1.simplebank.navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.team1.simplebank.common.constants.ScreenRoute
import com.team1.simplebank.ui.auth.LoginScreen
import com.team1.simplebank.ui.auth.forgotpswd.ForgotPasswordInputDataScreen
import com.team1.simplebank.ui.compose_components.CustomTopAppBarForAuth

@Preview(showBackground = true)
@Composable
fun AuthNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        containerColor = Color.White,
        topBar = {
            if (currentRoute != ScreenRoute.Login.route){
                CustomTopAppBarForAuth(
                    modifier = modifier,
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.Login.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(ScreenRoute.Login.route) {
                LoginScreen(
                    onForgotPasswordButtonClicked = {
                        navController.navigate(ScreenRoute.ForgotPwInputCardData.route)
                    }
                )
            }
            composable(ScreenRoute.ForgotPwInputCardData.route) {
                ForgotPasswordInputDataScreen(
                    onNavigateToInputBirthDate =  {
                        navController.navigate(ScreenRoute.ForgotPwInputBirthDate.route)
                    }
                )
            }
            composable(ScreenRoute.ForgotPwInputBirthDate.route){

            }
        }
    }
}