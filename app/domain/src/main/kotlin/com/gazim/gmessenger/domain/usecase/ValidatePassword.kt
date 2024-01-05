package com.gazim.gmessenger.domain.usecase

import kotlin.math.ln

class ValidatePassword : IValidatePassword {
    private val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,128}$")

    override suspend fun invoke(password: String): Boolean = checkEntropyEnough(password) && passwordRegex matches password

    private fun checkEntropyEnough(input: String) = calculateEntropy(input) > 1.5

    private fun calculateEntropy(input: String): Double {
        val charCounts: MutableMap<Char, Int> = HashMap()
        input.forEach {
            charCounts[it] = charCounts.getOrDefault(it, 0) + 1
        }
        var entropy = 0.0
        var isOrdered = true
        val inputLength = input.length
        for (count in charCounts.values) {
            val p = count.toDouble() / inputLength
            if (isOrdered && count > 1) isOrdered = false
            entropy -= p * ln(p) / ln(2.0)
        }
        if (isOrdered) entropy /= 2
        return entropy
    }
}
