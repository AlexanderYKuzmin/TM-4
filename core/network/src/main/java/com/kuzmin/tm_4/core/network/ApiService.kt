package com.kuzmin.tm_4.core.network

import com.kuzmin.tm_4.core.network.model.SiteListDto
import com.kuzmin.tm_4.core.network.model.SitePreviewListDto
import com.kuzmin.tm_4.core.network.model.UserDto
import com.kuzmin.tm_4.core.network.model.UserSignInDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/obtain-token")
    suspend fun getUser(@Body userSignInDto: UserSignInDto): UserDto

    // http://127.0.0.1:8000/api/v1/sites/search?region_code=22,50&site_name=U
    //http://127.0.0.1:8000/api/v1/sites/search_address/search_string=Bee

    /*@Headers("Content-Type: application/json")
    @GET("sites/search")
    suspend fun getPreviewSitesByRegionAndName(
        @Header(HEADER_PARAM_AUTHORIZATION) token: String,
        @Query(QUERY_PARAM_REGION_CODE) regionCode: String,
        @Query(QUERY_PARAM_SITE_NAME) siteName: String
    ): SitePreviewListDto*/

    @Headers("Content-Type: application/json")
    @GET("sites/get_sites")
    suspend fun getSitesSimpleAll(
        @Header("Authorization") auth: String
    ): SitePreviewListDto

    @Headers("Content-Type: application/json")
    @GET("sites/search")
    suspend fun getSitesSimpleByNames(
        @Header(HEADER_PARAM_AUTHORIZATION) token: String,
        @Query(QUERY_PARAM_NAMES) names: String
    ): SitePreviewListDto

    @Headers("Content-Type: application/json")
    @GET("sites/search_address/search_string={text}")
    suspend fun getSitesSimpleByText(
        @Header(HEADER_PARAM_AUTHORIZATION) token: String,
        @Path(QUERY_PARAM_TEXT) text: String
    ): SitePreviewListDto

    @Headers("Content-Type: application/json")
    @GET("sites/get_site/site_id={ids}")
    suspend fun getSitesById(
        @Header(HEADER_PARAM_AUTHORIZATION) token: String,
        @Path(QUERY_PARAM_TEXT) ids: String
    ): SiteListDto

    /*@GET("top/totalvolfull")
    suspend fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): CoinNamesListDto

    @GET("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
    ): CoinInfoJsonContainerDto
*/
    companion object {
        private const val HEADER_PARAM_AUTHORIZATION = "Authorization"
        private const val QUERY_PARAM_NAMES = "names"
        private const val QUERY_PARAM_TEXT = "text"
        private const val QUERY_PARAM_REGION_CODE = "region_code"
        private const val QUERY_PARAM_SITE_NAME = "site_name"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

        private const val CURRENCY = "USD"
    }
}