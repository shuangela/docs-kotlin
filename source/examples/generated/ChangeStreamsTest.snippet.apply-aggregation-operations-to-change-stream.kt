val pipeline = listOf(
    Aggregates.match(Filters.`in`("operationType",
        listOf("insert", "update")))
)

// Launch the change stream in a separate coroutine,
// so you can cancel it later.
val job = launch {
    val changeStream = collection.watch(pipeline)
    changeStream.collect {
        println("Received a change event: $it")
    }
}

// Perform MongoDB operations that trigger change events...

// Cancel the change stream when you're done listening for events.
job.cancel()
