package com.example.cw2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cw2.ui.theme.CW2Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class JerseyRetrieval : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jerseys()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Jerseys() {
    var clubName by rememberSaveable { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Retrieve Jerseys",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = clubName,
                onValueChange = { clubName = it },
                modifier = Modifier
                    .border(1.dp, Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { })
            {
                Text("Search")
            }
        }
    }

}

suspend fun fetchJerseys(keyword: String): String {
    val url_String = "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=$keyword"
    val url = URL(url_String)
    val con: HttpURLConnection = url.openConnection() as HttpURLConnection

    var stb = StringBuilder()
    withContext(Dispatchers.IO) {
        var bf = BufferedReader(InputStreamReader(con.inputStream))
        var line: String? = bf.readLine()
        while (line != null) {
            stb.append(line + "\n")
            line = bf.readLine()
        }
    }
    return parseJSON(stb)
}



fun parseJSON2(stb: StringBuilder): List<JerseyAndName> {
    val json = JSONObject(stb.toString())

    var allLeagues = StringBuilder()
    var jsonArray: JSONArray = json.getJSONArray("teams")
    val clubJerseys = mutableListOf<JerseyAndName>()

    for (i in 0..<jsonArray.length()) {
        val team = jsonArray.getJSONObject(i)
        val club=JerseyAndName(
            name=team.getString("strTeam"),
            strTeamJersey = team.getString("strTeamJersey")
        )
    }
    return clubJerseys
}