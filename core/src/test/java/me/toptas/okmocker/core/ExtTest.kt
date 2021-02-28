package me.toptas.okmocker.core

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtTest {

    @Test
    fun testFileName() {
        val file1 = "https://github.com/faruktoptas".toHttpUrlOrNull()?.toOkMockerFileName()
        val file2 =
            "https://github.com/faruktoptas/okmocker".toHttpUrlOrNull()?.toOkMockerFileName()
        val file3 =
            "https://github.com/faruktoptas/okmocker?q=a".toHttpUrlOrNull()?.toOkMockerFileName()

        assertEquals("faruktoptas", file1)
        assertEquals("faruktoptas_okmocker", file2)
        assertEquals("faruktoptas_okmocker_q_a", file3)
    }
}