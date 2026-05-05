package com.jorlina.syncapp.CreateAccount

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class CreateAccountViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CreateAccountViewModel


    private lateinit var errorObserver: Observer<String?>
    private lateinit var resultObserver: Observer<Boolean>

    // Variables donde guardamos los últimos valores observados
    private var lastError: String? = null
    private var lastResult: Boolean? = null

    @Before
    fun setUp() {
        viewModel = CreateAccountViewModel()

        // Configuramos los observers para capturar valores
        errorObserver = Observer { lastError = it }
        resultObserver = Observer { lastResult = it }

        viewModel.errorMessage.observeForever(errorObserver)
        viewModel.registerResult.observeForever(resultObserver)
    }

    // 1. CAMPOS OBLIGATORIOS VACÍOS

    @Test
    fun `campos obligatorios vacios - username vacio - muestra error`() {
        viewModel.createAccount(
            username = "",
            email = "usuario@test.com",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        assertNotNull("Debe emitir un mensaje de error", lastError)
        assertNull("No debe emitir resultado de éxito", lastResult)
    }

    @Test
    fun `campos obligatorios vacios - username en blanco con espacios - muestra error`() {
        viewModel.createAccount(
            username = "   ",
            email = "usuario@test.com",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        assertNotNull("Un username con solo espacios debe considerarse vacío", lastError)
        assertNull(lastResult)
    }

    // 2. EMAIL CON FORMATO INCORRECTO

    @Test
    fun `email con formato incorrecto - usuario@com - muestra error email invalido`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "usuario@com",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        assertNotNull("Debe emitir error de email inválido", lastError)
        assertTrue(
            "El mensaje debe indicar que el email es inválido",
            lastError!!.contains("email", ignoreCase = true) ||
                    lastError!!.contains("válido", ignoreCase = true)
        )
        assertNull("No debe crear la cuenta", lastResult)
    }

    @Test
    fun `email con formato incorrecto - sin arroba - muestra error`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "usuariocorreocom",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        assertNotNull(lastError)
        assertNull(lastResult)
    }

    @Test
    fun `email con formato incorrecto - email vacio - muestra error`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        assertNotNull(lastError)
        assertNull(lastResult)
    }

    // 3. EMAIL CORRECTO

    @Test
    fun `email correcto - usuario@test_com - campo aceptado sin error de email`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "usuario@test.com",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        val errorEsDeEmail = lastError?.contains("email", ignoreCase = true) == true &&
                lastError?.contains("válido", ignoreCase = true) == true
        assertFalse("No debe mostrar error de email inválido", errorEsDeEmail)
    }

    // 4. CONFIRMACIÓN DE CONTRASEÑA INCORRECTA

    @Test
    fun `confirmacion contrasena incorrecta - contrasenas distintas - muestra error`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "usuario@test.com",
            password = "123456",
            repeatPassword = "654321"
        )

        assertNotNull("Debe emitir error de contraseñas", lastError)
        assertTrue(
            "El mensaje debe indicar que las contraseñas no coinciden",
            lastError!!.contains("contraseña", ignoreCase = true) ||
                    lastError!!.contains("coincid", ignoreCase = true)
        )
        assertNull("No debe crear la cuenta", lastResult)
    }

    // 5. CONTRASEÑA CUMPLE REQUISITOS

    @Test
    fun `contrasena cumple requisitos - Abc12345! - campo aceptado`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "usuario@test.com",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        // Con todos los datos válidos no debe haber error y debe registrarse
        assertNull("No debe haber mensaje de error", lastError)
        assertEquals("Debe emitir resultado exitoso", true, lastResult)
    }

    // 6. CONTRASEÑA SIN REQUISITOS MÍNIMOS

    @Test
    fun `contrasena sin requisitos minimos - menos de 6 caracteres - muestra error`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "usuario@test.com",
            password = "123",
            repeatPassword = "123"
        )

        assertNotNull("Debe emitir error de contraseña corta", lastError)
        assertTrue(
            "El mensaje debe indicar longitud mínima",
            lastError!!.contains("contraseña", ignoreCase = true) ||
                    lastError!!.contains("corta", ignoreCase = true) ||
                    lastError!!.contains("6", ignoreCase = true)
        )
        assertNull("No debe crear la cuenta", lastResult)
    }

    @Test
    fun `contrasena sin requisitos minimos - exactamente 5 caracteres - muestra error`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "usuario@test.com",
            password = "Ab1!x",
            repeatPassword = "Ab1!x"
        )

        assertNotNull(lastError)
        assertNull(lastResult)
    }

    @Test
    fun `contrasena con exactamente 6 caracteres - debe aceptarse`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "usuario@test.com",
            password = "Abc123",
            repeatPassword = "Abc123"
        )

        assertNull("Una contraseña de 6 caracteres debe ser aceptada", lastError)
        assertEquals(true, lastResult)
    }

    // 7. LONGITUD DE NOMBRE DE USUARIO


    @Test
    fun `longitud username - 1 caracter - debe mostrar error de longitud`() {
        viewModel.createAccount(
            username = "a",
            email = "usuario@test.com",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )
        assertNotNull("Username de 1 carácter debe ser rechazado", lastError)

        // Por ahora solo verificamos que no crashea
        assertTrue("El test se ejecuta sin excepción", true)
    }

    @Test
    fun `longitud username - 300 caracteres - debe mostrar error de longitud`() {
        val usernameLargo = "a".repeat(300)
        viewModel.createAccount(
            username = usernameLargo,
            email = "usuario@test.com",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        assertNotNull("Username de 300 caracteres debe ser rechazado", lastError)

        assertTrue("El test se ejecuta sin excepción", true)
    }

    // 8. USUARIO CON CARACTERES NO PERMITIDOS

    @Test
    fun `usuario con caracteres no permitidos - user@@@### - debe mostrar error de formato`() {
        viewModel.createAccount(
            username = "user@@@###",
            email = "usuario@test.com",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )
       assertNotNull("Use rname con caracteres especiales debe ser rechazado", lastError)

        assertTrue("El test se ejecuta sin excepción", true)
    }

    // 9. CAMPOS CON ESPACIOS EN BLANCO

    @Test
    fun `campo email solo con espacios - se considera vacio y muestra error`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "   ",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        assertNotNull("Un email con solo espacios debe producir error", lastError)
        assertNull(lastResult)
    }

    // 10. SQL INJECTION

    @Test
    fun `sql injection en username - entrada debe ser rechazada o sanitizada`() {
        viewModel.createAccount(
            username = "' OR 1=1 --",
            email = "usuario@test.com",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        assertNotNull("SQL injection debe ser rechazado", lastError)
        assertNull("No debe crear la cuenta", lastResult)
    }

    // 11. REGISTRO EXITOSO (unitario — sin backend)

    @Test
    fun `registro exitoso - todos los campos validos - emite resultado true`() {
        viewModel.createAccount(
            username = "usuarioValido",
            email = "usuario@test.com",
            password = "Abc12345!",
            repeatPassword = "Abc12345!"
        )

        assertNull("No debe haber mensaje de error", lastError)
        assertEquals("Debe emitir registerResult = true", true, lastResult)
    }
}