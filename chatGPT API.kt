import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

fun main() {
    val endpointUrl = "https://api.openai.com/v1/engine/davinci/candidates"
    val apiKey = "your_api_key"
    val prompt = "Hello, how are you?"

    // API 요청을 위한 URL 생성
    val url = URL(endpointUrl)
    val postData = "prompt=" + URLEncoder.encode(prompt, "UTF-8")

    // HTTP 요청 생성 및 전송
    val conn = url.openConnection() as HttpURLConnection
    conn.requestMethod = "POST"
    conn.setRequestProperty("Authorization", "Bearer " + apiKey)
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
    conn.doOutput = true
    conn.outputStream.write(postData.toByteArray(charset("UTF-8")))
    conn.outputStream.flush()
    conn.outputStream.close()

    // HTTP 응답 수신
    val responseCode = conn.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val inputStreamReader = InputStreamReader(conn.inputStream, "UTF-8")
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        bufferedReader.close()

        // 수신한 응답 처리 및 출력
        val responseJson = stringBuilder.toString()
        println("ChatGPT Response: $responseJson")
    } else {
        println("API 요청 실패: HTTP 오류 코드 = $responseCode")
