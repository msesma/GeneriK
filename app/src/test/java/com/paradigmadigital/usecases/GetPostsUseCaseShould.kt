package com.paradigmadigital.usecases

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.paradigmadigital.domain.db.Database
import com.paradigmadigital.domain.entities.Author
import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.domain.mappers.AuthorMapper
import com.paradigmadigital.domain.mappers.PostMapper
import com.paradigmadigital.repository.DataRepository
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import retrofit2.Retrofit

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class GetPostsUseCaseShould {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit private var useCase: GetPostsUseCase
    @Mock lateinit var retrofit: Retrofit

    private lateinit var database: Database

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        database = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application,
                Database::class.java)
                .allowMainThreadQueries()
                .build()

        database.postDao().insert(Post(id = 1, userId = 1, title = "title", body = "body"))
        database.authorDao().insert(Author(aid = 1, name = "name", email = "email"))

        val repository = DataRepository(database.postDao(), database.authorDao(), retrofit, AuthorMapper(), PostMapper())
        useCase = GetPostsUseCase(repository)
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun getPosts() {
        val posts = arrayListOf(PostUiModel(
                id = 1,
                title = "title",
                body = "body",
                name = "name",
                email = "email"
        ))

        useCase.execute()
                .test()
                .assertValue(posts)
    }

}
