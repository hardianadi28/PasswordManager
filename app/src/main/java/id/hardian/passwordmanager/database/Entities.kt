package id.hardian.passwordmanager.database

import androidx.room.*
import java.io.Serializable
import java.sql.Timestamp
import java.util.*

@Entity(tableName = "password_data")
data class PasswordData(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "password_name")
    var passwordName: String = "",
    @ColumnInfo(name = "password_url")
    var passwordUrl: String = ""
)

@Entity(tableName = "password_account")
data class PasswordAccount(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "account_username")
    var accountUsername: String ="",
    @ColumnInfo(name = "account_password")
    var accountPassword: String = "",
    @ColumnInfo(name = "password_id")
    val passwordId: Long = 0
) : Serializable

data class PasswordDataWithAccounts(
    @Embedded
    val data: PasswordData,
    @Relation(
        parentColumn = "id",
        entityColumn = "password_id"
    )
    val accounts: List<PasswordAccount>
)