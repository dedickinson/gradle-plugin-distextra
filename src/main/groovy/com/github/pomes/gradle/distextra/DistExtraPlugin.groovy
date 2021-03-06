/*
 *    Copyright 2016 Duncan Dickinson
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.pomes.gradle.distextra

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.GroovyPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.bundling.Jar

import static org.gradle.api.plugins.GroovyPlugin.GROOVYDOC_TASK_NAME
import static org.gradle.api.plugins.JavaPlugin.*

class DistExtraPlugin implements Plugin<Project> {

    static final String TASK_GROUP_DOCUMENTATION = 'documentation'
    static final String TASK_GROUP_SOURCES = 'sources'

    //tasks
    static final String SOURCES_JAR_TASK_NAME = 'sourcesJar'
    static final String TEST_SOURCES_JAR_TASK_NAME = 'testSourcesJar'
    static final String JAVADOC_JAR_TASK_NAME = 'javadocJar'
    static final String GROOVYDOC_JAR_TASK_NAME = 'groovydocJar'

    //Maven classifiers
    static final String SOURCES_JAR_CLASSIFIER = 'sources'
    static final String TEST_SOURCES_JAR_CLASSIFIER = 'test-sources'
    static final String JAVADOC_JAR_CLASSIFIER = 'javadoc'
    static final String GROOVYDOC_JAR_CLASSIFIER = 'groovydoc'

    @Override
    void apply(Project project) {
        project.plugins.withType(JavaPlugin) {
            project.tasks.create(name: SOURCES_JAR_TASK_NAME, type: Jar) {
                group TASK_GROUP_SOURCES
                dependsOn CLASSES_TASK_NAME
                inputs.sourceDir project.sourceSets.main.allSource
                classifier SOURCES_JAR_CLASSIFIER
                from project.sourceSets.main.allSource
            }

            project.tasks.create(name: TEST_SOURCES_JAR_TASK_NAME, type: Jar) {
                group TASK_GROUP_SOURCES
                dependsOn TEST_CLASSES_TASK_NAME
                classifier TEST_SOURCES_JAR_CLASSIFIER
                inputs.sourceDir project.sourceSets.test.allSource
                from project.sourceSets.test.allSource
            }

            project.tasks.create(name: JAVADOC_JAR_TASK_NAME, type: Jar) {
                group TASK_GROUP_DOCUMENTATION
                dependsOn JAVADOC_TASK_NAME
                classifier JAVADOC_JAR_CLASSIFIER
                inputs.sourceDir project.javadoc.destinationDir
                from project.javadoc.destinationDir
            }
        }

        project.plugins.withType(GroovyPlugin) {
            project.tasks.create(name: GROOVYDOC_JAR_TASK_NAME, type: Jar) {
                group TASK_GROUP_DOCUMENTATION
                dependsOn GROOVYDOC_TASK_NAME
                classifier GROOVYDOC_JAR_CLASSIFIER
                inputs.sourceDir project.groovydoc.destinationDir
                from project.groovydoc.destinationDir
            }
        }
    }
}
