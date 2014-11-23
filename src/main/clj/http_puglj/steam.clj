(ns http-puglj.steam
  (:require [org.httpkit.client :as http]
            [cheshire.core :refer [parse-string]]
            [clojure.core.memoize :as memo])
  (:import (http_puglj.steam PlayerNotFoundException)))

(defonce api-key (atom nil))
(def summary-url "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/")
(def logs-tf-url "http://logs.tf/json_search")

(defn parse-identity [auth-map]
  {:identity (Long/parseLong (last (re-find #"http://steamcommunity.com/openid/id/(\d+)" (:identity auth-map))))})

(defn player-summary* [id]
  (let [resp @(http/get summary-url {:query-params {:key @api-key :steamids (str id)}})]
    (if (= 200 (:status resp))
      (if-let [ps (get-in (parse-string (:body resp) true) [:response :players 0])]
        ps
        (throw (PlayerNotFoundException. id)))
      (throw (RuntimeException. "Error retrieving GetPlayerSummaries")))))

(defn make-logs-tf-url [id]
  (str "http://logs.tf/profile/" id))

(defn logs-tf? [id]
  (let [resp @(http/get logs-tf-url {:query-params {:player (str id)}})]
    (if-let [logs (get (parse-string (:body resp) true) :logs)]
      (< 0 (count logs))
      false)))

(def player-summary (memo/lru player-summary* :lru/threshold 10))

(defn persona-name [id]
  (:personaname (player-summary id)))

(defn avatar [id]
  (:avatarfull (player-summary id)))

(defn profile-url [id]
  (:profileurl (player-summary id)))

