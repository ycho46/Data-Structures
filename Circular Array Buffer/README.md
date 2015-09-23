Circular Array Buffer
Buffers are extremely useful for buffering information coming into an application from an external source.
Two modes exists with this buffer
1. Regrow mode to regrow the buffer as it starts to fill so data is never lost
2. Overwrite mode to overwrite data at the front (removing the oldest data)

Primitive array is used as the backing storage.
