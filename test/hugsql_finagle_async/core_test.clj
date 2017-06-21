(ns hugsql-finagle-async.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [finagle-clojure.mysql.client :as mysql]
            [hugsql.core :as hugsql]
            [hugsql-finagle-async.core :refer :all]))

(def mysql-config
  {:user     "root"
   :password "BEE38DF8AA2040C4"
   :db       "testDB"
   :endpoint "127.0.0.1:33060"})


(def Client
  (let [{:keys [user password db endpoint]} mysql-config]
    (-> (mysql/mysql-client)
        (mysql/with-credentials user password)
        (mysql/with-database db)
        (mysql/rich-client endpoint))))

(hugsql/def-db-fns
  (.getAbsolutePath (io/file "test/hugsql_finagle_async/fns.sql")))

(hugsql/set-adapter! (hugsql-adapter-finagle-async))


(deftest integrate-test
  (testing "FIXME, I fail."
    (is (= 0 1))))


(deftest still-have-asynchrounous-ability
  (testing "FIXME, I fail."
    (is (= 0 1))))
