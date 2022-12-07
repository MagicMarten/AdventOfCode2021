package nl.mmeijboom.advent_of_code.puzzle.input

import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class InputService {

    companion object {
        const val BASE_URL = "https://adventofcode.com/2021/day/"
        const val COOKIE = "session=53616c7465645f5ffd8f01ce8ddb0a2e4000b9446f756d60d47ad14d3662bbd6c74d3dfa5e73a33eb8746ddf38b4024b43bba5df05396f76db8fa1cecba512d1; _ga=GA1.2.2019276976.1669962362"
    }

    fun retrievePuzzleInput(day: Int): List<String> {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create(buildUrl(day)))
                .header("cookie", COOKIE)
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofLines())

        return response.body().toList()
    }

    fun buildUrl(day: Int): String {
        return "$BASE_URL$day/input"
    }

}