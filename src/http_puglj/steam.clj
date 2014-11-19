(ns http-puglj.steam
  (:require [org.httpkit.client :as http]
            [cheshire.core :refer [parse-string]]
            [clojure.tools.logging :as log]))

(defonce api-key (atom nil))
(def summary-url "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/")

(defn steam-name [id]
  (let [resp @(http/get summary-url {:query-params {:key @api-key :steamids (str id)}})]
    (if (= 200 (:status resp))
      (:personaname (get-in (parse-string (:body resp) true) [:response :players 0]))
      (throw (RuntimeException. "Error retrieving name")))))