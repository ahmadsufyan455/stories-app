package com.zerodev.dicostories.view.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.zerodev.dicostories.DataDummy
import com.zerodev.dicostories.getOrAwaitValue
import com.zerodev.dicostories.model.FileUploadResponse
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private val addStoryViewModel = AddStoryViewModel()
    private val dummyUploadResponse = DataDummy.generateDummyFileUploadResponse()

    @Test
    fun `add Story Successfully`() {
        val expectedResponse = MutableLiveData<FileUploadResponse>()
        expectedResponse.value = dummyUploadResponse

        `when`(addStoryViewModel.getUploadResponse()).thenReturn(expectedResponse)

        val actualResponse = addStoryViewModel.getUploadResponse().getOrAwaitValue()
        verify(addStoryViewModel).getUploadResponse()
        Assert.assertNotNull(actualResponse)
        assertEquals(dummyUploadResponse, actualResponse)
    }
}