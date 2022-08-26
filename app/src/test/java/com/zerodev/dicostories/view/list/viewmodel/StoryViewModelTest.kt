package com.zerodev.dicostories.view.list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.zerodev.dicostories.DataDummy
import com.zerodev.dicostories.MainDispatcherRule
import com.zerodev.dicostories.getOrAwaitValue
import com.zerodev.dicostories.model.Story
import com.zerodev.dicostories.repository.StoryRepository
import com.zerodev.dicostories.view.list.adapter.StoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var storyViewModel: StoryViewModel

    @Before
    fun setup() {
        storyViewModel = StoryViewModel(storyRepository)
    }

    private val dummyToken = "auth_token"

    @Test
    fun `when Get Stories Should Not Null and Return Success`() = runTest {
        val dummyStories = DataDummy.generateDummyStories()
        val data: PagingData<Story> = StoryPagingSource.snapshot(dummyStories)
        val expectedStories = MutableLiveData<PagingData<Story>>()
        expectedStories.value = data
        Mockito.`when`(storyViewModel.getStories(dummyToken)).thenReturn(expectedStories)

        val actualStories: PagingData<Story> =
            storyViewModel.getStories(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStories)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStories, differ.snapshot())
        assertEquals(dummyStories.size, differ.snapshot().size)
        assertEquals(dummyStories[0].name, differ.snapshot()[0]?.name)
    }

}

class StoryPagingSource : PagingSource<Int, LiveData<List<Story>>>() {
    companion object {
        fun snapshot(items: List<Story>): PagingData<Story> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>) = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}