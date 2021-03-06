/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.fu.kofu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext
import org.springframework.context.support.ReloadableResourceBundleMessageSource

/**
 * @author Sebastien Deleuze
 */
class ApplicationDslTests {

	@Test
	fun `Create an empty application`() {
		val app = application(false) { }
		with(app) {
			run()
			assertFalse(context is ReactiveWebServerApplicationContext)
			context.getBean<ReloadableResourceBundleMessageSource>()
			stop()
		}
	}

	@Test
	fun `Create an application with a custom bean`() {
		val app = application(false) {
			beans {
				bean<Foo>()
			}
		}
		with(app) {
			run()
			context.getBean<ReloadableResourceBundleMessageSource>()
			context.getBean<Foo>()
			stop()
		}
	}

	@Test
	fun `Application configuration`() {
		val app = application(false) {
			configuration<City>("city")
		}
		with(app) {
			run()
			assertEquals(context.getBean<City>().name, "San Francisco")
			stop()
		}
	}


	class Foo

	// Switch to data classes when https://github.com/spring-projects/spring-boot/issues/8762 will be fixed
	class City {
		lateinit var name: String
		lateinit var country: String
	}

	data class TestConfiguration(
		val name: String
	)
}

