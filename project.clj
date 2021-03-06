(defproject http-puglj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [http-kit "2.1.18"]
                 [compojure "1.2.1"]
                 [ring/ring-core "1.3.1"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring/ring-devel "1.3.1"]
                 [selmer "0.7.2"]
                 [cheshire "5.3.1"]
                 [clojurewerkz/propertied "1.2.0"]
                 [com.cemerick/friend "0.2.1"]
                 [org.clojure/core.memoize "0.5.6"]
                 [org.clojure/core.cache "0.6.3"]
                 [org.clojure/tools.logging "0.3.1"]]
  :java-source-paths ["src/main/java"]
  :source-paths ["src" "src/main/clj"]
  :test-paths ["test" "test/main/clj"]
  :profiles {:test {:java-source-paths ["test/main/java"]
                    :dependencies [[ring-mock "0.1.5"]]}}
  :target-path "target/%s"
  :plugins [[lein-cljsbuild "1.0.3"]]
  :cljsbuild {
               :builds [{:source-paths ["src-cljs"]
                         :compiler {:output-to "static/main.js"
                                    :optimizations :whitespace
                                    :pretty-print true}}]}
  :main http-puglj.core)
