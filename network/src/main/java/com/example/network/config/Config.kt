package com.example.network.config

object Config {
    fun getAuth(): String? = System.getenv("GITHUB_TOKEN")
}