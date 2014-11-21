(ns http-puglj.steam-test
  (:require [clojure.test :refer :all]
            [http-puglj.player-not-found-exception])
  (:use http-puglj.steam)
  (:import [http_puglj.PlayerNotFoundException]))

(deftest test-bad-player-id-throws-exception
  (testing "A bad player id throws a PlayerNotFoundException"
    (with-redefs [http/get (fn [a b] {:status 200 :body "{\"response\":{\"players\":[]}}"})]
      (is (thrown? PlayerNotFoundException ())))))
