(ns main)

(def input (js/$ "#chat-input"))
(def history (js/$ "#chat-history"))

(defn add-msg [msg]
  (.append history (str "<li>" (.-msg msg) "</li>")))

(def conn
  (js/WebSocket. "ws://localhost:8080/ws"))

(set! (.-onerror conn)
  (fn []
    (js/alert "error")
    (.log js/console js/arguments)))

(set! (.-onmessage conn)
  (fn [e]
    (.log js/console (.-data e))
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