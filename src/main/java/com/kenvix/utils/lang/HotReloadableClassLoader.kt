package com.kenvix.utils.lang

import java.io.File
import java.net.URL
import java.net.URLClassLoader

@Suppress("MemberVisibilityCanBePrivate", "unused")
class HotReloadableURLClassLoader <CL: ClassLoader>(
    val parentLoader: CL,
    val enabledPackagePrefix: List<String>,
    val classPathUrls: Array<URL> = Thread.currentThread().contextClassLoader.run {
        if (this is URLClassLoader)
            this.urLs
        else
            emptyArray()
    }
) : ClassLoader() {

    class ExposedURLClassLoader(urls: Array<URL>) : URLClassLoader(urls) {
        public override fun addURL(url: URL?) {
            super.addURL(url)
        }

        public override fun findClass(name: String?): Class<*> {
            return super.findClass(name)
        }

        public override fun findClass(moduleName: String?, name: String?): Class<*> {
            return super.findClass(moduleName, name)
        }
    }

    var urlClassLoader: ExposedURLClassLoader
        private set

    init {
        urlClassLoader = ExposedURLClassLoader(classPathUrls)
    }

    @Throws(ClassNotFoundException::class)
    override fun findClass(name: String?): Class<*> {
        return urlClassLoader.findClass(name)
    }

    @Throws(ClassNotFoundException::class)
    override fun loadClass(name: String?): Class<*> {
        return if (name == null || enabledPackagePrefix.none { name.startsWith(it, true) }) {
            parentLoader.loadClass(name)
        } else {
            urlClassLoader.loadClass(name)
        }
    }

    fun addURL(url: URL?) {
        urlClassLoader.addURL(url)
    }

    fun addJarFile(file: File) {
        addURL(file.toURI().toURL())
    }

    fun reloadAll(doGarbageCollect: Boolean = false) {
        urlClassLoader = ExposedURLClassLoader(classPathUrls + urlClassLoader.urLs)

        if (doGarbageCollect)
            System.gc()
    }

    fun addJarDirectory(dir: File) {
        dir.listFiles()?.forEach(::addJarFile)
    }
}