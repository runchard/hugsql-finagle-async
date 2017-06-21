(ns hugsql-finagle-async.core
  (:require [clojure.core.async :as a]
            [hugsql.adapter :as adapter]
            [finagle-clojure.mysql.client :as mysql]
            [finagle-clojure.futures :as f]
            [clojure.string :as str])
  (:import (com.twitter.finagle.mysql Client))
  (:gen-class))

(def ^:private ^:const params-regex #"(?<!\")\?(?!\")")

(defn- get-one [result-chan]
  (let [return-chan (a/promise-chan)]
    (a/take! result-chan
             (fn [res]
               (let [return (if (instance? Exception res)
                              res
                              (first res))]
                 (if (nil? return)
                   (a/close! return-chan)
                   (a/put! return-chan return #(a/close! return-chan))))))
    return-chan))

(defn query!
  [^Client rich-client
   sql-vec]
  (let [pret (a/promise-chan)]
    (-> (mysql/prepare rich-client (first sql-vec))
        (mysql/select-stmt (rest sql-vec))
        (f/on-success
         [result]
         (a/put! pret result))
        (f/on-failure
         [e]
         (a/close! pret)))
    pret))

(defn execute!
  [^Client rich-client
   sql-vec]
  (let [pret (a/promise-chan)]
    (-> (mysql/prepare rich-client (first sql-vec))
        ;; (rest '(1 2 3 4))
        (mysql/select-stmt (rest sql-vec))
        (f/on-success
         [result]
         (a/put! pret result))
        (f/on-failure
         [e]
         (a/close! pret)))
    pret))

(deftype HugsqlAdapterFinagleAsync []
  adapter/HugsqlAdapter
  (execute [this db sqlvec options]
    ;; what did the options for?
    (execute! db sqlvec))

  (query [this db sqlvec options]
    ;; what did the options for?
    (query! db sqlvec))

  (result-one [this result options]
    (get-one result))

  (result-many [this result options]
    result)

  (result-affected [this result options]
    (get-one result))

  (result-raw [this result options]
    result)

  (on-exception [this exception]
    (let [c (a/chan)]
      (a/put! c exception #(a/close! c))
      c)))

(defn hugsql-adapter-finagle-async []
  (->HugsqlAdapterFinagleAsync))
