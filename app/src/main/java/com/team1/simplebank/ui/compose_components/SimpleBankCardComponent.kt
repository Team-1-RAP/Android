package com.team1.simplebank.ui.compose_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.R
import com.team1.simplebank.colors_for_composable.Blue

@Composable
fun SimpleBankCardComponent(
    modifier: Modifier,
    cardHolder: String,
    cardExp: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Blue)
                .padding(vertical = 16.dp, horizontal = 24.dp)
        ) {
            Text(
                text = "Simple Bank",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.padding(top = 8.dp)
            )
            Image(
                painterResource(id = R.drawable.logo_mini_simple_bank),
                contentDescription = "Logo",
                modifier = Modifier
                    .align(Alignment.TopEnd)
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Column {
                    Text(
                        text = "Card Holder Name",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = modifier
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = cardHolder,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Spacer(modifier = modifier.weight(1f))
                Column {
                    Text(
                        text = "Expired date",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = modifier
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = cardExp,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Spacer(modifier = modifier.weight(1f))
                Image(
                    painterResource(id = R.drawable.mastercard_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(52.dp)
                )
            }
        }
    }
}