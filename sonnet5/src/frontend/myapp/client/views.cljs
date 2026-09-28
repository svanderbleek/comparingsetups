(ns myapp.client.views
  (:require [re-frame.core :as rf]))

(defn- items-table [items]
  [:table.items
   [:thead
    [:tr [:th "ID"] [:th "Name"] [:th "Description"]]]
   [:tbody
    (for [{:keys [id name description]} items]
      ^{:key id}
      [:tr [:td id] [:td name] [:td description]])]])

(defn main-panel []
  (let [items    @(rf/subscribe [:items])
        loading? @(rf/subscribe [:loading?])
        error    @(rf/subscribe [:error])]
    [:div.container
     [:h1 "Items"]
     (cond
       loading? [:p "Loading..."]
       error    [:p.error (str "Error: " error)]
       (empty? items) [:p "No items found."]
       :else    [items-table items])]))
