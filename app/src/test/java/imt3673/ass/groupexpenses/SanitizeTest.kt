package imt3673.ass.groupexpenses

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests for data sanitizing.
 */
class SanitizeTest {

    /**
     * Check if the capitalization is correct.
     */
    @Test
    fun isNameCapitalizedCorrectly() {
        val testHelloData = mapOf(
            "Tom" to "Tom",
            "toM" to "Tom",
            "Alice" to "Alice",
            "aLIcE" to "Alice",
            "bob" to "Bob")

        // notice, that generateGreeting function is accessible here without ANY problems
        // because it lives in the same package as the test class
        testHelloData.forEach {
            assertEquals(it.value, sanitizeName(it.key))
        }
    }

    /**
     * Check if the name is trimmed correct.
     */
    @Test
    fun isNameTrimmedCorrectly() {
        val testHelloData = mapOf(
            "  Tom  " to "Tom",
            "\ttoM \t" to "Tom",
            "\t    Alice   " to "Alice",
            "\t\t\n   aLIcE \t\n\n" to "Alice",
            "    bob    \t\t" to "Bob")

        testHelloData.forEach {
            assertEquals(it.value, sanitizeName(it.key))
        }
    }

    /**
     * Check if two tokens are used correctly. No hyphen.
     */
    @Test
    fun areTwoTokensUsedCorrectly_noHyphen() {
        val testHelloData = mapOf(
            "  Tom  Sawyer" to "Tom Sawyer",
            "\ttoM \tRoise" to "Tom Roise",
            "\t    Alice  cooper " to "Alice Cooper",
            "\t\t\n   aLIcE in chains\t\n\n" to "Alice In",
            "    bob\tbob\tbob  \t\t" to "Bob Bob",
            "\t \t bob\t \n bob \t \t \n \n" to "Bob Bob")


        testHelloData.forEach {
            assertEquals(it.value, sanitizeName(it.key))
        }
    }

    /**
     * Check if two tokens are used correctly. With hyphen.
     */
    @Test
    fun areTwoTokensUsedCorrectly_withHyphen() {
        val testHelloData = mapOf(
            "  Tom-Rubert  Sawyer" to "Tom-Rubert Sawyer",
            "\ttoM-elise \tRoise" to "Tom-Elise Roise",
            "\t    Alice-elISE  cooper " to "Alice-Elise Cooper",
            "\t\t\n   aLIcE-in chains\t\n\n" to "Alice-In Chains",
            "    bob-bob\tbob  \t\t" to "Bob-Bob Bob",
            "\t \t bob-\t\nbob-\t \t" to "Bob- Bob-",
            "\tbo-by\t \t bO-b" to "Bo-By Bo-B")

        testHelloData.forEach {
            assertEquals(it.value, sanitizeName(it.key))
        }
    }

    @Test
    fun areNumbersAndPunctuationRemovedCorrectly() {
        val testHelloData = mapOf(
            " #$ Tom-Rubert 44 Sawyer" to "Tom-Rubert Sawyer",
            "\ttoM-elise .?\tRoise" to "Tom-Elise Roise",
            "\t   21 Alice-elISE  co6oper " to "Alice-Elise Cooper",
            "\t\t\n   aLIcE-5in chains\t\n\n" to "Alice-In Chains",
            "  ? bob-123bob\tbob?  \t\t" to "Bob-Bob Bob",
            "\t \t b__ob-\t\nb1ob-\t \t" to "Bob- Bob-",
            "\tbo-b#y\t \t bO-b^&" to "Bo-By Bo-B"
        )

        testHelloData.forEach {
            assertEquals(it.value, sanitizeName(it.key))
        }
    }

}