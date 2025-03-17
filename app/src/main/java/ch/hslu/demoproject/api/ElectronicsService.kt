package ch.hslu.demoproject.api

import ch.hslu.demoproject.api.data.Electronic
import retrofit2.Response
import retrofit2.http.GET

interface ElectronicsService {
    @GET("objects")
    suspend fun getElectronics(): Response<List<Electronic>>
}