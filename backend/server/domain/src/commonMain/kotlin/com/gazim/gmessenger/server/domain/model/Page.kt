package com.gazim.gmessenger.server.domain.model


interface Page<Key, Data> {
    val data: Data
    val next: Key?
    val prev: Key?
}
