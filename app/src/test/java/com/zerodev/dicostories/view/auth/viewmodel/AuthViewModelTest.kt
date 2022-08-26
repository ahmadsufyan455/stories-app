package com.zerodev.dicostories.view.auth.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.zerodev.dicostories.DataDummy
import com.zerodev.dicostories.getOrAwaitValue
import com.zerodev.dicostories.model.LoginResponse
import com.zerodev.dicostories.model.RegisterResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private val authViewModel = AuthViewModel()
    private val dummyRegister = DataDummy.generateDummyRegisterResponse()
    private val dummyLogin = DataDummy.generateDummyLoginResponse()
    private val dummyRegisterModel = DataDummy.generateRegisterModel()
    private val dummyLoginModel = DataDummy.generateDummyLoginModel()

    @Test
    fun `when Register Response Should Not Null And Return Success`() {
        val expectedResponse = MutableLiveData<RegisterResponse>()
        expectedResponse.value = dummyRegister

        `when`(authViewModel.register(dummyRegisterModel)).thenReturn(expectedResponse)

        val actualResponse = authViewModel.register(dummyRegisterModel).getOrAwaitValue()
        verify(authViewModel).register(dummyRegisterModel)
        assertNotNull(actualResponse)
        assertEquals(dummyRegister, actualResponse)
    }

    @Test
    fun `when Login Response Should Not Null And Return Success`() {
        val expectedResponse = MutableLiveData<LoginResponse>()
        expectedResponse.value = dummyLogin

        `when`(authViewModel.login(dummyLoginModel)).thenReturn(expectedResponse)

        val actualResponse = authViewModel.login(dummyLoginModel).getOrAwaitValue()
        verify(authViewModel).login(dummyLoginModel)
        assertNotNull(actualResponse)
        assertEquals(dummyLogin, actualResponse)
    }
}