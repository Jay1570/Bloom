package com.example.bloom.screens.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BloomTheme


@Composable
fun WeedSelectionScreen(){
    val weed = listOf("Yes","Sometimes","No","Prefer not to say")

    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    Column (modifier = Modifier.fillMaxSize().padding(16.dp, bottom = 60.dp, top = 20.dp)){
        Text(
            text = "Do you smoke Weed?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp, top = 10.dp)
        )

        LazyColumn (modifier = Modifier.padding(bottom = 20.dp)){
            items(weed){weed->
                Row (modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).clickable {
                    selectedOption = weed
                },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = weed,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(.1f)
                    )
                    RadioButton(
                        selected = selectedOption == weed,
                        onClick = { selectedOption = weed }
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.clickable(
                onClick = {
                    isVisibleOnProfile = !isVisibleOnProfile
                }
            )
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isVisibleOnProfile,
                    onCheckedChange = { isVisibleOnProfile = it }
                )
                Text(
                    text = "Visible on profile",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeedSelectionScreenPreview(){
    BloomTheme {
        WeedSelectionScreen()
    }
}