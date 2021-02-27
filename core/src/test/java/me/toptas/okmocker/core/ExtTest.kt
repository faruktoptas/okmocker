package me.toptas.okmocker.core

import okhttp3.HttpUrl
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtTest {

    @Test
    fun testFileName() {
        val file1 = HttpUrl.parse("https://github.com/faruktoptas")?.toOkMockerFileName()
        val file2 = HttpUrl.parse("https://github.com/faruktoptas/okmocker")?.toOkMockerFileName()
        val file3 =
            HttpUrl.parse("https://github.com/faruktoptas/okmocker?q=a")?.toOkMockerFileName()

        assertEquals("faruktoptas", file1)
        assertEquals("faruktoptas_okmocker", file2)
        assertEquals("faruktoptas_okmocker_q_a", file3)
    }
}