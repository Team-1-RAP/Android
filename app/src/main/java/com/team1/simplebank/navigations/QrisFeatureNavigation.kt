package com.team1.simplebank.navigations

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.navArgument
import com.team1.simplebank.R
import com.team1.simplebank.common.constants.ScreenRoute
import com.team1.simplebank.ui.compose_components.CustomTopAppBarForFeature
import com.team1.simplebank.ui.qris.QrisInitialScreen
import com.team1.simplebank.ui.qris.QrisTransactionSuccessScreen
import com.team1.simplebank.ui.qris.ScanQrisConfirmPaymentTransactionScreen
import com.team1.simplebank.ui.qris.ScanQrisConfirmReceivePaymentTransactionScreen

@Composable
@Preview(showBackground = true)
fun QrisFeatureNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val activity = LocalContext.current as? AppCompatActivity

    Scaffold(
        containerColor = Color.White,
        topBar = {
            if (currentRoute != ScreenRoute.QrisTransactionSuccess.route) {
                CustomTopAppBarForFeature(
                    modifier = modifier,
                    title = {
                        Image(
                            painterResource(id = R.drawable.qris_logo_white),
                            contentDescription = "Qris",
                            modifier = Modifier
                                .size(80.dp)
                                .fillMaxWidth(),
                        )
                    },
                    onBackPressed = {
                            val navControllerFragment = activity?.findNavController(R.id.nav_host_fragment_activity_home)
                            if (currentRoute == ScreenRoute.QrisScan.route) {
                                Log.d("NavigationTest", "Navigating to home")
                                navControllerFragment?.navigate(R.id.action_navigation_qris_to_home) //bug
                            } else {
                                Log.d("NavigationTest", "Back pressed")
                                navController.popBackStack()
                            }
                    },
                    isBackEnable = currentRoute != ScreenRoute.QrisTransactionSuccess.route,
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.QrisScan.route,
            modifier = modifier.padding(innerPadding),
        ) {
            composable(
                route = ScreenRoute.QrisScan.route,
            ) {
                QrisInitialScreen(
                    modifier = modifier,
                    onQrPaymentCodeValueObtained = { qrValue ->
                        navController.navigate(
                            ScreenRoute.ScanQrisConfrimPaymentTransaction.createRoute(
                                qrValue
                            )
                        ) { launchSingleTop = true }
                    },
                    onQrReceivePaymentCodeValueObtained = { qrValue ->
                        navController.navigate(
                            ScreenRoute.ScanQrisConfrimReceivePaymentTransaction.createRoute(
                                qrValue
                            )
                        ) { launchSingleTop = true }
                    },
                    onSuccess = { message ->
                        navController.navigate(
                            ScreenRoute.QrisTransactionSuccess.createRoute(
                                message
                            )
                        )
                    }

                )
            }

            composable(
                route = ScreenRoute.ScanQrisConfrimPaymentTransaction.route,
                arguments = listOf(
                    navArgument("qrValue") { type = NavType.StringType }
                )
            ) {
                val qrValue = it.arguments?.getString("qrValue") ?: ""
                ScanQrisConfirmPaymentTransactionScreen(
                    modifier = modifier,
                    qrValue = qrValue,
                    onSuccess = { message ->
                        navController.navigate(
                            ScreenRoute.QrisTransactionSuccess.createRoute(
                                message
                            )
                        )
                    },
                    onError = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = ScreenRoute.ScanQrisConfrimReceivePaymentTransaction.route,
                arguments = listOf(
                    navArgument("qrValue") { type = NavType.StringType }
                )
            ) {
                val qrValue = it.arguments?.getString("qrValue") ?: ""
                ScanQrisConfirmReceivePaymentTransactionScreen(
                    modifier = modifier,
                    qrValue = qrValue,
                    onSuccess = { message ->
                        navController.navigate(
                            ScreenRoute.QrisTransactionSuccess.createRoute(
                                message
                            )
                        )
                    },
                    onError = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = ScreenRoute.QrisTransactionSuccess.route,
                arguments = listOf(
                    navArgument("message") { type = NavType.StringType },
                )
            ) {
                val message = it.arguments?.getString("message") ?: ""
                QrisTransactionSuccessScreen(
                    onBackToHome = {
                        navController.popBackStack(ScreenRoute.QrisScan.route, false)
                    },
                    message = message
                )
            }

        }
    }
}