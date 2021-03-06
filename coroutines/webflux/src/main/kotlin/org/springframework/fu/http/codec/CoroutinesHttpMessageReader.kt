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

package org.springframework.fu.http.codec

import kotlinx.coroutines.channels.ReceiveChannel
import org.springframework.core.ResolvableType
import org.springframework.fu.http.CoroutinesHttpInputMessage
import org.springframework.fu.http.server.CoroutinesServerHttpResponse
import org.springframework.fu.http.server.coroutine.CoroutinesServerHttpRequest
import org.springframework.http.MediaType

interface CoroutinesHttpMessageReader<out T> {

	fun canRead(elementType: ResolvableType, mediaType: MediaType): Boolean

	fun read(
			elementType: ResolvableType,
			message: CoroutinesHttpInputMessage,
			hints: Map<String, Any>
	): ReceiveChannel<T>

	suspend fun readSingle(elementType: ResolvableType, message: CoroutinesHttpInputMessage, hints: Map<String, Any>): T?

	suspend fun readSingle(
			actualType: ResolvableType, elementType: ResolvableType, request: CoroutinesServerHttpRequest,
			response: CoroutinesServerHttpResponse?, hints: Map<String, Any>
	): T? =
		readSingle(elementType, request, hints)
}