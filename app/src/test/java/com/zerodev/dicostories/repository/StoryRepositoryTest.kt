package com.zerodev.dicostories.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.zerodev.dicostories.DataDummy
import com.zerodev.dicostories.MainDispatcherRule
import com.zerodev.dicostories.getOrAwaitValue
import com.zerodev.dicostories.model.Story
import com.zerodev.dicostories.view.list.adapter.StoryAdapter
import com.zerodev.dicostories.view.list.viewmodel.StoryPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private val dummyToken = "auth_token"

    @Test
    fun `when Get Stories Should Not Null and Return Success`() = runTest {
        val dummyStories = DataDummy.generateDummyStories()
        val data: PagingData<Story> = StoryPagingSource.snapshot(dummyStories)
        val expectedStories = MutableLiveData<PagingData<Story>>()
        expectedStories.value = data
        Mockito.`when`(storyRepository.getStories(dummyToken)).thenReturn(expectedStories)

        val actualStories: PagingData<Story> =
            storyRepository.getStories(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = com.zerodev.dicostories.view.list.viewmodel.noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStories)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStories, differ.snapshot())
        Assert.assertEquals(dummyStories.size, differ.snapshot().size)
        Assert.assertEquals(dummyStories[0].name, differ.snapshot()[0]?.name)
    }
}