(ns myapp.client.core
  (:require [reagent.dom.client :as rdom]
            [re-frame.core :as rf]
            [myapp.client.events]
            [myapp.client.subs]
            [myapp.client.views :as views]))

(defonce root (rdom/create-root (js/document.getElementById "app")))

(defn mount-root []
  (rdom/render root [views/main-panel]))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (rf/dispatch [:fetch-items])
  (mount-root))
