package com.example.jogodavelha

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@LargeTest
class MainActivityTests {

    @get:Rule
    val mainActivity = ActivityScenarioRule(MainActivity::class.java)

    // Inicializar todos os objetos
    @Before
    fun setUp() {
        val contexto = ApplicationProvider.getApplicationContext<Context>()
    }

    @After
    fun tearDown() {
    }
}