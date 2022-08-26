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

    private val dummyToken = "auth_token"
    private val dummyUploadResponse = DataDummy.generateDummyFileUploadResponse()
    private val dummyMultipart = DataDummy.generateDummyMultipartFile()
    private val dummyDescription = DataDummy.generateDummyRequestBody()

    @Test
    fun `add Story Successfully`() {
        val expectedResponse = MutableLiveData<FileUploadResponse>()
        expectedResponse.value = dummyUploadResponse

        `when`(
            addStoryViewModel.addStory(
                dummyToken,
                dummyMultipart,
                dummyDescription,
                null,
                null
            )
        ).thenReturn(expectedResponse)

        val actualResponse =
            addStoryViewModel.addStory(dummyToken, dummyMultipart, dummyDescription, null, null)
                .getOrAwaitValue()
        verify(addStoryViewModel).addStory(dummyToken, dummyMultipart, dummyDescription, null, null)
        Assert.assertNotNull(actualResponse)
        assertEquals(dummyUploadResponse, actualResponse)
    }
}