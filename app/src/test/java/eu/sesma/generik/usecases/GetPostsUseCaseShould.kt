package eu.sesma.generik.usecases

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import eu.sesma.generik.domain.db.Database
import eu.sesma.generik.domain.entities.Author
import eu.sesma.generik.domain.entities.Post
import eu.sesma.generik.domain.entities.PostUiModel
import eu.sesma.generik.domain.mappers.AuthorMapper
import eu.sesma.generik.domain.mappers.PostMapper
import eu.sesma.generik.repository.DataRepository
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
