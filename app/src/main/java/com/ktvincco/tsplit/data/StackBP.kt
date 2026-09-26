package com.ktvincco.tsplit.data

// ! GOOD, DO NOT TOUCH
/*
class Inputkey (val firstPosition: Pair<Int, Int>? = null, val secondPosition: Pair<Int, Int>? = null,
                val previewText: String? = null, val previewImage: String? = null,
                val inputText: String? = null, val actions: List<String>? = null,
                val gesture: String? = null, val isRemoveAllSwitchesFirst: Boolean? = null,
                val switchesToAdd: List<String>? = null, val switchesToRemove: List<String>? = null,
                val oneInputSwitches: List<String>? = null, val amount: Int? = null,
                val circleIndicator: Boolean = false, val squareIndicator: Boolean = false,
                val triangleIndicator: Boolean = false)


class Stack (private val packs: List<InputkeyPack>) {

    fun getPacksByName(packNames: List<String>): List<InputkeyPack> {
        val result = mutableListOf<InputkeyPack>()
        packs.forEach {
            if (it.name in packNames) {
                result += it
            }
        }
        return result
    }

}


class InputkeyPack (val name: String, val inputkeys: List<Inputkey?>)


val keyboardStack = Stack(listOf<InputkeyPack>(

    InputkeyPack("main",listOf<Inputkey>(

        // First layer

        Inputkey(Pair(1, 4), previewText = "SC"),
        Inputkey(Pair(2, 4), previewText = "SP", inputText = " "),

        Inputkey(Pair(5, 4), previewText = "SP", inputText = " "),
        Inputkey(Pair(6, 4), previewText = "DE", gesture = "deleteMultipleCharactersFromTheLeft", actions = listOf("deleteCharacterFromTheLeft", "deleteMultipleCharactersFromTheLeft")),

        // Combination

        Inputkey(Pair(2, 4), Pair(5, 2), previewText = "EN", actions = listOf("enter")),
        Inputkey(Pair(2, 4), Pair(5, 3), previewText = "\\N", inputText = "\n"),
        Inputkey(Pair(2, 4), Pair(5, 4), previewText = "TA", inputText = "  "),
        Inputkey(Pair(1, 1), Pair(5, 4), previewText = "↑", circleIndicator = true),
        Inputkey(Pair(2, 1),Pair(5, 4), previewText = "↓", circleIndicator = true),
        Inputkey(Pair(1, 2),Pair(5, 4), previewText = "←", circleIndicator = true, gesture = "moveCursor", amount = -1),
        Inputkey(Pair(2, 2),Pair(5, 4), previewText = "→", circleIndicator = true, gesture = "moveCursor", amount = 1),

        // Script change

        // Languages
        Inputkey(Pair(1, 4), Pair(5, 2), previewText = "LA", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("latin")),
        Inputkey(Pair(1, 4), Pair(5, 1), previewText = "КИ", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("cyrillic")),

        // Symbols
        Inputkey(Pair(1, 4), Pair(4, 1), previewText = "★", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("symbols")),
        Inputkey(Pair(1, 4), Pair(4, 2), previewText = "1", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("math")),
        Inputkey(Pair(1, 4), Pair(4, 3), previewText = "[ ]", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("brackets")),
        Inputkey(Pair(1, 4), Pair(3, 1), previewText = "✿", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("symbols2")),
        Inputkey(Pair(1, 4), Pair(3, 2), previewText = "@", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("math")),
        Inputkey(Pair(1, 4), Pair(3, 3), previewText = "✦", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("symbols3")),
        Inputkey(Pair(1, 4), Pair(4, 4), previewText = "ß", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("letters")),
        Inputkey(Pair(1, 4), Pair(3, 4), previewText = "Ø", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("letters2")),

        // Special
        Inputkey(Pair(1, 4), Pair(5, 4), previewText = "SD", actions = listOf("switchTheSound")),

        )),

    InputkeyPack("latin",listOf<Inputkey>(

        // First layer

        Inputkey(Pair(1, 1), previewText = "I", inputText = "i"),
        Inputkey(Pair(2, 1), previewText = "N", inputText = "n"),
        Inputkey(Pair(3, 1), previewText = "Y", inputText = "y"),
        Inputkey(Pair(4, 1), previewText = "W", inputText = "w"),
        Inputkey(Pair(5, 1), previewText = "S", inputText = "s"),
        Inputkey(Pair(6, 1), previewText = "R", inputText = "r"),

        Inputkey(Pair(1, 2), previewText = "A", inputText = "a"),
        Inputkey(Pair(2, 2), previewText = "T", inputText = "t"),
        Inputkey(Pair(3, 2), previewText = "L", inputText = "l"),
        Inputkey(Pair(4, 2), previewText = "U", inputText = "u"),
        Inputkey(Pair(5, 2), previewText = "E", inputText = "e"),
        Inputkey(Pair(6, 2), previewText = "O", inputText = "o"),

        Inputkey(Pair(2, 3), previewText = "H", inputText = "h"),
        Inputkey(Pair(3, 3), previewText = "C", inputText = "c"),
        Inputkey(Pair(4, 3), previewText = "M", inputText = "m"),
        Inputkey(Pair(5, 3), previewText = "D", inputText = "d"),

        Inputkey(Pair(3, 4), previewText = "G", inputText = "g"),
        Inputkey(Pair(4, 4), previewText = "F", inputText = "f"),

        // Switches

        Inputkey(Pair(1, 3), previewText = "SH", oneInputSwitches = listOf("latinShifted")),
        Inputkey(Pair(6, 3), previewText = "SH", oneInputSwitches = listOf("latinShifted")),
        Inputkey(Pair(1, 3), Pair(6, 3), previewText = "SH!", switchesToAdd = listOf("latinShifted")),

        // Combination

        Inputkey(Pair(2, 2), Pair(5, 1), previewText = "V", inputText = "v"),
        Inputkey(Pair(1, 1), Pair(6, 1), previewText = "J", inputText = "j"),
        Inputkey(Pair(1, 2), Pair(6, 2), previewText = "Z", inputText = "z"),
        Inputkey(Pair(2, 1), Pair(5, 2), previewText = "B", inputText = "b"),
        Inputkey(Pair(2, 1), Pair(5, 1), previewText = "P", inputText = "p"),
        Inputkey(Pair(2, 2), Pair(6, 1), previewText = "X", inputText = "x"),
        Inputkey(Pair(1, 1), Pair(5, 2), previewText = "Q", inputText = "q"),
        Inputkey(Pair(2, 2), Pair(5, 2), previewText = "K", inputText = "k"),

        )),

    InputkeyPack("latinShifted",listOf<Inputkey>(

        // First layer

        Inputkey(Pair(1, 1), previewText = "I", inputText = "I", squareIndicator = true),
        Inputkey(Pair(2, 1), previewText = "N", inputText = "N", squareIndicator = true),
        Inputkey(Pair(3, 1), previewText = "Y", inputText = "Y", squareIndicator = true),
        Inputkey(Pair(4, 1), previewText = "W", inputText = "W", squareIndicator = true),
        Inputkey(Pair(5, 1), previewText = "S", inputText = "S", squareIndicator = true),
        Inputkey(Pair(6, 1), previewText = "R", inputText = "R", squareIndicator = true),

        Inputkey(Pair(1, 2), previewText = "A", inputText = "A", squareIndicator = true),
        Inputkey(Pair(2, 2), previewText = "T", inputText = "T", squareIndicator = true),
        Inputkey(Pair(3, 2), previewText = "L", inputText = "L", squareIndicator = true),
        Inputkey(Pair(4, 2), previewText = "U", inputText = "U", squareIndicator = true),
        Inputkey(Pair(5, 2), previewText = "E", inputText = "E", squareIndicator = true),
        Inputkey(Pair(6, 2), previewText = "O", inputText = "O", squareIndicator = true),

        Inputkey(Pair(2, 3), previewText = "H", inputText = "H", squareIndicator = true),
        Inputkey(Pair(3, 3), previewText = "C", inputText = "C", squareIndicator = true),
        Inputkey(Pair(4, 3), previewText = "M", inputText = "M", squareIndicator = true),
        Inputkey(Pair(5, 3), previewText = "D", inputText = "D", squareIndicator = true),

        Inputkey(Pair(3, 4), previewText = "G", inputText = "G", squareIndicator = true),
        Inputkey(Pair(4, 4), previewText = "F", inputText = "F", squareIndicator = true),

        // Switches

        Inputkey(Pair(1, 3), previewText = "XX", switchesToRemove = listOf("latinShifted")),
        Inputkey(Pair(6, 3), previewText = "XX", switchesToRemove = listOf("latinShifted")),

        // Combination

        Inputkey(Pair(2, 2), Pair(5, 1), previewText = "V", inputText = "V", squareIndicator = true),
        Inputkey(Pair(1, 1), Pair(6, 1), previewText = "J", inputText = "J", squareIndicator = true),
        Inputkey(Pair(1, 2), Pair(6, 2), previewText = "Z", inputText = "Z", squareIndicator = true),
        Inputkey(Pair(2, 1), Pair(5, 2), previewText = "B", inputText = "B", squareIndicator = true),
        Inputkey(Pair(2, 1), Pair(5, 1), previewText = "P", inputText = "P", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(6, 1), previewText = "X", inputText = "X", squareIndicator = true),
        Inputkey(Pair(1, 1), Pair(5, 2), previewText = "Q", inputText = "Q", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(5, 2), previewText = "K", inputText = "K", squareIndicator = true),

        )),

    InputkeyPack("cyrillic",listOf<Inputkey>(

        // First layer

        Inputkey(Pair(1, 1), previewText = "И", inputText = "и"),
        Inputkey(Pair(2, 1), previewText = "Н", inputText = "н"),
        Inputkey(Pair(3, 1), previewText = "Я", inputText = "я"),
        Inputkey(Pair(4, 1), previewText = "В", inputText = "в"),
        Inputkey(Pair(5, 1), previewText = "С", inputText = "с"),
        Inputkey(Pair(6, 1), previewText = "Р", inputText = "р"),

        Inputkey(Pair(1, 2), previewText = "А", inputText = "а"),
        Inputkey(Pair(2, 2), previewText = "Т", inputText = "т"),
        Inputkey(Pair(3, 2), previewText = "Л", inputText = "л"),
        Inputkey(Pair(4, 2), previewText = "У", inputText = "у"),
        Inputkey(Pair(5, 2), previewText = "Е", inputText = "е"),
        Inputkey(Pair(6, 2), previewText = "О", inputText = "о"),

        Inputkey(Pair(2, 3), previewText = "Ь", inputText = "ь"),
        Inputkey(Pair(3, 3), previewText = "К", inputText = "к"),
        Inputkey(Pair(4, 3), previewText = "М", inputText = "м"),
        Inputkey(Pair(5, 3), previewText = "D", inputText = "д"),

        Inputkey(Pair(3, 4), previewText = "Ы", inputText = "ы"),
        Inputkey(Pair(4, 4), previewText = "П", inputText = "п"),

        // Switches

        Inputkey(Pair(1, 3), previewText = "SH", oneInputSwitches = listOf("cyrillicShifted")),
        Inputkey(Pair(6, 3), previewText = "SH", oneInputSwitches = listOf("cyrillicShifted")),
        Inputkey(Pair(1, 3), Pair(6, 3), previewText = "SH!", switchesToAdd = listOf("cyrillicShifted")),

        // Combination

        Inputkey(Pair(2, 2), Pair(5, 1), previewText = "Ж", inputText = "ж"),
        Inputkey(Pair(1, 1), Pair(6, 1), previewText = "Й", inputText = "й"),
        Inputkey(Pair(1, 2), Pair(6, 2), previewText = "З", inputText = "з"),
        Inputkey(Pair(2, 1), Pair(5, 2), previewText = "Б", inputText = "б"),
        Inputkey(Pair(2, 1), Pair(5, 1), previewText = "Ч", inputText = "ч"),
        Inputkey(Pair(2, 2), Pair(6, 1), previewText = "Х", inputText = "х"),
        Inputkey(Pair(1, 1), Pair(5, 2), previewText = "Ю", inputText = "ю"),
        Inputkey(Pair(2, 2), Pair(5, 2), previewText = "Г", inputText = "г"),
        Inputkey(Pair(1, 2), Pair(5, 2), previewText = "Ш", inputText = "ш"),
        Inputkey(Pair(2, 2), Pair(6, 2), previewText = "Щ", inputText = "щ"),
        Inputkey(Pair(2, 2), Pair(5, 3), previewText = "Ц", inputText = "ц"),
        Inputkey(Pair(2, 3), Pair(5, 2), previewText = "Ф", inputText = "ф"),
        Inputkey(Pair(1, 2), Pair(5, 1), previewText = "Э", inputText = "э"),
        Inputkey(Pair(2, 1), Pair(6, 2), previewText = "Ё", inputText = "ё"),
        Inputkey(Pair(2, 3), Pair(5, 3), previewText = "Ъ", inputText = "ъ"),
        Inputkey(Pair(1, 1), Pair(5, 1), previewText = "I", inputText = "i"),
        Inputkey(Pair(2, 1), Pair(6, 1), previewText = "Ї", inputText = "ї"),
        Inputkey(Pair(1, 1), Pair(6, 2), previewText = "Ґ", inputText = "ґ"),
        Inputkey(Pair(1, 2), Pair(6, 1), previewText = "'", inputText = "'"),

        )),

    InputkeyPack("cyrillicShifted",listOf<Inputkey>(

        // First layer

        Inputkey(Pair(1, 1), previewText = "И", inputText = "И", squareIndicator = true),
        Inputkey(Pair(2, 1), previewText = "Н", inputText = "Н", squareIndicator = true),
        Inputkey(Pair(3, 1), previewText = "Я", inputText = "Я", squareIndicator = true),
        Inputkey(Pair(4, 1), previewText = "В", inputText = "В", squareIndicator = true),
        Inputkey(Pair(5, 1), previewText = "С", inputText = "С", squareIndicator = true),
        Inputkey(Pair(6, 1), previewText = "Р", inputText = "Р", squareIndicator = true),

        Inputkey(Pair(1, 2), previewText = "А", inputText = "А", squareIndicator = true),
        Inputkey(Pair(2, 2), previewText = "Т", inputText = "Т", squareIndicator = true),
        Inputkey(Pair(3, 2), previewText = "Л", inputText = "Л", squareIndicator = true),
        Inputkey(Pair(4, 2), previewText = "У", inputText = "У", squareIndicator = true),
        Inputkey(Pair(5, 2), previewText = "Е", inputText = "Е", squareIndicator = true),
        Inputkey(Pair(6, 2), previewText = "О", inputText = "О", squareIndicator = true),

        Inputkey(Pair(2, 3), previewText = "Ь", inputText = "Ь", squareIndicator = true),
        Inputkey(Pair(3, 3), previewText = "К", inputText = "К", squareIndicator = true),
        Inputkey(Pair(4, 3), previewText = "М", inputText = "М", squareIndicator = true),
        Inputkey(Pair(5, 3), previewText = "D", inputText = "D", squareIndicator = true),

        Inputkey(Pair(3, 4), previewText = "Ы", inputText = "Ы", squareIndicator = true),
        Inputkey(Pair(4, 4), previewText = "П", inputText = "П", squareIndicator = true),

        // Switches

        Inputkey(Pair(1, 3), previewText = "XX", switchesToRemove = listOf("cyrillicShifted")),
        Inputkey(Pair(6, 3), previewText = "XX", switchesToRemove = listOf("cyrillicShifted")),

        // Combination

        Inputkey(Pair(2, 2), Pair(5, 1), previewText = "Ж", inputText = "Ж", squareIndicator = true),
        Inputkey(Pair(1, 1), Pair(6, 1), previewText = "Й", inputText = "Й", squareIndicator = true),
        Inputkey(Pair(1, 2), Pair(6, 2), previewText = "З", inputText = "З", squareIndicator = true),
        Inputkey(Pair(2, 1), Pair(5, 2), previewText = "Б", inputText = "Б", squareIndicator = true),
        Inputkey(Pair(2, 1), Pair(5, 1), previewText = "Ч", inputText = "Ч", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(6, 1), previewText = "Х", inputText = "Х", squareIndicator = true),
        Inputkey(Pair(1, 1), Pair(5, 2), previewText = "Ю", inputText = "Ю", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(5, 2), previewText = "Г", inputText = "Г", squareIndicator = true),
        Inputkey(Pair(1, 2), Pair(5, 2), previewText = "Ш", inputText = "Ш", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(6, 2), previewText = "Щ", inputText = "Щ", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(5, 3), previewText = "Ц", inputText = "Ц", squareIndicator = true),
        Inputkey(Pair(2, 3), Pair(5, 2), previewText = "Ф", inputText = "Ф", squareIndicator = true),
        Inputkey(Pair(1, 2), Pair(5, 1), previewText = "Э", inputText = "Э", squareIndicator = true),
        Inputkey(Pair(2, 1), Pair(6, 2), previewText = "Ё", inputText = "Ё", squareIndicator = true),
        Inputkey(Pair(2, 3), Pair(5, 3), previewText = "Ъ", inputText = "Ъ", squareIndicator = true),
        Inputkey(Pair(1, 1), Pair(5, 1), previewText = "I", inputText = "I", squareIndicator = true),
        Inputkey(Pair(2, 1), Pair(6, 1), previewText = "Ї", inputText = "Ї", squareIndicator = true),
        Inputkey(Pair(1, 1), Pair(6, 2), previewText = "Ґ", inputText = "Ґ", squareIndicator = true),
        Inputkey(Pair(1, 2), Pair(6, 1), previewText = "'", inputText = "'"),

        )),

    // ! HERE IS THE BLANK

    // Symbols

    InputkeyPack("blank",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "", inputText = ""),
        Inputkey(Pair(2, 1), previewText = "", inputText = ""),
        Inputkey(Pair(3, 1), previewText = "", inputText = ""),
        Inputkey(Pair(4, 1), previewText = "", inputText = ""),
        Inputkey(Pair(5, 1), previewText = "", inputText = ""),
        Inputkey(Pair(6, 1), previewText = "", inputText = ""),

        Inputkey(Pair(1, 2), previewText = "", inputText = ""),
        Inputkey(Pair(2, 2), previewText = "", inputText = ""),
        Inputkey(Pair(3, 2), previewText = "", inputText = ""),
        Inputkey(Pair(4, 2), previewText = "", inputText = ""),
        Inputkey(Pair(5, 2), previewText = "", inputText = ""),
        Inputkey(Pair(6, 2), previewText = "", inputText = ""),

        Inputkey(Pair(1, 3), previewText = "", inputText = ""),
        Inputkey(Pair(2, 3), previewText = "", inputText = ""),
        Inputkey(Pair(3, 3), previewText = "", inputText = ""),
        Inputkey(Pair(4, 3), previewText = "", inputText = ""),
        Inputkey(Pair(5, 3), previewText = "", inputText = ""),
        Inputkey(Pair(6, 3), previewText = "", inputText = ""),

        Inputkey(Pair(3, 4), previewText = "", inputText = ""),
        Inputkey(Pair(4, 4), previewText = "", inputText = ""),

        )),

    // ! SOME MATH HERE, BUT I WANT BETTER. KEEP ALL MATH IN ONE LANGUAGE PACK (but you can use switches trough layout)

    InputkeyPack("math",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "√", inputText = "√"),
        Inputkey(Pair(2, 1), previewText = "*", inputText = "*"),
        Inputkey(Pair(3, 1), previewText = "+", inputText = "+"),
        Inputkey(Pair(4, 1), previewText = "1", inputText = "1"),
        Inputkey(Pair(5, 1), previewText = "2", inputText = "2"),
        Inputkey(Pair(6, 1), previewText = "3", inputText = "3"),

        Inputkey(Pair(1, 2), previewText = "÷", inputText = "÷"),
        Inputkey(Pair(2, 2), previewText = "/", inputText = "/"),
        Inputkey(Pair(3, 2), previewText = "-", inputText = "-"),
        Inputkey(Pair(4, 2), previewText = "4", inputText = "4"),
        Inputkey(Pair(5, 2), previewText = "5", inputText = "5"),
        Inputkey(Pair(6, 2), previewText = "6", inputText = "6"),

        Inputkey(Pair(1, 3), previewText = "±", inputText = "±"),
        Inputkey(Pair(2, 3), previewText = "≈", inputText = "≈"),
        Inputkey(Pair(3, 3), previewText = "≠", inputText = "≠"),
        Inputkey(Pair(4, 3), previewText = "7", inputText = "7"),
        Inputkey(Pair(5, 3), previewText = "8", inputText = "8"),
        Inputkey(Pair(6, 3), previewText = "9", inputText = "9"),

        Inputkey(Pair(3, 4), previewText = "=", inputText = "="),
        Inputkey(Pair(4, 4), previewText = "0", inputText = "0"),

        /*

        Inputkey(previewText = "(", inputText = "("),
        Inputkey(previewText = ")", inputText = ")"),
        Inputkey(previewText = "±", inputText = "±"),
        Inputkey(previewText = "¹", inputText = "¹"),
        Inputkey(previewText = "²", inputText = "²"),
        Inputkey(previewText = "³", inputText = "³"),

        Inputkey(previewText = ".", inputText = "."),
        Inputkey(previewText = ",", inputText = ","),
        Inputkey(previewText = "%", inputText = "%"),
        Inputkey(previewText = "⁴", inputText = "⁴"),
        Inputkey(previewText = "⁵", inputText = "⁵"),
        Inputkey(previewText = "⁶", inputText = "⁶"),

        Inputkey(previewText = "!", inputText = "!"),
        Inputkey(previewText = "f", inputText = "f"),
        Inputkey(previewText = "÷", inputText = "÷"),
        Inputkey(previewText = "⁷", inputText = "⁷"),
        Inputkey(previewText = "⁸", inputText = "⁸"),
        Inputkey(previewText = "⁹", inputText = "⁹"),

        Inputkey(previewText = "π", inputText = "π"),
        Inputkey(previewText = "∞", inputText = "∞"),
        Inputkey(previewText = "√", inputText = "√"),
        Inputkey(previewText = "ⁿ", inputText = "ⁿ"),
        Inputkey(previewText = "⁰", inputText = "⁰"),
        Inputkey(previewText = "°", inputText = "°"),

         */

        )),

    // ! I PROBABLY WANT BETTER BRACKETS

    InputkeyPack("brackets",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "{", inputText = "{"),
        Inputkey(Pair(2, 1), previewText = "}", inputText = "}"),
        Inputkey(Pair(3, 1), previewText = "[", inputText = "["),
        Inputkey(Pair(4, 1), previewText = "]", inputText = "]"),
        Inputkey(Pair(5, 1), previewText = "(", inputText = "("),
        Inputkey(Pair(6, 1), previewText = ")", inputText = ")"),

        Inputkey(Pair(1, 2), previewText = "<", inputText = "<"),
        Inputkey(Pair(2, 2), previewText = ">", inputText = ">"),
        Inputkey(Pair(3, 2), previewText = "‹", inputText = "‹"),
        Inputkey(Pair(4, 2), previewText = "›", inputText = "›"),
        Inputkey(Pair(5, 2), previewText = "«", inputText = "«"),
        Inputkey(Pair(6, 2), previewText = "»", inputText = "»"),

        Inputkey(Pair(1, 3), previewText = "｢", inputText = "｢"),
        Inputkey(Pair(2, 3), previewText = "｣", inputText = "｣"),
        Inputkey(Pair(3, 3), previewText = "‚", inputText = "‚"),
        Inputkey(Pair(4, 3), previewText = "‘", inputText = "‘"),
        Inputkey(Pair(5, 3), previewText = "\"", inputText = "\""),
        Inputkey(Pair(6, 3), previewText = "'", inputText = "'"),

        Inputkey(Pair(3, 4), previewText = "⦗", inputText = "⦗"),
        Inputkey(Pair(4, 4), previewText = "⦘", inputText = "⦘"),

        )),

    ))

