package rs.dusk.script

import rs.dusk.cache.Cache
import rs.dusk.utility.inject

val cache: Cache by inject()

println("Test script loaded with cache: $cache")