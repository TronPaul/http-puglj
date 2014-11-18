(defproject http-puglj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [compojure "1.2.1"]
                 [ring/ring-core "1.3.1"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring/ring-devel "1.3.1"]
                 [http-kit "2.1.18"]
                 [selmer "0.7.2"]
                 [org.clojure/tools.logging "0.3.1"]
                 [cheshire "5.3.1"]
                 [com.cemerick/friend "0.2.1"]]
  :target-path "target/%s"
  :plugins [[lein-cljsbuild "1.0.3"]]
  :cljsbuild {
               :builds [{:source-paths ["src-cljs"]
                         :compiler {:output-to "static/main.js"
                                    :optimizations :whitespace
                                    :pretty-print true}}]}
  :main http-puglj.core)
