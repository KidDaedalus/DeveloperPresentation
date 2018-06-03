import org.jetbrains.kotlin.gradle.frontend.npm.NpmExtension
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackBundler

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



kotlinFrontend{
    downloadNodeJsVersion = "8.11.2"

    // In the groovy DSL this would just be "webpackBundle"
    // Seems like the kotlinJs does some dynamic goofiness here
    val webpackBundler = bundlers["webpack"] as WebPackBundler
    val webPackExtension = webpackBundler.createConfig(project)
    webPackExtension.apply {

    }

}

npm {
    devDependency("karma")
}


tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile::class.java) {
    kotlinOptions {
        sourceMap = true
        moduleKind = "umd"
    }
}
