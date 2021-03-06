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

package org.springframework.fu.web.function

import org.springframework.fu.http.CoroutinesHttpInputMessage
import org.springframework.fu.http.codec.CoroutinesHttpMessageReader
import org.springframework.fu.http.server.CoroutinesServerHttpResponse
import org.springframework.http.ReactiveHttpInputMessage
import org.springframework.web.reactive.function.BodyExtractor

interface CoroutinesBodyExtractor<T, in M : CoroutinesHttpInputMessage> {

	suspend fun extract(inputMessage: M, context: Context): T

	fun <N : ReactiveHttpInputMessage> asBodyExtractor(): BodyExtractor<T, N> = TODO()

	interface Context {
		fun messageReaders(): (() -> Sequence<CoroutinesHttpMessageReader<*>>)

		fun serverResponse(): CoroutinesServerHttpResponse?

		fun hints(): Map<String, Any>
	}
}