package pl.mareklangiewicz.notes.logic.utils

import pl.mareklangiewicz.notes.logic.main.home.Note
import kotlin.random.Random

fun Random.nextChar(from: Char, until: Char) = nextInt(from.toInt(), until.toInt()).toChar()

fun Random.fakeWord(length: Int = nextInt(3, 15)) =
    buildString { repeat(length) { append(nextChar('a', 'z')) } }

fun Random.fakeSentence(prefix: String = "", wordsCount: Int = nextInt(3, 15)) = buildString {
    append(if (prefix == "") nextChar('A', 'Z') else "$prefix ")
    repeat(wordsCount - 1) { append(fakeWord() + " ") }
    append(fakeWord() + '.')
}

fun Random.fakeNote() = Note(fakeSentence("note"))

fun Random.fakeNotes(size: Int = nextInt(3, 15)) = List(size) { fakeNote() }
