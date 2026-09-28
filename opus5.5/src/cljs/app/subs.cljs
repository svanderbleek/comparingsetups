(ns app.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub ::items    (fn [db _] (:items db)))
(rf/reg-sub ::loading? (fn [db _] (:loading? db)))
(rf/reg-sub ::error    (fn [db _] (:error db)))
