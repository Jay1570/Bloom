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
fun FamilyPlanScreen(){
    val childOptions = listOf("Don't want children","Want children","Open to children","Not sure yet","Prefer not to say")

    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    Column (modifier = Modifier.fillMaxSize().padding(16.dp))
    {
        Text(
            text = "What are your family plans?",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(childOptions) { options ->
                Row(modifier = Modifier.fillMaxWidth()
                    .clickable { selectedOption = options }
                    .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = options,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(.1f)
                    )
                    RadioButton(
                        selected = selectedOption == options,
                        onClick = { selectedOption = options }
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
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
}
@Preview(showBackground = true)
@Composable
fun FamilyPlanScreenPreview(){
    BloomTheme {
        FamilyPlanScreen()
    }
}
