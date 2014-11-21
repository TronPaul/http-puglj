(ns http-puglj.player-not-found-exception
  (:gen-class :name http_puglj.PlayerNotFoundException
              :extends java.lang.Exception
              :init init
              :constructors {[Long] [String]}))

(defn -init [^Long steam-id]
  [[(str "Player with steam-id=" steam-id " not found")] nil])