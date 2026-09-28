(ns app.views
  (:require [app.events :as events]
            [app.subs :as subs]
            [re-frame.core :as rf]))

(defn items-table [items]
  [:table
   [:thead
    [:tr [:th "ID"] [:th "Name"] [:th "Description"] [:th "Created"]]]
   [:tbody
    (for [{:keys [id name description created_at]} items]
      ^{:key id}
      [:tr [:td id] [:td name] [:td description] [:td created_at]])]])

(defn main-panel []
  (let [items    @(rf/subscribe [::subs/items])
        loading? @(rf/subscribe [::subs/loading?])
        error    @(rf/subscribe [::subs/error])]
    [:main
     [:h1 "Items"]
     [:button {:on-click #(rf/dispatch [::events/fetch-items])
               :disabled loading?}
      (if loading? "Loading…" "Refresh")]
     (when error [:p.error error])
     (if (and (empty? items) (not loading?))
       [:p "No rows in the items table."]
       [items-table items])]))
