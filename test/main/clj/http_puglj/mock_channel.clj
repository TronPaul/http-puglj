(ns http-puglj.mock-channel
  (:import [http_puglj MockAsyncChannel]))

(defprotocol MockChannel
  (sent [ch] "get messages sent by the mock channel")
  (receive [ch data] "put message into channel"))

(extend-type MockAsyncChannel
  MockChannel
  (sent [ch] (seq (.getSent ch)))
  (receive [ch data] (.messageReceived ch data)))

(defn mock-async-channel []
  (MockAsyncChannel.))