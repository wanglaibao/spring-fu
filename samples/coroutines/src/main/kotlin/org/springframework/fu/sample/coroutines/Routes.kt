package org.springframework.fu.sample.coroutines

import org.springframework.fu.web.function.server.coRouter

fun routes(userHandler: UserHandler) = coRouter {
	GET("/", userHandler::listView)
	GET("/api/user", userHandler::listApi)
	GET("/conf", userHandler::conf)
}

