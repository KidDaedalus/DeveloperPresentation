import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.frontend.config.BundleConfig
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackBundler
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackExtension

buildscript {
	repositories {
		jcenter()
		maven {
            url = project.uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
	}
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.51")
		classpath("org.jetbrains.kotlin:kotlin-frontend-plugin:0.0.31")
    }
}

plugins.apply(org.jetbrains.kotlin.gradle.plugin.Kotlin2JsPluginWrapper::class.java)
plugins.apply(org.jetbrains.kotlin.gradle.frontend.FrontendPlugin::class.java)

// Get some errors in the console when including this plugin
//plugins.apply("kotlin-dce-js")
//plugins.apply("com.craigburke.karma")

repositories {
	jcenter()
	maven {
		url = project.uri("https://dl.bintray.com/kotlin/kotlin-eap")
	}
}

val kotlinVersion = "1.2.51"
dependencies {
    compile ("org.jetbrains.kotlin:kotlin-stdlib-js:$kotlinVersion")
	compile ("org.jetbrains.kotlinx:kotlinx-html-js:0.6.6")
    compile ("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:0.23.4")

    testCompile("org.jetbrains.kotlin:kotlin-test-js:$kotlinVersion")
}

kotlinFrontend{
    downloadNodeJsVersion = "8.11.3"
    sourceMaps = true

    // In the groovy DSL this would just be "webpackBundle"
    // KotlinFrontendExtension overrides dynamic Groovy's missing method behavior to turn "[bundler]Bundle()"
    // into an invocation to "bundle([bundler])" which is a real method
    //
    // So kind of like a poor-man's AST transformation
    // Jetbrains, this is so unnecessary. Why did you make me spend time figuring this out just to write a kotlin buildscript
    bundle<WebPackExtension>(WebPackBundler.bundlerId) {
        this as WebPackExtension
        contentPath = project.file("src/main/web")
        mode = "development"
    }
}
npm {
    dependency("two.js", "0.7.0-alpha.1")

    devDependency("qunit", "2.6.1")
    devDependency("karma", "2.0.4")
}

kotlin {
    experimental.coroutines =  org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE
}

val compileKotlin = tasks.getByName("compileKotlin2Js") {
    this as KotlinJsCompile
    kotlinOptions {
        sourceMap = true
        moduleKind = "umd"
    }
}
val compileTestKotiln = tasks.getByName("compileTestKotlin2Js") {
    this as KotlinJsCompile
    kotlinOptions {
        sourceMap = true
        moduleKind = "commonjs"
        sourceMapEmbedSources = "always"
    }
}

/**
 * Github pages can be configured to serve static content out of the root
 * It's a bit messy but expedient
 */
val publish = tasks.create("githubPagesPublish") {
    dependsOn(tasks.getByName("bundle"))

    doLast {
        copy {
            from("build/bundle")
            from("src/main/web")
            into(".")
        }
    }
}
