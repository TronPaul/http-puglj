(ns http-puglj.core
  (:gen-class)
  (:use [compojure.core :only [defroutes GET POST DELETE ANY context]]
        [compojure.handler :only [site]]
        [compojure.route :only [not-found]]
        [selmer.parser :only [render-file]]
        org.httpkit.server)
  (:require [ring.middleware.reload :as reload]))

(defn dev-mode? []
  true)

(defonce server (atom nil))

(defn index [req]
  (render-file "index.html" {}))

(defn websocket [req]
  nil)

(defn get-user-by-id [req]
  "")

(defroutes all-routes
  (GET "/" [] index)
  (GET "/ws" [] websocket)
  (context "/user/:id" []
    (GET "/" [] get-user-by-id))
  (not-found "<p>Page not found.</p>"))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(def handler
  (if (dev-mode?)
    (reload/wrap-reload (site #'all-routes))
    (site all-routes)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (reset! server (run-server handler {:port 8080})))
