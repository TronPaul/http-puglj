(ns http-puglj.mock-channel
  (:import [http_puglj MockAsyncChannel]))

(defprotocol MockChannel
  (sent [ch] "get message sent by the mock channel"))

(extend-type MockAsyncChannel
  MockChannel
  (sent [ch] (seq (.getSent ch))))

(defn mock-async-channel []
  (MockAsyncChannel.))