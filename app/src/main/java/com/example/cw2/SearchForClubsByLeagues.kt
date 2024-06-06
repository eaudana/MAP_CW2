package com.example.cw2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

lateinit var leagueDao2: DAO2

class SearchForClubsByLeagues : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(
            this, CW2Database::class.java,
            "CW2Database"
        ).build()

        leagueDao2 = db.fLeagueDao2()
        setContent {

            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                var leagueInfo by rememberSaveable { mutableStateOf("") }
                var textB by rememberSaveable { mutableStateOf("") } //Text typed in the text-box by the user.
                val scope2 = rememberCoroutineScope()

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
                        Row() {
                            Button(onClick = {
                                scope2.launch {
                                    leagueInfo = fetchLeagues(textB)
                                }

                            }
                            )
                            {
                                Text("Retrieve Clubs")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(onClick = {
                                val splitLeagues =
                                    leagueInfo.split("\n\n")        //By splitting leagueInfo using "\n\n", I separated the leagues from each other.Using this and a for loop, I iterated through each league to get the relevant info.
                                for (league in splitLeagues) {
                                    val Name =
                                        league.lines().find { it.startsWith("Name:") }  //
                                            ?.substringAfter(":")
                                    val idTeam =
                                        league.lines().find { it.startsWith("idTeam:") }
                                            ?.substringAfter(":")
                                    val strLeague =
                                        league.lines().find { it.startsWith("strLeague:") }
                                            ?.substringAfter(":")
                                    val strAlternate =
                                        league.lines().find { it.startsWith("strAlternate:") }
                                            ?.substringAfter(":")
                                    val intFormed =
                                        league.lines().find { it.startsWith("intFormed:") }
                                            ?.substringAfter(":")
                                    val idLeague =
                                        league.lines().find { it.startsWith("idLeague:") }
                                            ?.substringAfter(":")
                                    val strStadium =
                                        league.lines().find { it.startsWith("strStadium:") }
                                            ?.substringAfter(":")
                                    val strKeywords =
                                        league.lines().find { it.startsWith("strKeywords:") }
                                            ?.substringAfter(":")
                                    val strStadiumLocation =
                                        league.lines().find { it.startsWith("strStadiumLocation:") }
                                            ?.substringAfter(":")
                                    val intStadiumCapacity =
                                        league.lines().find { it.startsWith("strStadiumCapacity:") }
                                            ?.substringAfter(":")
                                    val strWebsite =
                                        league.lines().find { it.startsWith("strWebsite:") }
                                            ?.substringAfter(":")
                                    val strTeamJersey =
                                        league.lines().find { it.startsWith("strTeamJersey:") }
                                            ?.substringAfter(":")
                                    val strTeamLogo =
                                        league.lines().find { it.startsWith("strTeamLogo:") }
                                            ?.substringAfter(":")
                                    val strTeamShort =
                                        league.lines().find { it.startsWith("strNameShort:") }
                                            ?.substringAfter(":")
                                    val strStadiumThumb =
                                        league.lines().find { it.startsWith("strStadiumThumb:") }
                                            ?.substringAfter(":")

                                    //The relevant data is stored in the above variables and then added to the LeagueTwo DB using the insertAll2 method.

                                    scope2.launch {
                                        leagueDao2.insertAll2(
                                            LeagueTwo(
                                                idTeam.toString(),
                                                Name,
                                                strTeamShort,
                                                strLeague,
                                                strAlternate,
                                                intFormed,
                                                idLeague,
                                                strStadium,
                                                strKeywords,
                                                strStadiumLocation,
                                                strStadiumThumb,
                                                intStadiumCapacity,
                                                strTeamJersey,
                                                strTeamLogo,
                                                strWebsite
                                            )
                                        )
                                    }
                                }


                            }) {
                                Text("Save clubs to Database")
                            }

                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .background(Color(0xFFE1BEE7).copy(alpha = 0.45f)),
                            text = leagueInfo,
                        )

                    }
                }
            }

        }
    }
}


suspend fun fetchLeagues(keyword: String): String {
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

fun parseJSON(stb: StringBuilder): String {
    var allLeagues = StringBuilder()
    val json = JSONObject(stb.toString())
    try {

        var jsonArray: JSONArray = json.getJSONArray("teams")

        for (i in 0..<jsonArray.length()) {

            val league: JSONObject = jsonArray[i] as JSONObject // this is a json object
            val strSport = league.optString("strSport", "")
            if (strSport.equals("Soccer")) {     //If condition was used to retrieve only soccer/football
                val name = league.optString("strTeam", "")
                val idTeam = league.optString("idTeam", "")
                val strLeague = league.optString("strLeague", "")
                val strAlternate = league.optString("strAlternate", "")
                val intFormedYear = league.optString("intFormedYear", "")
                val idLeague = league.optString("idLeague", "")
                val strStadium = league.optString("strStadium", "")
                val strKeywords = league.optString("strKeywords", "")
                val strStadiumLocation = league.optString("strStadiumLocation", "")
                val intStadiumCapacity = league.optString("intStadiumCapacity", "")
                val strWebsite = league.optString("strWebsite", "")
                val strTeamJersey = league.optString("strTeamJersey", "")
                val strTeamLogo = league.optString("strTeamLogo", "")
                val strNameShort = league.optString("strNameShort", "")
                val strStadiumThumb = league.optString("strStadiumThumb", "")

                allLeagues.append("Name:$name\n")
                allLeagues.append("idTeam:$idTeam\n")
                allLeagues.append("strLeague:$strLeague\n")
                allLeagues.append("strAlternate:$strAlternate\n")
                allLeagues.append("intFormed:$intFormedYear\n")
                allLeagues.append("idLeague:$idLeague\n")
                allLeagues.append("strStadium:$strStadium\n")
                allLeagues.append("strKeywords:$strKeywords\n")
                allLeagues.append("strStadiumLocation:$strStadiumLocation\n")
                allLeagues.append("intStadiumCapacity:$intStadiumCapacity\n")
                allLeagues.append("strWebsite:$strWebsite\n")
                allLeagues.append("strTeamJersey:$strTeamJersey\n")
                allLeagues.append("strTeamLogo:$strTeamLogo\n")
                allLeagues.append("strNameShort:$strNameShort\n")
                allLeagues.append("strStadiumThumb:$strStadiumThumb")


                allLeagues.append("\n\n")
            }
        }
    } catch (e: JSONException) {

    }
    return allLeagues.toString()
}



