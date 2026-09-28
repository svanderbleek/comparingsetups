(ns app.db
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [next.jdbc :as jdbc]
            [next.jdbc.connection :as connection]
            [next.jdbc.result-set :as rs])
  (:import (com.zaxxer.hikari HikariDataSource)))

(defn db-spec []
  {:dbtype   "postgresql"
   :host     (or (System/getenv "DB_HOST") "localhost")
   :port     (parse-long (or (System/getenv "DB_PORT") "5432"))
   :dbname   (or (System/getenv "DB_NAME") "clj_app")
   :username (or (System/getenv "DB_USER") (System/getProperty "user.name"))
   :password (or (System/getenv "DB_PASSWORD") "")})

(defn start-pool ^HikariDataSource []
  (connection/->pool HikariDataSource (db-spec)))

(defn migrate!
  "Run resources/schema.sql — idempotent: creates and seeds the items table if needed."
  [ds]
  (doseq [stmt (-> (io/resource "schema.sql") slurp (str/split #";\s*\n"))
          :when (not (str/blank? stmt))]
    (jdbc/execute! ds [stmt])))

(defn list-items [ds]
  (jdbc/execute! ds
                 ["SELECT id, name, description, created_at FROM items ORDER BY id"]
                 {:builder-fn rs/as-unqualified-maps}))
