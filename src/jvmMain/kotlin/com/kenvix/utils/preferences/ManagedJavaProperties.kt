//--------------------------------------------------
// Class ManagedJavaProperties
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.preferences

import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties

/**
 * Managed java properties
 * @author Kenvix Zure
 * @param inputStream Input stream of java properties
 * @param outputStream Output stream of java properties, if null, this properties will be read only
 */
open class ManagedJavaProperties(val inputStream: InputStream, val outputStream: OutputStream? = null) {
    private val logger = LoggerFactory.getLogger(javaClass)
    val properties = Properties()
    private var file: File? = null

    /**
     * Initialize Managed java properties from a file
     */
    constructor(file: File): this(file.inputStream(), null) {
        this.file = file
    }

    /**
     * Initialize Managed java properties from a file name
     */
    constructor(fileName: String): this(
        kotlin.run {
            val logger = LoggerFactory.getLogger(ManagedJavaProperties::class.java)

            val file = File(fileName)
            logger.debug("Loading Properties: $file")

            if (!file.exists()) {
                file.createNewFile()
                logger.info("Created Java Properties: " + file.absolutePath)
            }

            file
        }
    )

    /**
     * Initialize readonly Managed java properties from a java resources file name
     */
    constructor(clazz: Class<*>, fileName: String):
            this(clazz.getResourceAsStream(fileName) ?: throw FileNotFoundException("Properties not found"))


    init {
        properties.load(inputStream)
    }

    fun loadAllItems() {
        this::class.declaredMemberProperties.forEach {
            if (it.visibility != KVisibility.PRIVATE && !properties.contains(it.name))
                set(it.name, it.getter.call(this))
        }
    }

    operator fun get(key: String) = properties.getProperty(key)

    inline fun <reified T> get(key: String, defValue: T): T {
        return when (T::class) {
            String::class -> properties.getProperty(key, defValue as String) as T
            Int::class -> properties.getProperty(key, defValue.toString()).toInt() as T
            Long::class -> properties.getProperty(key, defValue.toString()).toLong()  as T
            Boolean::class -> properties.getProperty(key, defValue.toString())!!.toBoolean() as T
            Double::class -> properties.getProperty(key, defValue.toString()).toDouble() as T
            Short::class -> properties.getProperty(key, defValue.toString()).toShort() as T
            Float::class -> properties.getProperty(key, defValue.toString()).toFloat() as T
            else -> throw IllegalArgumentException("Type not supported: ${T::class.qualifiedName}")
        }
    }

    inline operator fun <reified T> set(key: String, value: T) {
        when (T::class) {
            String::class -> properties.setProperty(key, value as String)
            else -> properties.setProperty(key, value.toString())
        }
    }

    fun save(description: String? = null, shouldPreloadAllItems: Boolean = true) {
        if (outputStream == null && file == null)
            throw IllegalArgumentException("Due to outputStream is null, this properties is readonly and cannot be saved")

        if (shouldPreloadAllItems)
            loadAllItems()

        if (file != null) {
            file!!.outputStream().use {
                properties.store(it, description)
                it.flush()
                logger.debug("Saved properties to file ${file!!.name}")
            }
        } else {
            properties.store(outputStream, description)
            outputStream!!.flush()
            logger.debug("Saved properties to stream")
        }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> propertyOf(key: String, defValue: T): DelegatedProperties<T> {
        return object : DelegatedProperties<T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T
                    = get(key, defValue)

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
                    = set(key, value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> propertyOf(defValue: T): DelegatedProperties<T> {
        return object : DelegatedProperties<T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T
                    = get(property.name, defValue)

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
                    = set(property.name, value)
        }
    }

    interface DelegatedProperties<T> {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): T
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
    }
}