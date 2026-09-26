package com.ktvincco.tsplit.data

import android.graphics.Color
import android.util.Log

class Graphics {


    fun drawGrid(surface: Surface2D, startX: Float, startY: Float, endX: Float, endY: Float,
                 rows: Int, columns: Int, thickness: Float): Surface2D {
        // Draw grid
        var position = 0F
        while (position in startX..endX) {
            surface.drawObject(
                Line(
                    position, 0F,
                    position, surface.height.toFloat(), Color.GRAY, thickness
                )
            )
            position += (endX - startX) / columns
        }
        position = 0F
        while (position in startY..endY) {
            surface.drawObject(
                Line(
                    0F, position,
                    surface.width.toFloat(), position, Color.GRAY, thickness
                )
            )
            position += (endY - startY) / rows
        }
        return surface
    }


    fun drawInputkey(surface: Surface2D, inputkey: Inputkey,
                     position: Pair<Float, Float>, size: Pair<Float, Float>): Surface2D {

        // Get button for position
        val color = Color.RED

        // Draw text
        if (inputkey.previewText != null) {
            surface.drawObject(
                Text(
                    inputkey.previewText, position.first + 40F,
                    position.second - 40F, 72F, color
                )
            )
        }

        // Draw indicators
        // Stack or gesture
        if (inputkey.circleIndicator || inputkey.triangleIndicator) {
            val x = position.first + size.first - 40F
            val y = position.second - size.second + 40F
            surface.drawObject(
                Circle(
                    x, y, 10F, Color.RED
                )
            )
        }
        // Shift
        if (inputkey.squareIndicator) {
            val x = position.first + 40F
            val y = position.second - size.second + 40F
            surface.drawObject(
                Rectangle(
                    x, y, 20F, 20F, Color.RED
                )
            )
        }

        return surface
    }


    fun drawTouch(surface: Surface2D, startX: Float, startY: Float, endX: Float, endY: Float,
                  gridX: Int, gridY: Int, rows: Int, columns: Int): Surface2D {

        // Draw touch
        val size = Pair((endX - startX) / columns, (endY - startY) / rows)
        surface.drawObject(
            RectangleGradient(
                (gridX * size.first) + startX,
                (gridY * size.second) + startY,
                size.first, size.second, angle = 45F,
                colors = listOf(Color.RED, Color.BLUE).toIntArray()
            )
        )

        return surface
    }


}