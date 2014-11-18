(ns http-puglj.chat-test
  (:require [clojure.test :refer :all])
  (:use
    http-puglj.core
    ring.mock.request))

(deftest user-send-message-test
  (testing "Can send a message when logged in"
    (with-redefs [cemerick.friend/identity (fn [_] {:current 76561197999483354, :authentications {76561197999483354 {:identity 76561197999483354}}})]
      (is (= nil (secured-routes (request :get "/ws")))))))