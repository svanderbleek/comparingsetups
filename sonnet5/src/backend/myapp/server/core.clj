(ns myapp.server.core
  (:require [ring.adapter.jetty :as jetty]
            [myapp.server.handler :refer [app]])
  (:gen-class))

(defn -main [& _args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (println (str "Starting server on http://localhost:" port))
    (jetty/run-jetty app {:port port :join? true})))
