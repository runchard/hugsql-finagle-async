(defproject hugsql-finagle-async "0.1.0"
  :description "the asynchronous hugsql service"
  :url "http://cccc.im/"
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}
  :dev {:resource-paths ["test/resources"]}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.3.443"]
                 [com.layerware/hugsql "0.4.7"]
                 [finagle-clojure/mysql "0.7.0"]])
