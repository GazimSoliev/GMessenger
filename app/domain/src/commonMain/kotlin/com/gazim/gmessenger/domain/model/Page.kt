package com.gazim.gmessenger.domain.model

interface Page<Key, Data> {
    val data: Data
    val next: Key?
    val prev: Key?
}
