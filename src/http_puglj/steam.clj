(ns http-puglj.steam
  (:require [org.httpkit.client :as http]
            [cheshire.core :refer [parse-string]]
            [clojure.tools.logging :as log]))

(defonce api-key (atom nil))
(def summary-url "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/")

(defn parse-identity [auth-map]
  {:identity (Long/parseLong (last (re-find #"http://steamcommunity.com/openid/id/(\d+)" (:identity auth-map))))})

(defn player-summary [id]
  (let [resp @(http/get summary-url {:query-params {:key @api-key :steamids (str id)}})]
    (if (= 200 (:status resp))
      (get-in (parse-string (:body resp) true) [:response :players 0])
      (throw (RuntimeException. "Error retrieving GetPlayerSummaries")))))

(defn steam-name [id]
  (:personaname (player-summary id)))