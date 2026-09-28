(ns ccsdemo.core)

(defn init []
  (let [app (.getElementById js/document "app")]
    (set! app -innerHTML "Hey")))
