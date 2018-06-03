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

// Seriously why is this still necessary with gradle-kotlin? Where's the codegen?
val kotlinFrontend = extensions.getByType(org.jetbrains.kotlin.gradle.frontend.KotlinFrontendExtension::class.java)
kotlinFrontend.apply {

}

tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile::class.java) {
    kotlinOptions {
        sourceMap = true
        moduleKind = "umd"
    }
}
