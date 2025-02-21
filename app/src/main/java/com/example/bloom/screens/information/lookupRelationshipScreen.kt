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
fun RelationshipTypeScreen(){
    var Monogamy by remember { mutableStateOf(false) }
    var Nonmonogamy by remember { mutableStateOf(false) }
    var Figuringout by remember { mutableStateOf(false) }
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    val relation = listOf("Monogamy" to Monogamy,"Non-Monogamy" to Nonmonogamy,"Figuring out my relationship type" to Figuringout)

    val onCheckedChange: (String, Boolean) -> Unit = { label, isChecked ->
        when (label) {
            "Monogamy" -> Monogamy = isChecked
            "Non-Monogamy" -> Nonmonogamy = isChecked
            "Figuring out my relationship type" -> Figuringout = isChecked
        }
    }

    Column (modifier = Modifier.fillMaxSize().padding(24.dp)){
        Text(
            text = "What's tupe of relationship are you looking for?",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 16.dp),
            style = MaterialTheme.typography.titleLarge
        )
        LazyColumn {
            items(relation){(label,isChecked) ->
                relationItem(label,isChecked){ onCheckedChange(label,it) }
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
@Composable
fun relationItem(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
Row (modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onCheckedChange(!isChecked) },
    verticalAlignment = Alignment.CenterVertically){
    Text(
        text = label,
        fontSize = 18.sp,
        modifier = Modifier.weight(1f)
    )
    Checkbox(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
            checkedColor = Color(0xFF7B3F8F),
            uncheckedColor = Color.LightGray
        )
    )
  }
}

@Preview(showBackground = true)
@Composable
fun RelationshipTypeScreenPreview(){
    BloomTheme {
        RelationshipTypeScreen()
    }
}