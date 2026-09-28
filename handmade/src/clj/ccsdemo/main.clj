(ns ccsdemo.main
  (:require [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hey"})

(defn -main [& _]
  (jetty/run-jetty #'handler {:port 3000 :join? true}))
