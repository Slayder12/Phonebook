package com.example.phonebook

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phonebook.ui.theme.ItemViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    Scaffold(
        topBar = {
            MyTopAppBar()
        },
        bottomBar = {
            MyBottomAppBar()
        },
        content = { paddingValues ->
            MainContent(paddingValues)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(itemViewModel: ItemViewModel = viewModel()) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text("My Programm") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Cyan
        ),
        navigationIcon = {
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                myToast(
                    itemViewModel,
                    context,
                    "Звонок совершен:"
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Phone"
                )
            }
            IconButton(onClick = {
                (context as ComponentActivity).finishAffinity()
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel"
                )
            }
        }
    )
}


private fun myToast(
    itemViewModel: ItemViewModel,
    context: Context,
    text: String,

) {
    if (itemViewModel.itemListState.isEmpty()) {
        Toast.makeText(
            context,
            "Телефлнный справочник пуст",
            Toast.LENGTH_SHORT
        ).show()
    } else if (itemViewModel.item.value.isEmpty()) {
        Toast.makeText(
            context,
            "$text ${itemViewModel.itemListState.first()}",
            Toast.LENGTH_SHORT
        ).show()
    } else {
        Toast.makeText(
            context,
            "$text ${itemViewModel.item.value}",
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun MainContent(
    paddingValues: PaddingValues,
    itemViewModel: ItemViewModel = viewModel()
){
    val itemTextState = itemViewModel.itemTextState
    val itemListState = itemViewModel.itemListState

    Column(
        Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 5.dp)
    ) {
        OutlinedTextField(
            value = itemTextState.value,
            onValueChange = {
                itemViewModel.itemTextState.value = it
            },
            textStyle = TextStyle(fontSize = 20.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black
            ),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .padding(start = 2.dp, end = 2.dp, bottom = 8.dp, top = 2.dp)
                .fillMaxSize()
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(5.dp)
                )

        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                items(itemListState) { item: String ->
                    ItemList(
                        item = item,
                        onDeleteClick = { itemViewModel.removeItem(item) },
                        onSelect =  {
                            itemViewModel.item.value = item
                        }
                    )
                    Spacer(modifier = Modifier.padding(3.dp))
                }
            }
            FloatingActionButton(
                containerColor = Color.DarkGray,
                contentColor = Color.White,
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(10.dp),

                onClick = { itemViewModel.addItem() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }

    }
}

@Composable
fun MyBottomAppBar(itemViewModel: ItemViewModel = viewModel()) {
    val context = LocalContext.current
    BottomAppBar(
        containerColor = Color.Cyan,
        actions = {
            IconButton(onClick = {
                myToast(
                    itemViewModel,
                    context,
                    "Сообщение отправлено:"
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send"
                )
            }
            Row(modifier = Modifier.weight(1f)){}
            IconButton(onClick = {
                myToast(
                    itemViewModel,
                    context,
                    "Контакт отредактирован:"
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Favorite",

                )
            }
        }
    )
}

@Composable
fun ItemList(
    item: String,
    onDeleteClick: () -> Unit,
    onSelect: () -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable {
                onSelect()
            }
            .background(
                Color.White,
                shape = RoundedCornerShape(22.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = item,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
            )

            IconButton(
                onClick = { onDeleteClick() },
                modifier = Modifier.size(24.dp)

            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Удалить",
                    tint = Color.Black
                )
            }
        }
    }
}

