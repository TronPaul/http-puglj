(ns http-puglj.user-test
  (:require [clojure.test :refer :all])
  (:use http-puglj.core
        ring.mock.request))

(deftest test-bad-user-id-404s
  (testing "A bad user id will return a 404"
    (with-redefs [org.httpkit.client/get (fn [a b] (future {:status 200 :body "{\"response\":{\"players\":[]}}"}))]
      (is (= 404 (:status (secured-routes (request :get "/user/1337"))))))))