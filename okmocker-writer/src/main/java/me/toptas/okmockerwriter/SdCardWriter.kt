package me.toptas.okmockerwriter

import android.os.Environment
import android.util.Log
import okhttp3.HttpUrl
import java.io.File
import java.io.PrintWriter

fun String.toFileName() = replace("://", "_").replace("/", "_")

internal fun log(msg: String) {
    Log.i("OkMocker", msg)
}

class SdCardWriter : OkMockerWriter {


    override fun write(url: HttpUrl, body: String) {
        writeToSd(url.toString().toFileName(), body)
    }

    private fun writeToSd(file: String, content: String?) {
        if (content == null) return
        val folder = File(Environment.getExternalStorageDirectory().toString() + "/okmock")
        var success = true
        if (!folder.exists()) {
            success = folder.mkdir()
        }
        if (success) {
            if (success) {
                val dest = File(folder, file)
                try {
                    PrintWriter(dest).use { out -> out.println(content) }
                    log("$folder/$file saved with content: $content")
                } catch (e: Exception) {
                    log("Failed to write. $e")
                }
            } else {
                log("Failed to create file.")
            }
        }

    }
}