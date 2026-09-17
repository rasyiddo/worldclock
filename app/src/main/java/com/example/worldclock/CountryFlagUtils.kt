package com.example.worldclock

fun countryCodeToFlag(
    countryCode: String
): String {

    val code =
        countryCode.uppercase()


    if (
        code.length != 2
    ) {

        return "🌍"
    }


    return code
        .map { char ->

            Character.toChars(
                0x1F1E6 +
                        (char.code - 'A'.code)
            )
                .concatToString()
        }
        .joinToString("")
}