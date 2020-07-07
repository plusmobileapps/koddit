import com.plusmobileapps.sharedcode.db.MyDatabase
import com.plusmobileapps.sharedcode.db.data.PostQueries
import com.squareup.sqldelight.Transacter

class DatabaseMock : MyDatabase {

    lateinit var whenGetPosts: () -> PostQueries

    override val postQueries: PostQueries
        get() = whenGetPosts()

    override fun transaction(noEnclosing: Boolean, body: Transacter.Transaction.() -> Unit) {
        TODO("Not yet implemented")
    }
}