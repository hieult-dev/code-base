package com.utils
import jakarta.servlet.http.HttpServletResponse

object ResponseUtil {
    fun writeError(response: HttpServletResponse, status: Int, message: String) {
        response.status = status
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.use { out ->
            out.write("""{"message":"$message"}""")
        }
    }
}