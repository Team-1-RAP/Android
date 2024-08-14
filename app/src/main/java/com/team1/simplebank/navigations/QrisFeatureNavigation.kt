package com.team1.simplebank.navigations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.team1.simplebank.R
import com.team1.simplebank.common.constants.ScreenRoute
import com.team1.simplebank.ui.compose_components.CustomTopAppBarForFeature
import com.team1.simplebank.ui.qris.QrisInitialScreen
import com.team1.simplebank.ui.qris.ScanQrisConfirmTransactionScreen
import com.team1.simplebank.ui.qris.ShowQrisConfirmationScreen

@Composable
@Preview(showBackground = true)
fun QrisFeatureNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        containerColor = Color.White,
        topBar = {
            if (currentRoute != ScreenRoute.QrisTransactionResult.route){
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
                    onBackPressed = { navController.popBackStack() },
                    isBackEnable = currentRoute != ScreenRoute.QrisTransactionResult.route,
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.QrisScan.route,
            modifier = modifier.padding(innerPadding),
        ){
            composable(
                route = ScreenRoute.QrisScan.route,
            ) {
                QrisInitialScreen(
                    modifier = modifier,
                    onQrCodeValueObtained = { qrValue ->
                        navController.navigate(ScreenRoute.ScanQrisConfrimTransaction.createRoute(qrValue))
                    },

                )
            }

            composable(
                route = ScreenRoute.ScanQrisConfrimTransaction.route,
                arguments = listOf(
                    navArgument("qrValue") {type = NavType.StringType}
                )
            ){
                val qrValue = it.arguments?.getString("qrValue") ?: ""
                ScanQrisConfirmTransactionScreen(
                    modifier = modifier,
                    qrValue = qrValue,
                )
            }

            // trial error
            composable(
                route = ScreenRoute.ShowQrisConfirmationScreen.route
            ) {
                ShowQrisConfirmationScreen()
            }
        }
    }
}