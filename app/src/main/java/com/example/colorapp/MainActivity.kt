

package com.example.colorapp

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh

import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold


import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider

import androidx.room.Room.databaseBuilder

import com.example.colorapp.ui.theme.Repo.ColorRepository
import com.example.colorapp.ui.theme.data.ColorDatabase
import com.example.colorapp.ui.theme.data.ColorEntity

import com.example.colorapp.ui.theme.viewmodel.ColorViewModel
import com.example.colorapp.ui.theme.viewmodel.ColorViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.sql.Date


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = databaseBuilder(applicationContext, ColorDatabase::class.java, "color_db").build()
        val repository = ColorRepository(database.colorDao(),Firebase.database.reference)
        val viewModel = ViewModelProvider(this, ColorViewModelFactory(repository)).get(
            ColorViewModel::class.java)

        setContent {

            ColorListScreen(viewModel)
        }
    }
}

@Composable
fun ColorListScreen(viewModel: ColorViewModel) {
    val colors by viewModel.colors.observeAsState(emptyList())
    val unsyncedCount = colors.count { !it.synced }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Color App", color = Color.White, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) },
                backgroundColor = Color(0xFF3A5CA8),


                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        FloatingActionButton(onClick = { viewModel.syncColors() },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.height(35.dp)
                        ) {
                            Row {
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text(text = unsyncedCount.toString())
                                Spacer(modifier = Modifier.padding(5.dp))
                                Icon(Icons.Default.Refresh, contentDescription = "sync",
                                    modifier = Modifier.background(color = Color.Transparent, shape = CircleShape))
                                Spacer(modifier = Modifier.padding(2.dp))
                            }

                        }



                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addColor() },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.height(35.dp)
            ) {
                Row {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = "add color")
                    Spacer(modifier = Modifier.padding(5.dp))
                    Icon(Icons.Default.Add, contentDescription = "Add Color",
                        modifier = Modifier.background(color = Color.Blue, shape = CircleShape))
                    Spacer(modifier = Modifier.padding(2.dp))
                }

            }

        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(colors) { color ->
                Itemcolors(color)
            }
        }
    }
}




@Composable
fun Itemcolors(colorEntity: ColorEntity) {

//
    Box(
        modifier = Modifier
            .fillMaxWidth().height(140.dp)
            .background(
                color = Color(android.graphics.Color.parseColor(colorEntity.color)),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = colorEntity.color,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Created at ${SimpleDateFormat("dd/MM/yyyy").format(Date(colorEntity.timestamp))} ",
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(4.dp)
            )
        }
    }
}










