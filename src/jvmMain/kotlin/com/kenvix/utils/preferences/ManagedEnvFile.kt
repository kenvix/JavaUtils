//--------------------------------------------------
// Class ManagedEnvFile
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.android.preferences

import com.kenvix.utils.annotation.Description
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.slf4j.LoggerFactory
import sun.security.krb5.internal.crypto.Des
import java.io.Closeable
import java.io.File
import java.io.PrintStream
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaType

open class ManagedEnvFile(dirPath: Path = Paths.get(".")) : Iterable<Map.Entry<String, Any?>> {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val envFile: File = dirPath.resolve(".env").toFile()
    private val env: Dotenv = dotenv {
        directory = dirPath.toAbsolutePath().toString()
        ignoreIfMissing = true
    }

    operator fun get(key: String) = env[key]

    inline fun <reified T> get(key: String, defValue: T): T {
        return when (T::class) {
            String::class -> (this[key] ?: defValue) as T
            Int::class -> (this[key]?.toInt() ?: defValue) as T
            Long::class -> (this[key]?.toLong() ?: defValue) as T
            Boolean::class -> (this[key]?.toBoolean() ?: defValue) as T
            Double::class -> (this[key]?.toDouble() ?: defValue) as T
            Short::class -> (this[key]?.toShort() ?: defValue) as T
            Float::class -> (this[key]?.toFloat() ?: defValue) as T
            else -> throw IllegalArgumentException("Type not supported: ${T::class.qualifiedName}")
        }
    }

    fun save() {
        if (!envFile.exists()) {
            logger.info(".env file not exist, will create it")
            envFile.createNewFile()
        }

        val memberIter = this::class.declaredMemberProperties

        envFile.outputStream().use { stream ->
            PrintStream(stream).use { printStream ->
                memberIter.forEach {
                    if (it.visibility != KVisibility.PRIVATE) {
                        val value = it.getter.call(this)
                        val typeName = it.getter.returnType.javaType.typeName.run {
                            val index = lastIndexOf('.')
                            if (index < 0)
                                this
                            else
                                substring(index + 1)
                        }
                        val description = it.findAnnotation<Description>()?.message

                        printStream.printf("# %-7s %s  %s\n", typeName, it.name, description ?: "")
                        printStream.println("${it.name}=${value ?: ""}")
                    }
                }
            }

            stream.flush()
            logger.debug("Saved env file ${envFile.name}")
        }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> envOf(key: String, defValue: T): DelegatedEnvFile<T> {
        return object : DelegatedEnvFile<T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T = get(key, defValue)
        }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> envOf(defValue: T): DelegatedEnvFile<T> {
        return object : DelegatedEnvFile<T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T = get(property.name, defValue)
        }
    }

    interface DelegatedEnvFile<T> {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    }

    fun getAllItems(): Map<String, Any?> {
        val memberIter = this::class.declaredMemberProperties
        val map = mutableMapOf<String, Any?>()
        memberIter.forEach {
            if (it.visibility != KVisibility.PRIVATE )
                map[it.name] = it.getter.call(this)
        }

        return map
    }

    override fun iterator(): Iterator<Map.Entry<String, Any?>> = object : Iterator<Map.Entry<String, Any?>> {
        private val membersIter = getAllItems().iterator()

        override fun hasNext(): Boolean = membersIter.hasNext()
        override fun next(): Map.Entry<String, Any?> = membersIter.next()
    }
}