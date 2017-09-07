(defproject hugsql-finagle-async "0.2.0-SNAPSHOT"
  :description "the asynchronous hugsql service"
  :url "http://cccc.im/"
  :license {:name "MIT License"
            :url  "http://www.opensource.org/licenses/mit-license.php"}
  :dev {:resource-paths ["test/resources"]}
  :dependencies [[org.clojure/clojure    "1.8.0"]
                 [org.clojure/core.async "0.3.443"]
                 [com.layerware/hugsql   "0.4.7"]
                 [finagle-clojure/mysql  "0.7.0"]
                 [org.clojure/java.jdbc  "0.7.1"]]
  :profiles {:debug {:debug true
                     :injections [(prn (into {} (System/getProperties)))]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.0"]]}
             ;; activated by default
             :dev {:dependencies [[com.h2database/h2 "1.4.196"]]}})
