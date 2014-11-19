(ns http-puglj.chat-test
  (:require [clojure.test :refer :all]
            [cheshire.core :refer [parse-string generate-string]])
  (:use http-puglj.mock-channel
        http-puglj.core
        ring.mock.request))

(deftest user-send-message-test
  (testing "Can send a message when logged in"
    (with-redefs [cemerick.friend/identity (fn [_] {:current 76561197999483354, :authentications {76561197999483354 {:identity 76561197999483354}}})
                  http-puglj.steam/steam-name (fn [_] "TronPaul")]
      (let [data {:msg "Test message"}
            recv-message (generate-string data)
            sent-message (assoc data :name "TronPaul")
            async-channel (mock-async-channel)]
        (secured-routes (assoc (request :get "/ws") :async-channel async-channel))
        (receive async-channel recv-message)
        (is (= 2 (count (sent async-channel))))
        (is (= sent-message (parse-string (last (sent async-channel)) true)))))))

(deftest anon-send-message-fails-test
  (testing "Cannot send a message when anonymous"
    (let [message (generate-string {:msg "Test message"})
          async-channel (mock-async-channel)]
      (secured-routes (assoc (request :get "/ws") :async-channel async-channel))
      (receive async-channel message)
      (is (= 1 (count (sent async-channel)))))))