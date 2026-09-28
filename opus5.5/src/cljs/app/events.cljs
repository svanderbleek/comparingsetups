(ns app.events
  (:require [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [re-frame.core :as rf]))

(rf/reg-event-db
 ::initialize
 (fn [_ _]
   {:items [] :loading? false :error nil}))

(rf/reg-event-fx
 ::fetch-items
 (fn [{:keys [db]} _]
   {:db         (assoc db :loading? true :error nil)
    :http-xhrio {:method          :get
                 :uri             "/api/items"
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::fetch-items-success]
                 :on-failure      [::fetch-items-failure]}}))

(rf/reg-event-db
 ::fetch-items-success
 (fn [db [_ items]]
   (assoc db :items items :loading? false)))

(rf/reg-event-db
 ::fetch-items-failure
 (fn [db [_ {:keys [status status-text]}]]
   (assoc db :loading? false :error (str "Failed to load items (" status " " status-text ")"))))
