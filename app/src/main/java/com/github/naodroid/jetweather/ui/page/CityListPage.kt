package com.github.naodroid.jetweather.ui.page

import androidx.compose.Composable
import androidx.compose.State
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.TextField
import androidx.ui.core.dp
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.border.Border
import androidx.ui.graphics.Color
import androidx.ui.input.EditorStyle
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.CrossAxisAlignment
import androidx.ui.layout.FlexColumn
import androidx.ui.layout.LayoutSize
import androidx.ui.layout.Padding
import androidx.ui.layout.Row
import androidx.ui.layout.WidthSpacer
import androidx.ui.material.Button
import androidx.ui.material.TextButtonStyle
import androidx.ui.material.surface.Surface
import androidx.ui.material.themeTextStyle
import androidx.ui.tooling.preview.Preview
import com.github.naodroid.jetweather.data.entity.City
import com.github.naodroid.jetweather.data.state.useCityList
import java.util.*

@Composable
fun CityListPage() {
    val keywordState = +state { "" }
    val cityList = useCityList().filter(keywordState.value)

    FlexColumn(
        crossAxisSize = LayoutSize.Expand,
        mainAxisSize = LayoutSize.Expand
    ) {
        inflexible {
            KeywordInput(keywordState = keywordState)
        }
        flexible(flex = 1.0f) {
            CityListTable(cityList.take(100))
        }
    }
}

private fun List<City>.filter(keyword: String): List<City> =
    if (keyword.isBlank()) {
        this
    } else {
        val k = keyword.toLowerCase(Locale.getDefault())
        this.filter {
            it.name.toLowerCase(Locale.getDefault()).contains(k)
        }
    }

@Composable
private fun KeywordInput(keywordState: State<String>) {
    Surface(color = Color(0xFFD0D0D0)) {
        Padding(padding = 4.dp) {
            Container(
                expanded = false,
                height = 36.0.dp
            ) {
                Surface(
                    border = Border(
                        color = Color.Black,
                        width = 1.dp
                    )
                ) {
                    Padding(padding = 4.dp) {
                        TextField(
                            value = keywordState.value,
                            editorStyle = EditorStyle(
                                textStyle = (+themeTextStyle { body1 }).copy(
                                    color = Color.Black
                                ),
                                selectionColor = null
                            ),
                            onValueChange = {
                                keywordState.value = it
                            })
                    }
                }
            }
        }
    }
}

@Composable
private fun CityListTable(cityList: List<City>) {
    VerticalScroller {
        Column(
            crossAxisSize = LayoutSize.Expand,
            mainAxisSize = LayoutSize.Expand
        ) {
            cityList.map {
                CityRow(city = it, onCityClick = { (city) ->
                    println(city)
                })
            }
        }
    }
}


@Composable
private fun CityRow(city: City, onCityClick: ((City) -> Unit)) {
    Padding(padding = 8.dp) {
        Button(
            onClick = { onCityClick(city) },
            style = TextButtonStyle().copy(
                color = Color.Transparent
            )
        ) {
            Row(
                mainAxisSize = LayoutSize.Expand,
                crossAxisSize = LayoutSize.Wrap
            ) {
                Text(
                    text = city.name,
                    style = (+themeTextStyle { body1 }).copy(
                        color = Color.Black
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewGreeting() {
    //doesn't work when using Row, Buttons or others.
    Row(
        mainAxisSize = LayoutSize.Expand,
        crossAxisAlignment = CrossAxisAlignment.Center
    ) {
        WidthSpacer(16.dp)
        Text(
            text = "HOGE",
            style = (+themeTextStyle { body2 })
        )
    }
}