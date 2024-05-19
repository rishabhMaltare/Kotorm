@file:OptIn(ExperimentalResourceApi::class)

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.IllegalTimeZoneException
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotorm.composeapp.generated.resources.Res
import kotorm.composeapp.generated.resources.compose_multiplatform
import kotorm.composeapp.generated.resources.fr
import kotorm.composeapp.generated.resources.id
import kotorm.composeapp.generated.resources.india
import kotorm.composeapp.generated.resources.jp
import kotorm.composeapp.generated.resources.mx
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.AppTheme


data class Country(
    val name: String,
    val zone: TimeZone,
    val image: DrawableResource
)

fun currentTimeAt(location: String, zone: TimeZone): String {
    fun LocalTime.formatted() = "$hour:$minute:$second"
    val time = Clock.System.now()
    val localTime = time.toLocalDateTime(zone).time.formatted()

    return "time in\n\n$location is\n\n$localTime"
}

fun getCountryList() = listOf(
    Country("Japan", TimeZone.of("Asia/Tokyo"), Res.drawable.jp),
    Country("France", TimeZone.of("Europe/Paris"), Res.drawable.fr),
    Country("Mexico", TimeZone.of("America/Mexico_City"), Res.drawable.mx),
    Country("Indonesia", TimeZone.of("Asia/Jakarta"), Res.drawable.id),
    Country("India", TimeZone.of("Asia/Kolkata"), Res.drawable.india)
)


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(darkTheme: Boolean = false, dynamicColor: Boolean = false) {
    AppTheme(darkTheme, dynamicColor) {
        Surface {
            var showContent by remember { mutableStateOf(false) }
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Today's date is ${todaysDate()}",
                    modifier = Modifier.padding(20.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontSize = 24.sp
                )

                Button(
                    onClick = { showContent = !showContent }
                ) {
                    Text("Click me!")
                }

                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    var timeAtLocation by remember { mutableStateOf("Please select location") }
                    var showCountries by remember { mutableStateOf(false) }
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painterResource(Res.drawable.compose_multiplatform),
                            null,
                            modifier = Modifier.size(100.dp)
                        )
                        Text("Compose: $greeting")
                        Text(
                            text = "Current time here is ${currentTime()}",
                            modifier = Modifier.padding(20.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            fontSize = 20.sp
                        )

                        TextButton(
                            { showCountries = !showCountries }
                        ) {
                            Text("Select location")
                        }
                        Text(
                            text = timeAtLocation,
                            modifier = Modifier.padding(20.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            fontSize = 24.sp
                        )

                        DropdownMenu(
                            expanded = showCountries,
                            onDismissRequest = { showCountries = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            getCountryList().forEach { (name, zone, image) ->
                                DropdownMenuItem(
                                    onClick = {
                                        timeAtLocation = currentTimeAt(name, zone)
                                        showCountries = false
                                    },
                                    text = { DropDownItem(image, name) },
                                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DropDownItem(
    image: DrawableResource,
    name: String
) {
    Row(
        modifier = Modifier
            .heightIn(30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(resource = image),
            modifier = Modifier.size(20.dp).padding(5.dp),
            contentDescription = name
        )
        Text(name)
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}


fun todaysDate(): String {
    fun LocalDateTime.format() = toString().substringBefore('T')

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    return now.toLocalDateTime(zone).format()
}


fun currentTime(): String {
    fun LocalDateTime.format() = toString().substringAfter('T')

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    return now.toLocalDateTime(zone).format()
}

fun currentTimeAt(location: String): String? {
    fun LocalTime.formatted() = "$hour:$minute:$second"

    return try {
        val time = Clock.System.now()
        val zone = TimeZone.of(location)
        val localTime = time.toLocalDateTime(zone).time
        "The time in\n\n$location\n\nis\n\n${localTime.formatted()}"
    } catch (ex: IllegalTimeZoneException) {
        null
    }
}

