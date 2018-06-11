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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.41")
		classpath("org.jetbrains.kotlin:kotlin-frontend-plugin:0.0.30")
    }
}
plugins.apply(org.jetbrains.kotlin.gradle.frontend.FrontendPlugin::class.java)
plugins.apply("kotlin-dce-js")
plugins.apply(org.jetbrains.kotlin.gradle.plugin.Kotlin2JsPluginWrapper::class.java)

repositories {
	jcenter()
	maven {
		url = project.uri("https://dl.bintray.com/kotlin/kotlin-eap")
	}
}

val kotlinVersion = "1.2.41"
dependencies {
    "compile" ("org.jetbrains.kotlin:kotlin-stdlib-js:$kotlinVersion")
	"compile" ("org.jetbrains.kotlin:kotlin-test-js:$kotlinVersion")
	"compile" ("org.jetbrains.kotlinx:kotlinx-html-js:0.6.6")
}


npm {
    dependency("two.js", "0.7.0-alpha.1")
    devDependency("karma")
}

kotlinFrontend{
    downloadNodeJsVersion = "8.11.2"
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

tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile::class.java) {
    kotlinOptions {
        sourceMap = true
        moduleKind = "commonjs"
    }
}
