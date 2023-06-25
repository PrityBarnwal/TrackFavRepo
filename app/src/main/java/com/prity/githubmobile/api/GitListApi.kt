package com.prity.githubmobile.api

import com.prity.githubmobile.model.GitRepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitListApi {
    @GET("repos/{owner}/{repo}")
    fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<GitRepoResponse>
}