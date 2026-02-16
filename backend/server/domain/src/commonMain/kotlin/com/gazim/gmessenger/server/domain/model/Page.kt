package com.gazim.gmessenger.server.domain.model

public interface Page<Key, Data> {
    public val data: Data
    public val next: Key?
    public val prev: Key?
}
