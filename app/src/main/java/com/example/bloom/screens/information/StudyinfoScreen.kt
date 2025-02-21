package com.example.bloom.screens.information

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
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
fun StudySelectionScreen(){
    val StudyAt = listOf("Secondary school","Undergrad","Postgrad","Prefer not to say")

    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column (modifier = Modifier.fillMaxSize().padding(16.dp, bottom = 60.dp, top = 20.dp)){
        Text(
            text = "What's the highest level of you attained?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp, top = 10.dp)
        )

        LazyColumn (modifier = Modifier.padding(bottom = 20.dp)){
            items(StudyAt){StudyAt->
                Row (modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = StudyAt,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(.1f)
                    )
                    RadioButton(
                        selected = selectedOption == StudyAt,
                        onClick = { selectedOption = StudyAt }
                    )
                }
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card{
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.VisibilityOff, contentDescription = "", modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
                )
                Text(
                    text = "Hidden on profile",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudySelectionScreenPreview(){
    BloomTheme {
        StudySelectionScreen()
    }
}