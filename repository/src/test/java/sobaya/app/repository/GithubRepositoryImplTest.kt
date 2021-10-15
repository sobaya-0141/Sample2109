package sobaya.app.repository

import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import sobaya.app.network.data_source.GithubDataSource

class GithubRepositoryImplTest : TestCase() {
    private val testDispatcher = TestCoroutineDispatcher()
    private val githubDataSource: GithubDataSource = mockk()
    private val repository: GithubRepository = GithubRepositoryImpl(githubDataSource, testDispatcher)

    public override fun setUp() {
        super.setUp()
    }

    public override fun tearDown() {}

    @Test
    fun testGetUsers() {
        testDispatcher.runBlockingTest {

        }
    }

    fun testGetUSerDetail() {}
}
