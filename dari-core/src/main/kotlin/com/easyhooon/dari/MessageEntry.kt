package com.easyhooon.dari

/**
 * Data model representing the full lifecycle of a single bridge message.
 * A request and its response are grouped into one entry.
 *
 * @property id Auto-generated unique identifier for list key and entry lookup.
 * @property requestId Optional external request ID for matching request-response pairs.
 *                     When null, the entry is treated as a standalone (fire-and-forget) message.
 */
data class MessageEntry(
    val id: Long = 0L,
    val requestId: String? = null,
    val handlerName: String,
    val direction: MessageDirection,
    val requestData: String? = null,
    val responseData: String? = null,
    val status: MessageStatus = MessageStatus.IN_PROGRESS,
    val requestTimestamp: Long = System.currentTimeMillis(),
    val responseTimestamp: Long? = null,
) {
    val durationMs: Long?
        get() = responseTimestamp?.let { it - requestTimestamp }

    /** Total byte size of request + response data */
    val totalSizeBytes: Int
        get() {
            val requestSize = requestData?.toByteArray(Charsets.UTF_8)?.size ?: 0
            val responseSize = responseData?.toByteArray(Charsets.UTF_8)?.size ?: 0
            return requestSize + responseSize
        }
}
