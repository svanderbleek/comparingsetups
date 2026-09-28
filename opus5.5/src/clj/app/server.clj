(ns app.server
  (:require [app.db :as db]
            [clojure.java.io :as io]
            [muuntaja.core :as m]
            [reitit.ring :as ring]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [ring.adapter.jetty :as jetty]))

(defn- index-handler [_]
  {:status  200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body    (slurp (io/resource "public/index.html"))})

(defn- items-handler [ds]
  (fn [_]
    {:status 200
     :body   (db/list-items ds)}))

(defn app [ds]
  (ring/ring-handler
   (ring/router
    [["/" {:get index-handler}]
     ["/api/items" {:get (items-handler ds)}]]
    {:data {:muuntaja   m/instance
            :middleware [muuntaja/format-middleware]}})
   (ring/routes
    (ring/create-resource-handler {:path "/"})
    (ring/create-default-handler))))

(defn start! [ds {:keys [port]}]
  (jetty/run-jetty (app ds) {:port port :join? false}))
