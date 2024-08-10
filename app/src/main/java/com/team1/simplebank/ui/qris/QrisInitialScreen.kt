package com.team1.simplebank.ui.qris

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.colors_for_composable.BlueNormal


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun QrisInitialScreen(
    modifier: Modifier = Modifier,

) {
    val qrisTabItems = listOf(
        QrisTabItems("scan kode"),
        QrisTabItems("tampilkan kode"),
    )

    val pagerState = rememberPagerState { qrisTabItems.size }
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            modifier = modifier.fillMaxHeight(0.9f),
            state = pagerState,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) { pageIndex ->
            when (pageIndex) {
                0 -> {
                    ScanQrisScreen()
                }
                1 -> {
                    ShowQrisScreen(
                        modifier = modifier,
                        totalTime = 300,
                        onCountDownFinished = {selectedTabIndex = 0}
                    )
                }
            }

        }
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            divider = {},
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = modifier.tabIndicatorOffset(
                        selectedTabIndex,
                    ),
                    width = 96.dp,
                    height = 4.dp,
                    shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
                    color = BlueNormal
                )
            }
        ) {
            qrisTabItems.forEachIndexed { index, qrisTabItem ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = qrisTabItem.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    selectedContentColor = BlueNormal,
                    unselectedContentColor = Color.Gray
                )
            }
        }
    }
}

data class QrisTabItems(
    var title: String,
)