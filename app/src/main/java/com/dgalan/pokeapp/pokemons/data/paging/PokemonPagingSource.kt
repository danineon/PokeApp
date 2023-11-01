package com.dgalan.pokeapp.pokemons.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dgalan.pokeapp.pokemons.domain.DomainContract.Repository
import com.dgalan.pokeapp.pokemons.domain.model.PokemonResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
    private val pokemonRepository: Repository
) : PagingSource<Int, PokemonResult>() {

    fun getPokemonList(): Flow<PagingData<PokemonResult>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { this }
        ).flow
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(20)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(20)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        return try {
            val page = params.key ?: 0
            val response = pokemonRepository.getPokemonList(page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 0) null else page.minus(20),
                nextKey = if (response.results.isEmpty()) null else page.plus(20)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}