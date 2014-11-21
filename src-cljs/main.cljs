(ns main
  (:require [clojure.string :as s]))

(def input (js/$ "#chat-input"))
(def history (js/$ "#chat-history"))
(def ws-url (let [loc (.-location js/window)
                  protocol (if (= "https:" (.-protocol loc))
                         "wss:"
                         "ws:")
                  hostname (.-hostname loc)
                  port (.-port loc)]
              (if (or (and (= "ws:" protocol) (= 80 port))
                      (and (= "wss:" protocol (= 443 port))))
                (str protocol "//" hostname "/ws")
                (str protocol "//" hostname ":" port "/ws"))))

(defn add-msg [msg]
  (if (s/blank? (.-name msg))
    (.append history (str "<li>" "<i>" (.-msg msg) "</i>" "</li>"))
    (.append history (str "<li>" "<b>" (.-name msg) ":</b> " (.-msg msg) "</li>"))))

(def conn
  (js/WebSocket. ws-url))

(set! (.-onerror conn)
  (fn []
    (js/alert "error")
    (.log js/console js/arguments)))

(set! (.-onmessage conn)
  (fn [e]
    (let [new-msg (.parse js/JSON (.-data e))]
      (add-msg new-msg))))

(defn send-msg []
  (let [msg (.trim js/$ (.val input))]
    (if msg
      (do
        (.send conn (.stringify js/JSON (js-obj "msg" msg)))
        (.val input "")))))

(.click (js/$ "#send-message") send-msg)

(.keyup (.focus input)
  (fn [e]
    (if (= (.-which e) 13) (send-msg))))