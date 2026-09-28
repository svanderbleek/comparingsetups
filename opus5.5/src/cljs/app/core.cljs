(ns app.core
  (:require [app.events :as events]
            [app.views :as views]
            [re-frame.core :as rf]
            [reagent.dom.client :as rdc]))

(defonce root (delay (rdc/create-root (.getElementById js/document "app"))))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (rdc/render @root [views/main-panel]))

(defn init []
  (rf/dispatch-sync [::events/initialize])
  (rf/dispatch [::events/fetch-items])
  (mount-root))
