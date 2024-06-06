package com.example.cw2

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

lateinit var leagueDao3: DAO2

class SearchForClubs : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                db = Room.databaseBuilder(
                    this, CW2Database::class.java,
                    "CW2Database"
                ).build()
                leagueDao3 = db.fLeagueDao2()
                GUI()
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GUI() {
    var clubs by rememberSaveable { mutableStateOf<List<ImageAndName>>(emptyList()) }
    var textB by rememberSaveable { mutableStateOf("") }  //Text typed in the text-box by the user.
    val scope3 = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Search For Clubs",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = textB,
                onValueChange = { textB = it },
                modifier = Modifier
                    .border(1.dp, Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope3.launch {
                    clubs = leagueDao3.findLeagueWithImage(
                        textB
                    )
                }
            }

            )
            {

                Text("Search Club")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Surface( color = Color(0xFFE1BEE7).copy(alpha = 0.65f)){
                    LazyColumn() {
                        items(clubs) { item ->
                            Column(Modifier.fillMaxSize()) {
//                            if (item.strTeamLogo==null){
//                                item.strTeamLogo="https://www.j-bonsai.com/phone/data/j-bonsai/product/20220207_379e61.png"
//                                Log.d("URL",item.strTeamLogo)
//                            }
                                DisplayImageOnScreen(item.strTeamLogo)
                                Text(
                                    text = item.name.replace("_", ""),
                                    textAlign = TextAlign.Center,
                                    fontSize = 17.sp,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .align(Alignment.CenterHorizontally)

                                )
                                Divider(modifier = Modifier.padding(vertical = 16.dp),thickness=1.dp,color = Color.Black)
                            }
                        }
                    }
                }

            }

        }
    }
}



@Composable
fun ImageLoader(imageBitmap: ImageBitmap?) {
    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap,
            contentDescription = "Club Logo",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun DisplayImageOnScreen(imageURL: String) {
    var imageBitmap by remember(imageURL) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageURL) {
        val retrievedBitmap = getBitmapPicture(imageURL)
        imageBitmap = retrievedBitmap
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ImageLoader(imageBitmap = imageBitmap)
    }
}


//Reference - Referred to the code given at the bottom of Tutorial 10 in order to read the imgs from the URLs
suspend fun getBitmapPicture(pictureUrl: String): ImageBitmap? {
    return withContext(Dispatchers.IO) {
        var bitmap: ImageBitmap? = null

        try {
            val url = URL(pictureUrl)
            val con2 = url.openConnection() as HttpURLConnection
            val bfstream = BufferedInputStream(con2.inputStream)
            val decodedBitmap = BitmapFactory.decodeStream(bfstream)
            bitmap = decodedBitmap.asImageBitmap()
        } catch (e: Exception) {
            Log.d("Error","Error in handling the URL in the getBitmapPicture function")
        }
        bitmap
    }
}

