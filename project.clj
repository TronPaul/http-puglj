(defproject http-puglj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [http-kit "2.1.16"]
                 [compojure "1.2.1"]
                 [selmer "0.7.2"]
                 [org.clojure/tools.logging "0.3.1"]
                 [cheshire "5.3.1"]
                 [com.cemerick/friend "0.2.1"]]
  :main ^:skip-aot http-puglj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler http-puglj.core/handler})
