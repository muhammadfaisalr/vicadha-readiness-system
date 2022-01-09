package id.muhammadfaisal.vicadhareadinesssystem.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "m_user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "phone_number") var phoneNumber: Long,
    @ColumnInfo(name = "group_name") var groupName: String?,
    @ColumnInfo(name = "role_id") var roleId: Int,
    @ColumnInfo(name = "f_delete", defaultValue = "N") var flagDelete: String?
) {}