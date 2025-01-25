package dahlke.br.harrypotterapp

import retrofit2.http.GET
import retrofit2.http.Path

interface HarryPotterAPI {
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String) : List<CharacterResponse>
}