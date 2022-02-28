package com.example.myapplication

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetUserListTest {

    private val mockUserRepository = mockk<UserRepository>()

    private val firstName = "firstName"
    private val lastName = "lastName"
    private val avatarUrl = "https"
    private val user = ReqResService.UserResponse.Data(
        0, "email", firstName, lastName, avatarUrl
    )

    private val users = listOf(user, user, user, user)

    private val response = ReqResService.UserResponse(data = users)

    private val sut = GetUserList(mockUserRepository)

    @Test
    fun `Given getUsers is called when the use case is executed then returns mapped data`() =
        runBlocking {
            // Given
            coEvery { mockUserRepository.getUsers() } returns response

            // When
            val result = sut.execute()

            // Then
            assert(result.all { it.name == "$firstName $lastName" })
            assert(result.all { it.avatarUrl == avatarUrl })
        }
}