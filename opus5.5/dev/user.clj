(ns user
  (:require [app.db :as db]
            [app.server :as server]))

(defonce ^:private state (atom nil))

(defn start []
  (let [ds (db/start-pool)]
    (db/migrate! ds)
    (reset! state {:ds ds :server (server/start! ds {:port 3000})})
    :started))

(defn stop []
  (when-let [{:keys [ds server]} @state]
    (.stop server)
    (.close ds)
    (reset! state nil))
  :stopped)

(defn restart [] (stop) (start))
