package sobaya.app.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class SamplePagingSource : PagingSource<Int, String>() {
    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        val perPage = params.loadSize
        val page = params.key ?: 1
        val next = page + 1
        val prev = if (page == 1) null else page - 1

        return LoadResult.Page(
            data = (0..perPage).map { "$page-$it-DATA" },
            prevKey = prev,
            nextKey = next
        )
    }
}
