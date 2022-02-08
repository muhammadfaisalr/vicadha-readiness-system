package id.muhammadfaisal.vicadhareadinesssystem.utils

import android.media.MediaCodec
import android.util.Base64
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.media.MediaCodec.MetricsConstants.MODE

import java.lang.Exception


class Encrypter {
    companion object {
        private const val ALGORITHM = "Blowfish"
        private const val MODE = "Blowfish/CBC/PKCS5Padding"
        private const val IV = "abcdefgh"
        private const val KEY = "Encrypt-Db"

        fun encryptString(value: String?): String? {
            if (value == null || value.isEmpty()) return ""
            val secretKeySpec = SecretKeySpec(KEY.toByteArray(), ALGORITHM)
            var cipher: Cipher? = null
            try {
                cipher = Cipher.getInstance(MODE)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: NoSuchPaddingException) {
                e.printStackTrace()
            }
            try {
                cipher!!.init(Cipher.ENCRYPT_MODE, secretKeySpec, IvParameterSpec(IV.toByteArray()))
            } catch (e: InvalidAlgorithmParameterException) {
                e.printStackTrace()
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            }
            var values = ByteArray(0)
            try {
                values = cipher!!.doFinal(value.toByteArray())
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            }
            return Base64.encodeToString(values, Base64.DEFAULT)
        }

        fun decryptString(value: String?): String {
            if (value == null || value.isEmpty()) return ""
            var values: ByteArray? = ByteArray(0)
            try {
                values = Base64.decode(value, Base64.DEFAULT)
            } catch (e: Exception) {
                values = value.toByteArray()
                e.printStackTrace()
            }
            val secretKeySpec = SecretKeySpec(KEY.toByteArray(), ALGORITHM)
            var cipher: Cipher? = null
            try {
                cipher = Cipher.getInstance(MODE)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: NoSuchPaddingException) {
                e.printStackTrace()
            }
            try {
                cipher!!.init(Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(IV.toByteArray()))
            } catch (e: InvalidAlgorithmParameterException) {
                e.printStackTrace()
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            }
            try {
                return String(cipher!!.doFinal(values))
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            }
            return ""
        }

    }
}