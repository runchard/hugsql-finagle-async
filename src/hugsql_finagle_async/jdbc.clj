(ns hugsql-finagle-async.jdbc
  (:require [clojure.core.async :as a]
            [hugsql.adapter :as adapter]
            [clojure.java.jdbc :as jdbc]
            [clojure.string :as str])
  (:gen-class))


(defn- get-one [result-chan]
  (let [return-chan (a/promise-chan)]
    (a/take! result-chan
             (fn [res]
               (let [return (if (instance? Exception res)
                              res
                              (first res))]
                 (if (nil? return)
                   (a/close! return-chan)
                   (a/put! return-chan return
                           (fn [_]
                             (a/close! result-chan)))))))
    return-chan))

(deftype HugsqlAdapterClojureAsyncJdbc []

  adapter/HugsqlAdapter
  (execute [this db sqlvec options]
    (let [return-chan (a/promise-chan)]
      (try
        (let [ret (jdbc/execute!
                   db
                   sqlvec
                   (merge {:returning (some #(= % (:command options))
                                            [:insert :i!])}
                          (:command-options options)))]
          (a/put! return-chan ret))
        (catch Exception e
          (a/put! return-chan e)
          (a/close! return-chan)))
      return-chan))

  (query [this db sqlvec options]
    (let [return-chan (a/promise-chan)]
      (a/put! return-chan
              (jdbc/query db sqlvec (:command-options options)))
      return-chan))

  (result-one [this result options]
    (get-one result))

  (result-many [this result options]
    result)

  (result-affected [this result options]
    result)

  (result-raw [this result options]
    result)

  (on-exception [this exception]
    (let [c (a/promise-chan)]
      (a/put! c exception)
      c)))

(defn hugsql-adapter-clojure-async-jdbc []
  (->HugsqlAdapterClojureAsyncJdbc))
