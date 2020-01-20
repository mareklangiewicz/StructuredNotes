package pl.mareklangiewicz.notes.logic

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import pl.mareklangiewicz.uspek.eq
import pl.mareklangiewicz.uspek.o
import pl.mareklangiewicz.uspek.uspekTestFactory

internal class MyClassTest {

    @Test
    fun add() {
        val c = MyClass()
        assertEquals(c.x, 0)
    }

    @TestFactory
    fun factory() = uspekTestFactory {
        "On Myclass" o {
            val c = MyClass()

            "On adding 5" o {
                c.add(5)

                "MyClass.x is equal 5" o { c.x eq 5 }
            }
        }
    }
}
