package br.edu.dmos5.projeto_joao_santos_dmos5.api;

import br.edu.dmos5.projeto_joao_santos_dmos5.model.MoedaApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoedaService {

    @GET("rates?")
    Call<MoedaApi> buscaTodas(@Query(value="key") String key);
}
