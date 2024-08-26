package com.team1.simplebank.navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.team1.simplebank.common.constants.ScreenRoute
import com.team1.simplebank.ui.auth.LoginScreen
import com.team1.simplebank.ui.auth.forgotpswd.ForgotPasswordConfirmationSuccessScreen
import com.team1.simplebank.ui.auth.forgotpswd.ForgotPasswordInputBirthDateScreen
import com.team1.simplebank.ui.auth.forgotpswd.ForgotPasswordInputDataScreen
import com.team1.simplebank.ui.auth.forgotpswd.ForgotPasswordInputEmailScreen
import com.team1.simplebank.ui.auth.forgotpswd.ForgotPasswordInputNewPasswordScreen
import com.team1.simplebank.ui.auth.forgotpswd.ForgotPasswordInputOtpCodeScreen
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
                ForgotPasswordInputBirthDateScreen(
                    onNavigateToInputEmail = {
                        navController.navigate(ScreenRoute.ForgotPwInputEmail.route)
                    }
                )
            }
            composable(ScreenRoute.ForgotPwInputEmail.route){
                ForgotPasswordInputEmailScreen(
                    onNavigateToInputOtpCode = {
                        navController.navigate(ScreenRoute.ForgotPwInputOtpCode.createRoute(it))
                    }
                )
            }
            composable(
                route = ScreenRoute.ForgotPwInputOtpCode.route,
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType }
                )
            ){
                val email = it.arguments?.getString("email") ?: ""
                ForgotPasswordInputOtpCodeScreen(
                    email = email,
                    onNavigateToInputNewPassword = {
                        navController.navigate(ScreenRoute.ForgotPwInputNewPassword.route)
                    }
                )
            }
            composable(ScreenRoute.ForgotPwInputNewPassword.route){
                ForgotPasswordInputNewPasswordScreen (
                    onChangePasswordSuccess = {
                        navController.navigate(ScreenRoute.ForgotPwConfirmationSuccess.route)
                    }
                )
            }
            composable(ScreenRoute.ForgotPwConfirmationSuccess.route){
                ForgotPasswordConfirmationSuccessScreen(
                    onNavigateToLogin = {
                        navController.popBackStack(ScreenRoute.Login.route, inclusive = false)
                    }
                )
            }
        }
    }
}