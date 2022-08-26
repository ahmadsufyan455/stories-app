package com.zerodev.dicostories.view.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.zerodev.dicostories.DataDummy
import com.zerodev.dicostories.MainDispatcherRule
import com.zerodev.dicostories.getOrAwaitValue
import com.zerodev.dicostories.model.Story
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private val mapViewModel = MapViewModel()
    private val dummyStories = DataDummy.generateDummyStoriesWithLocation()

    @Test
    fun `when Get Stories With Location Should Not Null and Return Success`() {
        val expectedStories = MutableLiveData<List<Story>>()
        expectedStories.value = dummyStories

        `when`(mapViewModel.getStoriesWithLocation()).thenReturn(expectedStories)

        val actualStories = mapViewModel.getStoriesWithLocation().getOrAwaitValue()
        verify(mapViewModel).getStoriesWithLocation()
        assertNotNull(actualStories)
        assertEquals(dummyStories, actualStories)
        assertEquals(dummyStories.size, actualStories.size)
        assertEquals(dummyStories[0].lat, actualStories[0].lat)
        assertEquals(dummyStories[0].lon, actualStories[0].lon)
    }
}