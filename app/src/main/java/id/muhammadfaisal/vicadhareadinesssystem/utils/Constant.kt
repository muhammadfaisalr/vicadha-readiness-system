package id.muhammadfaisal.vicadhareadinesssystem.utils

class Constant {
    class Key {
        companion object {
            const val FOR_GROUP = "FOR_GROUP"
            const val BUNDLING = "BUNDLING"
            const val USER_NAME = "USER_NAME"
            const val USER_EMAIL = "USER_EMAIL"
            const val USER_PASSWORD = "USER_PASSWORD"
            const val ROLE_ID = "ROLE_ID"
            const val GROUP_ID = "GROUP_ID"
            const val GROUP_IMAGE = "GROUP_IMAGE"
            const val GROUP_NAME = "GROUP_NAME"
            const val INBOX_TITLE = "INBOX_TITLE"
            const val INBOX_CONTENT = "INBOX_CONTENT"
            const val INBOX_DATE = "INBOX_DATE"
            const val SUCCESS_TITLE = "SUCCESS_TITLE"
            const val IS_FIRST_OPEN = "IS_FIRST_OPEN"
            const val NO = "NO"
        }
    }

    class Role{
        companion object{
            const val SUPER_ADMIN = 2
            const val ADMIN = 1
            const val MEMBER = 0
        }
    }

    class MessageType {
        companion object {
            const val REGULAR = 0
            const val IMPORTANT = 1
        }
    }

    class RoleName{
        companion object{
            const val SUPER_ADMIN = "Super Admin"
            const val ADMIN = "Komandan"
            const val MEMBER = "Member"
        }
    }

    class GroupName{
        companion object{
            const val BAJRA_YUDHA_501 = "501 Bajra Yudha"
            const val UJWALA_YUDHA_502 = "502 Ujwala Yudha"
            const val MAYANGKARA_503 = "503 Mayangkara"
            const val BRIGIF_18 = "Brigif 18"
            const val DEPANDU_TAIKAM = "Depandu Taikam"
        }
    }

    class URL {
        companion object{
            const val FCM_GOOGLE = "https://fcm.googleapis.com/"
        }
    }

    class SharedPreference {
        companion object {
            const val NAME_OF_SHARED_PREFERENCE = "SHARED_VRS"
        }
    }
}