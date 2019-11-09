package com.github.naodroid.jetweather.ui.widget

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.TextField
import androidx.ui.core.dp
import androidx.ui.foundation.shape.border.Border
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.input.EditorStyle
import androidx.ui.layout.Container
import androidx.ui.layout.Padding
import androidx.ui.material.surface.Surface
import androidx.ui.material.themeTextStyle
import com.github.naodroid.jetweather.ui.MyTheme


@Composable
fun EditText(keyword: String, onValueChange: (String) -> Unit) {
    Surface(color = MyTheme.materialColor.secondary) {
        Padding(padding = 4.dp) {
            Container(
                expanded = true,
                height = 36.0.dp
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    border = Border(
                        color = MyTheme.materialColor.secondaryVariant,
                        width = 1.dp
                    )
                ) {
                    Padding(padding = 4.dp) {
                        TextField(
                            value = keyword,
                            editorStyle = EditorStyle(
                                textStyle = (+themeTextStyle { body1 }).copy(
                                    color = MyTheme.materialColor.onBackground,
                                    background = Color.Transparent
                                ),
                                selectionColor = null
                            ),
                            onValueChange = onValueChange
                        )
                    }
                }
            }
        }
    }
}
