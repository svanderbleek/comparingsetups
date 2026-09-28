(ns app.main
  (:require [app.db :as db]
            [app.server :as server])
  (:gen-class))

(defn -main [& _]
  (let [port (parse-long (or (System/getenv "PORT") "3000"))
        ds   (db/start-pool)]
    (db/migrate! ds)
    (server/start! ds {:port port})
    (println (str "Server running on http://localhost:" port))))
