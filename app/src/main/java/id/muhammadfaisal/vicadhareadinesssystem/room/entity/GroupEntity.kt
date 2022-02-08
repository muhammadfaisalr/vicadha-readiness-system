package id.muhammadfaisal.vicadhareadinesssystem.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "m_group")
data class GroupEntity(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "parent_group") var parentGroup: String?,
    @ColumnInfo(name = "image") var image: Int,
    @ColumnInfo(name = "f_delete") var flagDelete: String,
    @ColumnInfo(name = "f_can_send_danger_message") var isCanSendDangerMessage: Boolean,
    @ColumnInfo(name = "f_admin_only") var isAdminOnly: Boolean
) {}