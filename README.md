# hugsql-finagle-async

The difference is that this adapter returns core.async channels with the result or nil with an exception stack log.

## Usage
quick example:

```clojure
;; (:require [clojure.core.async :as async]  ; if in ns
;;           [finagle-clojure.mysql.client :as mysql]
;;           [hugsql.core :as hugsql]
;;           [hugsql-finagle-async.core :refer hugsql-adapter-finagle-async])
;; ;; or
;; (require '[clojure.core.async :as async])
;; (require '[finagle-clojure.mysql.client :as mysql])
;; (require '[hugsql.core :as hugsql])
;; (require '[hugsql-finagle-async.core :refer hugsql-adapter-finagle-async])
;;
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


(hugsql/set-adapter! (hugsql-adapter-finagle-async))

(hugsql/def-db-fns-from-string "-- :name select-character-by :query :1\n
   SELECT * FROM characters WHERE weight = :weight;")

(async/<!! (select-by-weight Client {:weight 0}))

```

for more kind of usage, reference the unittest.

## License

Copyright © 2017 Richard Wong

Distributed under the MIT license.
