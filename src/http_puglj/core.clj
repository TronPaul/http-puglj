(ns http-puglj.core
  (:use [compojure.core :only [defroutes GET POST DELETE ANY context]]
        [compojure.handler :only [site]]
        [compojure.route :only [files not-found]]
        [selmer.parser :only [render-file]]
        org.httpkit.server)
  (:require [http-puglj.steam :as steam]
            [ring.middleware.reload :as reload]
            [clojure.tools.logging :as log]
            [cheshire.core :refer [parse-string generate-string]]
            [cemerick.friend :as friend]
            [cemerick.friend.openid :as openid]
            [ring.util.response :as resp]
            [clojurewerkz.propertied.properties :as p]
            [clojure.java.io :as io])
  (:import java.net.URI)
  (:gen-class :main true))

(defonce server (atom nil))
(defonce clients (atom {}))
(def steam-openid-url "http://steamcommunity.com/openid/")

(defn dev-mode? []
  true)

(defn template-base-variables [req]
  {:id (friend/identity req)
   :login-action "/login"
   :steam-openid-url steam-openid-url})

(defn index [req]
  (render-file "index.html" (template-base-variables req)))

(defn get-user-by-id [req]
  (let [steam-id (-> req :params :id)]
    (render-file "user.html" (merge (template-base-variables req) {:steam-id steam-id}))))

(defn msg-received [id ws-msg]
  (let [data (parse-string ws-msg true)]
    (if-let [chat-msg (:msg data)]
      (if id
        (doseq [client (keys @clients)]
          (send! client (generate-string {:msg chat-msg :name (steam/steam-name id)})))
        (log/warn "Unauthed attempted to send message")))))

(defn websocket [req]
  (let [id (friend/identity req)]
    (with-channel req channel
      (if (websocket? channel)
        (log/debug "Websocket channel")
        (log/debug "HTTP channel"))
      (swap! clients assoc channel true)
      (on-receive channel (partial msg-received id))
      (on-close channel (fn [status]
                          (log/info channel "closed, status" status)))
      (send! channel (generate-string {:msg "Connected to chat!"})))))

(defroutes all-routes
  (GET "/" [] index)
  (GET "/ws" [] websocket)
  (context "/user/:id" []
    (GET "/" [] get-user-by-id))
  (GET "/logout" req
    (friend/logout* (resp/redirect (str (:context req) "/"))))
  (files "" {:root "static"})
  (not-found "<p>Page not found.</p>"))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(def secured-routes
  (friend/authenticate
    all-routes
    {:default-landing-uri "/"
     :workflows [(openid/workflow
                   :openid-uri "/login"
                   :credential-fn steam/parse-identity)]}))

(def handler
  (if (dev-mode?)
    (reload/wrap-reload (site #'secured-routes))
    (site secured-routes)))

(defn -main
  "Start puglj"
  [& args]
  (if (< 0 (count args))
    (let [props-obj (p/load-from (io/file (first args)))
          props (p/properties->map props-obj true)]
      (reset! steam/api-key (:steam-api-key props))))
  (reset! server (run-server handler {:port 8080})))
