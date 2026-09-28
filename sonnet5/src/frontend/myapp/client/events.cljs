(ns myapp.client.events
  (:require [re-frame.core :as rf]
            [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [myapp.client.db :as db]))

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-fx
 :fetch-items
 (fn [{:keys [db]} _]
   {:db         (assoc db :loading? true :error nil)
    :http-xhrio {:method          :get
                 :uri             "/api/items"
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [:fetch-items-success]
                 :on-failure      [:fetch-items-failure]}}))

(rf/reg-event-db
 :fetch-items-success
 (fn [db [_ items]]
   (assoc db :items items :loading? false)))

(rf/reg-event-db
 :fetch-items-failure
 (fn [db [_ error]]
   (assoc db :loading? false :error (str error))))
