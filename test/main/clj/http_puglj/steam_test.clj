(ns http-puglj.steam-test
  (:require [clojure.test :refer :all])
  (:use http-puglj.steam)
  (:import (http_puglj.steam PlayerNotFoundException)))

(deftest test-bad-player-id-throws-exception
  (testing "A bad player id throws a PlayerNotFoundException"
    (with-redefs [org.httpkit.client/get (fn [a b] (future {:status 200 :body "{\"response\":{\"players\":[]}}"}))]
      (is (thrown? PlayerNotFoundException (persona-name (long 1337)))))))
