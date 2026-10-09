package com.ktvincco.tsplit.data


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
        Inputkey(Pair(1, 1), Pair(5, 4), previewText = "↑", circleIndicator = true, actions = listOf("moveCursorVertically"), gesture = "moveCursorVertically", amount = 1),
        Inputkey(Pair(2, 1),Pair(5, 4), previewText = "↓", circleIndicator = true, actions = listOf("moveCursorVertically"), gesture = "moveCursorVertically", amount = -1),
        Inputkey(Pair(1, 2),Pair(5, 4), previewText = "←", circleIndicator = true, actions = listOf("moveCursorHorizontally"), gesture = "moveCursorHorizontally", amount = -1),
        Inputkey(Pair(2, 2),Pair(5, 4), previewText = "→", circleIndicator = true, actions = listOf("moveCursorHorizontally"), gesture = "moveCursorHorizontally", amount = 1),

        // Script change

        Inputkey(Pair(1, 4), Pair(4, 1), previewText = "★", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("symbols")),
        Inputkey(Pair(1, 4), Pair(4, 2), previewText = "1", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("math")),
        Inputkey(Pair(1, 4), Pair(4, 3), previewText = "[ ]", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("brackets")),
        Inputkey(Pair(1, 4), Pair(4, 4), previewText = "$", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("letters")),

        Inputkey(Pair(1, 4), Pair(5, 2), previewText = "LA", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("latin")),
        Inputkey(Pair(1, 4), Pair(5, 1), previewText = "КИ", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("cyrillic")),
        Inputkey(Pair(1, 4), Pair(5, 3), previewText = "?!", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("punctuation")),

        Inputkey(Pair(1, 4), Pair(6, 1), previewText = "Ø", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("vowels")),
        Inputkey(Pair(1, 4), Pair(6, 2), previewText = "Iː", isRemoveAllSwitchesFirst = true, switchesToAdd = listOf("IPA")),

        // Special

        Inputkey(Pair(1, 4), Pair(5, 4), previewText = "SD", actions = listOf("switchTheSound")),

        )),

    // ----------------------------------------------------------------------------------------
    // LATIN
    // ----------------------------------------------------------------------------------------

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

        Inputkey(Pair(2, 2), Pair(5, 1), previewText = "B", inputText = "b"),
        Inputkey(Pair(1, 1), Pair(6, 1), previewText = "J", inputText = "j"),
        Inputkey(Pair(1, 2), Pair(6, 2), previewText = "Z", inputText = "z"),
        Inputkey(Pair(2, 1), Pair(5, 2), previewText = "V", inputText = "v"),
        Inputkey(Pair(2, 1), Pair(5, 1), previewText = "P", inputText = "p"),
        Inputkey(Pair(2, 2), Pair(6, 1), previewText = "X", inputText = "x"),
        Inputkey(Pair(1, 1), Pair(5, 2), previewText = "Q", inputText = "q"),
        Inputkey(Pair(2, 2), Pair(5, 2), previewText = "K", inputText = "k"),
        Inputkey(Pair(1, 2), Pair(6, 1), previewText = "'", inputText = "'"),
        Inputkey(Pair(1, 1), Pair(6, 2), previewText = "ß", inputText = "ß"),

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
        Inputkey(Pair(1, 3), Pair(6, 3), previewText = "SH!", switchesToAdd = listOf("latinShifted")),

        // Combination

        Inputkey(Pair(2, 2), Pair(5, 1), previewText = "B", inputText = "B", squareIndicator = true),
        Inputkey(Pair(1, 1), Pair(6, 1), previewText = "J", inputText = "J", squareIndicator = true),
        Inputkey(Pair(1, 2), Pair(6, 2), previewText = "Z", inputText = "Z", squareIndicator = true),
        Inputkey(Pair(2, 1), Pair(5, 2), previewText = "V", inputText = "V", squareIndicator = true),
        Inputkey(Pair(2, 1), Pair(5, 1), previewText = "P", inputText = "P", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(6, 1), previewText = "X", inputText = "X", squareIndicator = true),
        Inputkey(Pair(1, 1), Pair(5, 2), previewText = "Q", inputText = "Q", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(5, 2), previewText = "K", inputText = "K", squareIndicator = true),
        Inputkey(Pair(1, 2), Pair(6, 1), previewText = "'", inputText = "'"),
        Inputkey(Pair(1, 1), Pair(6, 2), previewText = "ẞ", inputText = "ẞ", squareIndicator = true),

        )),

    // ----------------------------------------------------------------------------------------
    // CYRILLIC
    // ----------------------------------------------------------------------------------------

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

        Inputkey(Pair(2, 2), Pair(5, 1), previewText = "Б", inputText = "б"),
        Inputkey(Pair(1, 1), Pair(6, 1), previewText = "Й", inputText = "й"),
        Inputkey(Pair(1, 2), Pair(6, 2), previewText = "З", inputText = "з"),
        Inputkey(Pair(2, 1), Pair(5, 2), previewText = "Ж", inputText = "ж"),
        Inputkey(Pair(2, 1), Pair(5, 1), previewText = "Ч", inputText = "ч"),
        Inputkey(Pair(2, 2), Pair(6, 1), previewText = "Х", inputText = "х"),
        Inputkey(Pair(1, 1), Pair(5, 2), previewText = "Ю", inputText = "ю"),
        Inputkey(Pair(2, 2), Pair(5, 2), previewText = "Г", inputText = "г"),
        Inputkey(Pair(1, 2), Pair(5, 2), previewText = "Щ", inputText = "щ"),
        Inputkey(Pair(2, 2), Pair(6, 2), previewText = "Ш", inputText = "ш"),
        Inputkey(Pair(2, 2), Pair(4, 2), previewText = "Ц", inputText = "ц"),
        Inputkey(Pair(3, 2), Pair(5, 2), previewText = "Ф", inputText = "ф"),
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
        Inputkey(Pair(5, 3), previewText = "D", inputText = "Д", squareIndicator = true),

        Inputkey(Pair(3, 4), previewText = "Ы", inputText = "Ы", squareIndicator = true),
        Inputkey(Pair(4, 4), previewText = "П", inputText = "П", squareIndicator = true),

        // Switches

        Inputkey(Pair(1, 3), previewText = "XX", switchesToRemove = listOf("cyrillicShifted")),
        Inputkey(Pair(6, 3), previewText = "XX", switchesToRemove = listOf("cyrillicShifted")),
        Inputkey(Pair(1, 3), Pair(6, 3), previewText = "SH!", switchesToAdd = listOf("cyrillicShifted")),

        // Combination

        Inputkey(Pair(2, 2), Pair(5, 1), previewText = "Б", inputText = "Б", squareIndicator = true),
        Inputkey(Pair(1, 1), Pair(6, 1), previewText = "Й", inputText = "Й", squareIndicator = true),
        Inputkey(Pair(1, 2), Pair(6, 2), previewText = "З", inputText = "З", squareIndicator = true),
        Inputkey(Pair(2, 1), Pair(5, 2), previewText = "Ж", inputText = "Ж", squareIndicator = true),
        Inputkey(Pair(2, 1), Pair(5, 1), previewText = "Ч", inputText = "Ч", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(6, 1), previewText = "Х", inputText = "Х", squareIndicator = true),
        Inputkey(Pair(1, 1), Pair(5, 2), previewText = "Ю", inputText = "Ю", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(5, 2), previewText = "Г", inputText = "Г", squareIndicator = true),
        Inputkey(Pair(1, 2), Pair(5, 2), previewText = "Щ", inputText = "Щ", squareIndicator = true),
        Inputkey(Pair(2, 2), Pair(6, 2), previewText = "Ш", inputText = "Ш", squareIndicator = true),
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

    // ----------------------------------------------------------------------------------------
    // ADDITIONAL PUNCTUATION (punctuation pack for scripts)
    // ----------------------------------------------------------------------------------------

    InputkeyPack("additionalPunctuation", listOf<Inputkey>(

        // Combination

        Inputkey(Pair(1, 3), Pair(5, 2), previewText = "!", inputText = "!"),
        Inputkey(Pair(2, 3), Pair(5, 2), previewText = "‽", inputText = "‽"),
        Inputkey(Pair(3, 3), Pair(5, 2), previewText = "?", inputText = "?"),
        Inputkey(Pair(3, 4), Pair(5, 2), previewText = "*", inputText = "*"),

        Inputkey(Pair(2, 2), Pair(4, 4), previewText = ":", inputText = ":"),
        Inputkey(Pair(2, 2), Pair(4, 3), previewText = ",", inputText = ","),
        Inputkey(Pair(2, 2), Pair(5, 3), previewText = ".", inputText = "."),
        Inputkey(Pair(2, 2), Pair(6, 3), previewText = "—", inputText = "—"),

        )),

    // ----------------------------------------------------------------------------------------
    // PUNCTUATION
    // ----------------------------------------------------------------------------------------

    InputkeyPack("punctuation",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "~", inputText = "~"),
        Inputkey(Pair(2, 1), previewText = "@", inputText = "@"),
        Inputkey(Pair(3, 1), previewText = "&", inputText = "&"),
        Inputkey(Pair(4, 1), previewText = "‽", inputText = "‽"),
        Inputkey(Pair(5, 1), previewText = "!", inputText = "!"),
        Inputkey(Pair(6, 1), previewText = "?", inputText = "?"),

        Inputkey(Pair(1, 2), previewText = "//:", inputText = "https://"),
        Inputkey(Pair(2, 2), previewText = "-", inputText = "-"),
        Inputkey(Pair(3, 2), previewText = ",", inputText = ","),
        Inputkey(Pair(4, 2), previewText = ".", inputText = "."),
        Inputkey(Pair(5, 2), previewText = "\"", inputText = "\""),
        Inputkey(Pair(6, 2), previewText = "'", inputText = "'"),

        Inputkey(Pair(1, 3), previewText = "P2", switchesToAdd = listOf("punctuation2")),
        Inputkey(Pair(2, 3), previewText = "_", inputText = "_"),
        Inputkey(Pair(3, 3), previewText = "—", inputText = "—"),
        Inputkey(Pair(4, 3), previewText = "⸮", inputText = "⸮"),
        Inputkey(Pair(5, 3), previewText = "¡", inputText = "¡"),
        Inputkey(Pair(6, 3), previewText = "¿", inputText = "¿"),

        Inputkey(Pair(3, 4), previewText = ":", inputText = ":"),
        Inputkey(Pair(4, 4), previewText = ";", inputText = ";"),

        )),

    InputkeyPack("punctuation2",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "", inputText = ""),
        Inputkey(Pair(2, 1), previewText = "§", inputText = "§"),
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

        Inputkey(Pair(1, 3), previewText = "XX", switchesToRemove = listOf("punctuation2")),
        Inputkey(Pair(2, 3), previewText = "", inputText = ""),
        Inputkey(Pair(3, 3), previewText = "|", inputText = "|"),
        Inputkey(Pair(4, 3), previewText = "", inputText = ""),
        Inputkey(Pair(5, 3), previewText = "", inputText = ""),
        Inputkey(Pair(6, 3), previewText = "", inputText = ""),

        Inputkey(Pair(3, 4), previewText = "", inputText = ""),
        Inputkey(Pair(4, 4), previewText = "", inputText = ""),

        )),

    // ----------------------------------------------------------------------------------------
    // MATH
    // ----------------------------------------------------------------------------------------

    InputkeyPack("math",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "(", inputText = "("),
        Inputkey(Pair(2, 1), previewText = ")", inputText = ")"),
        Inputkey(Pair(3, 1), previewText = "+", inputText = "+"),
        Inputkey(Pair(4, 1), previewText = "1", inputText = "1"),
        Inputkey(Pair(5, 1), previewText = "2", inputText = "2"),
        Inputkey(Pair(6, 1), previewText = "3", inputText = "3"),

        Inputkey(Pair(1, 2), previewText = "÷", inputText = "÷"),
        Inputkey(Pair(2, 2), previewText = "×", inputText = "×"),
        Inputkey(Pair(3, 2), previewText = "-", inputText = "-"),
        Inputkey(Pair(4, 2), previewText = "4", inputText = "4"),
        Inputkey(Pair(5, 2), previewText = "5", inputText = "5"),
        Inputkey(Pair(6, 2), previewText = "6", inputText = "6"),

        Inputkey(Pair(1, 3), previewText = "M2", switchesToAdd = listOf("math2")),
        Inputkey(Pair(2, 3), previewText = "%", inputText = "%"),
        Inputkey(Pair(3, 3), previewText = "=", inputText = "="),
        Inputkey(Pair(4, 3), previewText = "7", inputText = "7"),
        Inputkey(Pair(5, 3), previewText = "8", inputText = "8"),
        Inputkey(Pair(6, 3), previewText = "9", inputText = "9"),

        Inputkey(Pair(3, 4), previewText = ".", inputText = "."),
        Inputkey(Pair(4, 4), previewText = "0", inputText = "0"),

        )),

    InputkeyPack("math2",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "f", inputText = "f"),
        Inputkey(Pair(2, 1), previewText = "π", inputText = "π"),
        Inputkey(Pair(3, 1), previewText = "±", inputText = "±"),
        Inputkey(Pair(4, 1), previewText = "¹", inputText = "¹"),
        Inputkey(Pair(5, 1), previewText = "²", inputText = "²"),
        Inputkey(Pair(6, 1), previewText = "³", inputText = "³"),

        Inputkey(Pair(1, 2), previewText = "∞", inputText = "∞"),
        Inputkey(Pair(2, 2), previewText = "√", inputText = "√"),
        Inputkey(Pair(3, 2), previewText = "≈", inputText = "≈"),
        Inputkey(Pair(4, 2), previewText = "⁴", inputText = "⁴"),
        Inputkey(Pair(5, 2), previewText = "⁵", inputText = "⁵"),
        Inputkey(Pair(6, 2), previewText = "⁶", inputText = "⁶"),

        Inputkey(Pair(1, 3), previewText = "XX", switchesToRemove = listOf("math2")),
        Inputkey(Pair(2, 3), previewText = "!", inputText = "!"),
        Inputkey(Pair(3, 3), previewText = "≠", inputText = "≠"),
        Inputkey(Pair(4, 3), previewText = "⁷", inputText = "⁷"),
        Inputkey(Pair(5, 3), previewText = "⁸", inputText = "⁸"),
        Inputkey(Pair(6, 3), previewText = "⁹", inputText = "⁹"),

        Inputkey(Pair(3, 4), previewText = "°", inputText = "°"),
        Inputkey(Pair(4, 4), previewText = "⁰", inputText = "⁰"),

        )),

    InputkeyPack("brackets",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "{", inputText = "{"),
        Inputkey(Pair(2, 1), previewText = "}", inputText = "}"),
        Inputkey(Pair(3, 1), previewText = "[", inputText = "["),
        Inputkey(Pair(4, 1), previewText = "]", inputText = "]"),
        Inputkey(Pair(5, 1), previewText = "(", inputText = "("),
        Inputkey(Pair(6, 1), previewText = ")", inputText = ")"),

        Inputkey(Pair(1, 2), previewText = "<", inputText = "<"),
        Inputkey(Pair(2, 2), previewText = ">", inputText = ">"),
        Inputkey(Pair(3, 2), previewText = "«", inputText = "«"),
        Inputkey(Pair(4, 2), previewText = "»", inputText = "»"),
        Inputkey(Pair(5, 2), previewText = "‹", inputText = "‹"),
        Inputkey(Pair(6, 2), previewText = "›", inputText = "›"),

        Inputkey(Pair(1, 3), previewText = "B2", switchesToAdd = listOf("brackets2")),
        Inputkey(Pair(2, 3), previewText = "B3", switchesToAdd = listOf("brackets3")),
        Inputkey(Pair(3, 3), previewText = "‚", inputText = "‚"),
        Inputkey(Pair(4, 3), previewText = "„", inputText = "„"),
        Inputkey(Pair(5, 3), previewText = "\"", inputText = "\""),
        Inputkey(Pair(6, 3), previewText = "'", inputText = "'"),

        Inputkey(Pair(3, 4), previewText = "｢", inputText = "｢"),
        Inputkey(Pair(4, 4), previewText = "｣", inputText = "｣"),

        )),

    InputkeyPack("brackets2",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "⟨", inputText = "⟨"),
        Inputkey(Pair(2, 1), previewText = "⟩", inputText = "⟩"),
        Inputkey(Pair(3, 1), previewText = "⟦", inputText = "⟦"),
        Inputkey(Pair(4, 1), previewText = "⟧", inputText = "⟧"),
        Inputkey(Pair(5, 1), previewText = "⦗", inputText = "⦗"),
        Inputkey(Pair(6, 1), previewText = "⦘", inputText = "⦘"),

        Inputkey(Pair(1, 2), previewText = "⌈", inputText = "⌈"),
        Inputkey(Pair(2, 2), previewText = "⌉", inputText = "⌉"),
        Inputkey(Pair(3, 2), previewText = "⌊", inputText = "⌊"),
        Inputkey(Pair(4, 2), previewText = "⌋", inputText = "⌋"),
        Inputkey(Pair(5, 2), previewText = "❬", inputText = "❬"),
        Inputkey(Pair(6, 2), previewText = "❭", inputText = "❭"),

        Inputkey(Pair(1, 3), previewText = "XX", switchesToRemove = listOf("brackets2")),
        Inputkey(Pair(2, 3), previewText = "XX", switchesToRemove = listOf("brackets2")),
        Inputkey(Pair(3, 3), previewText = "⁅", inputText = "⁅"),
        Inputkey(Pair(4, 3), previewText = "⁆", inputText = "⁆"),
        Inputkey(Pair(5, 3), previewText = "⦃", inputText = "⦃"),
        Inputkey(Pair(6, 3), previewText = "⦄", inputText = "⦄"),

        Inputkey(Pair(3, 4), previewText = "⟪", inputText = "⟪"),
        Inputkey(Pair(4, 4), previewText = "⟫", inputText = "⟫"),

        )),

    InputkeyPack("brackets3",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "《", inputText = "《"),
        Inputkey(Pair(2, 1), previewText = "》", inputText = "》"),
        Inputkey(Pair(3, 1), previewText = "【", inputText = "【"),
        Inputkey(Pair(4, 1), previewText = "】", inputText = "】"),
        Inputkey(Pair(5, 1), previewText = "（", inputText = "（"),
        Inputkey(Pair(6, 1), previewText = "）", inputText = "）"),

        Inputkey(Pair(1, 2), previewText = "「", inputText = "「"),
        Inputkey(Pair(2, 2), previewText = "」", inputText = "」"),
        Inputkey(Pair(3, 2), previewText = "『", inputText = "『"),
        Inputkey(Pair(4, 2), previewText = "』", inputText = "』"),
        Inputkey(Pair(5, 2), previewText = "〔", inputText = "〔"),
        Inputkey(Pair(6, 2), previewText = "〕", inputText = "〕"),

        Inputkey(Pair(1, 3), previewText = "XX", switchesToRemove = listOf("brackets3")),
        Inputkey(Pair(2, 3), previewText = "XX", switchesToRemove = listOf("brackets3")),
        Inputkey(Pair(3, 3), previewText = "❲", inputText = "❲"),
        Inputkey(Pair(4, 3), previewText = "❳", inputText = "❳"),
        Inputkey(Pair(5, 3), previewText = "᚛", inputText = "᚛"),
        Inputkey(Pair(6, 3), previewText = "᚜", inputText = "᚜"),

        Inputkey(Pair(3, 4), previewText = "༺", inputText = "༺"),
        Inputkey(Pair(4, 4), previewText = "༻", inputText = "༻"),

        )),

    // ----------------------------------------------------------------------------------------
    // SYMBOLS
    // ----------------------------------------------------------------------------------------

    InputkeyPack("symbols",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "S4", switchesToAdd = listOf("symbols4")),
        Inputkey(Pair(2, 1), previewText = "^", inputText = "^"),
        Inputkey(Pair(3, 1), previewText = "`", inputText = "`"),
        Inputkey(Pair(4, 1), previewText = "★", inputText = "★"),
        Inputkey(Pair(5, 1), previewText = "✿", inputText = "✿"),
        Inputkey(Pair(6, 1), previewText = "✦", inputText = "✦"),

        Inputkey(Pair(1, 2), previewText = "S3", switchesToAdd = listOf("symbols3")),
        Inputkey(Pair(2, 2), previewText = "♪", inputText = "♪"),
        Inputkey(Pair(3, 2), previewText = "·", inputText = "·"),
        Inputkey(Pair(4, 2), previewText = "☮", inputText = "☮"),
        Inputkey(Pair(5, 2), previewText = "⚒", inputText = "⚒"),
        Inputkey(Pair(6, 2), previewText = "☭", inputText = "☭"),

        Inputkey(Pair(1, 3), previewText = "S2", switchesToAdd = listOf("symbols2")),
        Inputkey(Pair(2, 3), previewText = "¶", inputText = "¶"),
        Inputkey(Pair(3, 3), previewText = "•", inputText = "•"),
        Inputkey(Pair(4, 3), previewText = "✓", inputText = "✓"),
        Inputkey(Pair(5, 3), previewText = "×", inputText = "×"),
        Inputkey(Pair(6, 3), previewText = "∅", inputText = "∅"),

        Inputkey(Pair(3, 4), previewText = "℅", inputText = "℅"),
        Inputkey(Pair(4, 4), previewText = "‰", inputText = "‰"),

        )),

    InputkeyPack("symbols2",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "❄", inputText = "❄"),
        Inputkey(Pair(2, 1), previewText = "☀", inputText = "☀"),
        Inputkey(Pair(3, 1), previewText = "☁", inputText = "☁"),
        Inputkey(Pair(4, 1), previewText = "⚡", inputText = "⚡"),
        Inputkey(Pair(5, 1), previewText = "☄", inputText = "☄"),
        Inputkey(Pair(6, 1), previewText = "✨", inputText = "✨"),

        Inputkey(Pair(1, 2), previewText = "☾", inputText = "☾"),
        Inputkey(Pair(2, 2), previewText = "☽", inputText = "☽"),
        Inputkey(Pair(3, 2), previewText = "●", inputText = "●"),
        Inputkey(Pair(4, 2), previewText = "▲", inputText = "▲"),
        Inputkey(Pair(5, 2), previewText = "▼", inputText = "▼"),
        Inputkey(Pair(6, 2), previewText = "◼", inputText = "◼"),

        Inputkey(Pair(1, 3), previewText = "XX", switchesToRemove = listOf("symbols2")),
        Inputkey(Pair(2, 3), previewText = "ツ", inputText = "ツ"),
        Inputkey(Pair(3, 3), previewText = "ت", inputText = "ت"),
        Inputkey(Pair(4, 3), previewText = "ᯥ", inputText = "ᯥ"),
        Inputkey(Pair(5, 3), previewText = "‣", inputText = "‣"),
        Inputkey(Pair(6, 3), previewText = "〓", inputText = "〓"),

        Inputkey(Pair(3, 4), previewText = "ꥄ", inputText = "ꥄ"),
        Inputkey(Pair(4, 4), previewText = "︙", inputText = "︙"),

        )),

    InputkeyPack("symbols3",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "\\", inputText = "\\"),
        Inputkey(Pair(2, 1), previewText = "⤴", inputText = "⤴"),
        Inputkey(Pair(3, 1), previewText = "⤵", inputText = "⤵"),
        Inputkey(Pair(4, 1), previewText = "↕", inputText = "↕"),
        Inputkey(Pair(5, 1), previewText = "↑", inputText = "↑"),
        Inputkey(Pair(6, 1), previewText = "↓", inputText = "↓"),

        Inputkey(Pair(1, 2), previewText = "XX", switchesToRemove = listOf("symbols3")),
        Inputkey(Pair(2, 2), previewText = "÷", inputText = "÷"),
        Inputkey(Pair(3, 2), previewText = "⇄", inputText = "⇄"),
        Inputkey(Pair(4, 2), previewText = "↔", inputText = "↔"),
        Inputkey(Pair(5, 2), previewText = "←", inputText = "←"),
        Inputkey(Pair(6, 2), previewText = "→", inputText = "→"),

        Inputkey(Pair(1, 3), previewText = "∆", inputText = "∆"),
        Inputkey(Pair(2, 3), previewText = "∇", inputText = "∇"),
        Inputkey(Pair(3, 3), previewText = "╱", inputText = "╱"),
        Inputkey(Pair(4, 3), previewText = "╲", inputText = "╲"),
        Inputkey(Pair(5, 3), previewText = "–", inputText = "–"),
        Inputkey(Pair(6, 3), previewText = "—", inputText = "—"),

        Inputkey(Pair(3, 4), previewText = "╳", inputText = "╳"),
        Inputkey(Pair(4, 4), previewText = "∑", inputText = "∑"),

        )),

    InputkeyPack("symbols4",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "XX", switchesToRemove = listOf("symbols4")),
        Inputkey(Pair(2, 1), previewText = "➕", inputText = "➕"),
        Inputkey(Pair(3, 1), previewText = "⟲", inputText = "⟲"),
        Inputkey(Pair(4, 1), previewText = "⟳", inputText = "⟳"),
        Inputkey(Pair(5, 1), previewText = "©", inputText = "©"),
        Inputkey(Pair(6, 1), previewText = "™", inputText = "™"),

        Inputkey(Pair(1, 2), previewText = "➔", inputText = "➔"),
        Inputkey(Pair(2, 2), previewText = "➖", inputText = "➖"),
        Inputkey(Pair(3, 2), previewText = "⟅", inputText = "⟅"),
        Inputkey(Pair(4, 2), previewText = "⟆", inputText = "⟆"),
        Inputkey(Pair(5, 2), previewText = "Ⓤ", inputText = "Ⓤ"),
        Inputkey(Pair(6, 2), previewText = "ⁿ", inputText = "ⁿ"),

        Inputkey(Pair(1, 3), previewText = "✖", inputText = "✖"),
        Inputkey(Pair(2, 3), previewText = "➗", inputText = "➗"),
        Inputkey(Pair(3, 3), previewText = "➟", inputText = "➟"),
        Inputkey(Pair(4, 3), previewText = "⟁", inputText = "⟁"),
        Inputkey(Pair(5, 3), previewText = "®", inputText = "®"),
        Inputkey(Pair(6, 3), previewText = "№", inputText = "№"),

        Inputkey(Pair(3, 4), previewText = "⟗", inputText = "⟗"),
        Inputkey(Pair(4, 4), previewText = "ᚩ", inputText = "ᚩ"),

        )),

    // ----------------------------------------------------------------------------------------
    // LETTERS
    // ----------------------------------------------------------------------------------------

    InputkeyPack("letters",listOf<Inputkey>(

        // First layer

        Inputkey(Pair(1, 1), previewText = "Þ", inputText = "Þ"),
        Inputkey(Pair(2, 1), previewText = "μ", inputText = "μ"),
        Inputkey(Pair(3, 1), previewText = "Ç", inputText = "Ç"),
        Inputkey(Pair(4, 1), previewText = "ç", inputText = "ç"),
        Inputkey(Pair(5, 1), previewText = "$", inputText = "$"),
        Inputkey(Pair(6, 1), previewText = "£", inputText = "£"),

        Inputkey(Pair(1, 2), previewText = "¢", inputText = "¢"),
        Inputkey(Pair(2, 2), previewText = "₹", inputText = "₹"),
        Inputkey(Pair(3, 2), previewText = "¥", inputText = "¥"),
        Inputkey(Pair(4, 2), previewText = "₱", inputText = "₱"),
        Inputkey(Pair(5, 2), previewText = "Ð", inputText = "Ð"),
        Inputkey(Pair(6, 2), previewText = "ð", inputText = "ð"),

        Inputkey(Pair(1, 3), previewText = "L2", switchesToAdd = listOf("letters2")),
        Inputkey(Pair(2, 3), previewText = "Ŋ", inputText = "Ŋ"),
        Inputkey(Pair(3, 3), previewText = "Ω", inputText = "Ω"),
        Inputkey(Pair(4, 3), previewText = "Π", inputText = "Π"),
        Inputkey(Pair(5, 3), previewText = "π", inputText = "π"),
        Inputkey(Pair(6, 3), previewText = "ŋ", inputText = "ŋ"),

        Inputkey(Pair(3, 4), previewText = "ω", inputText = "ω"),
        Inputkey(Pair(4, 4), previewText = "ł", inputText = "ł"),

        )),

    InputkeyPack("letters2",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "č", inputText = "č"),
        Inputkey(Pair(2, 1), previewText = "ć", inputText = "ć"),
        Inputkey(Pair(3, 1), previewText = "đ", inputText = "đ"),
        Inputkey(Pair(4, 1), previewText = "š", inputText = "š"),
        Inputkey(Pair(5, 1), previewText = "ž", inputText = "ž"),
        Inputkey(Pair(6, 1), previewText = "ň", inputText = "ň"),

        Inputkey(Pair(1, 2), previewText = "ř", inputText = "ř"),
        Inputkey(Pair(2, 2), previewText = "ď", inputText = "ď"),
        Inputkey(Pair(3, 2), previewText = "ť", inputText = "ť"),
        Inputkey(Pair(4, 2), previewText = "ą", inputText = "ą"),
        Inputkey(Pair(5, 2), previewText = "ę", inputText = "ę"),
        Inputkey(Pair(6, 2), previewText = "ń", inputText = "ń"),

        Inputkey(Pair(1, 3), previewText = "XX", switchesToRemove = listOf("letters2")),
        Inputkey(Pair(2, 3), previewText = "ś", inputText = "ś"),
        Inputkey(Pair(3, 3), previewText = "ź", inputText = "ź"),
        Inputkey(Pair(4, 3), previewText = "ż", inputText = "ż"),
        Inputkey(Pair(5, 3), previewText = "ğ", inputText = "ğ"),
        Inputkey(Pair(6, 3), previewText = "ş", inputText = "ş"),

        Inputkey(Pair(3, 4), previewText = "ț", inputText = "ț"),
        Inputkey(Pair(4, 4), previewText = "ș", inputText = "ș"),

        )),

    // ----------------------------------------------------------------------------------------
    // VOWELS
    // ----------------------------------------------------------------------------------------

    InputkeyPack("vowels",listOf<Inputkey>(

        // First layer

        Inputkey(Pair(1, 1), previewText = "ā", circleIndicator = true, oneInputSwitches = listOf("variantsOfA")),
        Inputkey(Pair(1, 2), previewText = "ē", circleIndicator = true, oneInputSwitches = listOf("variantsOfE")),
        Inputkey(Pair(2, 1), previewText = "ī", circleIndicator = true, oneInputSwitches = listOf("variantsOfI")),
        Inputkey(Pair(2, 2), previewText = "ō", circleIndicator = true, oneInputSwitches = listOf("variantsOfO")),
        Inputkey(Pair(2, 3), previewText = "ū", circleIndicator = true, oneInputSwitches = listOf("variantsOfU")),

        Inputkey(Pair(6, 1), previewText = "æ", inputText = "æ"),
        Inputkey(Pair(6, 2), previewText = "œ", inputText = "œ"),
        Inputkey(Pair(5, 1), previewText = "ñ", inputText = "ñ"),
        Inputkey(Pair(5, 2), previewText = "ø", inputText = "ø"),

        // Switches
        Inputkey(Pair(1, 3), previewText = "SH", switchesToAdd = listOf("vowelsShifted")),

        )),

    InputkeyPack("variantsOfA",listOf<Inputkey>(
        Inputkey(Pair(1, 1), previewText = "XX"),
        Inputkey(Pair(6, 1), previewText = "ā", inputText = "ā"),
        Inputkey(Pair(6, 2), previewText = "à", inputText = "à"),
        Inputkey(Pair(6, 3), previewText = "á", inputText = "á"),
        Inputkey(Pair(5, 1), previewText = "â", inputText = "â"),
        Inputkey(Pair(5, 2), previewText = "ã", inputText = "ã"),
        Inputkey(Pair(5, 3), previewText = "å", inputText = "å"),
        Inputkey(Pair(4, 1), previewText = "ä", inputText = "ä"),
    )),

    InputkeyPack("variantsOfE",listOf<Inputkey>(
        Inputkey(Pair(1, 2), previewText = "XX"),
        Inputkey(Pair(6, 1), previewText = "ē", inputText = "ē"),
        Inputkey(Pair(6, 2), previewText = "è", inputText = "è"),
        Inputkey(Pair(6, 3), previewText = "é", inputText = "é"),
        Inputkey(Pair(5, 1), previewText = "ê", inputText = "ê"),
        Inputkey(Pair(5, 2), previewText = "ë", inputText = "ë"),
    )),

    InputkeyPack("variantsOfI",listOf<Inputkey>(
        Inputkey(Pair(2, 1), previewText = "XX"),
        Inputkey(Pair(6, 1), previewText = "ī", inputText = "ī"),
        Inputkey(Pair(6, 2), previewText = "ì", inputText = "ì"),
        Inputkey(Pair(6, 3), previewText = "í", inputText = "í"),
        Inputkey(Pair(5, 1), previewText = "î", inputText = "î"),
        Inputkey(Pair(5, 2), previewText = "ï", inputText = "ï"),
        Inputkey(Pair(5, 3), previewText = "i", inputText = "i"),
    )),

    InputkeyPack("variantsOfO",listOf<Inputkey>(
        Inputkey(Pair(2, 2), previewText = "XX"),
        Inputkey(Pair(6, 1), previewText = "ō", inputText = "ō"),
        Inputkey(Pair(6, 2), previewText = "ò", inputText = "ò"),
        Inputkey(Pair(6, 3), previewText = "ó", inputText = "ó"),
        Inputkey(Pair(5, 1), previewText = "ô", inputText = "ô"),
        Inputkey(Pair(5, 2), previewText = "õ", inputText = "õ"),
        Inputkey(Pair(5, 3), previewText = "ö", inputText = "ö"),
    )),

    InputkeyPack("variantsOfU",listOf<Inputkey>(
        Inputkey(Pair(2, 3), previewText = "XX"),
        Inputkey(Pair(6, 1), previewText = "ū", inputText = "ū"),
        Inputkey(Pair(6, 2), previewText = "ù", inputText = "ù"),
        Inputkey(Pair(6, 3), previewText = "ú", inputText = "ú"),
        Inputkey(Pair(5, 1), previewText = "û", inputText = "û"),
        Inputkey(Pair(5, 2), previewText = "ü", inputText = "ü"),
    )),

    InputkeyPack("vowelsShifted",listOf<Inputkey>(

        // First layer

        Inputkey(Pair(1, 1), previewText = "Ā", circleIndicator = true, oneInputSwitches = listOf("variantsOfCapitalA")),
        Inputkey(Pair(1, 2), previewText = "Ē", circleIndicator = true, oneInputSwitches = listOf("variantsOfCapitalE")),
        Inputkey(Pair(2, 1), previewText = "Ī", circleIndicator = true, oneInputSwitches = listOf("variantsOfCapitalI")),
        Inputkey(Pair(2, 2), previewText = "Ō", circleIndicator = true, oneInputSwitches = listOf("variantsOfCapitalO")),
        Inputkey(Pair(2, 3), previewText = "Ū", circleIndicator = true, oneInputSwitches = listOf("variantsOfCapitalU")),

        Inputkey(Pair(6, 1), previewText = "Æ", inputText = "Æ"),
        Inputkey(Pair(6, 2), previewText = "Œ", inputText = "Œ"),
        Inputkey(Pair(5, 1), previewText = "Ñ", inputText = "Ñ"),
        Inputkey(Pair(5, 2), previewText = "Ø", inputText = "Ø"),

        // Switches
        Inputkey(Pair(1, 3), previewText = "XX", switchesToRemove = listOf("vowelsShifted")),

        )),

    InputkeyPack("variantsOfCapitalA",listOf<Inputkey>(
        Inputkey(Pair(1, 1), previewText = "XX"),
        Inputkey(Pair(6, 1), previewText = "Ā", inputText = "Ā"),
        Inputkey(Pair(6, 2), previewText = "À", inputText = "À"),
        Inputkey(Pair(6, 3), previewText = "Á", inputText = "Á"),
        Inputkey(Pair(5, 1), previewText = "Â", inputText = "Â"),
        Inputkey(Pair(5, 2), previewText = "Ã", inputText = "Ã"),
        Inputkey(Pair(5, 3), previewText = "Å", inputText = "Å"),
        Inputkey(Pair(4, 1), previewText = "Ä", inputText = "Ä"),
    )),

    InputkeyPack("variantsOfCapitalE",listOf<Inputkey>(
        Inputkey(Pair(1, 2), previewText = "XX"),
        Inputkey(Pair(6, 1), previewText = "Ē", inputText = "Ē"),
        Inputkey(Pair(6, 2), previewText = "È", inputText = "È"),
        Inputkey(Pair(6, 3), previewText = "É", inputText = "É"),
        Inputkey(Pair(5, 1), previewText = "Ê", inputText = "Ê"),
        Inputkey(Pair(5, 2), previewText = "Ë", inputText = "Ë"),
    )),

    InputkeyPack("variantsOfCapitalI",listOf<Inputkey>(
        Inputkey(Pair(2, 1), previewText = "XX"),
        Inputkey(Pair(6, 1), previewText = "Ī", inputText = "Ī"),
        Inputkey(Pair(6, 2), previewText = "Ì", inputText = "Ì"),
        Inputkey(Pair(6, 3), previewText = "Í", inputText = "Í"),
        Inputkey(Pair(5, 1), previewText = "Î", inputText = "Î"),
        Inputkey(Pair(5, 2), previewText = "Ï", inputText = "Ï"),
        Inputkey(Pair(5, 3), previewText = "I", inputText = "I"),
    )),

    InputkeyPack("variantsOfCapitalO",listOf<Inputkey>(
        Inputkey(Pair(2, 2), previewText = "XX"),
        Inputkey(Pair(6, 1), previewText = "Ō", inputText = "Ō"),
        Inputkey(Pair(6, 2), previewText = "Ò", inputText = "Ò"),
        Inputkey(Pair(6, 3), previewText = "Ó", inputText = "Ó"),
        Inputkey(Pair(5, 1), previewText = "Ô", inputText = "Ô"),
        Inputkey(Pair(5, 2), previewText = "Õ", inputText = "Õ"),
        Inputkey(Pair(5, 3), previewText = "Ö", inputText = "Ö"),
    )),

    InputkeyPack("variantsOfCapitalU",listOf<Inputkey>(
        Inputkey(Pair(2, 3), previewText = "XX"),
        Inputkey(Pair(6, 1), previewText = "Ū", inputText = "Ū"),
        Inputkey(Pair(6, 2), previewText = "Ù", inputText = "Ù"),
        Inputkey(Pair(6, 3), previewText = "Ú", inputText = "Ú"),
        Inputkey(Pair(5, 1), previewText = "Û", inputText = "Û"),
        Inputkey(Pair(5, 2), previewText = "Ü", inputText = "Ü"),
    )),

    // ----------------------------------------------------------------------------------------
    // IPA — International Phonetic Alphabet symbols
    // ----------------------------------------------------------------------------------------

    InputkeyPack("IPA",listOf<Inputkey>(

        Inputkey(Pair(1, 1), previewText = "ʃ", inputText = "ʃ"),
        Inputkey(Pair(2, 1), previewText = "ʒ", inputText = "ʒ"),
        Inputkey(Pair(3, 1), previewText = "θ", inputText = "θ"),
        Inputkey(Pair(4, 1), previewText = "ð", inputText = "ð"),
        Inputkey(Pair(5, 1), previewText = "ŋ", inputText = "ŋ"),
        Inputkey(Pair(6, 1), previewText = "ʔ", inputText = "ʔ"),

        Inputkey(Pair(1, 2), previewText = "ə", inputText = "ə"),
        Inputkey(Pair(2, 2), previewText = "ɛ", inputText = "ɛ"),
        Inputkey(Pair(3, 2), previewText = "ɪ", inputText = "ɪ"),
        Inputkey(Pair(4, 2), previewText = "ʊ", inputText = "ʊ"),
        Inputkey(Pair(5, 2), previewText = "ɔ", inputText = "ɔ"),
        Inputkey(Pair(6, 2), previewText = "ɑ", inputText = "ɑ"),

        Inputkey(Pair(1, 3), previewText = "ʌ", inputText = "ʌ"),
        Inputkey(Pair(2, 3), previewText = "ɜ", inputText = "ɜ"),
        Inputkey(Pair(3, 3), previewText = "ː", inputText = "ː"),
        Inputkey(Pair(4, 3), previewText = "ˈ", inputText = "ˈ"),
        Inputkey(Pair(5, 3), previewText = "ˌ", inputText = "ˌ"),
        Inputkey(Pair(6, 3), previewText = "ɲ", inputText = "ɲ"),

        Inputkey(Pair(3, 4), previewText = "ɾ", inputText = "ɾ"),
        Inputkey(Pair(4, 4), previewText = "ʁ", inputText = "ʁ"),

        )),

    // Spare empty template

    /*InputkeyPack("blank",listOf<Inputkey>(

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

        )),*/
))