(ns http-puglj.chat-test
  (:require [clojure.test :refer :all]
            [cheshire.core :refer [generate-string]])
  (:use http-puglj.mock-channel
        http-puglj.core
        ring.mock.request)
  (:import [http_puglj MockAsyncChannel]))

(deftest user-send-message-test
  (testing "Can send a message when logged in"
    (with-redefs [cemerick.friend/identity (fn [_] {:current 76561197999483354, :authentications {76561197999483354 {:identity 76561197999483354}}})]
      (let [message (generate-string {:msg "Test message"})
            async-channel (mock-async-channel)]
        (secured-routes (assoc (request :get "/ws") :async-channel async-channel))
        (.messageReceived async-channel message)
        (is (= 2 (count (sent async-channel))))
        (is (= message (last (sent async-channel))))))))

(deftest anon-send-message-fails-test
  (testing "Cannot send a message when anonymous"
    (let [message (generate-string {:msg "Test message"})
          async-channel (mock-async-channel)]
      (secured-routes (assoc (request :get "/ws") :async-channel async-channel))
      (.messageReceived async-channel message)
      (is (= 1 (count (sent async-channel)))))))