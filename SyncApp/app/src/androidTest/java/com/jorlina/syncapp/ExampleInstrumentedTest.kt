package com.jorlina.syncapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jorlina.syncapp.CreateAccount.CreateAcontActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateAccountInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(CreateAcontActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jorlina.syncapp", appContext.packageName)
    }

    @Test
    fun usuarioVacio_muestraError() {
        // Dejamos etName vacío a propósito

        onView(withId(R.id.etEmail))
            .perform(typeText("usuario@test.com"), closeSoftKeyboard())

        onView(withId(R.id.etPasswordCreate))
            .perform(typeText("Abc12345"), closeSoftKeyboard())

        onView(withId(R.id.etRepeatPasswordCreate))
            .perform(typeText("Abc12345"), closeSoftKeyboard())

        onView(withId(R.id.btCreateAccount))
            .perform(click())

        // El error se muestra en tvErrorCreate
        onView(withId(R.id.tvErrorCreate))
            .check(matches(withText("Usuario obligatorio")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailIncorrecto_muestraError() {
        onView(withId(R.id.etName))
            .perform(typeText("jordi"), closeSoftKeyboard())

        onView(withId(R.id.etEmail))
            .perform(typeText("usuario@com"), closeSoftKeyboard())

        onView(withId(R.id.etPasswordCreate))
            .perform(typeText("Abc12345"), closeSoftKeyboard())

        onView(withId(R.id.etRepeatPasswordCreate))
            .perform(typeText("Abc12345"), closeSoftKeyboard())

        onView(withId(R.id.btCreateAccount))
            .perform(click())

        onView(withId(R.id.tvErrorCreate))
            .check(matches(withText("Email no válido")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun passwordsDistintas_muestraError() {
        onView(withId(R.id.etName))
            .perform(typeText("jordi"), closeSoftKeyboard())

        onView(withId(R.id.etEmail))
            .perform(typeText("usuario@test.com"), closeSoftKeyboard())

        onView(withId(R.id.etPasswordCreate))
            .perform(typeText("123456"), closeSoftKeyboard())

        onView(withId(R.id.etRepeatPasswordCreate))
            .perform(typeText("654321"), closeSoftKeyboard())

        onView(withId(R.id.btCreateAccount))
            .perform(click())

        onView(withId(R.id.tvErrorCreate))
            .check(matches(withText("Las contraseñas no coinciden")))
            .check(matches(isDisplayed()))
    }
}