// ! THINGS TO PUT IN REMAINING LANGUAGE PACKS

/*
val keyboardStack = Stack(listOf<Layer>(

    // Special letters

    Layer("specialLetters1",listOf<Inputkey>(

        Inputkey(previewText = "ø", inputText = "ø", referenceLayerName = "variantsOfO1", stackIndicator = true),
        Inputkey(previewText = "æ", inputText = "æ", referenceLayerName = "variantsOfA1", stackIndicator = true),
        Inputkey(previewText = "œ", inputText = "œ", referenceLayerName = "variantsOfO1", stackIndicator = true),
        Inputkey(previewText = "Ω", inputText = "Ω"),

        Inputkey(previewText = "Π", inputText = "Π"),
        Inputkey(previewText = "ā", inputText = "ā", referenceLayerName = "variantsOfA1", stackIndicator = true),
        Inputkey(previewText = "ō", inputText = "ō", referenceLayerName = "variantsOfO1", stackIndicator = true),
        Inputkey(previewText = "ē", inputText = "ē", referenceLayerName = "variantsOfE1", stackIndicator = true),

        Inputkey(previewText = "π", inputText = "π"),
        Inputkey(previewText = "ū", inputText = "ū", referenceLayerName = "variantsOfU1", stackIndicator = true),
        Inputkey(previewText = "ī", inputText = "ī", referenceLayerName = "variantsOfI1", stackIndicator = true),
        Inputkey(previewText = "ñ", inputText = "ñ"),

        Inputkey(previewText = "μ", inputText = "μ"),
        Inputkey(previewText = "ẞ", inputText = "ẞ"),
        Inputkey(previewText = "ß", inputText = "ß"),
        Inputkey(previewText = "ç", inputText = "ç"),
    )),

    Layer("specialLettersCapital1",listOf<Inputkey>(

        Inputkey(previewText = "Ø", inputText = "Ø", referenceLayerName = "variantsOfOCapital1", stackIndicator = true),
        Inputkey(previewText = "Æ", inputText = "Æ", referenceLayerName = "variantsOfACapital1", stackIndicator = true),
        Inputkey(previewText = "Œ", inputText = "Œ", referenceLayerName = "variantsOfOCapital1", stackIndicator = true),

        Inputkey(previewText = "Ã", inputText = "Ã", referenceLayerName = "variantsOfACapital1", stackIndicator = true),
        Inputkey(previewText = "Ō", inputText = "Ō", referenceLayerName = "variantsOfOCapital1", stackIndicator = true),
        Inputkey(previewText = "Ē", inputText = "Ē", referenceLayerName = "variantsOfECapital1", stackIndicator = true),

        Inputkey(previewText = "Ū", inputText = "Ū", referenceLayerName = "variantsOfUCapital1", stackIndicator = true),
        Inputkey(previewText = "Ī", inputText = "Ī", referenceLayerName = "variantsOfICapital1", stackIndicator = true),
        Inputkey(previewText = "Ñ", inputText = "Ñ"),

        Inputkey(previewText = "ẞ", inputText = "ẞ"),
        Inputkey(previewText = "ß", inputText = "ß"),
        Inputkey(previewText = "Ç", inputText = "Ç"),

    Layer("currencies1",listOf<Inputkey>(

        Inputkey(previewText = "$", inputText = "$"),
        Inputkey(previewText = "£", inputText = "£"),
        Inputkey(previewText = "¢", inputText = "¢"),
        Inputkey(previewText = "₹", inputText = "₹"),
        Inputkey(previewText = "¥", inputText = "¥"),
        Inputkey(previewText = "₱", inputText = "₱"),

    Layer("variantsOfA1",listOf<Inputkey>(

        Inputkey(previewText = "æ", inputText = "æ"),
        Inputkey(previewText = "ã", inputText = "ã"),
        Inputkey(previewText = "ā", inputText = "ā"),
        Inputkey(previewText = "à", inputText = "à"),
        Inputkey(previewText = "á", inputText = "á"),
        Inputkey(previewText = "å", inputText = "å"),
        Inputkey(previewText = "â", inputText = "â"),
        Inputkey(previewText = "ä", inputText = "ä"),

    Layer("variantsOfACapital1",listOf<Inputkey>(

        Inputkey(previewText = "Æ", inputText = "Æ"),
        Inputkey(previewText = "Ã", inputText = "Ã"),
        Inputkey(previewText = "Ā", inputText = "Ā"),
        Inputkey(previewText = "À", inputText = "À"),
        Inputkey(previewText = "Á", inputText = "Á"),
        Inputkey(previewText = "Å", inputText = "Å"),
        Inputkey(previewText = "Â", inputText = "Â"),
        Inputkey(previewText = "Ä", inputText = "Ä"),

    Layer("variantsOfE1",listOf<Inputkey>(

        Inputkey(previewText = "ē", inputText = "ē"),
        Inputkey(previewText = "è", inputText = "è"),
        Inputkey(previewText = "é", inputText = "é"),
        Inputkey(previewText = "ê", inputText = "ê"),
        Inputkey(previewText = "ë", inputText = "ë"),

    Layer("variantsOfECapital1",listOf<Inputkey>(

        Inputkey(previewText = "Ē", inputText = "Ē"),
        Inputkey(previewText = "È", inputText = "È"),
        Inputkey(previewText = "É", inputText = "É"),
        Inputkey(previewText = "Ê", inputText = "Ê"),
        Inputkey(previewText = "Ë", inputText = "Ë"),

    Layer("variantsOfO1",listOf<Inputkey>(

        Inputkey(previewText = "œ", inputText = "œ"),
        Inputkey(previewText = "ø", inputText = "ø"),
        Inputkey(previewText = "ō", inputText = "ō"),
        Inputkey(previewText = "ò", inputText = "ò"),
        Inputkey(previewText = "ó", inputText = "ó"),
        Inputkey(previewText = "ô", inputText = "ô"),
        Inputkey(previewText = "ö", inputText = "ö"),

    Layer("variantsOfOCapital1",listOf<Inputkey>(

        Inputkey(previewText = "Œ", inputText = "Œ"),
        Inputkey(previewText = "Ø", inputText = "Ø"),
        Inputkey(previewText = "Ō", inputText = "Ō"),
        Inputkey(previewText = "Ò", inputText = "Ò"),
        Inputkey(previewText = "Ó", inputText = "Ó"),
        Inputkey(previewText = "Ô", inputText = "Ô"),
        Inputkey(previewText = "Ö", inputText = "Ö"),

    Layer("variantsOfU1",listOf<Inputkey>(

        Inputkey(previewText = "ū", inputText = "ū"),
        Inputkey(previewText = "ù", inputText = "ù"),
        Inputkey(previewText = "ú", inputText = "ú"),
        Inputkey(previewText = "û", inputText = "û"),
        Inputkey(previewText = "ü", inputText = "ü"),

    Layer("variantsOfUCapital1",listOf<Inputkey>(

        Inputkey(previewText = "Ū", inputText = "Ū"),
        Inputkey(previewText = "Ù", inputText = "Ù"),
        Inputkey(previewText = "Ú", inputText = "Ú"),
        Inputkey(previewText = "Û", inputText = "Û"),
        Inputkey(previewText = "Ü", inputText = "Ü"),

    Layer("variantsOfI1",listOf<Inputkey>(

        Inputkey(previewText = "ī", inputText = "ī"),
        Inputkey(previewText = "ì", inputText = "ì"),
        Inputkey(previewText = "í", inputText = "í"),
        Inputkey(previewText = "i", inputText = "i"),
        Inputkey(previewText = "î", inputText = "î"),
        Inputkey(previewText = "ï", inputText = "ï"),

    Layer("variantsOfICapital1",listOf<Inputkey>(

        Inputkey(previewText = "Ī", inputText = "Ī"),
        Inputkey(previewText = "Ì", inputText = "Ì"),
        Inputkey(previewText = "Í", inputText = "Í"),
        Inputkey(previewText = "I", inputText = "I"),
        Inputkey(previewText = "Î", inputText = "Î"),
        Inputkey(previewText = "Ï", inputText = "Ï"),

    // Special symbols

    Layer("specialSymbols1",listOf<Inputkey>(

        Inputkey(previewText = "@", inputText = "@"),
        Inputkey(previewText = "$", inputText = "$", referenceLayerName = "currencies1", stackIndicator = true),
        Inputkey(previewText = "&", inputText = "&"),
        Inputkey(previewText = "•", inputText = "•"),
        Inputkey(previewText = "!", inputText = "!"),
        Inputkey(previewText = "?", inputText = "?"),
        Inputkey(previewText = "‽", inputText = "‽"),
        Inputkey(previewText = ":/", inputText = "https://"),
        Inputkey(previewText = ",", inputText = ","),
        Inputkey(previewText = ".", inputText = "."),
        Inputkey(previewText = ":", inputText = ":"),
        Inputkey(previewText = ";", inputText = ";"),
        Inputkey(previewText = "✓", inputText = "✓"),
        Inputkey(previewText = "×", inputText = "×"),
        Inputkey(previewText = "\\", inputText = "\\"),
        Inputkey(previewText = "=", inputText = "="),

    Layer("specialSymbols2",listOf<Inputkey>(

        Inputkey(previewText = "~", inputText = "~"),
        Inputkey(previewText = "§", inputText = "§"),
        Inputkey(previewText = "№", inputText = "№"),
        Inputkey(previewText = "·", inputText = "·"),
        Inputkey(previewText = "¡", inputText = "¡"),
        Inputkey(previewText = "¿", inputText = "¿"),
        Inputkey(previewText = "⸮", inputText = "⸮"),
        Inputkey(previewText = "±", inputText = "±"),
        Inputkey(previewText = "←", inputText = "←"),
        Inputkey(previewText = "→", inputText = "→"),
        Inputkey(previewText = "%", inputText = "%"),
        Inputkey(previewText = "_", inputText = "_"),
        Inputkey(previewText = "↑", inputText = "↑"),
        Inputkey(previewText = "↓", inputText = "↓"),
        Inputkey(previewText = "∞", inputText = "∞"),
        Inputkey(previewText = "≈", inputText = "≈"),

    Layer("specialSymbols3",listOf<Inputkey>(

        Inputkey(previewText = "★", inputText = "★"),
        Inputkey(previewText = "¶", inputText = "¶"),
        Inputkey(previewText = "♪", inputText = "♪"),
        Inputkey(previewText = "°", inputText = "°"),
        Inputkey(previewText = "^", inputText = "^"),
        Inputkey(previewText = "∅", inputText = "∅"),
        Inputkey(previewText = "√", inputText = "√"),
        Inputkey(previewText = "|", inputText = "|"),
        Inputkey(previewText = "Ⓤ", inputText = "Ⓤ"),
        Inputkey(previewText = "™", inputText = "™"),
        Inputkey(previewText = "÷", inputText = "÷"),
        Inputkey(previewText = "—", inputText = "—"),
        Inputkey(previewText = "©", inputText = "©"),
        Inputkey(previewText = "®", inputText = "®"),
        Inputkey(previewText = "ت", inputText = "ت"),
        Inputkey(previewText = "≠", inputText = "≠"),

    Layer("specialSymbols4",listOf<Inputkey>(

        Inputkey(previewText = "╱", inputText = "╱"),
        Inputkey(previewText = "╲", inputText = "╲"),
        Inputkey(previewText = "╳", inputText = "╳"),
        Inputkey(previewText = "`", inputText = "`"),
        Inputkey(previewText = "ᯥ", inputText = "ᯥ"),
        Inputkey(previewText = "✦", inputText = "✦"),
        Inputkey(previewText = "∆", inputText = "∆"),
        Inputkey(previewText = "∇", inputText = "∇"),
        Inputkey(previewText = "∑", inputText = "∑"),
        Inputkey(previewText = "•", inputText = "•"),
        Inputkey(previewText = "ⁿ", inputText = "ⁿ"),
        Inputkey(previewText = "–", inputText = "–"),
        Inputkey(previewText = "☀", inputText = "☀"),
        Inputkey(previewText = "☁", inputText = "☁"),
        Inputkey(previewText = "‰", inputText = "‰"),
        Inputkey(previewText = "℅", inputText = "℅"),

    Layer("specialSymbols5",listOf<Inputkey>(

        Inputkey(previewText = "✿", inputText = "✿"),
        Inputkey(previewText = "❄", inputText = "❄"),
        Inputkey(previewText = "‣", inputText = "‣"),
        Inputkey(previewText = "⇄", inputText = "⇄"),
        Inputkey(previewText = "⚡", inputText = "⚡"),
        Inputkey(previewText = "☮", inputText = "☮"),
        Inputkey(previewText = "⚒", inputText = "⚒"),
        Inputkey(previewText = "☭", inputText = "☭"),
        Inputkey(previewText = "●", inputText = "●"),
        Inputkey(previewText = "▲", inputText = "▲"),
        Inputkey(previewText = "▼", inputText = "▼"),
        Inputkey(previewText = "◼", inputText = "◼"),
        Inputkey(previewText = "☽", inputText = "☽"),
        Inputkey(previewText = "☾", inputText = "☾"),
        Inputkey(previewText = "ツ", inputText = "ツ"),
        Inputkey(previewText = "☄", inputText = "☄"),
    )),
))*/