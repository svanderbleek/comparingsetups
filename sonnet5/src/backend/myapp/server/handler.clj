(ns myapp.server.handler
  (:require [reitit.ring :as ring]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.util.response :as response]
            [jsonista.core :as json]
            [myapp.server.db :as db]))

(defn- json-response [data]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (json/write-value-as-string data)})

(defn- items-handler [_req]
  (json-response (db/list-items)))

(defn- health-handler [_req]
  (json-response {:status "ok"}))

(defn- index-handler [_req]
  (response/resource-response "index.html" {:root "public"}))

(def app
  (-> (ring/ring-handler
       (ring/router
        [["/" {:get index-handler}]
         ["/api/health" {:get health-handler}]
         ["/api/items" {:get items-handler}]])
       (ring/create-default-handler))
      (wrap-resource "public")
      wrap-content-type
      wrap-not-modified
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :post :put :delete])))
