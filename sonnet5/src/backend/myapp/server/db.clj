(ns myapp.server.db
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]))

(defn- env [k default]
  (or (System/getenv k) default))

(def db-spec
  {:dbtype   "postgresql"
   :host     (env "PGHOST" "localhost")
   :port     (Integer/parseInt (env "PGPORT" "5432"))
   :dbname   (env "PGDATABASE" "myapp")
   :user     (env "PGUSER" "myapp")
   :password (env "PGPASSWORD" "myapp")})

(defonce ds (jdbc/get-datasource db-spec))

(defn list-items []
  (jdbc/execute! ds
                 ["SELECT id, name, description FROM items ORDER BY id"]
                 {:builder-fn rs/as-unqualified-lower-maps}))
